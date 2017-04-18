package com.example.ankitchaturvedi.popularmoviesstage2.ResponseParser;

import com.example.ankitchaturvedi.popularmoviesstage2.objects.GetMovieTrailerList;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by Ankit.Chaturvedi on 18-Apr-17.
 */

public class MovieTrailerResponseParser {

    @SerializedName("id")
    public int id;

    public List<GetMovieTrailerList> results;
}
