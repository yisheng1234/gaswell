package com.gaswell.utils;

public class DeletePrefixUtils {
    public static String deleteDown(String str){
        return str.replace("down_","");
    }
    public static  String deleteUp(String str){
        return str.replace("up_","");
    }
}
