package com.marlin.tralp.Model;

import android.graphics.Bitmap;
import android.util.Log;

import org.opencv.android.Utils;
import org.opencv.core.CvException;
import org.opencv.imgproc.Imgproc;

/**
 * Created by gabriel on 16-04-09.
 */
public class Mat{
    public int second = 0;
    public double quality = 0;
    public Bitmap data;

    public Mat(org.opencv.core.Mat parentMat){
//        super(parentMat.nativeObj);
        data = matToBitmap(parentMat);
    }

//    public Mat clone(){
//        org.opencv.core.Mat temp = this.clone() ;
//        org.opencv.core.Mat clone = temp.clone();
//        Mat customClone = new Mat(clone);
//        customClone.second = this.second;
//        customClone.quality = this.quality;
//        return customClone;
//    }
     private Bitmap matToBitmap(org.opencv.core.Mat img){
        if(img.size().area() == 0){
            Log.d("matToBitmap", "Got empty mat");
            return null;
        }
        org.opencv.core.Mat temp = new org.opencv.core.Mat();
        try {
            //Imgproc.cvtColor(img, temp, Imgproc.COLOR_RGB2BGRA);
            Imgproc.cvtColor(img, temp, Imgproc.COLOR_GRAY2RGBA, 4);
            Bitmap bmp = Bitmap.createBitmap(temp.cols(), temp.rows(), Bitmap.Config.ARGB_8888);
            Utils.matToBitmap(temp, bmp);
            temp.release();
            return bmp;
        }
        catch (CvException e){
            Log.d("matToBitmap","error while trying to convert");
            Log.d("Exception",e.getMessage());
            throw e;
        }
    }
}
