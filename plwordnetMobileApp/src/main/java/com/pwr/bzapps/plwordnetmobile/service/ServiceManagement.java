package com.pwr.bzapps.plwordnetmobile.service;

import android.app.ActivityManager;
import android.content.Context;
import android.os.Process;

import java.util.Iterator;
import java.util.List;

import static android.content.Context.ACTIVITY_SERVICE;

public class ServiceManagement {
    private Context context;

    public ServiceManagement(Context context) {
        this.context=context;
    }

    public boolean killService(String serviceName){
        ActivityManager am = (ActivityManager) context.getSystemService(ACTIVITY_SERVICE);
        List<ActivityManager.RunningAppProcessInfo> runningAppProcesses = am.getRunningAppProcesses();

        Iterator<ActivityManager.RunningAppProcessInfo> iter = runningAppProcesses.iterator();

        while(iter.hasNext()){
            ActivityManager.RunningAppProcessInfo next = iter.next();

            String processName = context.getPackageName() + serviceName;

            if(next.processName.equals(processName)){
                Process.killProcess(next.pid);
                return true;
            }
        }
        return false;
    }
}
