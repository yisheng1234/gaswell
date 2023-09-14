package com.gaswell.utils;


import com.gaswell.pojo.User;

public class UserThreadLocal {

    private static final ThreadLocal<User> LOCAL=new ThreadLocal<>();
    public static void put(User sysUser){
        LOCAL.set(sysUser);
    }
    public static User get(){
        return LOCAL.get();
    }
    public static void remove(){
        LOCAL.remove();
    }
}
