package com.uvstudio.pritampc.voiceapp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import org.json.JSONException;
import org.json.JSONObject;

public class Display_Json extends AppCompatActivity {
    String json_data;
    JSONObject jo=null,jo_full=null,jo_res=null;
    String speech,action,source;
    private TextView display_response,dis_action,dis_source;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_json);
        display_response = (TextView) findViewById(R.id.speech_value);
        dis_action= (TextView) findViewById(R.id.action_value);
        dis_source= (TextView) findViewById(R.id.source_value);
        json_data=getIntent().getExtras().getString("json_data");

       try {
           if(json_data!=null) {
               jo = new JSONObject(json_data);
               jo_res = jo.getJSONObject("result");
               jo_full = jo_res.getJSONObject("fulfillment");
               speech = jo_full.getString("speech");//values of key=speech and obj=fulfillment
               action = jo_res.getString("action");// values of key=action and obj=result
               source = jo_res.getString("source");// values of key=source and obj=result
               display_response.setText("Response:"+speech);
               dis_action.setText("Action:"+action);
               dis_source.setText("Source:"+source);
           }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }


}
