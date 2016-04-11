package com.marlin.tralp.Transcriber.Models;

/**
 * Created by gabriel on 16-02-16.
 */
public class FeatureStructure {
    public boolean failedToDetect;


    public int handCenterX;
    public int handCenterY;
    public int faceCenterX;
    public int faceCenterY;
    public int handRelativeX;
    public int handRelativeY;
    public float isSimilingProbability;

    public FingerState pinky;
    public FingerState ring;
    public FingerState middle;
    public FingerState index;
    public FingerState thumb;

    public boolean isSeparetedFingers;


    public boolean touchingFingers;

    public int palmAng1;
    public int palmAng2;
    public int palmAng3;


}