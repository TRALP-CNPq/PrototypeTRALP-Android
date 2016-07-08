package com.marlin.tralp.Transcriber.Models;

import android.app.Application;
import android.content.Context;
import android.util.Log;

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
import com.marlin.tralp.Transcriber.ImageProcess.Point3D;
import com.marlin.tralp.Transcriber.ImageProcess.Position;
import com.marlin.tralp.Transcriber.Models.Dtos.SignAnimationDto;

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
        context = app.getApplicationContext();
    }
    public Sign(MainApplication app) {
        this.codSin = codSin;
        this.mApp = app;
        context = app.getApplicationContext();
    }
    public Sign() {
        context = new AppContext().getAppContext();
     //   this.codSin = 183;
    }
    public Sign(Context context) {
        this.context = context;
    }
    public Sign(int codSin, int codPart, int codOp, int codEf, int codCm,
                int codPal, String palavra, HandConfiguration hand, PivotPoint pivotPoint,
                FaceExpression faceExpression, PalmOrientation palmOrientation, ArrayList<Movement> movements,
                OrientacaoQuadrante orientacaoQuadrante, int ultimaPosX, int ultimaPosY) {
        this.codSin = codSin;
        this.codPart = codPart;
        this.codOp = codOp;
        this.codEf = codEf;
        this.codCm = codCm;
        this.codPal = codPal;
        this.palavra = palavra;
        this.hand = hand;
        this.pivotPoint = pivotPoint;
        this.faceExpression = faceExpression;
        this.palmOrientation = palmOrientation;
        this.movements = movements;
        this.orientacaoQuadrante = orientacaoQuadrante;
        this.ultimaPosX = ultimaPosX;
        this.ultimaPosY = ultimaPosY;
    }

    public ArrayList<Sign> GetSignsStartInPosition(int faceX,int faceY, float faceWidth, float faceHeight,
                                                   int x, int y, float handWidth, float handHeight, double tolerance){
        ArrayList<Sign> signsResult = new ArrayList<Sign>();
        Log.d("GetSignsStartInPositi: ", "faceX: " + faceX + " faceY: " + faceY + " faceWidth: " + faceWidth +
                "  faceHeight: " + faceHeight + "  x: " + x + "  y: " + y + " handWidth: " + handWidth +
                "  handHeight " + handHeight + "  tolerance " + tolerance);
        Point3D point3d1 = new Position().handPosition(faceX, faceY, (faceX + faceWidth),
                (faceY + faceHeight), x, y, (x+handWidth),(y+handHeight) );
        ArrayList<Point3D> quads1 = QuadrantesPossiveis(faceX, faceY, faceWidth, faceHeight, x, y, handWidth, handHeight, tolerance, point3d1);
        Log.d("GetSignsStartInPositi: ", "quads1.size: " + quads1.size());
        SignDAO daoSign = new SignDAO(context.getApplicationContext());
        signsResult = daoSign.ObterOrientacaoQuaPorLocalDaMaoAproximada(quads1, 1);
        //(x*(1-tolerance)), (x*(1+tolerance)),(y*(1-tolerance)), (y*(1+tolerance)),1);
    //    SignProperties signProperties = daoSign.ObterSignProperties(tempSign.codSin);
//        signsResult.add(CreateSign(0, 0, 522, 217));
//        signsResult.add(CreateSign(0, 0, 528, 207));
//        signsResult.add(CreateSign(0, 0, 80, 236));
//        signsResult.add(CreateSign(0, 0, 1024, 117));
//        signsResult.add(CreateSign(0, 0, 759, 417));

        return signsResult;
    }

    private ArrayList<Point3D> QuadrantesPossiveis(int faceX, int faceY, float faceWidth, float faceHeight, int x, int y, float handWidth, float handHeight, double tolerance, Point3D point3d1) {
        ArrayList<Point3D> quads = new ArrayList<Point3D>();
        quads.add(point3d1);
        point3d1 = new Position().handPosition(faceX, faceY, (faceX + faceWidth),(faceY + faceHeight),
                x*(1-tolerance), y*(1-tolerance), (x+handWidth),(y+handHeight) );
        quads.add(point3d1);
        point3d1 = new Position().handPosition(faceX, faceY, (faceX + faceWidth),(faceY + faceHeight),
                x, y*(1-tolerance), (x+handWidth),(y+handHeight) );
        quads.add(point3d1);
        point3d1 = new Position().handPosition(faceX, faceY, (faceX + faceWidth),(faceY + faceHeight),
                x*(1+tolerance), y*(1-tolerance), (x+handWidth),(y+handHeight) );
        quads.add(point3d1);
        point3d1 = new Position().handPosition(faceX, faceY, (faceX + faceWidth),(faceY + faceHeight),
                x*(1-tolerance), y, (x+handWidth),(y+handHeight) );
        quads.add(point3d1);
        point3d1 = new Position().handPosition(faceX, faceY, (faceX + faceWidth),(faceY + faceHeight),
                x*(1+tolerance), y, (x+handWidth),(y+handHeight) );
        quads.add(point3d1);
        point3d1 = new Position().handPosition(faceX, faceY, (faceX + faceWidth),(faceY + faceHeight),
                x*(1-tolerance), y*(1+tolerance), (x+handWidth),(y+handHeight) );
        quads.add(point3d1);
        point3d1 = new Position().handPosition(faceX, faceY, (faceX + faceWidth),(faceY + faceHeight),
                x, y*(1+tolerance), (x+handWidth),(y+handHeight) );
        quads.add(point3d1);
        point3d1 = new Position().handPosition(faceX, faceY, (faceX + faceWidth),(faceY + faceHeight),
                x*(1+tolerance), y*(1+tolerance), (x+handWidth),(y+handHeight) );
        quads.add(point3d1);
        return quads;
    }

    private Sign CreateSign(int quadranteX, int quadranteY, int handCenterX, int handCenterY) {
        //@TODO busca no banco usando quadranteX e quadranteY como posicoes iniciais
        Sign tempSign = new Sign();
        tempSign.codCm = 1; //@INFO This [May] vary with the screen orientation
        tempSign.codEf = 2;
        tempSign.codOp = 3;
        tempSign.codPal = 11;
        tempSign.codSin = 187;
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
        SignDAO daoSign = new SignDAO(context.getApplicationContext());
        SignProperties signProperties = daoSign.ObterSignProperties(tempSign.codSin);
        tempSign.orientacaoQuadrante = new SignDAO(context.getApplicationContext())
                                .ObterOrientacaoQuadrante(signProperties.getCodPonti());

        ultimaPosX = handCenterX ;
        ultimaPosY = handCenterY;

        return tempSign;
    }

    public String GetSignsToAnimationSerialized(String frase) {
        String[] palavras = frase.split(" ");
        //    String[] sinais = null;
        SignAnimationDto[] sadto = new SignAnimationDto[palavras.length];
        SignDAO signDao = new SignDAO(context);

        for(int i=0; i<palavras.length; i++){      //  String palavra : palavras

            try {
                Sign sign = signDao.ObterSinalParaAnimacao(palavras[i]);
                if(sign != null){
                    sadto[i] = new SignAnimationDto(sign);
                }

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        //String dtoMov="", JsonSerializedSigns="", animParDTO="";

        //for (SignAnimationDto sad:sadto){
        //    for (MovementDTO mov: sad.getMovements()){
        //        Gson gson = new Gson();
        //        dtoMov = dtoMov + gson.toJson(mov);
        //    }
        //     sad.setMovements(null);
        //    sad.movDTO = dtoMov;
        //    for (AnimationParametersDTO par: sad.getAnimationParameters()){
        //        Gson gson = new Gson();
        //        animParDTO = animParDTO + gson.toJson(par);
        //    }
        //    sad.setAnimationParameters(null);
        //    sad.AnimParDTO = animParDTO;
        //    JsonSerializedSigns = JsonSerializedSigns+SerializeSigns(sad);
        //}

        String JsonSerializedSigns = SignAnimationDto.Serialize(sadto);
        return JsonSerializedSigns;
    }

    public String SerializeSigns(ArrayList<SignAnimationDTO> sadto) {
        Gson gson = new Gson();
    //    Foo<Bar> foo = new Foo<Bar>();

        String textSerilized = gson.toJson(sadto);
        return textSerilized;
    }
    public String SerializeSigns(SignAnimationDTO sadto) {
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

    public int getMovementsSize(){
        return movements.size();
    }
    public void removeMovement(int index){
        movements.remove(index);
    }
    public void removeMovement(Movement obj){
        movements.remove(obj);
    }
    public Movement getMovement(int index) {
        return movements.get(index);
    }

}
