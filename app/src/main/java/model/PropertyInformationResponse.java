package model;

import android.graphics.Movie;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by pedro on 28/07/16.
 */
public class PropertyInformationResponse {

    @SerializedName("results")
    private List<PropertyInformation> results;

    public List<PropertyInformation> getResults() {
        return results;
    }

    public void setResults(List<PropertyInformation> results) {
        this.results = results;
    }
}
