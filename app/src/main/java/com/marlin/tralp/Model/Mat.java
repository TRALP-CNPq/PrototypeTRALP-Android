package com.marlin.tralp.Model;

/**
 * Created by gabriel on 16-04-09.
 */
public class Mat extends org.opencv.core.Mat {
    public int second = 0;
    public double quality = 0;

    public Mat(org.opencv.core.Mat parentMat){
        super(parentMat.nativeObj);
    }
}
