package com.marlin.tralp.Views;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Service;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import com.marlin.tralp.Conexao.Dao.UltimasFrasesDao;
import com.marlin.tralp.MenuViews.Principal;
import com.marlin.tralp.Model.Frase;
import com.marlin.tralp.R;

import java.util.ArrayList;

/**
 * Created by psalum on 24/09/2015.
 */
public class CapturaTexto extends Activity {
    EditText editText;
    Button enviar;
    Context context;
    ListView listView;
    UltimasFrasesDao ultimasFrasesDao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.digitartexto);
        super.onCreate(savedInstanceState);

        editText = (EditText) findViewById(R.id.editTextDigitarTexto);
        enviar = (Button) findViewById(R.id.Button_Enviar);
        listView = (ListView) findViewById(R.id.listView2);
        context = this;
        ultimasFrasesDao = new UltimasFrasesDao(this);
        popularListview();
        CriarListeners();

    }

    protected void CriarListeners() {
        enviar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!("").equalsIgnoreCase(editText.getText().toString())){
                    ultimasFrasesDao.adicionar(new Frase(editText.getText().toString()));
                }
                finalizarActivity();
            }


        });
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                String item = listView.getItemAtPosition(position).toString();
                String[]frase = item.trim().split("-");
                editText.setText(frase[1].trim());
            }
        });

        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                DeleteListItemPopUp(position);
                return true;
            }
        });

    }

    protected void DeleteListItemPopUp(final int position) {

        final AlertDialog.Builder alert = new AlertDialog.Builder(this);
        alert.setMessage("Deseja excluir esta frase?");
        alert.setPositiveButton("Sim",  new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {
                ultimasFrasesDao.DeletarFrase(listView.getItemAtPosition(position).toString());
                popularListview();
                dialog.dismiss();
            }


        });
        alert.setNegativeButton("Não", new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }


        });

        alert.show();

    }

    protected void popularListview() {
        ArrayList<String> itens = new ArrayList<String>();
        for (Frase frase : ultimasFrasesDao.buscarUltimasFrases()) {
            itens.add(frase.getId() +" - "+frase.getFrase());
        }
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_selectable_list_item, itens);
        listView.setAdapter(adapter);
        adapter.notifyDataSetChanged();

    }

    private void finalizarActivity() {
        Principal.editText.setText(editText.getText().toString());
        InputMethodManager imm = (InputMethodManager) context.getSystemService(Service.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(editText.getWindowToken(), 0);
        finish();
    }

    @Override
    public void onBackPressed() {
        finalizarActivity();
        super.onBackPressed();
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        System.out.println(event.toString());
        return super.onKeyDown(keyCode, event);
    }

    @Override
    public void onStart() {
        super.onStart();
        editText.setText(getIntent().getExtras().getString("texto"));
        editText.setSelection(0,editText.getText().length());
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
    }

    @Override
    public void onStop() {
        super.onStop();
    }
}
