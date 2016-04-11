package com.marlin.tralp.Views;

import android.app.Activity;
import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.marlin.tralp.MainApplication;
import com.marlin.tralp.R;
import com.marlin.tralp.Transcriber.ImageProcess.Controller;

import javax.xml.transform.sax.TemplatesHandler;


/**
 * Created by gabriel on 16-03-15.
 */
public class ProcessView extends Activity{
    Handler uiThreadHandler;
    Thread imageProcessControllerThread;

    @Nullable
    //@Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootview = inflater.inflate(R.layout.process_view, container, false);
        rootview.setVisibility(View.VISIBLE);

        //MainApplication app = (MainApplication) getActivity().getApplicationContext();

        /* Instanciate the handler object to be used as communication source*/
        uiThreadHandler = new Handler(Looper.getMainLooper()) {
            @Override
            public void handleMessage(Message inputMessage) {
                Log.d("msg", " " + inputMessage.what);
                //String receivedObject = (String) inputMessage.obj;
                //String someKey = inputMessage.getData().getString("thisKey");
                TextView mTxtView = (TextView)findViewById(R.id.textView); // getView().
                mTxtView.setText("Consegui!! "+inputMessage.what);
                //mTxtView.setText(receivedObject);
                //Log.d("msg", someKey);
                //filterThread.interrupt();
                //filterThread.interrupt();
            }
        };
        imageProcessControllerThread = new Thread(new Controller((MainApplication)this.getApplicationContext(), uiThreadHandler));
        imageProcessControllerThread.start();

        //Should create Thread and keep this Fragment updated
        return rootview;
    }
}
