package com.marlin.tralp.Transcriber.tr_Models;

import android.content.Context;

import com.google.android.gms.vision.Detector;
import com.marlin.tralp.MainApplication;
import com.marlin.tralp.Model.Mat;

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
        private MainApplication app;
        public int idConfigMao;
        public String descricao;

        public Detector(MainApplication app, int resourceID, int idConfigMao, String descricao){
            this.app = app;
            this.descricao = descricao;
            this.idConfigMao = idConfigMao;
        }

        public Rect[] detect(Mat imgGray) {
            MatOfRect objs = new MatOfRect();
            cascadeDectector.detectMultiScale(imgGray, objs, 1.1, 2, 0, // TODO: objdetect.CV_HAAR_SCALE_IMAGE
                    new org.opencv.core.Size(45,80), new org.opencv.core.Size(165,320));
            Rect[] objsArray = objs.toArray();
            return objsArray;
        }

        private void loadDetector(int resourceID){
            try {
                InputStream is = app.getResources().openRawResource(resourceID);
                File cascadeDir = app.getDir("cascade", Context.MODE_PRIVATE);
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

    public ArrayList<Detector> getDetectors(MainApplication app){
        ArrayList<Detector> a = new ArrayList<Detector>();
        a.add(new Detector(app, 1,2,"testing"));
        return a;
    }

}


