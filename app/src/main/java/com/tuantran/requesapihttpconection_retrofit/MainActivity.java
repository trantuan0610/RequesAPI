package com.tuantran.requesapihttpconection_retrofit;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    RecyclerView recyclerView;
    AlbumAdapter adapter;
    ArrayList<Album> arrayList;
    ProgressDialog p;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        recyclerView = findViewById(R.id.recyclerview);
        arrayList = new ArrayList<>();
        adapter = new AlbumAdapter(this);
        recyclerView.setAdapter(adapter);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(linearLayoutManager);

        callApi();
    }

    private void callApi() {
        //sử dụng asyncTask
        AsyncTaskExample asyncTask = new AsyncTaskExample();
        asyncTask.execute("https://jsonplaceholder.typicode.com/photos");
        // suwr dungj retrofit

        //        ApiService.apiService.getAllAlbums().enqueue(new Callback<List<Album>>() {
//            @Override
//            public void onResponse(Call<List<Album>> call, Response<List<Album>> response) {
//                albumList = response.body();
//
//                if (albumList != null) {
//                    adapter.setData(albumList);
//                    displayToast("Call Api Successfully");
//                }
//            }
//
//            @Override
//            public void onFailure(Call<List<Album>> call, Throwable t) {
//                displayToast("Call Api Fail");
//            }
//        });


    }

    private class AsyncTaskExample extends AsyncTask<String, Void, String> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            p = new ProgressDialog(MainActivity.this);
            p.setMessage("Please wait...");
            p.setIndeterminate(false);
            p.setCancelable(false);
            p.show();
        }

        @Override
        protected String doInBackground(String... strings) {
            HttpURLConnection urlConnection = null;
            String result = null;
            try {
                URL url = new URL(strings[0]);
                urlConnection = (HttpURLConnection) url.openConnection();
                int code = urlConnection.getResponseCode();
                Log.d("HttpResponse Code", code + "");
                BufferedReader in = new BufferedReader(
                        new InputStreamReader(urlConnection.getInputStream()));
                String inputLine;
                StringBuffer reponse = new StringBuffer();
                while ((inputLine = in.readLine()) != null){
                    reponse.append(inputLine);
                }
                in.close();

                result = reponse.toString();

            } catch (Exception e) {
                e.printStackTrace();

            }finally {
                if(urlConnection != null){
                    urlConnection.disconnect();
                }
            }
            return result;
        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);

            p.hide();
            arrayList.clear();
            if (result != null) {
                try {
                    JSONArray arr = new JSONArray(result);

                    for (int i=0; i < arr.length(); i++) {
                        JSONObject data = arr.getJSONObject(i);

                        Album album = new Album(
                                data.getInt("id"), data.getString("title"), data.getString("url"));

                        arrayList.add(album);
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            //cach 2: gson
//            if (sResult != null) {
//                Gson gson = new Gson();
//                Album [] albums = gson.fromJson(sResult, Album[].class);
//                Collections.addAll(albumList, albums);
//            }

            adapter.setData(arrayList);
        }

    }
    private void displayToast(String message) {
        Toast.makeText(getApplicationContext(), message, Toast.LENGTH_SHORT).show();
    }
}