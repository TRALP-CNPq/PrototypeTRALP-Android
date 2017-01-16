package com.marlin.tralp.Transcriber.FeatureProcess;

import com.marlin.tralp.MainApplication;

import java.util.ArrayList;

/**
 * Created by gabriel on 16-02-16.
 */
public class Controller {
    MainApplication mApp;
        public Controller(MainApplication app){
            mApp = app;
        }

        public String process(){
            SignClassification signProcessor = new SignClassification(mApp);
            ArrayList<String> result = signProcessor.process();

            String setence = "";
            for(String word:result){
                setence = setence + " " + word;
            }

            return setence;
        }
}
