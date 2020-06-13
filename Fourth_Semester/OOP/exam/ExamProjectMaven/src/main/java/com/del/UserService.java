package com.del;

public class UserService {
    private UserDatabase userDatabase;

    public String getUserInfo(String username) {
        String infoInCache = Cache.getInstance().get(username);
        //if no user info in cache
        if(infoInCache == null) {
            //then load info from DB
            infoInCache = userDatabase.getInfo(username);
            //put info in cache
            Cache.getInstance().put(username, infoInCache);
        }
        return infoInCache;
    }
}
