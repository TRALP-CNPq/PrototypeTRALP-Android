package com.marlin.tralp.MenuViews;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.marlin.tralp.R;

/**
 * Created by psalum on 23/09/2015.
 */
public class Contato extends Fragment {
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootview = inflater.inflate(R.layout.contato, container, false);
        return rootview;
    }
}