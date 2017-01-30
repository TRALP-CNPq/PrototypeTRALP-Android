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

            String setence = "", last = "";
            for(String word:result){
                if(!last.equals(word)){
                    setence = setence + " " + word;
                    last = word;
                }
            }

            return setence;
        }
}
