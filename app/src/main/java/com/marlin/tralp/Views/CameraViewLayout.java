package com.marlin.tralp.Views;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.hardware.Camera;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.SurfaceView;


import org.opencv.android.BaseLoaderCallback;
import org.opencv.android.CameraBridgeViewBase;
import org.opencv.android.JavaCameraView;
import org.opencv.android.LoaderCallbackInterface;
import org.opencv.android.OpenCVLoader;
import org.opencv.core.Core;
import org.opencv.core.CvType;
import org.opencv.core.Mat;
import org.opencv.core.MatOfRect;
import org.opencv.core.Point;
//import org.opencv.core.Rect;
import org.opencv.core.Scalar;
import org.opencv.imgproc.Imgproc;
import org.opencv.objdetect.CascadeClassifier;



import com.marlin.tralp.R;

import java.io.IOException;

/**
 * Created by psalum on 08/09/2015.
 */
public class CameraViewLayout extends SurfaceView implements SurfaceHolder.Callback,Camera.FaceDetectionListener {

    private static final String TAG = "TraLP::CameraViewLayout";
    private static final int colorTable[] = {Color.RED, Color.GREEN, Color.BLUE, Color.YELLOW, Color.CYAN, Color.MAGENTA};
    private SurfaceHolder mHolder;
    private Camera mCamera;
    private int				mCameraId 	= 0;
    private final Paint[]   mPaintList 	= new Paint[colorTable.length];
    private int 			mViewWidth 	= 0;
    private int 			mViewHeight = 0;
    private Rect RectSilhueta = new Rect(0,0,0,0);
    private Rect Enquadrado = new Rect(0,0,0,0);
    private Camera.Face[] 			mFaces 		= null;

    Bitmap verde = BitmapFactory.decodeResource(getResources(), R.drawable.silhuetaverde);
    Bitmap vermelho = BitmapFactory.decodeResource(getResources(), R.drawable.silhuetavermelha);

    public CameraViewLayout(Context context, Camera camera, int cameraId) {
        super(context);
        setWillNotDraw(false);
        mCamera = camera;
        mCameraId = cameraId;
        Camera.Parameters parameters = camera.getParameters();
        mCamera.setDisplayOrientation(90);
        parameters.setFocusMode(Camera.Parameters.FOCUS_MODE_AUTO);
        Camera.Size tamanho = parameters.getPreviewSize();

        //get the holder and set this class as the callback, so we can get camera data here
        mHolder = getHolder();
        mHolder.addCallback(this);
        mHolder.setType(SurfaceHolder.SURFACE_TYPE_NORMAL);

        mCamera.setFaceDetectionListener(this);
        for(int i = 0; i < mPaintList.length; i++)
        {
            mPaintList[i] = new Paint();
            mPaintList[i].setColor(colorTable[i]);
            mPaintList[i].setStyle(Paint.Style.STROKE);
            mPaintList[i].setStrokeWidth(7);
        }
    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        try {
            //when the surface is created, we can set the camera to draw images in this surfaceholder
            mCamera.setPreviewDisplay(holder);
            // mCamera.startPreview();
        } catch (IOException e) {
            Log.d("ERROR", "Camera error on surfaceCreated " + e.getMessage());
        }
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
    //before changing the application orientation, you need to stop the preview, rotate and then start it again
        mViewWidth = width;
        mViewHeight = height;

        if (mHolder.getSurface() == null)//check if the surface is ready to receive camera data
            return;

        try {
            mCamera.stopPreview();
        } catch (Exception e) {
            //this will happen when you are trying the camera if it's not running
        }

        //now, recreate the camera preview
        try {
            mCamera.setPreviewDisplay(mHolder);
            mCamera.startPreview();
            int MaxNumDetectedFaces = mCamera.getParameters().getMaxNumDetectedFaces();
            Log.i(TAG, "[INFO] MaxNumDetectedFaces: " + MaxNumDetectedFaces);
            if (MaxNumDetectedFaces > 0)                 mCamera.startFaceDetection();

        } catch (IOException e) {
            Log.d("ERROR", "Camera error on surfaceChanged " + e.getMessage());
        }
    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
//our app has only one screen, so we'll destroy the camera in the surface
        //if you are unsing with more screens, please move this code your activity
        mCamera.stopPreview();
        mCamera.release();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        // TODO Auto-generated method stub
        super.onDraw(canvas);
        Log.i(TAG, "[INFO] OnDraw()");

        RectSilhueta.left = (int) (mViewWidth*0.23);
        RectSilhueta.right = (int) (mViewWidth*0.77);
        RectSilhueta.top = (int) (mViewHeight*0.13);
        RectSilhueta.bottom = (int) (mViewWidth*0.54*262/300+mViewHeight*0.13);
        Enquadrado.left = (int) (RectSilhueta.left + (RectSilhueta.right-RectSilhueta.left)*75/300);
        Enquadrado.right = (int) (RectSilhueta.left + (RectSilhueta.right-RectSilhueta.left)*225/300);
        Enquadrado.top = (int) (RectSilhueta.top + (RectSilhueta.bottom-RectSilhueta.top)*53/262);
        Enquadrado.bottom = (int) (RectSilhueta.top + (RectSilhueta.bottom-RectSilhueta.top)*200/262);

        //Coordinate Transform
        if (mFaces != null && mFaces.length != 0)
        {
            for (int i = 0; i < mFaces.length; i++)
            {
                Paint paint = mPaintList[i%mPaintList.length];
                canvas.drawRect(transFromCameraToView(mFaces[i].rect), paint);
            }

            int top 	= (int)((float)mViewHeight/2000 * (mFaces[0].rect.left+ 1000));
            int right 	= (int)((float)mViewWidth/2000 * (1000 - mFaces[0].rect.top));
            int bottom 	= (int)((float)mViewHeight/2000 * (mFaces[0].rect.right + 1000));
            int left 	= (int)((float)mViewWidth/2000 * (1000 - mFaces[0].rect.bottom));
            if (((top - Enquadrado.top)^2 + (left - Enquadrado.left)^2) < 100 && ((bottom - Enquadrado.bottom)^2 + (right - Enquadrado.right)^2) < 200  ) {
                canvas.drawBitmap(verde,null,RectSilhueta,null);
            } else {
                canvas.drawBitmap(vermelho,null,RectSilhueta,null);
            }

  /*          if (mFaces.length > 0){
                canvas.drawBitmap(verde,null,RectSilhueta,null);
            } else {
                canvas.drawBitmap(vermelho,null,RectSilhueta,null);
            };
    */
            mFaces = null;

        } else {
          canvas.drawBitmap(vermelho,null,RectSilhueta,null);
        }
    }

    /**
     *  Transforms the coordinates from the detection system to the screen system
     * @param cameraRect the rectangle coordinates
     * @return  the new coordinates as a Rect variable
     */
    private Rect transFromCameraToView(Rect cameraRect) {
        int top 	= (int)((float)mViewHeight/2000 * (cameraRect.left+ 1000));
        int right 	= (int)((float)mViewWidth/2000 * (1000 - cameraRect.top));
        int bottom 	= (int)((float)mViewHeight/2000 * (cameraRect.right + 1000));
        int left 	= (int)((float)mViewWidth/2000 * (1000 - cameraRect.bottom));

        Rect viewRect = new Rect( left, top, right, bottom);
        return viewRect;
    }

    @Override
    public void onFaceDetection(Camera.Face[] faces, Camera camera) {
        // TODO Auto-generated method stub
        Log.i(TAG, "[INFO] Face Detect: " + faces.length);
        for (int i =  0; i < faces.length; i++)
        {
            Log.i(TAG, "[INFO] \t["+i+"] (" + faces[i].rect.left + "x" + faces[i].rect.right + ", "
                    + faces[i].rect.top + "x" + faces[i].rect.bottom + ", "
                    + faces[i].rect.width() + "x" + faces[i].rect.height() + ")" );
        }

        mFaces = faces;
        invalidate();
    }
}