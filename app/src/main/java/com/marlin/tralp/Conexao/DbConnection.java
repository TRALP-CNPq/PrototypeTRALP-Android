package com.marlin.tralp.Conexao;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import android.widget.Toast;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.sql.SQLException;

/**
 * Created by psalum on 05/05/2015.
 */

/*Esta classe gerencia o banco antigo. Ela deve ser Substituida para adaptar-se ao banco que será desenvolvido pela Dominique.*/
public class DbConnection extends SQLiteOpenHelper {
    private static String DB_PATH = "/data/data/com.marlin.tralp/databases/";
    //Environment.getExternalStorageDirectory().toString()+"/Android/data/com.marlin.tralp/databases/";
    //this.ctx.getDatabasePath(DBNAME).getAbsolutePath();
    //Environment.getExternalStorageDirectory().toString()+"/Android/data/com.examples.sms/databases/";  /mnt/sdcard/Android/data/com.marlin.tralp/databases/
    public static final String NOME_DB = "teste-sqlite";
    public static final int VERSAO_DB = 2;
    public static int verificador;
    private final Context myContext;
    private SQLiteDatabase myDataBase;

    public DbConnection(Context context) {
        super(context, NOME_DB, null, VERSAO_DB);
        myContext = context;
    }

    /**
     * Creates a empty database on the system and rewrites it with your own database.
     * */
    public void createDataBase() throws IOException {

        boolean dbExist = checkDataBase();

        if(dbExist){
            //do nothing - database already exist
        }else{
            //By calling this method and empty database will be created into the default system path
            //of your application so we are gonna be able to overwrite that database with our database.
            try {
                this.getReadableDatabase();
                Log.d("createDataBase: ", "Vou criar o banco ");
                copyDataBase();
            } catch (IOException e) {
                throw new Error("Error copying database: " + e.getMessage().toString());
            } catch (Exception a){
                Log.d("DB CREATE", "createDataBase: failed " + a.getMessage().toString());
            }
        }

    }

    /**
     * Check if the database already exist to avoid re-copying the file each time you open the application.
     * @return true if it exists, false if it doesn't
     */
    private boolean checkDataBase(){
        SQLiteDatabase checkDB = null;

        try{
            String myPath = DB_PATH + NOME_DB;
            File file = new File(myPath);
            file.setWritable(true);
            Log.d("checkDataBase: ", "caminho do banco: " + myPath);
            Log.d("checkDataBase: ", "getAbsolutePath: " + file.getAbsolutePath().toString());
            SQLiteDatabase.openDatabase(file.getAbsolutePath(), null, SQLiteDatabase.OPEN_READWRITE);
            if (file.exists() && !file.isDirectory())
                checkDB = SQLiteDatabase.openDatabase(myPath, null, SQLiteDatabase.OPEN_READONLY);
            else {
                Log.d("checkDataBase: ", "Diretorio nao existe " + myPath);
            }
        }catch(SQLiteException e){
            try {
                //            Toast.makeText(myContext, e.getMessage(), Toast.LENGTH_LONG).show();
                //database does't exist yet.
                Log.d("checkDataBase: ", "Banco nao existe ");
            }catch(Exception a){

            }
        }
        if(checkDB != null){
            checkDB.close();
        }
        return checkDB != null;
    }

    /**
     * Copies your database from your local assets-folder to the just created empty database in the
     * system folder, from where it can be accessed and handled.
     * This is done by transfering bytestream.
     * */
    private void copyDataBase() throws IOException {

        Log.d("copyDataBase: ", "Open your local db as the input stream " + NOME_DB);
        //Open your local db as the input stream
        InputStream myInput = myContext.getAssets().open(NOME_DB);

        // Path to the just created empty db
        String outFileName = DB_PATH + NOME_DB;

        //Open the empty db as the output stream
        OutputStream myOutput = new FileOutputStream(outFileName);
        Log.d("copyDataBase: ", "copiando banco " + outFileName);
        //transfer bytes from the inputfile to the outputfile
        byte[] buffer = new byte[1024];
        int length;
        while ((length = myInput.read(buffer))>0){
            myOutput.write(buffer, 0, length);
        }
        //Close the streams
        myOutput.flush();
        myOutput.close();
        myInput.close();

    }

    public SQLiteDatabase openDataBase() throws SQLException {

        //Open the database
        String myPath = DB_PATH + NOME_DB;
        return myDataBase = SQLiteDatabase.openDatabase(myPath, null, SQLiteDatabase.OPEN_READWRITE);
    }

    @Override
    public synchronized void close() {

        if(myDataBase != null)
            myDataBase.close();
        super.close();
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        if(newVersion>oldVersion)
            try {
                copyDataBase();
            } catch (IOException e) {
                e.printStackTrace();
            }
    }

    // Add your public helper methods to access and get content from the database.
    // You could return cursors by doing "return myDataBase.query(....)" so it'd be easy
    // to you to create adapters for your views.


















//    @Override
//    //método é executado na primeira vez que o aplicativo inicar.
//    public void onCreate(SQLiteDatabase db) {
//        Inserts loader = new Inserts();
//        String[] Array = loader.Querrys();
//        db.execSQL("CREATE TABLE if not exists definicoespalavras ( _id INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT, Palavra TEXT NOT NULL, definicao TEXT NOT NULL)");
//
//        //Executa a Carga no Banco de Acordo com o conteudo da Classe Inserts.
//        for (String s : Array) {
//            db.execSQL(s);
//        }
//    }
//
//    @Override
//    //método será executado quando alguma modificação for feita.
//    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
//        db.execSQL("drop table if exists definicoespalavras");
//        onCreate(db);
//    }
}