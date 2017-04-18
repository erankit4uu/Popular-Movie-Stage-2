package com.example.ankitchaturvedi.popularmoviesstage2.Adapter;


import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.ankitchaturvedi.popularmoviesstage2.R;
import com.example.ankitchaturvedi.popularmoviesstage2.ResponseParser.MovieTrailerResponseParser;
import com.example.ankitchaturvedi.popularmoviesstage2.Utils.Constant;

/**
 * Created by Ankit.Chaturvedi on 18-Apr-17.
 */

public class MovieTrailerAdapter extends RecyclerView.Adapter<MovieTrailerAdapter.MyViewHolder> {
    private Context mcontext;
    MovieTrailerResponseParser mMovieTrailerResponseParser;

    public MovieTrailerAdapter(Context mcontext, MovieTrailerResponseParser mMovieTrailerResponseParser) {
        this.mcontext = mcontext;
        this.mMovieTrailerResponseParser = mMovieTrailerResponseParser;
    }

    @Override
    public MovieTrailerAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.trailer_row, parent, false);
        return new MovieTrailerAdapter.MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(MovieTrailerAdapter.MyViewHolder holder, final int position) {


        holder.mtv_trailer.setText(mMovieTrailerResponseParser.results.get(position).name);

        holder.mfab_trailer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                PlayTrailer(mMovieTrailerResponseParser.results.get(position).key);
            }
        });

    }

    @Override
    public int getItemCount() {
        return mMovieTrailerResponseParser.results != null ? mMovieTrailerResponseParser.results.size() : 0;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        FloatingActionButton mfab_trailer;
        TextView mtv_trailer;

        public MyViewHolder(View itemView) {
            super(itemView);

            mtv_trailer = (TextView) itemView.findViewById(R.id.tv_trailer);
            mfab_trailer = (FloatingActionButton) itemView.findViewById(R.id.fab_trailer);


        }
    }

    private void PlayTrailer(String key) {

        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("vnd.youtube:" + key));
        if (intent.resolveActivity(mcontext.getPackageManager()) == null) {
            intent = new Intent(Intent.ACTION_VIEW, Uri.parse(Constant.YouTube_URL + key));
        }
        mcontext.startActivity(intent);
    }
}
