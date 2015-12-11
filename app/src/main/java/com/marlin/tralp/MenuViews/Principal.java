package com.marlin.tralp.MenuViews;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Fragment;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.speech.RecognizerIntent;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.marlin.tralp.Conexao.Dao.UltimasFrasesDao;
import com.marlin.tralp.Model.Frase;
import com.marlin.tralp.R;
import com.marlin.tralp.Service.PortuguesLibras;
import com.marlin.tralp.Views.CapturaCameraView;
import com.marlin.tralp.Views.CapturaTexto;

import java.util.ArrayList;
import java.util.Locale;

/**
 * Created by psalum on 23/09/2015.
 */
public class Principal extends Fragment {
    public static EditText editText;

    private View rootview;
    private Button btnVoice;
    private Button btnCamera;
    private UltimasFrasesDao ultimasFrasesDao;
    private TextView TextViewFraseTraduzida;
    private PortuguesLibras portuguesLibras;

    private final int REQ_CODE_SPEECH_INPUT = 100;
    private Animation in;
    private Animation out;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        in = new AlphaAnimation(0.0f, 1.0f);
        in.setDuration(1000);
        out = new AlphaAnimation(1.0f, 0.0f);
        out.setDuration(1000);


        rootview = inflater.inflate(R.layout.principal, container, false);
        editText = (EditText) rootview.findViewById(R.id.editText);
        btnVoice = (Button) rootview.findViewById(R.id.btn_Voice);
        btnCamera = (Button) rootview.findViewById(R.id.btn_Camera);
        ultimasFrasesDao = new UltimasFrasesDao(rootview.getContext());
        TextViewFraseTraduzida = (TextView)rootview.findViewById(R.id.txtPTLB);
        portuguesLibras = new PortuguesLibras(getActivity());

        CriarListeners();
        return rootview;

    }

    private void CriarListeners() {
        editText.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (MotionEvent.ACTION_UP == event.getAction()) {
                    Intent intent = new Intent(getActivity(), CapturaTexto.class);
                    String text = editText.getText().toString();
                    intent.putExtra("texto", text);
                    getActivity().startActivity(intent);
                }
                return true;
            }
        });
        btnVoice.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editText.setText("");
                TextViewFraseTraduzida.setText("");
                if (VerificarConexaoInternet()) {
                    ExibirSpeechInput();
                } else {
                    new AlertDialog.Builder(getActivity())
                            .setTitle("Indisponivel")
                            .setMessage(R.string.speech_not_supported)
                            .setCancelable(true)
                            .setNeutralButton("OK", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.dismiss();
                                }
                            })
                            .setIcon(android.R.drawable.ic_dialog_info)
                            .show();
                }
            }
        });
        btnCamera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), CapturaCameraView.class);
                getActivity().startActivity(intent);
            }
        });
    }



    /**
     * Showing google speech input dialog
     */
    private void ExibirSpeechInput() {
        Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL,
                RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, Locale.getDefault());
        intent.putExtra(RecognizerIntent.EXTRA_PROMPT,
                getString(R.string.speech_prompt));
        intent.putExtra(RecognizerIntent.EXTRA_PARTIAL_RESULTS, true);
        intent.putExtra(RecognizerIntent.EXTRA_MAX_RESULTS, 100);
        try {
            startActivityForResult(intent, REQ_CODE_SPEECH_INPUT);
        } catch (ActivityNotFoundException a) {
            Toast.makeText(getActivity(),
                    getString(R.string.speech_not_supported),
                    Toast.LENGTH_SHORT).show();
        }
    }

    /**
     * Receiving speech input
     */
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        String RetornoActivity = "";

        switch (requestCode) {
            case REQ_CODE_SPEECH_INPUT: {
                if (resultCode == Activity.RESULT_OK && null != data) {

                    ArrayList<String> result = data
                            .getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);

                    RetornoActivity = result.get(0);
                    ultimasFrasesDao.adicionar(new Frase(RetornoActivity));


                    try {

                        editText.setText(RetornoActivity, TextView.BufferType.EDITABLE);
                    } catch (Exception e) {
                        System.out.println("Erro: " + e);
                    }
                }
                break;
            }

        }if(!("").equalsIgnoreCase(RetornoActivity)){
            Traduzir(RetornoActivity);
        }

    }

    @Override
    public void onResume() {
        super.onResume();
        if(editText.getText()!=null || "".equalsIgnoreCase(editText.getText().toString())){
            Traduzir(editText.getText().toString());
        }
    }

    public void Traduzir(String retornoActivity) {
        TextViewFraseTraduzida.setAnimation(out);
        TextViewFraseTraduzida.setText(portuguesLibras.Executar(retornoActivity));
        TextViewFraseTraduzida.setAnimation(in);
    }


    public boolean VerificarConexaoInternet() {
        ConnectivityManager connectivity = (ConnectivityManager) getActivity().getSystemService(Context.CONNECTIVITY_SERVICE);
        try {
            if (connectivity != null) {
                NetworkInfo[] info = connectivity.getAllNetworkInfo();
                if (info != null)
                    for (int i = 0; i < info.length; i++)
                        if (info[i].getState() == NetworkInfo.State.CONNECTED) {
                            return true;
                        }

            }
            return false;
        } catch (Exception e) {
            System.out.println(e);
            return false;
        }

    }
}
