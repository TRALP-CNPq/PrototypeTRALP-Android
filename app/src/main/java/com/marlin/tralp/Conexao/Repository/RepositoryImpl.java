package com.marlin.tralp.Conexao.Repository;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

/**
 * Created by cfernandes on 27/05/2016.
 */
public abstract class RepositoryImpl<T> implements Repository<T> {

    private ArrayList<PropertyConfig<T>> configs;
    private String tableName;
    private String primaryKeyName;
    private SQLiteDatabase db;

    public RepositoryImpl(SQLiteDatabase db, ArrayList<PropertyConfig<T>> configs, String tableName, String primaryKeyName) throws java.sql.SQLException {
        this.db = db;
        this.configs = configs;
        this.tableName = tableName;
        this.primaryKeyName = primaryKeyName;
    }

    private String[] getColumns() {
        int count = this.configs.size();
        String[] result = new String[count];

        for (int i = 0; i < count; i++) {
            result[i] = this.configs.get(i).getFieldName();
        }

        return  result;
    }

    private ContentValues getContentValues(T instance) throws IllegalAccessException {
        ContentValues result = new ContentValues();
        for (PropertyConfig<T> config : this.configs) {
            if (config.getPropertyClass().equals(String.class)) {
                result.put(config.getFieldName(), (String) config.getValueFromInstance(instance));
                continue;
            }
            if (config.getPropertyClass().equals(Integer.class)) {
                result.put(config.getFieldName(), (Integer) config.getValueFromInstance(instance));
                continue;
            }
            if (config.getPropertyClass().equals(Float.class)) {
                result.put(config.getFieldName(), (Float) config.getValueFromInstance(instance));
                continue;
            }
            if (config.getPropertyClass().equals(Double.class)) {
                result.put(config.getFieldName(), (Double) config.getValueFromInstance(instance));
                continue;
            }
            if (config.getPropertyClass().equals(Boolean.class)) {
                result.put(config.getFieldName(), ((Boolean) config.getValueFromInstance(instance)) ? 1 : 0);
                continue;
            }
            if (config.getPropertyClass().equals(Long.class)) {
                result.put(config.getFieldName(), (Long) config.getValueFromInstance(instance));
                continue;
            }
            if (config.getPropertyClass().equals(Date.class)) {
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                String date = sdf.format((Date) config.getValueFromInstance(instance));
                result.put(config.getFieldName(), date);
            }
        }
        return result;
    }

    public T get(int id) {
        try{
            Cursor result = this.db.query(this.tableName, this.getColumns(), this.primaryKeyName + " = ?", new String[]{Integer.toString(id)}, null, null, null, null);
            return this.configs.get(0).createAndMapObject(this.configs, result);
        }
        catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }

    public ArrayList<T> getAll() {
        try {
            return this.getAll("1 = 1", null);
        }
        catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }

    public ArrayList<T> getAll(String selection, String[] selectionArgs) {
        try {
            return this.getAll(selection, selectionArgs, null, null, null, null);
        }
        catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }

    public ArrayList<T> getAll(String selection, String[] selectionArgs, String groupBy, String having, String orderBy, String limit) {
        try {
            Cursor result = this.db.query(this.tableName, this.getColumns(),  selection, selectionArgs, groupBy, having, orderBy, limit);
            return this.configs.get(0).createAndMapObjectCollection(this.configs, result);
        }
        catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }

    public long create(T entity){
        try {
            ContentValues values = this.getContentValues(entity);
            return this.db.insert(this.tableName, null, values);
        }
        catch (Exception e){
            e.printStackTrace();
            return 0;
        }
    }

    public boolean update(int id, T entity){
        try {
            ContentValues values = this.getContentValues(entity);
            return this.db.update(this.tableName, values, this.primaryKeyName+ "=?", new String[] {Long.toString(id)}) > 0;
        }
        catch (Exception e){
            e.printStackTrace();
            return false;
        }
    }

    public boolean delete(int id){
       try {
           return this.db.delete(this.tableName, this.primaryKeyName+ "=?", new String[] {Long.toString(id)}) > 0;
       }
       catch (Exception e){
           e.printStackTrace();
           return false;
       }
    }
}
