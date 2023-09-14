package com.gaswell.utils;

public class DynamicTableNameThreadLocal {
    private static final ThreadLocal<String> LOCAL=new ThreadLocal<>();
//    private static final ThreadLocal<String> LOCAL1=new ThreadLocal<>();
    public static void put(String dateAndywbh){
        LOCAL.set(dateAndywbh);
    }
    public static String get(){
        return LOCAL.get();
    }
    public static void remove(){
        LOCAL.remove();
    }
}
