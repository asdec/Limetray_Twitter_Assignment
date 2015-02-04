package com.iwillcode.limetraytwitterassignment.Utilities;


public class Constants {

    public static final int LOGIN_TO_TWITTER = 1;


    private static final String NAMESPACE = "com.iwillcode.limetraytwitterassignment";


    public static final String EXTRA_CALLBACK_URL_KEY = NAMESPACE + ".extra.callbackUrlKey";
    public static final String EXTRA_AUTH_URL_KEY = NAMESPACE + ".extra.authUrlKey";

    public static final String URL_ROOT_TWITTER_API = "https://api.twitter.com";
    public static final String URL_SEARCH = URL_ROOT_TWITTER_API + "/1.1/search/tweets.json?q=";
    public static final String URL_AUTHENTICATION = URL_ROOT_TWITTER_API + "/oauth2/token";


}
