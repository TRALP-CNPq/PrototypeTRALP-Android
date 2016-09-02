package com.marlin.tralp.Transcriber.Models;

import android.content.Context;

import com.marlin.tralp.AppContext;
import com.marlin.tralp.Conexao.Dao.AnimationParametersDAO;

import java.util.ArrayList;

/**
 * Created by aneves on 6/1/2016.
 */
public class AnimationParametersDTO {

    public int codMov;
    private float RShoulder_V;
    private float RShoulder_H;
    private float RArm_V;
    private float RArm_H;
    private float RArm_R;
    private float RForearm_V;
    private float RForearm_H;
    private float RForearm_R;
    private float RHand_V;
    private float RHand_H;
    private float RThumb1V;
    private float RThumb1H;
    private float RThumb2V;
    private float RIndex1V;
    private float RIndex1H;
    private float RIndex2V;
    private float RMiddle1V;
    private float RMiddle1H;
    private float RMiddle2V;
    private float RRing1V;
    private float RRing1H;
    private float RRing2V;
    private float RPinky1V;
    private float RPinky1H;
    private float RPinky2V;
    private float LShoulder_V;
    private float LShoulder_H;
    private float LArm_V;
    private float LArm_H;
    private float LArm_R;
    private float LForearm_V;
    private float LForearm_H;
    private float LForearm_R;
    private float LHand_V;
    private float LHand_H;
    private float LThumb1V;
    private float LThumb1H;
    private float LThumb2V;
    private float LIndex1V;
    private float LIndex1H;
    private float LIndex2V;
    private float LMiddle1V;
    private float LMiddle1H;
    private float LMiddle2V;
    private float LRing1V;
    private float LRing1H;
    private float LRing2V;
    private float LPinky1V;
    private float LPinky1H;
    private float LPinky2V;
//    Context context;
//
//    public AnimationParametersDTO(){
//        context = new AppContext().getAppContext();
//
//    }
//    public AnimationParametersDTO(Context context){
//        this.context = context;
//
//    }

    public int getCodMov() {
        return codMov;
    }

    public void setCodMov(int codMov) {
        this.codMov = codMov;
    }

    public float getRShoulder_V() {
        return RShoulder_V;
    }

    public void setRShoulder_V(float RShoulder_V) {
        this.RShoulder_V = RShoulder_V;
    }

    public float getRShoulder_H() {
        return RShoulder_H;
    }

    public void setRShoulder_H(float RShoulder_H) {
        this.RShoulder_H = RShoulder_H;
    }

    public float getRArm_V() {
        return RArm_V;
    }

    public void setRArm_V(float RArm_V) {
        this.RArm_V = RArm_V;
    }

    public float getRArm_H() {
        return RArm_H;
    }

    public void setRArm_H(float RArm_H) {
        this.RArm_H = RArm_H;
    }

    public float getRArm_R() {
        return RArm_R;
    }

    public void setRArm_R(float RArm_R) {
        this.RArm_R = RArm_R;
    }

    public float getRForearm_V() {
        return RForearm_V;
    }

    public void setRForearm_V(float RForearm_V) {
        this.RForearm_V = RForearm_V;
    }

    public float getRForearm_H() {
        return RForearm_H;
    }

    public void setRForearm_H(float RForearm_H) {
        this.RForearm_H = RForearm_H;
    }

    public float getRForearm_R() {
        return RForearm_R;
    }

    public void setRForearm_R(float RForearm_R) {
        this.RForearm_R = RForearm_R;
    }

    public float getRHand_V() {
        return RHand_V;
    }

    public void setRHand_V(float RHand_V) {
        this.RHand_V = RHand_V;
    }

    public float getRHand_H() {
        return RHand_H;
    }

    public void setRHand_H(float RHand_H) {
        this.RHand_H = RHand_H;
    }

    public float getRThumb1V() {
        return RThumb1V;
    }

    public void setRThumb1V(float RThumb1V) {
        this.RThumb1V = RThumb1V;
    }

    public float getRThumb1H() {
        return RThumb1H;
    }

    public void setRThumb1H(float RThumb1H) {
        this.RThumb1H = RThumb1H;
    }

    public float getRThumb2V() {
        return RThumb2V;
    }

    public void setRThumb2V(float RThumb2V) {
        this.RThumb2V = RThumb2V;
    }

    public float getRIndex1V() {
        return RIndex1V;
    }

    public void setRIndex1V(float RIndex1V) {
        this.RIndex1V = RIndex1V;
    }

    public float getRIndex1H() {
        return RIndex1H;
    }

    public void setRIndex1H(float RIndex1H) {
        this.RIndex1H = RIndex1H;
    }

    public float getRIndex2V() {
        return RIndex2V;
    }

    public void setRIndex2V(float RIndex2V) {
        this.RIndex2V = RIndex2V;
    }

    public float getRMiddle1V() {
        return RMiddle1V;
    }

    public void setRMiddle1V(float RMiddle1V) {
        this.RMiddle1V = RMiddle1V;
    }

    public float getRMiddle1H() {
        return RMiddle1H;
    }

    public void setRMiddle1H(float RMiddle1H) {
        this.RMiddle1H = RMiddle1H;
    }

    public float getRMiddle2V() {
        return RMiddle2V;
    }

    public void setRMiddle2V(float RMiddle2V) {
        this.RMiddle2V = RMiddle2V;
    }

    public float getRRing1V() {
        return RRing1V;
    }

    public void setRRing1V(float RRing1V) {
        this.RRing1V = RRing1V;
    }

    public float getRRing1H() {
        return RRing1H;
    }

    public void setRRing1H(float RRing1H) {
        this.RRing1H = RRing1H;
    }

    public float getRRing2V() {
        return RRing2V;
    }

    public void setRRing2V(float RRing2V) {
        this.RRing2V = RRing2V;
    }

    public float getRPinky1V() {
        return RPinky1V;
    }

    public void setRPinky1V(float RPinky1V) {
        this.RPinky1V = RPinky1V;
    }

    public float getRPinky1H() {
        return RPinky1H;
    }

    public void setRPinky1H(float RPinky1H) {
        this.RPinky1H = RPinky1H;
    }

    public float getRPinky2V() {
        return RPinky2V;
    }

    public void setRPinky2V(float RPinky2V) {
        this.RPinky2V = RPinky2V;
    }

    public float getLShoulder_V() {
        return LShoulder_V;
    }

    public void setLShoulder_V(float LShoulder_V) {
        this.LShoulder_V = LShoulder_V;
    }

    public float getLShoulder_H() {
        return LShoulder_H;
    }

    public void setLShoulder_H(float LShoulder_H) {
        this.LShoulder_H = LShoulder_H;
    }

    public float getLArm_V() {
        return LArm_V;
    }

    public void setLArm_V(float LArm_V) {
        this.LArm_V = LArm_V;
    }

    public float getLArm_H() {
        return LArm_H;
    }

    public void setLArm_H(float LArm_H) {
        this.LArm_H = LArm_H;
    }

    public float getLArm_R() {
        return LArm_R;
    }

    public void setLArm_R(float LArm_R) {
        this.LArm_R = LArm_R;
    }

    public float getLForearm_V() {
        return LForearm_V;
    }

    public void setLForearm_V(float LForearm_V) {
        this.LForearm_V = LForearm_V;
    }

    public float getLForearm_H() {
        return LForearm_H;
    }

    public void setLForearm_H(float LForearm_H) {
        this.LForearm_H = LForearm_H;
    }

    public float getLForearm_R() {
        return LForearm_R;
    }

    public void setLForearm_R(float lForearm_R) {
        LForearm_R = lForearm_R;
    }

    public float getLHand_V() {
        return LHand_V;
    }

    public void setLHand_V(float LHand_V) {
        this.LHand_V = LHand_V;
    }

    public float getLHand_H() {
        return LHand_H;
    }

    public void setLHand_H(float LHand_H) {
        this.LHand_H = LHand_H;
    }

    public float getLThumb1V() {
        return LThumb1V;
    }

    public void setLThumb1V(float LThumb1V) {
        this.LThumb1V = LThumb1V;
    }

    public float getLThumb1H() {
        return LThumb1H;
    }

    public void setLThumb1H(float LThumb1H) {
        this.LThumb1H = LThumb1H;
    }

    public float getLThumb2V() {
        return LThumb2V;
    }

    public void setLThumb2V(float LThumb2V) {
        this.LThumb2V = LThumb2V;
    }

    public float getLIndex1V() {
        return LIndex1V;
    }

    public void setLIndex1V(float LIndex1V) {
        this.LIndex1V = LIndex1V;
    }

    public float getLIndex1H() {
        return LIndex1H;
    }

    public void setLIndex1H(float LIndex1H) {
        this.LIndex1H = LIndex1H;
    }

    public float getLIndex2V() {
        return LIndex2V;
    }

    public void setLIndex2V(float LIndex2V) {
        this.LIndex2V = LIndex2V;
    }

    public float getLMiddle1V() {
        return LMiddle1V;
    }

    public void setLMiddle1V(float LMiddle1V) {
        this.LMiddle1V = LMiddle1V;
    }

    public float getLMiddle1H() {
        return LMiddle1H;
    }

    public void setLMiddle1H(float LMiddle1H) {
        this.LMiddle1H = LMiddle1H;
    }

    public float getLMiddle2V() {
        return LMiddle2V;
    }

    public void setLMiddle2V(float LMiddle2V) {
        this.LMiddle2V = LMiddle2V;
    }

    public float getLRing1V() {
        return LRing1V;
    }

    public void setLRing1V(float LRing1V) {
        this.LRing1V = LRing1V;
    }

    public float getLRing1H() {
        return LRing1H;
    }

    public void setLRing1H(float LRing1H) {
        this.LRing1H = LRing1H;
    }

    public float getLRing2V() {
        return LRing2V;
    }

    public void setLRing2V(float LRing2V) {
        this.LRing2V = LRing2V;
    }

    public float getLPinky1V() {
        return LPinky1V;
    }

    public void setLPinky1V(float LPinky1V) {
        this.LPinky1V = LPinky1V;
    }

    public float getLPinky1H() {
        return LPinky1H;
    }

    public void setLPinky1H(float LPinky1H) {
        this.LPinky1H = LPinky1H;
    }

    public float getLPinky2V() {
        return LPinky2V;
    }

    public void setLPinky2V(float LPinky2V) {
        this.LPinky2V = LPinky2V;
    }

}
