package myproject.weatherproject;

/**
 * Created by piyush on 6/3/16.
 */

    import android.util.Log;

    import java.io.BufferedReader;
    import java.io.ByteArrayOutputStream;
    import java.io.InputStream;
    import java.io.InputStreamReader;
    import java.net.HttpURLConnection;
    import java.net.URL;
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
    public class WeatherHttpClient {

        private static String BASE_URL = "http://api.openweathermap.org/data/2.5/weather?q=";
        private static String IMG_URL = "http://openweathermap.org/img/w/";


        public String getWeatherData(String location) {
            HttpURLConnection con = null ;
            InputStream is = null;
            Log.d("Jesus","Entered in the weatherhttpclieny");
            Log.d("Jesus",location+"jesus be with us");

            try {
                con = (HttpURLConnection) ( new URL(BASE_URL + location + "&APPID=b86c660825dd1b405a0d545e9c52c16a")).openConnection();
                con.setRequestMethod("GET");
                con.setDoInput(true);
                con.setDoOutput(true);
                con.connect();
                Log.d("Jesus","Connetion successful");
                // Let's read the response
                StringBuffer buffer = new StringBuffer();
                is = con.getInputStream();
                BufferedReader br = new BufferedReader(new InputStreamReader(is));
                String line = null;
                while (  (line = br.readLine()) != null )
                    buffer.append(line + "\r\n");

                is.close();
                con.disconnect();
                Log.d("Jesus",buffer.toString()+"Holy shit");
                return buffer.toString();
            }
            catch(Throwable t) {
                t.printStackTrace();
                Log.d("Jesus",t.getMessage());
            }
            finally {
                try { is.close(); } catch(Throwable t) {}
                try { con.disconnect(); } catch(Throwable t) {}
            }

            return null;

        }

        public byte[] getImage(String code) {
            HttpURLConnection con = null ;
            InputStream is = null;
            try {
                con = (HttpURLConnection) ( new URL(IMG_URL + code)).openConnection();
                con.setRequestMethod("GET");
                con.setDoInput(true);
                con.setDoOutput(true);
                con.connect();

                // Let's read the response
                is = con.getInputStream();
                byte[] buffer = new byte[1024];
                ByteArrayOutputStream baos = new ByteArrayOutputStream();

                while ( is.read(buffer) != -1)
                    baos.write(buffer);

                return baos.toByteArray();
            }
            catch(Throwable t) {
                t.printStackTrace();
            }
            finally {
                try { is.close(); } catch(Throwable t) {}
                try { con.disconnect(); } catch(Throwable t) {}
            }

            return null;

        }

}
