package com.aa.thrivetrack.models;

public class Security {
    String security_question;
    String security_answer;

    public Security() {
    }

    public String getSecurity_question() {
        return security_question;
    }

    public void setSecurity_question(String security_question) {
        this.security_question = security_question;
    }

    public String getSecurity_answer() {
        return security_answer;
    }

    public void setSecurity_answer(String security_answer) {
        this.security_answer = security_answer;
    }

    @Override
    public String toString() {
        return "Security{" +
                "security_question='" + security_question + '\'' +
                ", security_answer='" + security_answer + '\'' +
                '}';
    }
}
