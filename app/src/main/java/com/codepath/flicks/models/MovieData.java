package com.codepath.flicks.models;

import org.json.JSONException;
import org.json.JSONObject;

public class MovieData {
    // value from API
    private String title;
    private String overview;
    private String posterPath; // only the path
    private String backdropPath;

    // initialize from JSON data
    public MovieData(JSONObject object) throws JSONException {
        title = object.getString("title");
        overview = object.getString("overview");
        posterPath = object.getString("poster_path");
        backdropPath = object.getString("backdrop_path");


    }

    public String getTitle() {
        return title;
    }

    public String getOverview() {
        return overview;
    }

    public String getPosterPath() {
        return posterPath;
    }

    public String getBackdropPath() {
        return backdropPath;
    }
}
