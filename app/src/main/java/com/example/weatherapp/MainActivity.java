package com.example.weatherapp;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.sql.Connection;

public class MainActivity extends AppCompatActivity {
     //this class provide background thread services
      class WeatherInfo extends AsyncTask<String, Void, String>
      {


          @Override
          protected String doInBackground(String... params) {


              try{
                  URL url=new URL(params[0] );
                  HttpURLConnection connection=(HttpURLConnection)url.openConnection();
                  connection.connect();
                  InputStream is=connection.getInputStream();
                  InputStreamReader reader=new InputStreamReader(is);
                  int data=reader.read();
                  String apiDetails="";
                  char current;
                  while (data != -1)

                  {
                      current=(char)data;
                      apiDetails += current;
                      data=reader.read();
                  }
                  return apiDetails;


              }
              catch(Exception e)

              {
                  e.printStackTrace();
              }


              return null;
          }
      }
    Button show_weather;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);//main UI Thread
        setContentView(R.layout.activity_main);
        show_weather=(Button)findViewById(R.id.btnweather);
        show_weather.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                WeatherInfo weatherInfo=new WeatherInfo();
                EditText cityname=(EditText)findViewById(R.id.cityEditText);
                TextView weatherTextView=(TextView)findViewById(R.id.weatherTextView);

                try {

                    if(cityname.getText().toString().isEmpty())
                    {
                        Toast.makeText(MainActivity.this,"please enter city", Toast.LENGTH_SHORT).show();
                    }

                    String weatherApiDetails=weatherInfo.execute("https://samples.openweathermap.org/data/2.5/weather?q= "
                            +cityname.getText().toString()+ "&appid=55ff6d07b629e3f3e8b279f194cbd288").get();
                   // Log.i("weather api info",weatherApiDetails);

                    JSONObject jsonObject=new JSONObject(weatherApiDetails);
                    String weather=jsonObject.getString("weather");
                   // Log.i("weather details",weather);
                    JSONArray array=new JSONArray(weather);
                    String main="";
                    String description="";

                    for (int i=0; i<array.length(); i++)
                    {
                        JSONObject arrayObject=array.getJSONObject(i);
                        main=arrayObject.getString("main");
                        description=arrayObject.getString("description");

                    }
                    weatherTextView.setText("Main:"+main +"\n"+
                            "Description:"+ description);


                }catch (Exception  e) {
                    e.printStackTrace();
                }


            }
        });





}

    }

