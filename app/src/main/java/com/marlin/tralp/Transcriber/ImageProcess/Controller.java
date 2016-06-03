package com.marlin.tralp.Transcriber.ImageProcess;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;

import com.marlin.tralp.MainApplication;

/**
 * Created by gabriel on 16-02-16.
 */
public class Controller implements Runnable{
    MainApplication mApp;
    Handler uiHandler;

    public Controller(MainApplication app, Handler mHandler){
        mApp = app;
        uiHandler = mHandler;
    }

    @Override
    public void run(){
       this.process();
    }

    public void process(){
        //@TODO Create Filter class
        Filter localFilter = new Filter(mApp);
        sendMessage(1);
        localFilter.process();
        sendMessage(35);

        FeatureAnnotation localFA = new FeatureAnnotation(mApp);

        sendMessage(37);

        localFA.annotateFeatures();

        sendMessage(50);
        Log.d("annotationResult: ", "Size " + localFA.annotationResult.size());
        for (int index = 0; index < (localFA.annotationResult.size()-1); index++) {
            if (localFA.annotationResult.get(index) != null) {
                Log.d("annotationResult: ", "index: " + index +
                        "  X " + localFA.annotationResult.get(index).handCenterX +
                        "  Y " + localFA.annotationResult.get(index).handCenterY);
            }
        }

        UnderstandMovement movs = new UnderstandMovement(mApp);
        String frase = movs.StartSignsAndMovementsInterpreter(localFA);

    }
    private void sendMessage(int percentage){

        Message msg = new Message();
        Bundle bndMock= new Bundle();
        msg.what = percentage;
        Log.d("msg sendMessage: ", " " + percentage);
        uiHandler.sendEmptyMessage(percentage);

    }
}
