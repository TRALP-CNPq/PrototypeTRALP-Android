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
        for (int secProcessed = 0; secProcessed < (localFA.annotationResult.size()-1); secProcessed++) {
            if (localFA.annotationResult.get(secProcessed) != null) {
                Log.d("annotationResult: ", "secProcessed: " + secProcessed +
                        "  X " + localFA.annotationResult.get(secProcessed).handCenterX +
                        "  Y " + localFA.annotationResult.get(secProcessed).handCenterY);
//                Log.d("annotationResult: ", "secProcessed: " + secProcessed +
//                        "  X " + localFA.annotationResult.get(secProcessed). +
//                        "  Y " + localFA.annotationResult.get(secProcessed).handCenterY);
            }
        }

        UnderstandMovement movs = new UnderstandMovement(mApp);
        String frase = movs.StartSignsAndMovementsInterpreter(localFA);

        /* Keeping this comment for future reference
        * On Loop
        * keep getting next as x (filter.getNext())
        *   process x over FeatureAnnotation,
        *   over incomplete info
        *       keep getting next on fail as x (Filter.getNextOnFail()) (Will eventually get null)
        *           merge old info with new one, always prioritizing newest (call mergeFeature)
        *   save in myApp all the extracted List of info,
         *      as per contract with FeatureProcess.Controller
        * */
    }
    private void sendMessage(int percentage){

        Message msg = new Message();
        Bundle bndMock= new Bundle();
        //msg.obj = "Some new text for the screen";
        msg.what = percentage;
        Log.d("msg sendMessage: ", " " + percentage);
        //bndMock.putString("thisKey", "Nice MSG");
        uiHandler.sendEmptyMessage(percentage);

    }
    private int mergeFeature(int older, int newer){
        /**
         * merge old info with new one, always prioritizing newest
         *
         */
        //@TODO change int to type used to specify feature structure (Models.?)


        return 0;
    }
}
