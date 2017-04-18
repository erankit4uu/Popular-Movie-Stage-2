package com.example.ankitchaturvedi.popularmoviesstage2.objects;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Ankit.Chaturvedi on 18-Apr-17.
 */

public class GetReviews {

    @SerializedName("id")
    public String id;

    @SerializedName("author")
    public String author;

    @SerializedName("content")
    public String content;

    @SerializedName("url")
    public String url;


}
