package com.marlin.tralp.Views;

import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.marlin.tralp.MainApplication;
import com.marlin.tralp.R;

/**
 * Created by gabriel on 16-03-15.
 */
public class ProcessView extends Fragment{
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootview = inflater.inflate(R.layout.fragment_main, container, false);
        rootview.setVisibility(View.VISIBLE);

        //MainApplication app = (MainApplication) Context.getApplicationContext();

        return rootview;
    }
}
