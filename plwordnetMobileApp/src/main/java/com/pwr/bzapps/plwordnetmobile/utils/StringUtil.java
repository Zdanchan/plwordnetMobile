package com.pwr.bzapps.plwordnetmobile.utils;

import java.util.LinkedList;

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
            string+=array[i]+",";
        }
        return string.substring(0,string.length()-1);
    }

    public static Integer[] parseStringToIntegerArray(String string){
        if("null".equals(string))
            return new Integer[0];
        LinkedList<Integer> list = new LinkedList<Integer>();
        string = string.replace("[","").replace("]","");
        String[] string_values = string.split(",");
        for(int i=0; i<string_values.length; i++){
            if(!"null".equals(string_values[i].trim()))
                list.add(Integer.parseInt(string_values[i].trim()));
        }
        return list.toArray(new Integer[0]);
    }
}
