package com.marlin.tralp.Views;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.hardware.Camera;
import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;

import com.marlin.tralp.R;

import java.util.Locale;

/**
 * Created by psalum on 28/09/2015.
 */
public class CapturaCameraView extends Activity {
    private static final String TAG = "TraLP";

    Camera camera;
    private final static int mCameraId = Camera.CameraInfo.CAMERA_FACING_BACK;

    private CameraViewLayout mCameraViewLayout = null;
    private Button btnGravar;
    private TextToSpeech t1;
    private String frasePortugues;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.cameracapturalayout);
        super.onCreate(savedInstanceState);
        btnGravar = (Button) findViewById(R.id.Btn_Gravar);

        start_camera();
        CarregarListeners();

    }

    private void CarregarListeners() {
        t1 = new TextToSpeech(getApplicationContext(), new TextToSpeech.OnInitListener() {
            @Override
            public void onInit(int status) {
                if (status != TextToSpeech.ERROR) {
                    t1.setLanguage(Locale.getDefault());
                }
            }
        });

        btnGravar.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_UP) {
                    btnGravar.setBackgroundDrawable(getResources().getDrawable(android.R.drawable.presence_video_busy));
                    ChamarEventoConfirma();
                } else if (event.getAction() == MotionEvent.ACTION_DOWN) {
                    btnGravar.setBackgroundDrawable(getResources().getDrawable(android.R.drawable.presence_video_online));
                }
                return true;
            }
        });
    }

    private void ChamarEventoConfirma() {
        AlertDialog.Builder alert = new AlertDialog.Builder(this);

        alert.setMessage("Confirmar Video?");
        alert.setPositiveButton("Sim", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                ExibitTextoTraduzido();
            }
        }).setNegativeButton("Não", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        alert.show();
    }

    private void ExibitTextoTraduzido() {
        frasePortugues = "Eu fui ao Cinema";
        AlertDialog.Builder alert = new AlertDialog.Builder(this);
        alert.setMessage(frasePortugues);
        t1.speak(frasePortugues, TextToSpeech.QUEUE_FLUSH, null);
        alert.setNeutralButton("Sair", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        alert.show();
    }

    private void start_camera() {
        try {
            camera = Camera.open();
        } catch (RuntimeException e) {
            System.out.println("Error: " + e);
            return;
        }
        if (camera != null) {
            mCameraViewLayout = new CameraViewLayout(this, camera,mCameraId);//create a SurfaceView to show camera data
            FrameLayout camera_view = (FrameLayout) findViewById(R.id.CameraView);
            camera_view.addView(mCameraViewLayout);//add the SurfaceView to the layout
        }

    }
}
