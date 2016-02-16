package com.marlin.tralp.Transcriber.ImageProcess;

/**
 * Created by gabriel on 16-02-16.
 */
public class FIlter {
    public class Classification{
        String [] best;
        String [] worst;
    }
    public Classification[] Filter(String frames_type_loaded){
        Classification [] mock = new Classification[2];
        // @TODO Make classification based on FFT to classify per second
        return mock;
    }
}
