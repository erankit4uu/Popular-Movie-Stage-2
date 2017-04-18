package com.example.ankitchaturvedi.popularmoviesstage2.ResponseParser;

import com.example.ankitchaturvedi.popularmoviesstage2.objects.GetReviews;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by Ankit.Chaturvedi on 18-Apr-17.
 */

public class ReviewsResponseParser {

    @SerializedName("id")
    public int id;

    @SerializedName("page")
    public int page;

    @SerializedName("total_results")
    public int total_results;

    public List<GetReviews> results;
}
