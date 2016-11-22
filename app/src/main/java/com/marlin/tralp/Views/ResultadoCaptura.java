package com.marlin.tralp.Views;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.support.annotation.Nullable;
//import android.support.design.widget.FloatingActionButton;
//import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.marlin.tralp.MainActivity;
import com.marlin.tralp.R;

public class ResultadoCaptura extends Activity implements View.OnTouchListener {
    private TextToSpeech t1;
    private String frasePortugues;
    private Button btnVoltar;

 //   @Override
    @Nullable
//    protected void onCreate(Bundle savedInstanceState) {
    protected void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.activity_resultado_captura);
        super.onCreate(savedInstanceState);
        Log.d("msg ", "Entrei ResultadoCaptura.onCreate");
        Bundle extras = getIntent().getExtras();
    //    String inputString = extras.getString("frase");
        //TextView view = (TextView) findViewById(R.id.displayintentextra);
        //    view.setText(inputString);
        String frase = extras.getString("frase");
        TextView mTxtView = (TextView)findViewById(R.id.textView3); // getView().
        mTxtView.setText(frase);
        btnVoltar = (Button) findViewById(R.id.Btn_Voltar);
//
//        start_camera();
        CarregarListeners();

    }

//    public void onCreate(Bundle bundle) {
//        super.onCreate(bundle);
//        setContentView(R.layout.activity_result);
//        Bundle extras = getIntent().getExtras();
//        String inputString = extras.getString("frase");
//        TextView view = (TextView) findViewById(R.id.displayintentextra);
//        view.setText(inputString);
//    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.content_resultado_captura);
        Log.d("msg ", "Entrei ResultadoCaptura.onCreateView");
        View rootview = inflater.inflate(R.layout.activity_resultado_captura, container, false);
        rootview.setVisibility(View.VISIBLE);


        String frase = "Capturei: ";
        TextView mTxtView = (TextView)findViewById(R.id.textView); // getView().
        mTxtView.setText("Consegui!! "+ frase);

        return rootview;
    }
    private void ExibitTextoTraduzido() {
        String frasePortugues = "Eu fui ao Cinema";
        AlertDialog.Builder alert = new AlertDialog.Builder(this);
        alert.setMessage(frasePortugues);
        //t1.speak(frasePortugues, TextToSpeech.QUEUE_FLUSH, null);
        alert.setNeutralButton("Sair", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        alert.show();
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        Log.d("[onTouch]", "voltar para tela principal");
        Intent intent;//CameraViewLayout.class);// MainActivity.class    ProcessView

        intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        return false;
    }

    private void CarregarListeners() {
        btnVoltar.setOnTouchListener(this);
//        btnVoltar.setOnTouchListener(new View.OnTouchListener() {
//            @Override
//            public boolean onTouch(View v, MotionEvent event) {
//                if (event.getAction() == MotionEvent.ACTION_UP) {
//                    btnVoltar.setBackgroundDrawable(getResources().getDrawable(android.R.drawable.presence_video_busy));
//                    //ChamarEventoConfirma();
//                    Log.d("[onTouch]", "voltar para tela principal");
//                    Intent intent;//CameraViewLayout.class);// MainActivity.class    ProcessView
//
//                    intent = new Intent(ResultadoCaptura.class, MainActivity.class);
//                    startActivity(intent);
//                    return false;
//
//                } else if (event.getAction() == MotionEvent.ACTION_DOWN) {
//                    btnVoltar.setBackgroundDrawable(getResources().getDrawable(android.R.drawable.presence_video_online));
//                }
//                return true;
//            }
//        });
    }
}
