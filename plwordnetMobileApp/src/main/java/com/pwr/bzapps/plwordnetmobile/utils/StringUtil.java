package com.pwr.bzapps.plwordnetmobile.utils;

public class StringUtil {
    public static String findValueForArgumentName(String[] arguments, String name){
        for(int i=0; i<arguments.length; i++){
            if(arguments[i].startsWith(name+":")){
                String value = arguments[i].substring(arguments[i].indexOf(":")+1);
                if(value.equals("null"))
                    return null;
                return value;
            }
        }
        return null;
    }

    public static String parseIntegerArrayToString(Integer[] array){
        String string = "";
        for(int i=0; i<array.length; i++){
            string+=array+",";
        }
        return string.substring(0,string.length()-1);
    }
}
