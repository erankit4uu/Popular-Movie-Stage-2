package com.example.ankitchaturvedi.popularmoviesstage2.Adapter;


import android.app.Activity;
import android.app.ActivityOptions;
import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.ankitchaturvedi.popularmoviesstage2.ActivityMovieDetails;
import com.example.ankitchaturvedi.popularmoviesstage2.R;
import com.example.ankitchaturvedi.popularmoviesstage2.ResponseParser.MainGridResponseParser;
import com.example.ankitchaturvedi.popularmoviesstage2.Utils.helper;
import com.squareup.picasso.Picasso;

/**
 * Created by Ankit.Chaturvedi on 11-Apr-17.
 */

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.MyViewHolder> {
    private Context mcontext;
    MainGridResponseParser mMainGridResponseParser;

    public RecyclerViewAdapter(Context mcontext, MainGridResponseParser mMainGridResponseParser) {
        this.mcontext = mcontext;
        this.mMainGridResponseParser = mMainGridResponseParser;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.grid_item, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, final int position) {

        Picasso.with(mcontext)
                .load(helper.base_url + helper.poster_sizes + mMainGridResponseParser.results.get(position).poster_path)
                .into(holder.miv_detail);


        holder.mtv_ratings.setText(String.valueOf(mMainGridResponseParser.results.get(position).vote_average));

        holder.miv_detail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent in = new Intent(mcontext, ActivityMovieDetails.class);
                in.putExtra("backdrop_path", mMainGridResponseParser.results.get(position).backdrop_path);
                in.putExtra("overview", mMainGridResponseParser.results.get(position).overview);
                in.putExtra("release_date", mMainGridResponseParser.results.get(position).release_date);
                in.putExtra("vote_average", mMainGridResponseParser.results.get(position).vote_average);
//                in.putExtra("adult",mMainGridResponseParser.results.get(position).get);
                in.putExtra("title", mMainGridResponseParser.results.get(position).title);
                in.putExtra("id", mMainGridResponseParser.results.get(position).id);
                String transitionName = mcontext.getString(R.string.title);
                ActivityOptions transitionActivityOptions = null;
                if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP) {
                    transitionActivityOptions = ActivityOptions.makeSceneTransitionAnimation((Activity) mcontext, view, transitionName);
                }
                if (transitionActivityOptions != null) {
                    mcontext.startActivity(in, transitionActivityOptions.toBundle());
                } else mcontext.startActivity(in);
            }
        });
    }

    @Override
    public int getItemCount() {
        return mMainGridResponseParser.results != null ? mMainGridResponseParser.results.size() : 0;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        ImageView miv_detail;
        TextView mtv_ratings;
        CardView mcard_view;

        public MyViewHolder(View itemView) {
            super(itemView);

            miv_detail = (ImageView) itemView.findViewById(R.id.iv_detail);
            mtv_ratings = (TextView) itemView.findViewById(R.id.tv_ratings);
            mcard_view = (CardView) itemView.findViewById(R.id.card_view);

        }
    }
}
