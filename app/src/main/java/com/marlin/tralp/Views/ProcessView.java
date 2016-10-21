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
        Log.d("msg ", " Entrei ProcessView.onCreateView");
    
        return rootview;
    }
}
