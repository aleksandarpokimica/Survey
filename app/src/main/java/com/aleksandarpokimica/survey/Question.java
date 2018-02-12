package com.aleksandarpokimica.survey;

/**
 * Created by Aleksandar on 2/16/2017.
 */
public class Question {

    private String date;
	private String question;
	private String uid;

    public String getUid() {
        return uid;
    }

    public String getDate() {
        return date;
    }

    public String getQuestion() {
        return question;
    }

    public Question() {
    }

    public Question(String date, String question, String uid) {
        this.date = date;
        this.question = question;
        this.uid = uid;
    }

    public Question(String question) {
        this.question = question;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }
}
