package com.example.ankitchaturvedi.popularmoviesstage2.Adapter;

import android.content.Context;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.ankitchaturvedi.popularmoviesstage2.R;

import com.example.ankitchaturvedi.popularmoviesstage2.ResponseParser.ReviewsResponseParser;

/**
 * Created by Ankit.Chaturvedi on 18-Apr-17.
 */

public class ReviewsAdapter extends RecyclerView.Adapter<ReviewsAdapter.MyViewHolder> {
    private Context mcontext;
    ReviewsResponseParser mReviewsResponseParser;

    public ReviewsAdapter(Context mcontext, ReviewsResponseParser mReviewsResponseParser) {
        this.mcontext = mcontext;
        this.mReviewsResponseParser = mReviewsResponseParser;
    }

    @Override
    public ReviewsAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.trailer_row, parent, false);
        return new ReviewsAdapter.MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ReviewsAdapter.MyViewHolder holder, final int position) {


        holder.mtv_trailer.setText(mReviewsResponseParser.results.get(position).author);
        holder.mtv_content.setVisibility(View.VISIBLE);
        holder.mtv_content.setText(mReviewsResponseParser.results.get(position).content);
        holder.mfab_trailer.setImageResource(R.drawable.review_white);


    }

    @Override
    public int getItemCount() {
        return mReviewsResponseParser.results != null ? mReviewsResponseParser.results.size() : 0;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        FloatingActionButton mfab_trailer;
        TextView mtv_trailer,mtv_content;

        public MyViewHolder(View itemView) {
            super(itemView);

            mtv_trailer = (TextView) itemView.findViewById(R.id.tv_trailer);
            mtv_content = (TextView) itemView.findViewById(R.id.tv_content);
            mfab_trailer = (FloatingActionButton) itemView.findViewById(R.id.fab_trailer);


        }
    }

}
