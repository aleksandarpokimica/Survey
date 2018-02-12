package com.aleksandarpokimica.survey;

public class Answer {

    public String uid;
    public int ans;

    public Answer(String uid, int ans) {
        this.uid = uid;
        this.ans = ans;
    }


    public void setuid(String uid) {
        this.uid = uid;
    }

    public void setAns(int ans) {
        this.ans = ans;
    }

    public String getuid() {
        return uid;
    }

    public int getAns() {
        return ans;
    }
}
