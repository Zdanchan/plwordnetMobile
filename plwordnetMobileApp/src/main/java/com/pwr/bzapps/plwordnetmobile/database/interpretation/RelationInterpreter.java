package com.pwr.bzapps.plwordnetmobile.database.interpretation;

import android.content.Context;

public class RelationInterpreter {
    public static String getRelationTypeLabel(Context context, Integer id, String package_name){
        String result = context.getResources().getString(context.getResources().getIdentifier("rel_type_" + id, "string", package_name));
        result = result.substring(0,1).toUpperCase() + result.substring(1);
        return result;
    }
}
