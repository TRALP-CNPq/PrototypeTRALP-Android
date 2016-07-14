package com.marlin.tralp.Conexao.Dao;

import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.marlin.tralp.Conexao.DbConnection;
import com.marlin.tralp.Conexao.Repository.PropertyConfig;
import com.marlin.tralp.Model.FaceExpression;
import com.marlin.tralp.Model.HandConfiguration;
import com.marlin.tralp.Model.Palavra;
import com.marlin.tralp.Model.PalmOrientation;
import com.marlin.tralp.Model.PivotPoint;
import com.marlin.tralp.Transcriber.ImageProcess.Point3D;
import com.marlin.tralp.Transcriber.Models.AnimationParameters;
import com.marlin.tralp.Transcriber.Models.AnimationParametersDTO;
import com.marlin.tralp.Transcriber.Models.Movement;
import com.marlin.tralp.Transcriber.Models.MovementDTO;
import com.marlin.tralp.Transcriber.Models.OrientacaoQuadrante;
import com.marlin.tralp.Transcriber.Models.Sign;
import com.marlin.tralp.Transcriber.Models.SignAnimationDTO;
import com.marlin.tralp.Transcriber.Models.SignProperties;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

/**
 * Created by aneves on 5/20/2016.
 */
public class SignDAO {
    private SQLiteDatabase db;
    private Context context;

    public SignDAO(Context context) {
        //cria uma nova conexao com o banco e coloca ele como editavel pela Aplicacao.
        this.context = context;
        DbConnection connection = new DbConnection(this.context);
        try {
            connection.createDataBase();
        } catch (IOException ioe) {
            throw new Error("Unable to create database");
        }
        try {
            db = connection.openDataBase();
        } catch (SQLException sqle) {
            sqle.printStackTrace();
            Log.e("SignDAO(context)", "Error ao abrir a conexão", sqle);
            //throw sqle;
        } catch (java.sql.SQLException e) {
            e.printStackTrace();
            Log.e("SignDAO(context)", "Error ao abrir a conexão", e);
        }
    }

    public ArrayList<Sign> ObterOrientacaoQuaPorLocalDaMaoAproximada(ArrayList<Point3D> quads1, int qual_mao) {
        String[] colunas = new String[]{"*"};
        for (Point3D quad : quads1) {
            Log.d("SignDAO: ", "quadrante X = " + Double.toString(quad.getX()) + "   Y = " + Double.toString(quad.getY()));
        }
        String[] selectionArgs = new String[]{Double.toString(quads1.get(0).getX()), Double.toString(quads1.get(0).getY()),
                Double.toString(quads1.get(1).getX()), Double.toString(quads1.get(1).getY()),
                Double.toString(quads1.get(2).getX()), Double.toString(quads1.get(2).getY()),
                Double.toString(quads1.get(3).getX()), Double.toString(quads1.get(3).getY()),
                Double.toString(quads1.get(4).getX()), Double.toString(quads1.get(4).getY()),
                Double.toString(quads1.get(5).getX()), Double.toString(quads1.get(5).getY()),
                Double.toString(quads1.get(6).getX()), Double.toString(quads1.get(6).getY()),
                Double.toString(quads1.get(7).getX()), Double.toString(quads1.get(7).getY()),
                Double.toString(quads1.get(8).getX()), Double.toString(quads1.get(8).getY())};
//        Cursor busca = db.query("ORIENTACAO_QUADRANTE", colunas, "(ORIENTACAOX = ? AND ORIENTACAOY = ? ) OR "
//                + "(ORIENTACAOX = ? AND ORIENTACAOY = ? ) OR (ORIENTACAOX = ? AND ORIENTACAOY = ? ) OR "
//                        + "(ORIENTACAOX = ? AND ORIENTACAOY = ? ) OR (ORIENTACAOX = ? AND ORIENTACAOY = ? ) OR "
//                        + "(ORIENTACAOX = ? AND ORIENTACAOY = ? ) OR (ORIENTACAOX = ? AND ORIENTACAOY = ? ) OR "
//                        + "(ORIENTACAOX = ? AND ORIENTACAOY = ? ) OR (ORIENTACAOX = ? AND ORIENTACAOY = ? ) OR "
//                        + "(ORIENTACAOX = ? AND ORIENTACAOY = ? )", selectionArgs, null, null, null, null);
                String sql = "SELECT d.CODOQUA as CODOQUA, sc.CODSIN as CODSIN, sc.CODMAO as CODMAO, " +
                "sc.CODCM as CODCM, sc.CODPONTI as CODPONTI, sc.CODPART as CODPART, sc.CODOP as CODOP,  " +
                "d.ORIENTACAOX as ORIENTACAOX, d.ORIENTACAOY as ORIENTACAOY, ps.CODPAL as CODPAL, di.TOKEN as PALAVRA " +
                "FROM ORIENTACAO_QUADRANTE d " +
                "INNER JOIN SINALXCARACTERISTICA sc ON (sc.CODPONTI = d.CODOQUA) " +
                "INNER JOIN PALAVRAXSINAL ps ON (ps.CODSIN = sc.CODSIN) " +
                "INNER JOIN dicionario di ON (di.CODPAL = ps.CODPAL) " +
                "INNER JOIN SINAL s ON (s.CODSIN = sc.CODSIN) " +
                "LEFT JOIN CONFIGURACAO_MAO cm ON (cm.CODCM = sc.CODCM) " +
                "LEFT JOIN ORIENTACAO_PALMA op ON (op.CODOP = sc.CODOP) " +
                "LEFT JOIN PONTO_ARTICULACAO pa ON (pa.CODPART = sc.CODPART) " +
                "WHERE (ORIENTACAOX = ? AND ORIENTACAOY = ? ) OR (ORIENTACAOX = ? AND ORIENTACAOY = ? ) OR "
                + "(ORIENTACAOX = ? AND ORIENTACAOY = ? ) OR (ORIENTACAOX = ? AND ORIENTACAOY = ? ) OR "
                + "(ORIENTACAOX = ? AND ORIENTACAOY = ? ) OR (ORIENTACAOX = ? AND ORIENTACAOY = ? ) OR "
                + "(ORIENTACAOX = ? AND ORIENTACAOY = ? ) OR (ORIENTACAOX = ? AND ORIENTACAOY = ? ) OR "
                + "(ORIENTACAOX = ? AND ORIENTACAOY = ? )";
        Cursor busca = db.rawQuery(sql, selectionArgs);
        busca.moveToFirst();
        ArrayList<Sign> signsResult = new ArrayList<Sign>();
        signsResult = CreateSign(busca);

        busca.close();
        db.close();
        return signsResult;
    }

    private ArrayList<Sign> CreateSign(Cursor busca) {
        //@TODO busca no banco usando quadranteX e quadranteY como posicoes iniciais

        ArrayList<Sign> signResult = new ArrayList<Sign>();
        if (busca.getCount() > 0) {
//            busca.moveToFirst();
            do {
                Sign tempSign = new Sign();
                tempSign.setCodSin(busca.getInt(1));
                tempSign.setCodCm(busca.getInt(3));
                tempSign.setCodPart(busca.getInt(5));
                tempSign.setUltimaPosX(busca.getInt(7));
                tempSign.setUltimaPosY(busca.getInt(8));
                tempSign.setCodPal(busca.getInt(9));
                tempSign.setPalavra(busca.getString(10));
                tempSign.setMovements(new Movement().GetAllMovements(tempSign.getCodSin()));
                if (tempSign.getMovementsSize() > 0 && tempSign.getPalavra() != null) {
                    signResult.add(tempSign);
                }
            } while (busca.moveToNext());
        }
        return signResult;
    }
    public OrientacaoQuadrante ObterOrientacaoQuadrante(int codQua) {
        String[] colunas = new String[]{"*"};
        Log.d("SignDAO: ", "CODOQUA = " + Integer.toString(codQua));
        String[] selectionArgs = new String[]{Integer.toString(codQua)};
        Cursor busca = db.query("ORIENTACAO_QUADRANTE", colunas, "CODOQUA = ? ", selectionArgs, null, null, null, null);
        OrientacaoQuadrante orientacaoQuadrante = new OrientacaoQuadrante();
        busca.moveToFirst();
        orientacaoQuadrante.setOrientacaoX(busca.getInt(2));
        orientacaoQuadrante.setOrientacaoY(busca.getInt(3));
        Log.d("SignDAO: ", "OriQuad X = " + Integer.toString(busca.getInt(2)) + "   Y = " + Integer.toString(busca.getInt(3)));
        busca.close();
        db.close();
        return  orientacaoQuadrante;
    }

    public SignProperties ObterSignProperties(int codSin) {
        String[] colunas = new String[]{"*"};
        String[] selectionArgs = new String[]{Integer.toString(codSin)};
        Log.d("SignDAO: ", "CODSIN = " + Integer.toString(codSin));
        Cursor busca = db.query("SINALXCARACTERISTICA", colunas, "CODSIN = ? ", selectionArgs, null, null, null, null);
        SignProperties signProperties = new SignProperties();
        busca.moveToFirst();
        signProperties.setCodSin(busca.getInt(1));
        signProperties.setCodMao(busca.getInt(2));
        signProperties.setCodCm(busca.getInt(3));
        signProperties.setCodPonti(busca.getInt(4));
        signProperties.setCodPart(busca.getInt(5));
        signProperties.setCodOp(busca.getInt(6));
        signProperties.setCodCm1(busca.getInt(7));
        signProperties.setCodCm2(busca.getInt(8));
        busca.close();
        db.close();
        return  signProperties;
    }
    public Sign ObterSinalParaAnimacao(String palavra) {
        String[] selectionArgs = new String[]{palavra.toLowerCase()};
        String sql = "SELECT d.CODPAL as CODPAL, d.TOKEN as TOKEN, ps.CODSIN as CODSIN, sc.CODMAO as CODMAO, " +
                "sc.CODCM as CODCM, sc.CODPONTI as CODPONTI, sc.CODPART as CODPART, sc.CODOP as CODOP, sc.CODCM1 as CODCM1, " +
                "sc.CODCM2 as CODCM2, cm.MEIO as MEIO, cm.ANELAR_E_MINDINHO as ANELAR_E_MINDINHO, cm.DEDOS_SEPARADOS as DEDOS_SEPARADOS, " +
                "cm.PONTAS_DO_DEDO_TOCANDO as PONTAS_DO_DEDO_TOCANDO, cm.POLEGAR as POLEGAR, cm.INDICADOR as INDICADOR, " +
                "oq.ORIENTACAOX as ORIENTACAOX, oq.ORIENTACAOY as ORIENTACAOY, 0 as CODEF " +
                "FROM DICIONARIO d " +
                "LEFT JOIN PALAVRAXSINAL ps ON (ps.CODPAL = d.CODPAL) " +
                "LEFT JOIN SINAL s ON (s.CODSIN = ps.CODSIN) " +
                "LEFT JOIN SINALXCARACTERISTICA sc ON (sc.CODSIN = ps.CODSIN) " +
                "LEFT JOIN CONFIGURACAO_MAO cm ON (cm.CODCM = sc.CODCM) " +
                "LEFT JOIN ORIENTACAO_PALMA op ON (op.CODOP = sc.CODOP) " +
                "LEFT JOIN PONTO_ARTICULACAO pa ON (pa.CODPART = sc.CODPART) " +
                "LEFT JOIN ORIENTACAO_QUADRANTE oq ON (oq.CODOQUA = sc.CODPONTI) " +
                "WHERE d.TOKEN = ? ";

        Cursor busca = db.rawQuery(sql, selectionArgs);//TOKEN = ?
        if(busca.moveToFirst()){
            try {
                return getSignFromCursor(busca);
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }

        busca.close();
//        db.close();
        return null;
    }

    private Sign getSignFromCursor(Cursor cur) throws RuntimeException, ParseException {
        if(cur != null && !cur.isClosed()){
            Sign sign = new Sign();
            sign.setCodSin(getInt("CODSIN", cur));
            sign.setCodPart(getInt("CODPART", cur));
            sign.setCodOp(getInt("CODOP", cur));
            sign.setCodEf(getInt("CODEF", cur));
            sign.setCodCm(getInt("CODCM", cur));
            sign.setCodPal(getInt("CODPAL", cur));
            sign.setPalavra(getString("TOKEN", cur));
            sign.setUltimaPosX(getInt("ORIENTACAOX", cur));
            sign.setUltimaPosY(getInt("ORIENTACAOY", cur));

            sign.setHand(getHandConfigurationFromCursor(cur));
            sign.setPivotPoint(getPivotPointFromCursor(cur));
            sign.setFaceExpression(getFaceExpressionFromCursor(cur));
            sign.setOrientacaoQuadrante(getOrientacaoQuadranteFromCursor(cur));
            sign.setPalmOrientation(getPalmOrientationFromCursor(cur));
            sign.setMovements(getMovementsFromSign(sign.getCodSin()));
            return sign;
        }
        return null;
    }

    private ArrayList<Movement> getMovementsFromSign(int codSin) throws ParseException {
        //Log.d("MovimentoDAO: ", "codSin = Id " + Integer.toString(codSin));

        ArrayList<Movement> movements = new ArrayList<Movement>();
        String[] colunas = new String[]{ "CODMOV", "CODSIN", "CODMAO", "DESCSINMOV", "CARD", "TEMPO", "CODTIPOMOV" };
        String[] selectionArgs = new String[]{Integer.toString(codSin)};
        Cursor busca = db.query("MOVIMENTO", colunas, "CODSIN = ? ", selectionArgs, null, null, null, null);

        if (busca.moveToFirst()) {

            do {
                Movement mov = new Movement();
                mov.setCodMov(getInt("CODMOV",busca));
                mov.setCodSign(getInt("CODSIN", busca));
                mov.setCodMao(getInt("CODMAO", busca));
                mov.setCard(getInt("CARD", busca));
                //mov.setTempo(getDate("TEMPO", busca));
                mov.setTipo(getInt("CODTIPOMOV", busca));
                mov.setAnimationParameters(getAnimationParametersPorCodigodeMovimento(mov.getCodMov()));
                movements.add(mov);
            } while (busca.moveToNext());
        }

        busca.close();
        return movements;
    }

    private AnimationParameters getAnimationParametersPorCodigodeMovimento(int codMov) {
        String[] colunas = new String[]{"CODMOV", "RShoulder_V", "RShoulder_H", "RArm_V", "RArm_H", "RArm_R", "RForearm_V", "RForearm_H", "RHand_V",
                "RHand_H", "RThumb", "RIndex", "RMiddle", "RRing", "RPinky", "LShoulder_V", "LShoulder_H", "LArm_V", "LArm_H",
                "LArm_R", "LForearm_V", "LForearm_H", "Lforearm_R", "LHand_V", "LHand_H", "LThumb", "LIndex", "LMiddle", "LRing", "LPinky"};

        String[] selectionArgs = new String[]{Integer.toString(codMov)};

        Cursor busca = db.query("parametrosmovimento", colunas, "CODMOV = ? ", selectionArgs, null, null, null, null);
        if (busca.moveToFirst()) {
            AnimationParameters par = new AnimationParameters();
            par.setCodMov(getInt("CODMOV", busca));
            par.setRShoulder_V(getFloat("RShoulder_V", busca));
            par.setRShoulder_H(getFloat("RShoulder_H", busca));
            par.setRArm_V(getFloat("RArm_V", busca));
            par.setRArm_H(getFloat("RArm_H", busca));
            par.setRArm_R(getFloat("RArm_R", busca));
            par.setLForearm_V(getFloat("RForearm_V", busca));
            par.setRForearm_H(getFloat("RForearm_H", busca));
            par.setRHand_V(getFloat("RHand_V", busca));
            par.setRHand_H(getFloat("RHand_H", busca));
            par.setRThumb(getFloat("RThumb", busca));
            par.setRIndex(getFloat("RIndex", busca));
            par.setRMiddle(getFloat("RMiddle", busca));
            par.setRRing(getFloat("RRing", busca));
            par.setRPinky(getFloat("RPinky", busca));
            par.setLShoulder_V(getFloat("LShoulder_V", busca));
            par.setLShoulder_H(getFloat("LShoulder_H", busca));
            par.setLArm_V(getFloat("LArm_V", busca));
            par.setLArm_H(getFloat("LArm_H", busca));
            par.setLArm_R(getFloat("LArm_R", busca));
            par.setLForearm_V(getFloat("LForearm_V", busca));
            par.setLForearm_H(getFloat("LForearm_H", busca));
            par.setLforearm_R(getFloat("Lforearm_R", busca));
            par.setLHand_V(getFloat("LHand_V", busca));
            par.setLHand_H(getFloat("LHand_H", busca));
            par.setLThumb(getFloat("LThumb", busca));
            par.setLIndex(getFloat("LIndex", busca));
            par.setLMiddle(getFloat("LMiddle", busca));
            par.setLRing(getFloat("LRing", busca));
            par.setLPinky(getFloat("LPinky", busca));
            busca.close();
            return par;
        }

        busca.close();
        return null;
    }

    private HandConfiguration getHandConfigurationFromCursor(Cursor cur){
        //mockado
        return new HandConfiguration();
    }

    private PivotPoint getPivotPointFromCursor(Cursor cur){
        //mockado
        return new PivotPoint();
    }

    private FaceExpression getFaceExpressionFromCursor(Cursor cur){
        //mockado
        return new FaceExpression();
    }

    private PalmOrientation getPalmOrientationFromCursor(Cursor cur){
        //mockado
        return new PalmOrientation();
    }

    private OrientacaoQuadrante getOrientacaoQuadranteFromCursor(Cursor cur){
        //mockado
        return new OrientacaoQuadrante();
    }

    private int getInt(String name, Cursor cur) throws RuntimeException {
        if(cur.isClosed())
            throw new RuntimeException("Cursor fechado ao tentar capturar o valor inteiro do campo '" + name + "'");

        int index = cur.getColumnIndex(name);
        if(index >= 0)
            return cur.getInt(index);

        throw new IndexOutOfBoundsException("Campo '" + name + "' não foi encontrado no cursor");
    }

    private String getString(String name, Cursor cur) throws RuntimeException {
        if(cur.isClosed())
            throw new RuntimeException("Cursor fechado ao tentar capturar o valor String do campo '" + name + "'");

        int index = cur.getColumnIndex(name);
        if(index >= 0)
            return cur.getString(index);

        throw new IndexOutOfBoundsException("Campo '" + name + "' não foi encontrado no cursor");
    }

    private float getFloat(String name, Cursor cur) throws RuntimeException {
        if(cur.isClosed())
            throw new RuntimeException("Cursor fechado ao tentar capturar o valor float do campo '" + name + "'");

        int index = cur.getColumnIndex(name);
        if(index >= 0)
            return cur.getFloat(index);

        throw new IndexOutOfBoundsException("Campo '" + name + "' não foi encontrado no cursor");
    }

    private Date getDate(String name, Cursor cur) throws ParseException {
        if(cur.isClosed())
            throw new RuntimeException("Cursor fechado ao tentar capturar o valor Date do campo '" + name + "'");

        int index = cur.getColumnIndex(name);
        if(index >= 0) {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            return sdf.parse(cur.getString(index));
        }

        throw new IndexOutOfBoundsException("Campo '" + name + "' não foi encontrado no cursor");
    }

    private String getSelectionArgsFromIntArray(int[] args) {
        String result = "";
        for (int i = 0; i < args.length; i++){
            result += Integer.toString(args[i]);
            if(i < args.length - 1)
                result += ", ";
        }
        return result;
    }

/*    public SignAnimationDTO ObterSinalParaAnimacao(String palavra) throws NoSuchFieldException, ParseException, IllegalAccessException, InstantiationException {
        String[] colunas = new String[]{"*"};
        String[] selectionArgs = new String[]{palavra.toLowerCase()};
     //   Cursor busca = db.query("dicionario", colunas, "TOKEN = ? ", selectionArgs, null, null, null, null);
     //   int codPal = busca.getInt(0);
        String sql = "SELECT d.CODPAL as CODPAL, d.TOKEN as TOKEN, ps.CODSIN as CODSIN, sc.CODMAO as CODMAO, " +
                "sc.CODCM as CODCM, sc.CODPONTI as CODPONTI, sc.CODPART as CODPART, sc.CODOP as CODOP, sc.CODCM1 as CODCM1, " +
                "sc.CODCM2 as CODCM2, cm.MEIO as MEIO, cm.ANELAR_E_MINDINHO as ANELAR_E_MINDINHO, cm.DEDOS_SEPARADOS as DEDOS_SEPARADOS, " +
                "cm.PONTAS_DO_DEDO_TOCANDO as PONTAS_DO_DEDO_TOCANDO, cm.POLEGAR as POLEGAR, cm.INDICADOR as INDICADOR, " +
                "oq.ORIENTACAOX as ORIENTACAOX, oq.ORIENTACAOY as ORIENTACAOY " +
                "FROM DICIONARIO d " +
                "LEFT JOIN PALAVRAXSINAL ps ON (ps.CODPAL = d.CODPAL) " +
                "LEFT JOIN SINAL s ON (s.CODSIN = ps.CODSIN) " +
                "LEFT JOIN SINALXCARACTERISTICA sc ON (sc.CODSIN = ps.CODSIN) " +
                "LEFT JOIN CONFIGURACAO_MAO cm ON (cm.CODCM = sc.CODCM) " +
                "LEFT JOIN ORIENTACAO_PALMA op ON (op.CODOP = sc.CODOP) " +
                "LEFT JOIN PONTO_ARTICULACAO pa ON (pa.CODPART = sc.CODPART) " +
                "LEFT JOIN ORIENTACAO_QUADRANTE oq ON (oq.CODOQUA = sc.CODPONTI) " +
                "WHERE d.TOKEN = ? ";
        //        "INNER JOIN PONTO_ARTICULACAO pa ON (pa.CODPART = sc.CODPART)";//'jovial'
//        String sql = "SELECT * FROM dicionario WHERE CODPAL = 9";
    //    Cursor busca = db.rawQuery(sql, null);
        Cursor busca = db.rawQuery(sql, selectionArgs);//TOKEN = ?
        //    Cursor busca = db.query("dicionario", colunas, " TOKEN = ? ", new String[]{palavra.toLowerCase()}, null, null, null, null);
        busca.moveToFirst();
        int codPal = busca.getInt(0);
        SignAnimationDTO sa = new SignAnimationDTO();
        sa.setCodPal(busca.getInt(0));
        sa.setPalavra(busca.getString(1));
        sa.setCodSin(busca.getInt(2));
        sa.setCodCm(busca.getInt(4));
        sa.setUltimaPosX(busca.getInt(16));
        sa.setUltimaPosY(busca.getInt(17));
//        ArrayList<PropertyConfig> signsPropDTO = new ArrayList<PropertyConfig>();
//        CreateListPropertiesSignDTO(signsPropDTO);
//        ArrayList<SignAnimationDTO> signs = new ArrayList<SignAnimationDTO>();
//        signs = signsPropDTO.get(0).createAndMapObjectCollection(signsPropDTO, busca);
//        SignAnimationDTO si = signs.get(0);
        ArrayList<MovementDTO> movements = new ArrayList<MovementDTO>();
        movements = new Movement().GetAllMovementsDTO(busca.getInt(2));
        sa.setMovements(movements);
        ArrayList<AnimationParametersDTO> animationParameters = new ArrayList<AnimationParametersDTO>();
        animationParameters = new AnimationParameters(context).GetAllAnimationParametersSignDTO(movements);
        sa.setAnimationParameters(animationParameters);
        busca.close();
        db.close();
        return  sa;
    }*/


    private void CreateListPropertiesSignDTO(ArrayList<PropertyConfig> signsPropDTO) throws NoSuchFieldException {
    //    ArrayList<PropertyConfig> signsPropDTO = new ArrayList<PropertyConfig>();

        PropertyConfig<SignAnimationDTO> signProp = new PropertyConfig<SignAnimationDTO>("CODPAL","codPal", SignAnimationDTO.class);
        signsPropDTO.add(signProp);
//        signProp = new PropertyConfig<SignAnimationDTO>("CODPAL","codPal");
        signProp = new PropertyConfig<SignAnimationDTO>("TOKEN","palavra", SignAnimationDTO.class);
        signProp = new PropertyConfig<SignAnimationDTO>("CODSIN","codSin", SignAnimationDTO.class);
//        signProp = new PropertyConfig<SignAnimationDTO>("CODMAO","TOKEN", SignAnimationDTO.class);
        signProp = new PropertyConfig<SignAnimationDTO>("CODCM","codCm", SignAnimationDTO.class);
//        signProp = new PropertyConfig<SignAnimationDTO>("CODPONTI","TOKEN", SignAnimationDTO.class);
        signProp = new PropertyConfig<SignAnimationDTO>("CODPART","codPart", SignAnimationDTO.class);
        signProp = new PropertyConfig<SignAnimationDTO>("CODOP","codOp", SignAnimationDTO.class);
        signsPropDTO.add(signProp);
//        signProp = new PropertyConfig<SignAnimationDTO>("CODCM1","TOKEN", SignAnimationDTO.class);
//        signProp = new PropertyConfig<SignAnimationDTO>("CODCM2","TOKEN", SignAnimationDTO.class);
//        signProp = new PropertyConfig<SignAnimationDTO>("ANELAR_E_MINDINHO","TOKEN", SignAnimationDTO.class);
//        signProp = new PropertyConfig<SignAnimationDTO>("DEDOS_SEPARADOS","TOKEN", SignAnimationDTO.class);
//        signProp = new PropertyConfig<SignAnimationDTO>("PONTAS_DO_DEDO_TOCANDO","TOKEN", SignAnimationDTO.class);
//        signProp = new PropertyConfig<SignAnimationDTO>("POLEGAR","TOKEN", SignAnimationDTO.class);
//        signProp = new PropertyConfig<SignAnimationDTO>("INDICADOR","TOKEN", SignAnimationDTO.class);

    }
//        String[] colunas = new String[]{"CODPAL"};
//        String[] selectionArgs = new String[]{palavra};
//        Cursor busca = db.query("dicionario", colunas, "TOKEN = ? ", selectionArgs, null, null, null, null);
//        int codPal = busca.getInt(0);
//
//        colunas = new String[]{"CODSIN"};
//        selectionArgs = new String[]{Integer.toString(codPal)};
//        busca = db.query("PALAVRAXSINAL", colunas, "CODPAL = ? ", selectionArgs, null, null, null, null);
//        int codSin = busca.getInt(0);
//
//        colunas = new String[]{"*"};
//        selectionArgs = new String[]{Integer.toString(codSin)};
//        busca = db.query("SINALXCARACTERISTICA", colunas, "CODPAL = ? ", selectionArgs, null, null, null, null);
//        int codSin = busca.getInt(0);
}
