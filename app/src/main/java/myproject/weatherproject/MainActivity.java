package myproject.weatherproject;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import org.json.JSONException;

/*
 * Copyright (C) 2013 Surviving with Android (http://www.survivingwithandroid.com)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

public class MainActivity extends Activity {


    private TextView cityText;
    private TextView condDescr;
    private TextView temp;
    private TextView press,temp_min,temp_max,groundlevel,sealevel;
    private TextView windSpeed;
    private TextView windDeg;
    private Button submitButton;
    private TextView hum,latitude,longitude;
    private ImageView imgView;
    public TextView cityinput;
    String city;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        cityinput = (TextView)findViewById(R.id.citytextview);
        cityText = (TextView) findViewById(R.id.cityText);
        condDescr = (TextView) findViewById(R.id.condDescr);
        temp = (TextView) findViewById(R.id.temp);
        hum = (TextView) findViewById(R.id.hum);
        press = (TextView) findViewById(R.id.press);
        windSpeed = (TextView) findViewById(R.id.windSpeed);
        windDeg = (TextView) findViewById(R.id.windDeg);
        temp_min = (TextView)findViewById(R.id.mintemp);
        temp_max = (TextView)findViewById(R.id.maxtemp);
        groundlevel = (TextView)findViewById(R.id.groundlevel);
        sealevel = (TextView)findViewById(R.id.sealeveltv);
        latitude=(TextView)findViewById(R.id.latitudeinput);
        longitude=(TextView)findViewById(R.id.longitudeinput);
        imgView = (ImageView) findViewById(R.id.condIcon);
        submitButton = (Button)findViewById(R.id.submitrequestbutton);
        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("Jesus",cityinput.getText().toString());
                city = cityinput.getText().toString();
//                Log.d("Jesus",city);

                JSONWeatherTask task = new JSONWeatherTask();
                Log.d("Jesus","task object created");

                task.execute(cityinput.getText().toString());
                Log.d("Jesus", "After task.execute()");

            }
        });
    }





    private class JSONWeatherTask extends AsyncTask<String, Void, Weather> {

        @Override
        protected Weather doInBackground(String ... params) {
            Log.d("Jesus",city+"  Entered in the doinBackground");
            Weather weather = new Weather();
            String data = ( (new WeatherHttpClient()).getWeatherData(city));
            Log.d("Jesus",data  +"  This is at line 80 before try");
            try {
                weather = JSONWeatherParser.getWeather(data);

                // Let's retrieve the icon
                weather.iconData = ( (new WeatherHttpClient()).getImage(weather.currentCondition.getIcon()));

            } catch (JSONException e) {
                e.printStackTrace();
            }
            return weather;

        }




        @Override
        protected void onPostExecute(Weather weather) {
            super.onPostExecute(weather);
            Log.d("Jesus", "Entered in onPostExecute");

            if (weather.iconData != null && weather.iconData.length > 0) {
                Bitmap img = BitmapFactory.decodeByteArray(weather.iconData, 0, weather.iconData.length);
                imgView.setImageBitmap(img);
            }

            cityText.setText(weather.location.getCity() + "," + weather.location.getCountry());
            condDescr.setText(weather.currentCondition.getCondition() + "(" + weather.currentCondition.getDescr() + ")");
            temp.setText("" + Math.round((weather.temperature.getTemp() - 273.15)) + "�C");
            hum.setText("" + weather.currentCondition.getHumidity() + "%");
            press.setText("" + weather.currentCondition.getPressure() + " hPa");
            windSpeed.setText("" + weather.wind.getSpeed() + " mps");
            windDeg.setText("" + weather.wind.getDeg() + "�");
            temp_min.setText("" + Math.round((weather.temperature.getMinTemp() - 273.15)) + "�C");
            temp_max.setText("" + Math.round((weather.temperature.getMaxTemp() - 273.15)) + "�C");
            sealevel.setText("" + weather.currentCondition.getsea_level());
            groundlevel.setText("" + weather.currentCondition.getGrnd_level());
            latitude.setText("" + weather.location.getLatitude()+"degrees");
            longitude.setText("" + weather.location.getLongitude()+"degrees");

        }







    }
}