package com.marlin.tralp.Conexao.Repository;

import android.database.sqlite.SQLiteDatabase;
import android.support.annotation.Nullable;
import com.marlin.tralp.Transcriber.Models.Movement;
import com.marlin.tralp.Transcriber.Models.SignAnimationDTO;

import java.sql.SQLException;
import java.util.ArrayList;

/**
 * Created by cfernandes on 27/05/2016.
 */
public class MovementRepositoryImpl extends RepositoryImpl<Movement> implements MovementRepository {

    public MovementRepositoryImpl(SQLiteDatabase db) throws SQLException {
        super(db, MovementRepositoryImpl.getConfigs(), "MOVIMENTO", "CODSIN");
    }

    @Nullable
    private static ArrayList<PropertyConfig<Movement>> getConfigs()
    {
        try{
            ArrayList<PropertyConfig<Movement>> _configs = new ArrayList<PropertyConfig<Movement>>();
            _configs.add(new PropertyConfig<Movement>("CODSIN", "codSign", Movement.class));
            return _configs;
        }
        catch (NoSuchFieldException e){
            e.printStackTrace();
            return null;
        }
    }
}
