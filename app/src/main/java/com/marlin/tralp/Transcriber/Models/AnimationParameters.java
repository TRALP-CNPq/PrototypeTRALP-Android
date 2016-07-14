package com.marlin.tralp.Transcriber.Models;

import android.content.Context;

import com.marlin.tralp.AppContext;
import com.marlin.tralp.Conexao.Dao.AnimationParametersDAO;
import com.marlin.tralp.Conexao.Dao.MovimentoDAO;

import java.util.ArrayList;

/**
 * Created by aneves on 6/1/2016.
 */
public class AnimationParameters {

    private int codMov;
    private float RShoulder_V;
    private float RShoulder_H;
    private float RArm_V;
    private float RArm_H;
    private float RArm_R;
    private float RForearm_V;
    private float RForearm_H;
    private float RHand_V;
    private float RHand_H;
    private float RThumb;
    private float RIndex;
    private float RMiddle;
    private float RRing;
    private float RPinky;
    private float LShoulder_V;
    private float LShoulder_H;
    private float LArm_V;
    private float LArm_H;
    private float LArm_R;
    private float LForearm_V;
    private float LForearm_H;
    private float Lforearm_R;
    private float LHand_V;
    private float LHand_H;
    private float LThumb;
    private float LIndex;
    private float LMiddle;
    private float LRing;
    private float LPinky;
    Context context;

    public AnimationParameters(){
        context = new AppContext().getAppContext();

    }
    public AnimationParameters(Context context){
        this.context = context;

    }

    public AnimationParameters(int codMov){
        this.codMov = codMov;
    }

    public ArrayList<AnimationParameters> GetAllAnimationParametersSign(ArrayList<Movement> movements) {
        ArrayList<AnimationParameters> ap = new ArrayList<AnimationParameters>();
        AnimationParametersDAO dao = new AnimationParametersDAO(context);
        int codMov;
        for (Movement movement : movements) {
            codMov =  movement.codMov;
            ap.add(dao.ObterParametrosPorId(movement.codMov));
        }
        return ap;
    }
    public ArrayList<AnimationParametersDTO> GetAllAnimationParametersSignDTO(ArrayList<MovementDTO> movements) {
        ArrayList<AnimationParametersDTO> ap = new ArrayList<AnimationParametersDTO>();
        AnimationParametersDAO dao = new AnimationParametersDAO(context);
        int codMov;
        for (MovementDTO movement : movements) {
            codMov =  movement.codMov;
            ap.add(dao.ObterParametrosPorIdDTO(movement.codMov));
        }
        return ap;
    }
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

    public float getRThumb() {
        return RThumb;
    }

    public void setRThumb(float RThumb) {
        this.RThumb = RThumb;
    }

    public float getRIndex() {
        return RIndex;
    }

    public void setRIndex(float RIndex) {
        this.RIndex = RIndex;
    }

    public float getRMiddle() {
        return RMiddle;
    }

    public void setRMiddle(float RMiddle) {
        this.RMiddle = RMiddle;
    }

    public float getRRing() {
        return RRing;
    }

    public void setRRing(float RRing) {
        this.RRing = RRing;
    }

    public float getRPinky() {
        return RPinky;
    }

    public void setRPinky(float RPinky) {
        this.RPinky = RPinky;
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

    public float getLforearm_R() {
        return Lforearm_R;
    }

    public void setLforearm_R(float lforearm_R) {
        Lforearm_R = lforearm_R;
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

    public float getLThumb() {
        return LThumb;
    }

    public void setLThumb(float LThumb) {
        this.LThumb = LThumb;
    }

    public float getLIndex() {
        return LIndex;
    }

    public void setLIndex(float LIndex) {
        this.LIndex = LIndex;
    }

    public float getLMiddle() {
        return LMiddle;
    }

    public void setLMiddle(float LMiddle) {
        this.LMiddle = LMiddle;
    }

    public float getLRing() {
        return LRing;
    }

    public void setLRing(float LRing) {
        this.LRing = LRing;
    }

    public float getLPinky() {
        return LPinky;
    }

    public void setLPinky(float LPinky) {
        this.LPinky = LPinky;
    }

    public Integer getIdentifier() {
        return Integer.valueOf(this.codMov);
    }

}
