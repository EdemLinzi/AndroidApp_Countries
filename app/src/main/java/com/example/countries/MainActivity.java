package com.example.countries;

import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;

public class MainActivity extends AppCompatActivity {
    Button button;
    RecyclerView recyclerView ;
    RecyclerView.Adapter mAdapter;
    RecyclerView.LayoutManager layoutManager;

    ArrayList<CountryData> countryData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        recyclerView = findViewById(R.id.recyclerView);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        countryData = new ArrayList<>();
        mAdapter = new MyAdapter(countryData,MainActivity.this);
        recyclerView.setAdapter(mAdapter);

        read();

    }

    public void read() {
        Log.i("MainActivity","ReadStarted");
        countryData.clear();
         new AsyncTask<String,Void, Bitmap>() {
            String responseJSON;
            @Override
            protected Bitmap doInBackground(String... voids) {
                URL url;
                StringBuffer response = new StringBuffer();
                try

                {
                    url = new URL("http://users.itk.ppke.hu/~matad/country_list.json");
                } catch(
                        MalformedURLException e)

                {
                    throw new IllegalArgumentException("invalid url");
                }

                HttpURLConnection conn = null;
                try

                {
                    conn = (HttpURLConnection) url.openConnection();
                    conn.setDoOutput(false);
                    conn.setDoInput(true);
                    conn.setUseCaches(false);
                    conn.setRequestMethod("GET");
                    conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded;charset=UTF-8");

                    // handle the response
                    int status = conn.getResponseCode();
                    if (status != 200) {
                        throw new IOException("Post failed with error code " + status);
                    } else {
                        BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
                        String inputLine;
                        while ((inputLine = in.readLine()) != null) {
                            response.append(inputLine);
                        }
                        in.close();
                    }
                } catch(
                        Exception e)

                {
                    e.printStackTrace();
                } finally

                {
                    if (conn != null) {
                        conn.disconnect();
                    }

                    //Here is your json in string format
                    Log.i("MainActivity",response.toString());
                    responseJSON = response.toString();
                    String data = "";
                    JSONArray jArray = null;
                    try {
                        jArray = new JSONArray(responseJSON);
                        for(int i = 0; i<jArray.length(); ++i)
                        {
                            CountryData tmp = new CountryData();
                            String name = jArray.getJSONObject(i).getString("name");// name of the country
                            tmp.setName(name);
                            String imageUrl = jArray.getJSONObject(i).getString("imageUrl");// imageUrl of the country
                            tmp.setImageUrl(imageUrl);
                            Integer population = jArray.getJSONObject(i).optInt("population");
                            tmp.setPopulation(population);
                            Integer area = jArray.getJSONObject(i).optInt("area");
                            tmp.setArea(area);
                            if(jArray.getJSONObject(i).getJSONArray("latlng").length() == 2) {
                                tmp.setLatlng((Double)jArray.getJSONObject(i).getJSONArray("latlng").get(0),(Double) jArray.getJSONObject(i).getJSONArray("latlng").get(1));
                            }
                            countryData.add(tmp);

                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }


                }
                return null;
            }

            @Override
            protected void onPostExecute(Bitmap aVoid) {
                super.onPostExecute(aVoid);
               // datas.setText(responseJSON);
                mAdapter.notifyDataSetChanged();

            }
        }.execute();

        /*MyAsyncTask myAsyncTask = new MyAsyncTask();
        myAsyncTask.execute();*/
       /* Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                String data = "";
                JSONArray jArray = null;
                try {
                    jArray = new JSONArray(readJSONFromAsset());

                for(int i = 0; i<jArray.length(); ++i)
                {
                    String name = jArray.getJSONObject(i).getString("name");// name of the country
                    data = data + name + "\n";
                }

                datas.setText(data);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
        thread.start();
        */

    }



        public String readJSONFromAsset() {
            String json = null;
            try {
                InputStream is = getAssets().open("country_list.json");
                int size = is.available();
                byte[] buffer = new byte[size];
                is.read(buffer);
                is.close();
                json = new String(buffer, "UTF-8");
            } catch (IOException ex) {
                ex.printStackTrace();
                return null;
            }
            return json;
        }


}
