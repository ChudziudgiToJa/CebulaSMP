package pl.chudziudgi.lifesteal.feature.question;

import lombok.Getter;

import java.io.Serializable;

@Getter
public class Question implements Serializable {

    private final String question;
    private final String answer;

    public Question(String question, String answer) {
        this.question = question;
        this.answer = answer;
    }

}