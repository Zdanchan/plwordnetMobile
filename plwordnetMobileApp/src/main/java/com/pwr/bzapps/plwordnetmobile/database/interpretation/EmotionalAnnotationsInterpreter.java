package com.pwr.bzapps.plwordnetmobile.database.interpretation;

import android.content.Context;

import com.pwr.bzapps.plwordnetmobile.R;

public class EmotionalAnnotationsInterpreter {
    public static String interpretMarkedness(Context context, String markedness){
        if(markedness==null){
            return "";
        }
        switch(markedness){
            case "- s":
                return context.getResources().getString(R.string.weak_negative);
            case "- m":
                return context.getResources().getString(R.string.strong_negative);
            case "+ s":
                return context.getResources().getString(R.string.weak_positive);
            case "+ m":
                return context.getResources().getString(R.string.strong_positive);
            case "amb":
                return context.getResources().getString(R.string.ambiguous);
        }

        return "";
    }

    public static String interpretEmotions(Context context, String emotions_string){
        String[] emotions_array = emotions_string.split(";");
        String result = "";
        for(String emotion : emotions_array){
            result+=emotion + ", ";
        }
        if(result.length()>=2){
            result=result.substring(0,result.length()-2);
        }
        return result;
    }

    public static String interpretValuations(Context context, String valuations_string){
        String[] valuations_array = valuations_string.split(";");
        String result = "";
        for(String valuation : valuations_array){
            result+=valuation + ", ";
        }
        if(result.length()>=2){
            result=result.substring(0,result.length()-2);
        }
        return result;
    }

    public static String prepareExamples(String example1, String example2){
        String result = "";
        result+=(example1!=null ? example1 : "");
        result+=(example1!=null && example2!=null ? "\n" : "");
        result+=(example2!=null ? example2 : "");


        return result;
    }
}
