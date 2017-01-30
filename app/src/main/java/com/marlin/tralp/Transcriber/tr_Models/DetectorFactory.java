package com.marlin.tralp.Transcriber.tr_Models;

import android.content.Context;
import android.content.res.Resources;
import android.util.Log;

import com.google.android.gms.vision.Detector;
import com.marlin.tralp.AppContext;
import com.marlin.tralp.MainApplication;
import com.marlin.tralp.R;

import org.opencv.core.Mat;
import org.opencv.core.MatOfRect;
import org.opencv.core.Rect;
import org.opencv.objdetect.CascadeClassifier;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by paulo on 12/11/2016.
 */


public class DetectorFactory {

    public class Detector{

        private CascadeClassifier cascadeDectector;
        public int idConfigMao;
        public String descricao;

        public Detector(int resourceID, int idConfigMao, String descricao){
            this.descricao = descricao;
            this.idConfigMao = idConfigMao;
            loadDetector(resourceID);
        }

        public Rect[] detect(Mat imgGray) {
            MatOfRect objs = new MatOfRect();
            Log.d("Detector", "Type: " + this.descricao + ", area: " + (int)imgGray.size().area());
            if(imgGray.size().area() == 0){
                return null;
            }
            cascadeDectector.detectMultiScale(imgGray, objs, 1.1, 2, 0, // TODO: objdetect.CV_HAAR_SCALE_IMAGE
                    new org.opencv.core.Size(40,40), new org.opencv.core.Size(250,250));
            Rect[] objsArray = objs.toArray();
            if(objsArray.length > 0)
                Log.d("Detector", "Found: " + objsArray.length + " " + this.descricao);
            return objsArray;
        }

        private void loadDetector(int resourceID){
            try {
                Resources r =  MainApplication.getContext().getResources();
                InputStream is = r.openRawResource(resourceID);
                File cascadeDir = MainApplication.getInstance().getDir("cascade", Context.MODE_PRIVATE);
                File mCascadeFile = new File(cascadeDir, "cascade["+resourceID+"].xml");
                FileOutputStream os = null;
                os = new FileOutputStream(mCascadeFile);
                byte[] buffer = new byte[4096];
                int bytesRead;
                while((bytesRead = is.read(buffer)) != -1)
                {
                    os.write(buffer, 0, bytesRead);
                }
                is.close();
                os.close();
                cascadeDectector = new CascadeClassifier(mCascadeFile.getAbsolutePath());
            } catch (java.io.IOException e) {
                e.printStackTrace();
            }
        }
    }

    public ArrayList<Detector> getDetectors(){
        ArrayList<Detector> a = new ArrayList<Detector>();
        a.add(new Detector(R.raw.fist, 0, "letraAfist1"));
        a.add(new Detector(R.raw.letraa, 0, "letraAfist2"));
        a.add(new Detector(R.raw.palm, 1, "letraB"));
        a.add(new Detector(R.raw.letrav, 2, "letraV"));
        a.add(new Detector(R.raw.maoaberta, 3, "maoaberta"));
        return a;
    }
}


