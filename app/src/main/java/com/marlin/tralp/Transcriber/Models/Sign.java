package com.marlin.tralp.Transcriber.Models;

import android.app.Application;
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
import com.marlin.tralp.R;

import java.sql.Array;
import java.text.ParseException;
import java.util.ArrayList;

/**
 * Created by aneves on 5/19/2016.
 */
public class Sign {
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

    private int ultimaPosX;
    private int ultimaPosY;
    private Context context;
    private MainApplication mApp;

    public Sign(int codSin) {
        this.codSin = codSin;
    }
    public Sign(MainApplication app, int codSin) {
        this.codSin = codSin;
        this.mApp = app;
        context = new AppContext().getAppContext();
    }
    public Sign(MainApplication app) {
        this.codSin = codSin;
        this.mApp = app;
        context = new AppContext().getAppContext();
    }
    public Sign() {
        context = new AppContext().getAppContext();
    }

    public ArrayList<Sign> GetSignsStartInPosition(int x, int y, double tolerance){
        ArrayList<Sign> signsResult;
        signsResult = new ArrayList<Sign>();
        signsResult.add(CreateSign(522, 217));
        signsResult.add(CreateSign(528, 207));
        signsResult.add(CreateSign(80, 236));
        signsResult.add(CreateSign(1024, 117));
        signsResult.add(CreateSign(759, 417));

        return signsResult;
    }

    private Sign CreateSign(int handCenterX, int handCenterY) {
        Sign tempSign = new Sign();
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
        context = mApp;     //new AppContext();
        PalavraDAO dao = new PalavraDAO(context.getApplicationContext());
        tempSign.palavra = dao.BuscarPalavraPorId(tempSign.codPal);
        tempSign.orientacaoQuadrante = new SignDAO(context.getApplicationContext()).ObterOrientacaoQuadrante(getCodSin());

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
            SignAnimationDTO sa = new SignAnimationDTO();
            SignDAO signDao = new SignDAO(context);
            try {
                sa = signDao.ObterSinalParaAnimacao(palavras[i]);
            } catch (NoSuchFieldException e) {
                e.printStackTrace();
            } catch (ParseException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            } catch (InstantiationException e) {
                e.printStackTrace();
            }
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
}