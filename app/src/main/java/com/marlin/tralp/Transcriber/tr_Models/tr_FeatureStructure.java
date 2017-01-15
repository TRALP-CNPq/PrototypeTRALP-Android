package com.marlin.tralp.Transcriber.tr_Models;

import com.google.android.gms.common.server.converter.StringToIntConverter;
import com.marlin.tralp.Transcriber.Models.FingerState;

/**
 * Created by gabriel on 16-02-16.
 */
public class tr_FeatureStructure {
    public boolean succeedToDetect;


    public int handCenterX;
    public int handCenterY;

    public int handX;
    public int handY;

    public float handWidth;
    public float handHeight;

    public int second;
    public int group;

    public int idConfigMao;
    public String descricaoConfigMao;

    public int faceCenterX;
    public int faceCenterY;

    public int faceX;
    public int faceY;

    public float faceWidth;
    public float faceHeight;

    public int handRelativeX;
    public int handRelativeY;

}