package com.marlin.tralp.Transcriber.Models;

import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.ContentResolver;
import android.content.Context;
import android.content.ContextWrapper;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.IntentSender;
import android.content.ServiceConnection;
import android.content.SharedPreferences;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.content.res.AssetManager;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.database.DatabaseErrorHandler;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.UserHandle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.Display;

import com.google.android.gms.vision.face.FaceDetector;
import com.marlin.tralp.AppContext;
import com.marlin.tralp.Conexao.Dao.MovimentoDAO;
import com.marlin.tralp.MainApplication;
import com.marlin.tralp.R;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.Random;

/**
 * Created by aneves on 5/16/2016.
 */
public class Movement {

    int codMov;
    int codSign;
    int codMao;
    int card;
    Date tempo;
    int tipo;
    boolean processed;
    Context context;
    AnimationParameters animationParameters;

    public Movement(){
        context = new AppContext().getAppContext();
    }

    public Movement(int codMov, int codSign,int codMao,int card,Date tempo,int param1,
                    int param2,int param3,int param4,int param5,int param6,
                    int posicaoInicialX,int posicaoInicialY){
        context = new AppContext().getAppContext();
    }

    public AnimationParameters getAnimationParameters() {
        return animationParameters;
    }

    public void setAnimationParameters(AnimationParameters animationParameters) {
        this.animationParameters = animationParameters;
    }

    public ArrayList<FeatureStructure> GetMovementsStartInPosition(int x, int y, double tolerance){
        ArrayList<FeatureStructure> movementsResult;
        movementsResult = new ArrayList<FeatureStructure>();
        movementsResult.add(CreateMovement(522, 217));
        movementsResult.add(CreateMovement(528, 207));
        movementsResult.add(CreateMovement(80, 236));
        movementsResult.add(CreateMovement(1024, 117));
        movementsResult.add(CreateMovement(759, 417));

        return movementsResult;
    }

    private FeatureStructure CreateMovement(int handCenterX, int handCenterY) {
        FeatureStructure tempFS = new FeatureStructure();
        tempFS.handCenterX = handCenterX; //@INFO This [May] vary with the screen orientation
        tempFS.handCenterY = handCenterY;
    //    Log.d("FeatureAnnotation: ", "secProcessed: " + img.second + "  X " + tempObj[0].x + "  Y " + tempObj[0].y);
    //    if (tempObj.length > 1)
    //        Log.d("FeatureAnnotation: ", "secProcessed: " + img.second + "  X " + tempObj[1].x + "  Y " + tempObj[1].y);
        tempFS.pinky = FingerState.stretched;
        tempFS.ring = FingerState.stretched;
        tempFS.middle = FingerState.stretched;
        tempFS.index = FingerState.stretched;
        tempFS.thumb = FingerState.stretched;

        tempFS.isSeparetedFingers = false;
        tempFS.touchingFingers = false;
        //@Todo determine how to find angles
        //Rect.br == bottom rigth point
        //Rect.tl == top left
        tempFS.palmAng1 = 0; //guess
        tempFS.palmAng2 = 90; //Likely
        tempFS.palmAng3 = 0; //precise

        return tempFS;
    }

    public boolean MovementsThroughThatAddress(Sign candidate, FeatureStructure frame, double tolerance) {
        ArrayList<FeatureStructure> movementsResult = new ArrayList<FeatureStructure>();
        //movementsResult.add(CreateMovement(522, 217));
        //new Math();
        int i = (int) (Math.random() % 2);
        return i > 0;
    }

    public ArrayList<Movement> GetAllMovements(int codSin) {
        ArrayList<Movement> movements = new ArrayList<Movement>();
        MovimentoDAO dao = new MovimentoDAO(context);
        movements = dao.ObterTodosMovimentosDeUmSinal(codSin);

        return movements;
    }
    public ArrayList<MovementDTO> GetAllMovementsDTO(int codSin) {
        ArrayList<MovementDTO> movements = new ArrayList<MovementDTO>();
        MovimentoDAO dao = new MovimentoDAO(context);
        movements = dao.ObterTodosMovimentosDeUmSinalDTO(codSin);

        return movements;
    }

    public boolean isProcessed() {
        return processed;
    }

    public void setProcessed(boolean processed) {
        this.processed = processed;
    }

    public int getCodMov() {
        return codMov;
    }

    public void setCodMov(int codMov) {
        this.codMov = codMov;
    }
    public int getCodSign() {
        return codSign;
    }

    public void setCodSign(int codSign) {
        this.codSign = codSign;
    }

    public int getCodMao() {
        return codMao;
    }

    public void setCodMao(int codMao) {
        this.codMao = codMao;
    }

    public int getCard() {
        return card;
    }

    public void setCard(int card) {
        this.card = card;
    }

    public Date getTempo() {
        return tempo;
    }

    public void setTempo(Date tempo) {
        this.tempo = tempo;
    }

    public int getTipo() {
        return tipo;
    }

    public void setTipo(int tipo) {
        this.tipo = tipo;
    }

}
