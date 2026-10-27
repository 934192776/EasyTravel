// TriviaQuestion.java
package com.example.easytravel;

import com.google.gson.annotations.SerializedName;

public class TriviaQuestion {
    @SerializedName("question")
    private String question;

    @SerializedName("correct_answer")
    private String correctAnswer;

    public String getQuestion() { return question; }
    public String getCorrectAnswer() { return correctAnswer; }
}