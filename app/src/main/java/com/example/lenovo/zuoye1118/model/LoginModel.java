package com.example.lenovo.zuoye1118.model;



import com.example.lenovo.zuoye1118.ApiService;
import com.example.lenovo.zuoye1118.beans.LoginResultBean;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class LoginModel {

    private Disposable mDis;

    public void login(String username, String passworld, final LoginCallBack loginCallBack) {
        //网络请求
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(ApiService.LOGIN_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build();
        ApiService apiService = retrofit.create(ApiService.class);
        Observable<LoginResultBean> observable = apiService.login(username, passworld);
        observable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<LoginResultBean>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        mDis = d;

                    }

                    @Override
                    public void onNext(LoginResultBean loginResultBean) {
                        loginCallBack.onSuccess(loginResultBean);//把网络请求的解析后的对象回传到p层

                    }

                    @Override
                    public void onError(Throwable e) {
                        loginCallBack.onFail(e.getMessage());
                    }

                    @Override
                    public void onComplete() {

                    }
                });

    }

    public void dispose() {
        mDis.dispose();//停掉网络请求
    }

    public interface LoginCallBack{
        void onSuccess(LoginResultBean loginResultBean);
        void onFail(String str);
    }
}
