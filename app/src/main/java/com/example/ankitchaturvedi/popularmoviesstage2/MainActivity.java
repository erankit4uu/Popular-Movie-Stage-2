package com.example.ankitchaturvedi.popularmoviesstage2;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import com.example.ankitchaturvedi.popularmoviesstage2.Adapter.RecyclerViewAdapter;
import com.example.ankitchaturvedi.popularmoviesstage2.ResponseParser.MainGridResponseParser;
import com.example.ankitchaturvedi.popularmoviesstage2.Utils.Constant;
import com.example.ankitchaturvedi.popularmoviesstage2.Utils.ServiceCall;
import com.google.gson.Gson;

import org.apache.http.entity.StringEntityHC4;


public class MainActivity extends AppCompatActivity {

    RecyclerView mrecycler_view;
    RecyclerViewAdapter mRecyclerViewAdapter;
    MainGridResponseParser mMainGridResponseParser;
    ProgressBar progressBar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        mrecycler_view = (RecyclerView) findViewById(R.id.recycler_view);
        progressBar = (ProgressBar) findViewById(R.id.progress_bar);

        RecyclerView.LayoutManager layoutManager = new GridLayoutManager(MainActivity.this,2);
        mrecycler_view.setLayoutManager(layoutManager);

        int PERMISSION = 1;
        String[] PERMISSIONS = {Manifest.permission.INTERNET};


        if(!CheckPermissions(this, PERMISSIONS)){
            ActivityCompat.requestPermissions(this, PERMISSIONS, PERMISSION);
        }

        GridViewAsync mGridViewAsync = new GridViewAsync();
        mGridViewAsync.execute(Constant.TXT_BLANK);

    }

    public static boolean CheckPermissions(Context context, String... permissions) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && context != null && permissions != null) {
            for (String permission : permissions) {
                if (ActivityCompat.checkSelfPermission(context, permission) != PackageManager.PERMISSION_GRANTED) {
                    return false;
                }
            }
        }
        return true;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.top_rated) {
            TopRatedUrlAsync mTopRatedUrlAsync=new TopRatedUrlAsync();
            mTopRatedUrlAsync.execute(Constant.TXT_BLANK);
            return true;
        }
        if(id==R.id.popular) {
            GridViewAsync mGridViewAsync = new GridViewAsync();
            mGridViewAsync.execute(Constant.TXT_BLANK);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private class GridViewAsync extends AsyncTask<String, Void, String> {

        private String response = null;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            try {
                progressBar.setVisibility(View.VISIBLE);
                mrecycler_view.setVisibility(View.GONE);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        @Override
        protected String doInBackground(String... params) {
            response = CallLoginService();
            return response;
        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);
            progressBar.setVisibility(View.GONE);
            mrecycler_view.setVisibility(View.VISIBLE);
            try {
                Gson gson = new Gson();

                mMainGridResponseParser = gson.fromJson(response, MainGridResponseParser.class);


                if (mMainGridResponseParser != null) {

                    mRecyclerViewAdapter = new RecyclerViewAdapter(MainActivity.this, mMainGridResponseParser);
                    mrecycler_view.setAdapter(mRecyclerViewAdapter);

                }
            }  catch (Exception e) {

                e.printStackTrace();

            }
        }

        private String CallLoginService() {

            ServiceCall.sortMovie = "popular";
            String urls = String.valueOf(ServiceCall.buildUrl(Constant.api_key));

            try {

                StringEntityHC4 stringEntity = new StringEntityHC4("");
                ServiceCall serviceCall = new ServiceCall(MainActivity.this, urls, stringEntity);

                response = String.valueOf(serviceCall.request(urls));
                Log.e("","############################### Response"+response);

            }  catch (Exception e) {
                e.printStackTrace();
            }
            return response;
        }
    }


    private class TopRatedUrlAsync extends AsyncTask<String, Void, String> {

        private String response = null;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            try {
                progressBar.setVisibility(View.VISIBLE);
                mrecycler_view.setVisibility(View.GONE);

            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        @Override
        protected String doInBackground(String... params) {
            response = CallLoginService();
            return response;
        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);
            progressBar.setVisibility(View.GONE);
            mrecycler_view.setVisibility(View.VISIBLE);

            try {
                Gson gson = new Gson();

                mMainGridResponseParser = gson.fromJson(response, MainGridResponseParser.class);
                if (mMainGridResponseParser != null) {

                    mRecyclerViewAdapter = new RecyclerViewAdapter(MainActivity.this, mMainGridResponseParser);
                    mrecycler_view.setAdapter(mRecyclerViewAdapter);

                }


            } catch (Exception e) {

                e.printStackTrace();

            }
        }

        private String CallLoginService() {

            ServiceCall.sortMovie = "top_rated";
            String urls = String.valueOf(ServiceCall.buildUrl(Constant.api_key));

            try {

                StringEntityHC4 stringEntity = new StringEntityHC4("");
                ServiceCall serviceCall = new ServiceCall(MainActivity.this, urls, stringEntity);

                response = String.valueOf(serviceCall.request(urls));
                Log.e("","############################### Response"+response);
            }  catch (Exception e) {
                e.printStackTrace();
            }
            return response;

        }

    }

}
