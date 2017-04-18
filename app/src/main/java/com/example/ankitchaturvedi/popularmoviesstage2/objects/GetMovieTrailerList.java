package com.example.ankitchaturvedi.popularmoviesstage2.objects;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Ankit.Chaturvedi on 18-Apr-17.
 */

public class GetMovieTrailerList {

    @SerializedName("id")
    public String id;

    @SerializedName("iso_639_1")
    public String iso_639_1;

    @SerializedName("iso_3166_1")
    public String iso_3166_1;

    @SerializedName("key")
    public String key;

    @SerializedName("name")
    public String name;

    @SerializedName("site")
    public String site;

    @SerializedName("size")
    public int size;

    @SerializedName("type")
    public String type;
}
