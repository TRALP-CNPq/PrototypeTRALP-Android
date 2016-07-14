package com.marlin.tralp.Conexao.Repository;

import android.database.Cursor;
import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class PropertyConfig<T>
{
    private String fieldName, propertyName;
    private Class<T> objectClass;
    private Class<?> propertyClass;
    private Field property;

    public String getFieldName() {
        return fieldName;
    }

    public void setFieldName(String fieldName) {
        this.fieldName = fieldName;
    }

    public String getPropertyName() {
        return propertyName;
    }

    public void setPropertyName(String propertyName) {
        this.propertyName = propertyName;
    }

    public Class<?> getPropertyClass() {
        return propertyClass;
    }

    public PropertyConfig(String fieldName, String propertyName, Class<T> tipo)throws NoSuchFieldException {
        this.fieldName = fieldName;
        this.propertyName = propertyName;
     //   ParameterizedType genericSuperclass = (ParameterizedType) getClass().getGenericSuperclass();
    //    this.objectClass = (Class<T>) ((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments()[0];
        this.objectClass = tipo; //(Class<T>) genericSuperclass.getActualTypeArguments()[0];
        this.property = this.objectClass.getDeclaredField(this.propertyName);
        this.propertyClass = this.property.getType();
    }

    public PropertyConfig(String fieldAndPropertyName, Class<T> tipo) throws NoSuchFieldException {
        this(fieldAndPropertyName, fieldAndPropertyName, tipo);
    }
    private PropertyConfig(){}

    private T getNewInstance() throws InstantiationException, IllegalAccessException {
        T instancedObject = null;
        try {
            instancedObject = this.objectClass.newInstance();
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
        return instancedObject;
    }

    public Object getValueFromInstance(T instance) throws IllegalAccessException {
        return this.property.get(instance);
    }

    private void captureValue(T instance, Cursor cursor) throws IllegalAccessException, ParseException, NoSuchFieldException {
        int columnIndex = cursor.getColumnIndex(this.fieldName);
        String name = this.propertyName;
        if(columnIndex >= 0) {

            if (propertyClass.equals(String.class)) {
                property.set(instance, cursor.getString(columnIndex));
                return;
            }

            if (propertyClass.getName().equals("int")) {
                propertyClass.getField(getPropertyName()).setInt(instance, cursor.getInt(columnIndex));
    //            property.setInt(instance, cursor.getInt(columnIndex));
                return;
            }

            if (propertyClass.equals(Integer.class)) {
                property.setInt(instance, cursor.getInt(columnIndex));
                return;
            }

            if (propertyClass.equals(Float.class)) {
                property.setFloat(instance, cursor.getFloat(columnIndex));
                return;
            }

            if (propertyClass.equals(Double.class)) {
                property.setDouble(instance, cursor.getDouble(columnIndex));
                return;
            }

            if (propertyClass.equals(Boolean.class)) {
                property.setBoolean(instance, cursor.getInt(columnIndex) > 0);
                return;
            }

            if (propertyClass.equals(Date.class)) {
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                Date dt = sdf.parse(cursor.getString(columnIndex));
                property.set(instance, dt);
                return;
            }
        }
        throw new IllegalAccessException("A coluna '"+ this.getFieldName()+ "' não foi encontrada no cursor da consulta.");
    }

    public void mapObject(T instance, ArrayList<PropertyConfig<T>> configs, Cursor cursor) throws ParseException, IllegalAccessException, NoSuchFieldException {
        if(!cursor.isClosed() && (cursor.getCount() > 0)) {
            for (PropertyConfig config: configs) {
                config.captureValue(instance, cursor);
            }
        }
    }

    public T createAndMapObject(ArrayList<PropertyConfig<T>> configs, Cursor cursor) throws ParseException, IllegalAccessException, InstantiationException, NoSuchFieldException {
        T instance = this.getNewInstance();
        this.mapObject(instance, configs, cursor);
        return instance;
    }

    public ArrayList<T> createAndMapObjectCollection(ArrayList<PropertyConfig<T>> configs, Cursor cursor) throws ParseException, IllegalAccessException, InstantiationException, NoSuchFieldException {

        ArrayList<T> result = new ArrayList<T>();

        if(!cursor.isClosed() && cursor.getCount() > 0) {
            cursor.moveToFirst();
            do {
                T instance = this.createAndMapObject(configs, cursor);
                result.add(instance);
            } while (cursor.moveToNext());
        }
        return result;
    }
}
