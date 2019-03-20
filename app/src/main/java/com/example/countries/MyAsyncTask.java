package com.example.countries;

import android.os.AsyncTask;
import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class MyAsyncTask extends AsyncTask<Void,Void,Void> {

    @Override
    protected Void doInBackground(Void... voids) {

        JSONParser jParser = new JSONParser();
        JSONObject json = jParser.getJSONFromUrl("http://users.itk.ppke.hu/~matad/country_list.json");
        String str = "tmp";

        try {
            if(json.getString("name")!=null) {
                str = json.getString("name");
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }


        Log.d("MyAsyncTask", ""+str);

        return null;
    }
    //TODO Cleartext HTTP traffic to users.itk.ppke.hu not permitted
    public static class JSONParser {

        public JSONObject getJSONFromUrl(String url) {

            try {
                final HttpURLConnection connection = (HttpURLConnection) new URL(
                        url).openConnection();

                if (connection.getResponseCode() == 200) {
                    final BufferedReader reader = new BufferedReader(
                            new InputStreamReader(connection.getInputStream()));

                    String line = null;
                    final StringBuffer buffer = new StringBuffer(4096);
                    while ((line = reader.readLine()) != null) {
                        buffer.append(line);
                    }
                    reader.close();

                    final JSONObject jObj = new JSONObject(buffer.toString());
                    return jObj;
                } else {
                    return null;
                }

            } catch (IOException e) {
                throw new RuntimeException(e);
            } catch (JSONException e) {
                e.printStackTrace();
            }
            return null;
        }
    }



}
