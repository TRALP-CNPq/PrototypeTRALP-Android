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
    Handler uiHandler;

    public Controller(MainApplication app, Handler mHandler){
        mApp = app;
        uiHandler = mHandler;
    }
    public Controller(MainApplication app){
        mApp = app;
    }

//    @Override
//    public void run(){
//       this.process();
//    }

    public String process(){
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
        Log.d("annotationResult: ", "frase: " + frase);

        return frase;
    }
    private void sendMessage(int percentage){

        Message msg = new Message();
        Bundle bndMock= new Bundle();
        msg.what = percentage;
        Log.d("msg sendMessage: ", " " + percentage);
//        uiHandler.sendEmptyMessage(percentage);

    }
}
