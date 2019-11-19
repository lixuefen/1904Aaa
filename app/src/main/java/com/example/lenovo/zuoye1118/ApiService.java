package com.example.lenovo.zuoye1118;



import com.example.lenovo.zuoye1118.beans.LoginResultBean;

import io.reactivex.Observable;
import okhttp3.ResponseBody;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface ApiService {
    public static String LOGIN_URL = "https://www.wanandroid.com/";

    @POST("user/login")
    @FormUrlEncoded
    Observable<LoginResultBean> login(@Field("username") String username, @Field("password") String password);

}
