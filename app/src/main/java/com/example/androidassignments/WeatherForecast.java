package com.example.androidassignments;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.util.Xml;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import org.xmlpull.v1.XmlPullParser;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.net.URL;

import javax.net.ssl.HttpsURLConnection;

public class WeatherForecast extends AppCompatActivity {
    private final String ACTIVITY_NAME = "WeatherForecastActivity";
    ProgressBar pBar;
    ImageView imageView;
    TextView min_temp, max_temp, current_temp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.i(ACTIVITY_NAME, "In onCreate()");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_weather_forecast);
        current_temp = findViewById(R.id.CurrentTmp);
        min_temp = findViewById(R.id.MinTmp);
        max_temp = findViewById(R.id.MaxTmp);
        imageView = findViewById(R.id.image_forecast);
        pBar = findViewById(R.id.progressBar);
        pBar.setVisibility(View.VISIBLE);
        ForecastQuery f = new ForecastQuery();
        f.execute();

    }

    private class ForecastQuery extends AsyncTask<String, Integer, String> {
        private String min, max, current;
        private Bitmap weather;



        @Override
        protected String doInBackground(String... strings) {
            try {
                Bundle bundle = getIntent().getExtras();
                String stuff = bundle.getString("stuff");
                URL url = new URL("https://api.openweathermap.org/data/2.5/weather?q=" + stuff +",ca&APPID=8741296de0b3a921b78384ba552a3995&mode=xml&units=metric");
                HttpsURLConnection conn = (HttpsURLConnection) url.openConnection();
                conn.setReadTimeout(10000);
                conn.setConnectTimeout(15000);
                conn.setRequestMethod("GET");
                conn.setDoInput(true);
                conn.connect();

                InputStream in = conn.getInputStream();
                try {
                    XmlPullParser parser = Xml.newPullParser();
                    parser.setFeature(XmlPullParser.FEATURE_PROCESS_NAMESPACES, false);
                    parser.setInput(in, null);

                    int type;

                    while((type = parser.getEventType()) != XmlPullParser.END_DOCUMENT){
                        if(parser.getEventType() == XmlPullParser.START_TAG){
                            if(parser.getName().equals("temperature")) {
                                current = parser.getAttributeValue(null, "value");
                                publishProgress(25);
                                min = parser.getAttributeValue(null, "min");
                                publishProgress(50);
                                max = parser.getAttributeValue(null, "max");
                                publishProgress(75);
                            } else if (parser.getName().equals("weather")) {
                                String iconName = parser.getAttributeValue(null, "icon");
                                String fileName = iconName + ".png";
                                Log.i(ACTIVITY_NAME, "Looking for file: " + fileName);
                                if (fileExistance(fileName)) {
                                    FileInputStream fis = null;
                                    try {
                                        fis = openFileInput(fileName);
                                    } catch (FileNotFoundException e) {
                                        e.printStackTrace();
                                    }
                                    Log.i(ACTIVITY_NAME, "Found the file locally");
                                    weather = BitmapFactory.decodeStream(fis);
                                } else {
                                    String iconUrl = "https://openweathermap.org/img/w/" + fileName;
                                    weather = getImage(new URL(iconUrl));
                                    FileOutputStream outputStream = openFileOutput(fileName, Context.MODE_PRIVATE);
                                    weather.compress(Bitmap.CompressFormat.PNG, 80, outputStream);
                                    Log.i(ACTIVITY_NAME, "Downloaded the file from the Internet");
                                    outputStream.flush();
                                    outputStream.close();
                                }
                                publishProgress(100);
                            }
                        }
                        parser.next();
                    }
                } finally {
                    in.close();
                }
            } catch (Exception ex){
                ex.printStackTrace();
            }
            return "";
        }

        public boolean fileExistance(String fname){
            File file = getBaseContext().getFileStreamPath(fname);
            return file.exists();
        }

        public Bitmap getImage(URL url) {
            HttpsURLConnection connection = null;
            try {
                connection = (HttpsURLConnection) url.openConnection();
                connection.connect();
                int responseCode = connection.getResponseCode();
                if (responseCode == 200) {
                    return BitmapFactory.decodeStream(connection.getInputStream());
                } else {
                    return null;
                }
            } catch (Exception e) {
                return null;
            } finally {
                if (connection != null) {
                    connection.disconnect();
                }
            }
        }
        @Override
        protected void onPostExecute(String a) {
            pBar.setVisibility(View.INVISIBLE);
            imageView.setImageBitmap(weather);
            current_temp.setText(current + "C\u00b0");
            min_temp.setText(min + "C\u00b0");
            max_temp.setText(max + "C\u00b0");

        }
        @Override
        protected void onProgressUpdate(Integer... values){
            pBar.setProgress(values[0]);
        }

    }
    @Override

    public void onBackPressed() {

        super.onBackPressed();

        setResult(Activity.RESULT_CANCELED);

        finish();

    }
}