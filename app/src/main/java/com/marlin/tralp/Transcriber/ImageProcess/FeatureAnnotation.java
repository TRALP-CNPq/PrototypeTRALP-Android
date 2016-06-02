package com.marlin.tralp.Transcriber.ImageProcess;

import android.content.Context;
import android.graphics.Bitmap;
import android.util.Log;
import android.util.SparseArray;

import com.google.android.gms.vision.Frame;
import com.google.android.gms.vision.face.Face;
import com.google.android.gms.vision.face.FaceDetector;

import com.marlin.tralp.MainApplication;
import com.marlin.tralp.Model.Mat;
import com.marlin.tralp.R;
import com.marlin.tralp.Transcriber.Models.FeatureStructure;
import com.marlin.tralp.Transcriber.Models.FingerState;
import com.marlin.tralp.Transcriber.Models.FrameQueue;

import org.opencv.android.Utils;
import org.opencv.core.MatOfRect;
import org.opencv.core.Rect;
import org.opencv.objdetect.CascadeClassifier;
import org.opencv.core.*;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.util.ArrayList;

/**
 * Created by gabriel on 16-02-16.
 */
public class FeatureAnnotation {
    MainApplication mApp;
    CascadeClassifier palm, fist;
    FrameQueue frameQueue;
    FaceDetector faceDetector;
    ArrayList<FeatureStructure> annotationResult;

    public FeatureAnnotation(MainApplication app){
        mApp = app;
        palm = loadClassifiers(R.raw.palm);
        fist = loadClassifiers(R.raw.fist);
        frameQueue = mApp.frameQueue;
        if(frameQueue == null){
            throw new NullPointerException("No FrameQueue object found at the MainApplication");
        }
        annotationResult = new ArrayList<FeatureStructure>();

        faceDetector = new
                FaceDetector.Builder(mApp.getApplicationContext()).setTrackingEnabled(false)
                .build();
    }

    public void annotateFeatures(){
        Rect [] objects;
        frameQueue.getMatFrame(0,0,0);
        for (int secProcessed = 0; secProcessed < frameQueue.howManySeconds; secProcessed++) {
            for (int resPosition = 0; resPosition < frameQueue.resolutionFramesAmount; resPosition++) {
                findObjectExtractFeature(secProcessed,resPosition);
            }
        }

        mApp.annotation = annotationResult;
    }

    private void findObjectExtractFeature(int second, int position){
        int i = 0;
        Mat temp = null;
        if (second < 15) {
            temp = frameQueue.getMatFrame(second,position,i);
        }
        for (; temp!=null ; i++) {
            if(palmFrameDetector(temp))
                break;
            if(fistFrameDetector(temp))
                break;

            temp = frameQueue.getMatFrame(second,position);
        }
        if(temp == null)
            annotationResult.add(null); // Mark this position as failed to detect

        temp = frameQueue.popMatFrame(second,position);
//        while(temp!= null){
//            if(faceDetector(temp))
//                break;
//            temp = frameQueue.popMatFrame(second,position);
//        }
        /* Free resources*/
        while (temp != null){
            temp = frameQueue.popMatFrame(second,position);
        }

    }

    private boolean faceDetector(Mat img){
        //@TODO Implement face detection with Android
        if(!faceDetector.isOperational()){
            Log.d("Face Detector","Could not set up the face detector!" );
            return false;
        }

        Frame tempFrame = fromMatToFrame(img);
        SparseArray<Face> faces = faceDetector.detect(tempFrame);
        Face face;
        if(faces.size() >= 1)
            face = faces.get(0); //@INFO only working with 1 face
        else{
            return false;
        }

        int size = annotationResult.size();
        FeatureStructure tempFS = annotationResult.get(size-1);
        if(tempFS == null){
            annotationResult.remove(size-1);
            tempFS = new FeatureStructure();
            annotationResult.add(tempFS);
        }
        tempFS.faceCenterX= (int)( face.getPosition().x + face.getWidth()/2);
        tempFS.faceCenterY= (int)( face.getPosition().y + face.getHeight()/2);
        tempFS.faceX= (int)( face.getPosition().x);
        tempFS.faceY= (int)( face.getPosition().y);
        tempFS.faceWidth = face.getWidth();
        tempFS.faceHeight = face.getHeight();
        Point3D tempPoint3D = new Position().handPosition(face.getPosition().x,
                                                            face.getPosition().y,
                                                            face.getPosition().x + face.getWidth(),
                                                            face.getPosition().y + face.getHeight(),
                                                            tempFS.handCenterX - tempFS.handWidth/2,
                                                            tempFS.handCenterY - tempFS.handHeight/2,
                                                            tempFS.handCenterX + tempFS.handWidth/2,
                                                            tempFS.handCenterY + tempFS.handHeight/2);
        tempFS.handRelativeX = (int)tempPoint3D.getX();
        tempFS.handRelativeY= (int)tempPoint3D.getY();
        tempFS.isSimilingProbability = face.getIsSmilingProbability();


        return true;
    }
    private boolean palmFrameDetector(Mat img){ //@TODO This boolean [MAY] take out imprecision; makes sense at this point
        Rect [] tempObj = detectOnFrame(palm, img);

        if(tempObj.length != 0) {
            //@TODO Annotate a new FeatureStructure Object
            FeatureStructure tempFS = new FeatureStructure();
            tempFS.handCenterX = tempObj[0].x + (tempObj[0].width / 2); //@INFO This [May] vary with the screen orientation
            tempFS.handCenterY = tempObj[0].y + (tempObj[0].height / 2);
            tempFS.handWidth = tempObj[0].width;
            tempFS.handHeight = tempObj[0].height;
            tempFS.handX = tempObj[0].x;
            tempFS.handY = tempObj[0].y;
            Log.d("FeatureAnnotation: ", "secProcessed: " + img.second + "  X " + tempObj[0].x + "  Y " + tempObj[0].y);
            if (tempObj.length > 1)
                Log.d("FeatureAnnotation: ", "secProcessed: " + img.second + "  X " + tempObj[1].x + "  Y " + tempObj[1].y);
            tempFS.pinky = FingerState.stretched;
            tempFS.ring = FingerState.stretched;
            tempFS.middle = FingerState.stretched;
            tempFS.index = FingerState.stretched;
            tempFS.thumb = FingerState.stretched;

            tempFS.isSeparetedFingers = false;


            tempFS.touchingFingers = false;

            //@Todo determine how to find angles
            //Rect.br == bottom rigth point
            //Rect.tl == top left
            tempFS.palmAng1 = 90; //guess
            tempFS.palmAng2 = 0; //Likely
            tempFS.palmAng3 = 0; //precise

            annotationResult.add(tempFS);
            return true;
        }
        return false;

    }
    private boolean fistFrameDetector(Mat img){ //@TODO This boolean [MAY] take out imprecision; makes sense at this point
        Rect [] tempObj = detectOnFrame(fist, img);

        if(tempObj.length != 0){
            //@TODO Annotate a new FeatureStructure Object
            FeatureStructure tempFS = new FeatureStructure();
            tempFS.handCenterX= tempObj[0].x + (tempObj[0].width/2); //@INFO This [May] vary with the screen orientation
            tempFS.handCenterY= tempObj[0].y + (tempObj[0].height/2);
            tempFS.handWidth = tempObj[0].width;
            tempFS.handHeight = tempObj[0].height;
            tempFS.handX = tempObj[0].x;
            tempFS.handY = tempObj[0].y;

            tempFS.pinky = FingerState.bent;
            tempFS.ring= FingerState.bent;
            tempFS.middle= FingerState.bent;
            tempFS.index= FingerState.bent;
            tempFS.thumb= FingerState.bent;

            tempFS.isSeparetedFingers= false;

            tempFS.touchingFingers= false;

            //@Todo determine how to find angles
            //Rect.br == bottom rigth point
            //Rect.tl == top left
            tempFS.palmAng1= 90; //guess
            tempFS.palmAng2= 0; //Likely
            tempFS.palmAng3= 0; //precise

            annotationResult.add(tempFS);
            return true;
        }
        return false;
    }
    private Rect[] detectOnFrame(CascadeClassifier detector, Mat img){
        MatOfRect objectsDetected = new MatOfRect();

    //    detector.detectMultiScale(img, objectsDetected); //@TODO Default parameters: Better precision, slower processing
    //    detector.detectMultiScale(img,objectsDetected,1.1,3,0,new Size(5,13), new Size(45,80));
        detector.detectMultiScale(img,objectsDetected,1.1,2,0,new Size(45,80), new Size(165,320));
        return objectsDetected.toArray();
    }
    private CascadeClassifier loadClassifiers(int rawResource){
        CascadeClassifier haarCascade;
        try{
            InputStream is = mApp.getResources().openRawResource(rawResource);
            File cascadeDir = mApp.getDir("cascade", Context.MODE_PRIVATE);
            File mCascadeFile = new File(cascadeDir, "cascade["+rawResource+"].xml");
            FileOutputStream os = new FileOutputStream(mCascadeFile);

            byte[] buffer = new byte[4096];
            int bytesRead;
            while((bytesRead = is.read(buffer)) != -1)
            {
                os.write(buffer, 0, bytesRead);
            }
            is.close();
            os.close();
            haarCascade = new CascadeClassifier(mCascadeFile.getAbsolutePath());
            if (haarCascade.empty())
            {
                Log.i("Cascade Error", "Failed to load cascade classifier");
                haarCascade = null;
            }
        }
        catch(Exception e)
        {
            Log.i("Cascade Error: ", e.getMessage());
            haarCascade = null;
        }
        return haarCascade;
    }

    private Frame fromMatToFrame(Mat img){
        Bitmap tmp_bmp = Bitmap.createBitmap(img.cols(), img.rows(), Bitmap.Config.ARGB_8888); //Maybe ARGB_8888
        Utils.matToBitmap(img,tmp_bmp);
        return new Frame.Builder().setBitmap(tmp_bmp).build();
    }
}

