package com.marlin.tralp.Views;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
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
import com.marlin.tralp.MainApplication;
import com.marlin.tralp.R;
import com.marlin.tralp.Transcriber.ImageProcess.Controller;

public class ResultadoCaptura extends Activity implements View.OnTouchListener {
    private TextToSpeech t1;
    private String frasePortugues;
    private Button btnVoltar;
    TextView mTxtView;
    boolean blocked;
    private Handler handler;

 //   @Override
    @Nullable
    protected void onCreate(Bundle savedInstanceState) {
        blocked = false;

        setContentView(R.layout.activity_resultado_captura);
        super.onCreate(savedInstanceState);

        Log.d("msg ", "Entrei ResultadoCaptura.onCreate");

        Bundle extras = getIntent().getExtras();

        String frase = extras.getString("frase");
        TextView mTxtView = (TextView)findViewById(R.id.textView3); // getView().
        mTxtView.setText(frase);
        btnVoltar = (Button) findViewById(R.id.Btn_Voltar);

        handler = new Handler(Looper.getMainLooper()) {
            @Override
            public void handleMessage(Message inputMessage) {
                // Gets the task from the incoming Message object.
                String supermsg = (String) inputMessage.getData().getString("what");
                Log.d("handler", supermsg);
                setContentView(R.layout.activity_resultado_captura);
                TextView mTxtView = (TextView)findViewById(R.id.textView3); // getView().
                mTxtView.setText(supermsg);
            }
        };


        CarregarListeners();

    }


    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.content_resultado_captura);
        Log.d("msg ", "Entrei ResultadoCaptura.onCreateView");
        View rootview = inflater.inflate(R.layout.activity_resultado_captura, container, false);
        rootview.setVisibility(View.VISIBLE);


        String frase = "Capturei: ";
        mTxtView = (TextView)findViewById(R.id.textView); // getView().
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
        if(!blocked) {
            processaImages();
            blocked = true;
        }


        return false;
    }

    public void processaImages(){
        Log.d("ResCap-ProcessaImages", "comecando");

        new Thread(new Runnable() {
            @Override
            public void run() {
                simpleSendString(handler, "Sera?!");
                Log.d("Resultado 1st runnable", "entered");

                MainApplication mApp = MainApplication.getInstance();
                Log.d("RCap-ProcessaImages", "instance");
                new Controller(mApp).process();
                Log.d("RCap-ProcessaImages", "Annotation done");
                String frase = (new com.marlin.tralp.Transcriber.FeatureProcess.Controller(mApp)).process();
                Log.d("RCap-ProcessaImages", frase);
                simpleSendString(handler, frase);

            }

            public void simpleSendString(Handler h, String sMsg){
                Message msg = new Message();
                Bundle o = new Bundle();
                o.putString("what", sMsg);
                msg.setData(o);
                msg.what = 15;
                h.sendMessage(msg);
            }

        }).start();

    }


    private void CarregarListeners() {
        btnVoltar.setOnTouchListener(this);

    }
}
