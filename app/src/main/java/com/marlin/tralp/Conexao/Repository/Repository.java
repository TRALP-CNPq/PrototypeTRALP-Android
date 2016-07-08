package com.marlin.tralp.Conexao.Repository;

import java.text.ParseException;
import java.util.ArrayList;

/**
 * Created by cfernandes on 27/05/2016.
 */
public interface Repository<T> {

    public T get(int id) throws ParseException, IllegalAccessException, InstantiationException;
    public ArrayList<T> getAll() throws ParseException, IllegalAccessException, InstantiationException;
    public ArrayList<T> getAll(String selection, String[] selectionArgs) throws ParseException, IllegalAccessException, InstantiationException;
    public ArrayList<T> getAll(String selection, String[] selectionArgs, String groupBy, String having, String orderBy, String limit) throws ParseException, IllegalAccessException, InstantiationException;
    public long create(T entity);
    public boolean update(int id, T entity);
    public boolean delete(int id);
}
