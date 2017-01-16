package com.marlin.tralp.Transcriber.ImageProcess;


import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;

import com.marlin.tralp.MainActivity;
import com.marlin.tralp.MainApplication;
import com.marlin.tralp.Views.ProcessView;
import com.marlin.tralp.Views.ResultadoCaptura;


/**
 * Created by gabriel on 16-02-16.
 */
//public class Controller implements Runnable{
public class Controller {
    MainApplication mApp;

    public Controller(MainApplication app){
        mApp = app;
    }

    public void process(){

        Filter localFilter = new Filter(mApp);

        localFilter.process();

        tr_FeatureAnnotation localFA = new tr_FeatureAnnotation(mApp);


        localFA.annotateFeatures();

        Log.d("annotationResult: ", "Size " + localFA.annotationResult.size());
        for (int index = 0; index < (localFA.annotationResult.size()-1); index++) {
            if (localFA.annotationResult.get(index) != null) {
                Log.d("annotationResult: ", "index: " + index +
                        "  X " + localFA.annotationResult.get(index).handCenterX +
                        "  Y " + localFA.annotationResult.get(index).handCenterY);
            }
        }

    }
}
