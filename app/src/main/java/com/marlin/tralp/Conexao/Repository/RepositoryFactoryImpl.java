package com.marlin.tralp.Conexao.Repository;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import com.marlin.tralp.Conexao.DbConnection;
import java.io.IOException;
import java.sql.SQLException;

/**
 * Created by cfernandes on 27/05/2016.
 */
public class RepositoryFactoryImpl implements RepositoryFactory {

    private SQLiteDatabase dataBase;
    private DbConnection connection;
    private Context context;

    public RepositoryFactoryImpl(Context context) {
        this.context = context;
    }

    private DbConnection getDbConnection()    {
        if(connection == null)
            this.connection = new DbConnection(this.context);

        return this.connection;
    }

    private SQLiteDatabase getDatabase() throws IOException, SQLException {
        if(dataBase != null && dataBase.isOpen()){
            return  this.dataBase;
        }

        try {
            this.getDbConnection().createDataBase();
            this.dataBase = connection.openDataBase();
            return this.dataBase;
        }
        catch (IOException | SQLException ioEx){
            ioEx.printStackTrace();
            throw ioEx;
        }
    }

    public MovementRepository getMovementRepository() throws IOException, SQLException {
        return new MovementRepositoryImpl(this.getDatabase());
    }
}
