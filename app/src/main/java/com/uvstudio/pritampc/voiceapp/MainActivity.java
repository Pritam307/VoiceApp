package com.uvstudio.pritampc.voiceapp;


import android.content.Intent;
import android.speech.tts.TextToSpeech;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import java.util.Locale;
import ai.api.AIListener;
import ai.api.android.AIConfiguration;
import ai.api.android.AIService;
import ai.api.android.GsonFactory;
import ai.api.model.AIError;
import ai.api.model.AIResponse;
import ai.api.model.Result;

public class MainActivity extends AppCompatActivity implements AIListener {
    private TextView display;
    private AIConfiguration config;
    private Gson gson= GsonFactory.getGson();
    private AIService aiService;
    private TextToSpeech tts;
    String json_string;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        display= (TextView) findViewById(R.id.textView);
        display.setVisibility(View.INVISIBLE);
        tts=new TextToSpeech(getApplicationContext(), new TextToSpeech.OnInitListener() {
            @Override
            public void onInit(int i) {
                if(i!=TextToSpeech.ERROR){
                    tts.setLanguage(Locale.ENGLISH);
                    tts.setSpeechRate(0.8f);
                }
            }
        });

       // lay.setVisibility(View.INVISIBLE);
         config=new AIConfiguration("8991af5d3dec442097713076a4e989a3",AIConfiguration.SupportedLanguages.English, AIConfiguration.RecognitionEngine.System );
         aiService=AIService.getService(this,config);
         aiService.setListener(this);
        }


    @Override
    public void onResult(final AIResponse result)
    {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {

                json_string=gson.toJson(result); // get json output from result
                Result res=result.getResult();
                display.setVisibility(View.VISIBLE);
                display.setText(res.getResolvedQuery());
                tts.speak("Sure sir",TextToSpeech.QUEUE_FLUSH,null);
            }
        });

    }

    @Override
    public void onError(AIError error) {
        display.setText(error.toString() +"Or,Check your internet connection");
    }

    @Override
    public void onAudioLevel(float level) {}

    @Override
    public void onListeningStarted() {}

    @Override
    public void onListeningCanceled() {}

    @Override
    public void onListeningFinished() {}


    public void StartListen( View v){
        aiService.startListening();
        display.setText(null);
    }

    @Override
    protected void onPause() {
        if(tts!=null){
            tts.stop();
            tts.shutdown();
        }
        super.onPause();
    }

    public void DisplayJson(View v){
        try
        {
            // send json output to Display_Json.class
            Intent i = new Intent(this, Display_Json.class);
            i.putExtra("json_data", json_string);
            startActivity(i);
        }catch(NullPointerException e)
        {
            Toast.makeText(getApplicationContext(),"First get some respone!",Toast.LENGTH_LONG).show();
        }
    }


}


