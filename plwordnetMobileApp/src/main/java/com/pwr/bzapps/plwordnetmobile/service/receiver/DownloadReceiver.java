package com.pwr.bzapps.plwordnetmobile.service.receiver;

import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.ResultReceiver;
import android.widget.ImageView;
import android.widget.ProgressBar;
import com.pwr.bzapps.plwordnetmobile.service.DownloadService;

public class DownloadReceiver extends ResultReceiver {

    private ProgressBar progressBar;
    private ImageView rotatingImage;

    public DownloadReceiver(Handler handler, ProgressBar progressBar, ImageView rotatingImage) {
        super(handler);
        this.progressBar=progressBar;
        this.rotatingImage=rotatingImage;
    }

    @Override
    protected void onReceiveResult(int resultCode, Bundle resultData) {
        super.onReceiveResult(resultCode, resultData);
        if (resultCode == DownloadService.UPDATE_PROGRESS) {
            int progress = resultData.getInt("progress");
            if(progressBar!=null){
                if (Build.VERSION.SDK_INT >= 24)
                    progressBar.setProgress(progress,true);
                else
                    progressBar.setProgress(progress);
            }
            if(rotatingImage!=null){
                rotatingImage.clearAnimation();
            }
        }
    }
}
