package com.example.prototypetralp;

import java.io.File;

import android.speech.tts.TextToSpeech;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBarActivity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;


public class MainActivity extends ActionBarActivity {
	//public final static String EXTRA_MESSAGE = "com.lambda.Prototypetralp.MESSAGE";
	
	protected TextView t;
	protected TextToSpeech tts;
	protected View btSpeak;
	
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        
        Button text = (Button)findViewById(R.id.text_enabled);     
	    text.setOnClickListener(new OnClickListener() {

	        @Override
			public void onClick(View Button) {

	    Intent show_text = new Intent(MainActivity.this, EditMessage.class); 
	    startActivity(show_text);

	        } 

	    });
	    
	    
	    
	    Button voicer = (Button)findViewById(R.id.voice);
	    voicer.setOnClickListener (new OnClickListener() {
	    
	    	@Override
			public void onClick (View Button) {
	    		
	    	Intent voice_interpreter = new Intent(MainActivity.this, VoiceRecognition.class);
	    	startActivity(voice_interpreter);
	    	
	    	}
	    
	    });
	    
	    Button record = (Button)findViewById(R.id.record_button);     
	    record.setOnClickListener(new OnClickListener() {

	        @Override
			public void onClick(View Button) {

	        	 Intent imageIntent = new Intent(MediaStore.ACTION_VIDEO_CAPTURE);
	             File imagesFolder = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES), "PrototypeTRALP");
	             imagesFolder.mkdirs(); 
	             File image = new File(imagesFolder, "image_001.jpg");
	             Uri uriSavedImage = Uri.fromFile(image);
	             imageIntent.putExtra(MediaStore.EXTRA_OUTPUT, uriSavedImage);
	             startActivityForResult(imageIntent,1);
	             //TODO Save video captured.
	        } 

	    });
	    
    }


    public void OnInit(int status) {
		if(status == TextToSpeech.SUCCESS) {
			Toast.makeText(this, "TTS Inicializado com sucesso", Toast.LENGTH_SHORT).show();
			btSpeak.setEnabled(true);
		}
	}
	
	
	private OnClickListener onClickSpeak () {
		return new OnClickListener() {
			@Override
			public void onClick(View v) {
				String s = t.getText().toString();
				tts.speak(s, TextToSpeech.QUEUE_FLUSH, null);
			}
		};
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {

		// Inflate the menu; this adds items to the action bar if it is present.
		//getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
    
    public static class PlaceholderFragment extends Fragment {

		public PlaceholderFragment() {
		}

		@Override
		public View onCreateView(LayoutInflater inflater, ViewGroup container,
				Bundle savedInstanceState) {
			View rootView = inflater.inflate(R.layout.activity_main, container,
					false);
			return rootView;
		}
	}
    
}
