package pl.chudziudgi.lifesteal.feature.question;

import java.util.List;
import java.util.Random;

public class QuestionManager {

    private Question question;

    public Question getQuestion() {
        return question;
    }

    public void setQuestion(Question question) {
        this.question = question;
    }

    public Question getRandomQuestion(List<Question> list) {
        Random random = new Random();
        return list.get(random.nextInt(list.size()));
    }
}