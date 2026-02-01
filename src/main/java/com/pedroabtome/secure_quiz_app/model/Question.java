package com.pedroabtome.secure_quiz_app.model;

import java.util.ArrayList;
import java.util.List;

public class Question {

    private int id;
    private String questionText;
    private List<String> options;
    private String correctAnswer;

    // Constructors 
    public Question() {
        this.options = new ArrayList<>();
    }

    public Question(int id, String questionText, List<String> options, String correctAnswer) {
        this.id = id;
        this.questionText = questionText;
        this.options = options;
        this.correctAnswer = correctAnswer;
    }

    // Getters
    public int getId() {
        return id;
    }

    public String getQuestionText() {
        return questionText;
    }

    public List<String> getOptions() {
        return options;
    }

    public String getCorrectAnswer() {
        return correctAnswer;
    }

    // Setters
    public void setId(int id) {
        this.id = id;
    }

    public void setQuestionText(String questionText) {
        this.questionText = questionText;
    }

    public void setOptions(List<String> options) {
        this.options = options;
    }

    public void setCorrectAnswer(String correctAnswer) {
        this.correctAnswer = correctAnswer;
    }

    @Override
    public String toString() {
        // Consider excluding correctAnswer from toString() to avoid leaking it into logs
        return "Question{" +
                "id=" + id +
                ", questionText='" + questionText + '\'' +
                ", options=" + options +
                '}';
    }
}
