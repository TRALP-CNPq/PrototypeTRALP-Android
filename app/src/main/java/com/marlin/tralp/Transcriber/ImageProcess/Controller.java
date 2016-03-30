package com.marlin.tralp.Transcriber.ImageProcess;

import com.marlin.tralp.MainApplication;

/**
 * Created by gabriel on 16-02-16.
 */
public class Controller {
    MainApplication mApp;

    public void Controller(MainApplication app){
        mApp = app;
        return;
    }



    public void process(){
        //@TODO Create Filter class

        //@TODO Call Filter process

        //@TODO Create FeatureAnnotation class

        /*@TODO
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

    private int mergeFeature(int older, int newer){
        /**
         * merge old info with new one, always prioritizing newest
         *
         */
        //@TODO change int to type used to specify feature structure (Models.?)


        return 0;
    }
}
