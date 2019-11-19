package com.example.lenovo.zuoye1118.Presenter;


import com.example.lenovo.zuoye1118.beans.LoginResultBean;
import com.example.lenovo.zuoye1118.model.LoginModel;
import com.example.lenovo.zuoye1118.view.LoginView;

public class LoginPresenter implements LoginModel.LoginCallBack{
    private LoginView loginView;
    private LoginModel loginModel;

    public LoginPresenter(LoginView loginView) {
        this.loginView = loginView;
        loginModel = new LoginModel();
    }

    public void login() {
        //获得用户名和密码
        String username = loginView.getUname();
        String passworld = loginView.getUpass();

        if(!"".equals(username) && !"".equals(passworld)){//用户名和密码都不为空，进行登录请求
            loginModel.login(username,passworld,this);
        }else {//有一个为null,不能登录
            loginView.showToast("用户名和密码不能为空，请检查!");
        }
    }

    @Override
    public void onSuccess(LoginResultBean loginResultBean) {
        if(loginResultBean != null){
            if(loginResultBean.getErrorCode() == 0){//账号和密码正确，登录成功，跳转到主页
                loginView.toMainActivity();
            }else {
                loginView.showToast(loginResultBean.getErrorMsg());//登录失败，把服务器传回的失败信息，直接显示给用户
            }
        }
    }

    @Override
    public void onFail(String str) {//登录请求失败，执行显示服务项目的失败信息
        loginView.showToast(str);
    }

    public void destroy() {
        if(loginView != null){
            loginView = null;
        }
        if (loginModel != null) {
            loginModel = null;
        }
        //停止网络请求
        loginModel.dispose();
    }


}
