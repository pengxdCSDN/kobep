package com.xd.kobepgateway.constant;

/**
 * @description: oauth2常量
 * @author: pxd
 * @create: 2018-11-22 11:30
 **/
public final  class Oauth2Constant {

    //请求oauth/token接口的请求头
    public static final String OAUTH_BASIC = "Basic d3AtdXNlcnNlcnZpY2U6d2VlcGFs";


    //授权模式-密码模式
    public static final String OAUTH_PASSWORD = "password";

    //授权模式-刷新token
    public static final String OAUTH_REFRESH_TOKEN = "refresh_token";


    //授权模式BCRYPT密码前缀
    public static final String BCRYPT_PREFIX = "{bcrypt}";


    //请求头 Bearer前缀
    public static final String HEAR_BEARER_PREFIX = "Bearer ";

    //请求头
    public static final String AUTHORIZATION_HEARER = "Authorization";





}
