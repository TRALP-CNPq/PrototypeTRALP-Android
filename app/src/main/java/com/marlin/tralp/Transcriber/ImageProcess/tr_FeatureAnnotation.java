package com.marlin.tralp.Transcriber.ImageProcess;

import android.graphics.Bitmap;
import android.util.Log;
import android.util.SparseArray;

import com.google.android.gms.vision.Frame;
import com.google.android.gms.vision.face.Face;
import com.google.android.gms.vision.face.FaceDetector;
import com.marlin.tralp.MainApplication;
import com.marlin.tralp.Model.Mat;
import com.marlin.tralp.Transcriber.Models.FrameQueue;
import com.marlin.tralp.Transcriber.tr_Models.DetectorFactory;
import com.marlin.tralp.Transcriber.tr_Models.tr_FeatureStructure;

import org.opencv.android.Utils;
import org.opencv.core.CvException;
import org.opencv.core.Rect;
import org.opencv.imgproc.Imgproc;

import java.util.ArrayList;

/**
 * Created by gabriel on 16-02-16.
 */
public class tr_FeatureAnnotation {
    MainApplication mApp;
    FrameQueue frameQueue;
    FaceDetector faceDetector;
    ArrayList<tr_FeatureStructure> annotationResult;
    ArrayList<DetectorFactory.Detector> detectors;

    public tr_FeatureAnnotation(MainApplication app){
        mApp = app;

        frameQueue = mApp.frameQueue;
        if(frameQueue == null){
            throw new NullPointerException("No FrameQueue object found at the MainApplication");
        }
        annotationResult = new ArrayList<tr_FeatureStructure>();

        faceDetector = new
                FaceDetector.Builder(mApp.getApplicationContext()).setTrackingEnabled(false)
                .build();
        loadDetectors();
    }

    public void annotateFeatures(){
        frameQueue.getMatFrame(0,0,0);
        for (int secProcessed = 0; secProcessed < frameQueue.howManySeconds; secProcessed++) {
            for (int resPosition = 0; resPosition < frameQueue.resolutionFramesAmount; resPosition++) {
                findObjectExtractFeature(secProcessed,resPosition);
            }
        }

        mApp.annotation = annotationResult;
        mApp.frameQueue = null;
    }

    private void findObjectExtractFeature(int second, int position){

        boolean hasFoundFace = false;
        boolean hasFoundHand = false;
        tr_FeatureStructure tempFS = new tr_FeatureStructure();
        tempFS.succeedToDetect = false;

        for(Mat img: frameQueue.getGroupList(second, position)){
            if(!hasFoundFace) {
                tempFS = handDetector(img, second,position, tempFS);
                hasFoundFace = tempFS.succeedToDetect;
                tempFS.succeedToDetect = false;
            }
            if(!hasFoundHand){
                tempFS = faceDetector(img,tempFS);
                hasFoundHand = tempFS.succeedToDetect;
                tempFS.succeedToDetect = false;
            }

            if (hasFoundFace && hasFoundHand) {
                tempFS = calculateRelativePos(tempFS);
                tempFS.succeedToDetect = true;
                annotationResult.add(tempFS);
                Log.d("Annotation", "Finshed one group with success");
                break;
            }
        }

    }


    private void loadDetectors(){
        detectors = (new DetectorFactory()).getDetectors(mApp);
    }

    private tr_FeatureStructure handDetector(Mat img, int second, int position, tr_FeatureStructure fs){
        for (DetectorFactory.Detector detector : detectors){
            Rect[]found = detector.detect(img);
            if (found != null && found.length > 0){
                ////@// TODO: 2017-01-15 checar orientacao
                fs.handCenterX = found[0].x + (found[0].width / 2);
                fs.handCenterY = found[0].y + (found[0].height / 2);
                fs.handX = found[0].x;
                fs.handY = found[0].y;
                fs.handWidth = found[0].width;
                fs.handHeight = found[0].height;
                fs.succeedToDetect = true;
                fs.group = position;
                fs.second = second;
                fs.idConfigMao = detector.idConfigMao;
                fs.descricaoConfigMao = detector.descricao;
                return fs;
            }
        }

        return fs;
    }

    private tr_FeatureStructure faceDetector(Mat img, tr_FeatureStructure toFill){
        Bitmap bitmap = matToBitmap(img);

        Frame frame = new Frame.Builder().setBitmap(bitmap).build();

        SparseArray<Face> faces = faceDetector.detect(frame);

        if( faces.size() == 0) return toFill;

        Face face = faces.get(0);

        toFill.faceX = (int) face.getPosition().x;
        toFill.faceY = (int) face.getPosition().y;
        toFill.faceCenterX = (int) (face.getPosition().x + face.getWidth()/2);
        toFill.faceCenterY = (int) (face.getPosition().y + face.getHeight()/2);
        toFill.faceHeight = (int) face.getHeight();
        toFill.faceWidth = (int) face.getWidth();
        toFill.succeedToDetect = true;

        Log.d("FaceDetection", "suceeded");

        return toFill;
    }


    private tr_FeatureStructure calculateRelativePos(tr_FeatureStructure fs){
        Point2D faceUpLeftPoint = new Point2D(fs.faceX, fs.faceY);
        Point2D faceDownRightPoint = new Point2D(fs.faceX + fs.faceWidth, fs.faceY + fs.faceHeight);
        Point2D handUpLeftPoint = new Point2D(fs.handX, fs.handY);
        Point2D handDownRightPoint = new Point2D(fs.handX +fs.handWidth, fs.handY + fs.handHeight);
        Point3D temp = (new Position()).calculateHandPosition(faceUpLeftPoint,
                faceDownRightPoint, handUpLeftPoint, handDownRightPoint);
        fs.handRelativeX = (int) temp.getX();
        fs.handRelativeY = (int) temp.getY();
        return fs;

    }

    private Bitmap matToBitmap(Mat img){
        org.opencv.core.Mat temp = new org.opencv.core.Mat();
        try {
            //Imgproc.cvtColor(img, temp, Imgproc.COLOR_RGB2BGRA);
            Imgproc.cvtColor(img, temp, Imgproc.COLOR_GRAY2RGBA, 4);
            Bitmap bmp = Bitmap.createBitmap(temp.cols(), temp.rows(), Bitmap.Config.ARGB_8888);
            Utils.matToBitmap(temp, bmp);
            return bmp;
        }
        catch (CvException e){
            Log.d("matToBitmap","error while trying to convert");
            Log.d("Exception",e.getMessage());
            throw e;
        }
    }

}

