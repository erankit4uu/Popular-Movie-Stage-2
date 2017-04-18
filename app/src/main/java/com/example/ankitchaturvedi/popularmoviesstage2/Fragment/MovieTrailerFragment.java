package com.example.ankitchaturvedi.popularmoviesstage2.Fragment;

import android.os.AsyncTask;
import android.os.Bundle;

import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.ankitchaturvedi.popularmoviesstage2.Adapter.MovieTrailerAdapter;
import com.example.ankitchaturvedi.popularmoviesstage2.R;
import com.example.ankitchaturvedi.popularmoviesstage2.ResponseParser.MovieTrailerResponseParser;
import com.example.ankitchaturvedi.popularmoviesstage2.Utils.Constant;
import com.example.ankitchaturvedi.popularmoviesstage2.Utils.ServiceCall;
import com.google.gson.Gson;

import org.apache.http.entity.StringEntityHC4;

/**
 * Created by Ankit.Chaturvedi on 18-Apr-17.
 */

public class MovieTrailerFragment extends Fragment {
    RecyclerView mrecycler_view;
    MovieTrailerResponseParser mMovieTrailerResponseParser;
    MovieTrailerAdapter movieTrailerAdapter;
    ProgressBar progressBar;
    TextView mtv_title;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        View rootView = inflater.inflate(R.layout.movie_trailer_fragment, container, false);
        mrecycler_view = (RecyclerView) rootView.findViewById(R.id.recycler_view);
        progressBar = (ProgressBar) rootView.findViewById(R.id.progress_bar);
        mtv_title = (TextView) rootView.findViewById(R.id.tv_title);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        mrecycler_view.setLayoutManager(linearLayoutManager);

        mtv_title.setText(R.string.trailer);

        new MovieTrailersAsync().execute(Constant.TXT_BLANK);
        return rootView;
    }

    private class MovieTrailersAsync extends AsyncTask<String, Void, String> {

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

                mMovieTrailerResponseParser = gson.fromJson(response, MovieTrailerResponseParser.class);


                if (mMovieTrailerResponseParser != null) {

                    movieTrailerAdapter = new MovieTrailerAdapter(getActivity(), mMovieTrailerResponseParser);
                    mrecycler_view.setAdapter(movieTrailerAdapter);

                }
            } catch (Exception e) {

                e.printStackTrace();

            }
        }

        private String CallLoginService() {

            ServiceCall.sortMovie = "videos";
            String urls = String.valueOf(ServiceCall.buildMovieUrl(Constant.api_key));
            try {
                StringEntityHC4 stringEntity = new StringEntityHC4("");
                ServiceCall serviceCall = new ServiceCall(getActivity(), urls, stringEntity);

                response = String.valueOf(serviceCall.request(urls));
                Log.e("", "############################### Response" + response);

            } catch (Exception e) {
                e.printStackTrace();
            }
            return response;
        }
    }

}



