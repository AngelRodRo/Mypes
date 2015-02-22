package com.example.angel.mypes;

public interface Config {

	// used to share GCM regId with application server - using node app server
	static final String APP_SERVER_CATEGORY_PLACES = "http://mypesapp.evennode.com/mypes/listcategorymypes";
    static final String APP_SERVER_ID_PLACE = "http://mypesapp.evennode.com/mypes/searchforid";
    static final String APP_SERVER_REGISTER_USER = "http://mypesapp.evennode.com/users/adduser";
    static final String APP_SERVER_LOGIN_USER = "http://mypesapp.evennode.com/users/loginuser";
    static final String APP_SERVER_SEARCH_REQUEST = "http://mypesapp.evennode.com/mypes/search";

}
