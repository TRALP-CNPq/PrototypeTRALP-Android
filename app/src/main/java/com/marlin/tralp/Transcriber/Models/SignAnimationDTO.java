package com.marlin.tralp.Transcriber.Models;

import android.content.Context;

import com.google.gson.Gson;
import com.marlin.tralp.AppContext;
import com.marlin.tralp.Conexao.Dao.PalavraDAO;
import com.marlin.tralp.Conexao.Dao.SignDAO;
import com.marlin.tralp.MainApplication;
import com.marlin.tralp.Model.FaceExpression;
import com.marlin.tralp.Model.HandConfiguration;
import com.marlin.tralp.Model.Palavra;
import com.marlin.tralp.Model.PalmOrientation;
import com.marlin.tralp.Model.PivotPoint;

import java.util.ArrayList;

/**
 * Created by aneves on 5/19/2016.
 */
public class SignAnimationDTO {
    private int codSin;
    private int codPart;
    private int codOp;
    private int codEf;
    private int codCm;
    private int codPal;
    private String palavra;
    public String movDTO;
    public String AnimParDTO;

    private HandConfiguration hand;
    private PivotPoint pivotPoint;
    private FaceExpression faceExpression;
    private PalmOrientation palmOrientation;
    private ArrayList<MovementDTO> movements;
    private OrientacaoQuadrante orientacaoQuadrante;
    private ArrayList<AnimationParametersDTO> AnimationParameters;

    private int ultimaPosX;
    private int ultimaPosY;
    private Context context;
    private MainApplication mApp;

    public SignAnimationDTO(int codSin) {
        this.codSin = codSin;
    }
    public SignAnimationDTO(MainApplication app, int codSin) {
        this.codSin = codSin;
        this.mApp = app;
    }
    public SignAnimationDTO(MainApplication app) {
        this.codSin = codSin;
        this.mApp = app;
    }
    public SignAnimationDTO() {
        this.mApp = new MainApplication();
        context = new AppContext().getAppContext();
    }

    public SignAnimationDTO(String palavra) {

    }




    public int getCodSin() {
        return codSin;
    }

    public void setCodSin(int codSin) {
        this.codSin = codSin;
    }

    public int getCodPart() {
        return codPart;
    }

    public void setCodPart(int codPart) {
        this.codPart = codPart;
    }

    public int getCodOp() {
        return codOp;
    }

    public void setCodOp(int codOp) {
        this.codOp = codOp;
    }

    public int getCodEf() {
        return codEf;
    }

    public void setCodEf(int codEf) {
        this.codEf = codEf;
    }

    public int getCodCm() {
        return codCm;
    }

    public void setCodCm(int codCm) {
        this.codCm = codCm;
    }

    public int getCodPal() {
        return codPal;
    }

    public void setCodPal(int codPal) {
        this.codPal = codPal;
    }

    public String getPalavra() {
        return palavra;
    }

    public void setPalavra(String palavra) {
        this.palavra = palavra;
    }

    public HandConfiguration getHand() {
        return hand;
    }

    public void setHand(HandConfiguration hand) {
        this.hand = hand;
    }

    public PivotPoint getPivotPoint() {
        return pivotPoint;
    }

    public void setPivotPoint(PivotPoint pivotPoint) {
        this.pivotPoint = pivotPoint;
    }

    public FaceExpression getFaceExpression() {
        return faceExpression;
    }

    public void setFaceExpression(FaceExpression faceExpression) {
        this.faceExpression = faceExpression;
    }

    public PalmOrientation getPalmOrientation() {
        return palmOrientation;
    }

    public void setPalmOrientation(PalmOrientation palmOrientation) {
        this.palmOrientation = palmOrientation;
    }

    public ArrayList<MovementDTO> getMovements() {
        return movements;
    }

    public void setMovements(ArrayList<MovementDTO> movements) {
        this.movements = movements;
    }

    public Context getContext() {
        return context;
    }

    public void setContext(Context context) {
        this.context = context;
    }

    public OrientacaoQuadrante getOrientacaoQuadrante() {
        return orientacaoQuadrante;
    }

    public void setOrientacaoQuadrante(OrientacaoQuadrante orientacaoQuadrante) {
        this.orientacaoQuadrante = orientacaoQuadrante;
    }

    public int getUltimaPosX() {
        return ultimaPosX;
    }

    public void setUltimaPosX(int ultimaPosX) {
        this.ultimaPosX = ultimaPosX;
    }

    public int getUltimaPosY() {
        return ultimaPosY;
    }

    public void setUltimaPosY(int ultimaPosY) {
        this.ultimaPosY = ultimaPosY;
    }

    public ArrayList<AnimationParametersDTO> getAnimationParameters() {
        return AnimationParameters;
    }

    public void setAnimationParameters(ArrayList<AnimationParametersDTO> animationParameters) {
        AnimationParameters = animationParameters;
    }

}
