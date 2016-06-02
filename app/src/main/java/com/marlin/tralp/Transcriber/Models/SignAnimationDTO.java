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

    private HandConfiguration hand;
    private PivotPoint pivotPoint;
    private FaceExpression faceExpression;
    private PalmOrientation palmOrientation;
    private ArrayList<Movement> movements;
    private OrientacaoQuadrante orientacaoQuadrante;
    private ArrayList<AnimationParameters> AnimationParameters;

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
//        this.mApp = new MainApplication();
//        context = mApp.getApplicationContext();
//        PalavraDAO dao = new PalavraDAO(context);
//    //    tempSign.palavra = dao.BuscarPalavraPorId(tempSign.codPal);
//        SignDAO signDao = new SignDAO(context);
//        SignAnimationDTO sa = signDao.ObterSinalParaAnimacao(palavra);

    }
    public ArrayList<SignAnimationDTO> GetSignsStartInPosition(int x, int y, double tolerance){
        ArrayList<SignAnimationDTO> signsResult;
        signsResult = new ArrayList<SignAnimationDTO>();
        signsResult.add(CreateSign(522, 217));
        signsResult.add(CreateSign(528, 207));
        signsResult.add(CreateSign(80, 236));
        signsResult.add(CreateSign(1024, 117));
        signsResult.add(CreateSign(759, 417));

        return signsResult;
    }

    private SignAnimationDTO CreateSign(int handCenterX, int handCenterY) {
        SignAnimationDTO tempSign = new SignAnimationDTO();
        tempSign.codCm = 1; //@INFO This [May] vary with the screen orientation
        tempSign.codEf = 2;
        tempSign.codOp = 3;
        tempSign.codPal = 11;
        tempSign.codSin = 5;
        tempSign.faceExpression = new FaceExpression();
        tempSign.codPart = 6;

        tempSign.hand = new HandConfiguration();
    //    tempSign.movements = new ArrayList<Movement>();
        tempSign.movements = new Movement().GetAllMovements(tempSign.codSin);
        tempSign.palmOrientation = new PalmOrientation(); //guess
        tempSign.pivotPoint = new PivotPoint(); //Likely
        //   context = this;
        context = mApp.getApplicationContext();     //new AppContext();
        PalavraDAO dao = new PalavraDAO(context);
        tempSign.palavra = dao.BuscarPalavraPorId(tempSign.codPal);
        tempSign.orientacaoQuadrante = new SignDAO(context).ObterOrientacaoQuadrante(getCodSin());

        ultimaPosX = tempSign.orientacaoQuadrante.getOrientacaoX();
        ultimaPosY = tempSign.orientacaoQuadrante.getOrientacaoY();

        return tempSign;
    }

    public String GetSignsToAnimationSerialized(String frase) {
        String[] palavras = frase.split(" ");
        //    String[] sinais = null;
        ArrayList<SignAnimationDTO> sadto = new ArrayList<SignAnimationDTO>();

        for(int i=0; i<=palavras.length; i++){      //  String palavra : palavras
            Sign sign = new Sign();
            SignAnimationDTO sa = new SignAnimationDTO(palavras[i]);
            sadto.add(sa);
            //    sinais[i] = SerializeSigns(palavras[i]);
        }
        String JsonSerializedSigns = SerializeSigns(sadto);
        return JsonSerializedSigns;
    }

    public String SerializeSigns(ArrayList<SignAnimationDTO> sadto) {
        Gson gson = new Gson();
        //    Foo<Bar> foo = new Foo<Bar>();

        String textSerilized = gson.toJson(sadto);
        return textSerilized;
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

    public ArrayList<Movement> getMovements() {
        return movements;
    }

    public void setMovements(ArrayList<Movement> movements) {
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

    public ArrayList<AnimationParameters> getAnimationParameters() {
        return AnimationParameters;
    }

    public void setAnimationParameters(ArrayList<AnimationParameters> animationParameters) {
        AnimationParameters = animationParameters;
    }

}
