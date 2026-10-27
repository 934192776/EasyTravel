// TriviaResponse.java
package com.example.easytravel;

import com.google.gson.annotations.SerializedName;
import java.util.List;

public class TriviaResponse {
    @SerializedName("results")
    private List<TriviaQuestion> results;

    public List<TriviaQuestion> getResults() { return results; }
}