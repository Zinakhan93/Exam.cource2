package com.sky.exam.service;

import com.sky.exam.model.Question;

import java.util.*;

public class JavaQuestionService  implements QuestionService{
    private final Set<Question> questions;
    private final Random random;

    public JavaQuestionService(Set<Question> questions) {
        this.questions = questions;
        this.random = new Random();
    }
    @Override
    public Question add(String question, String answer) {
        Question newQuestion = new Question(question, answer);
        questions.add(newQuestion);
        return newQuestion;
    }

    @Override
    public Question remove(String question, String answer) {
        Question questionToRemove = new Question(question, answer);
        return questions.remove(questionToRemove) ? questionToRemove : null;
    }
    @Override
    public Collection<Question> getAll() {
        return new HashSet<>(questions);
    }

    @Override
    public Question getRandomQuestion() {
        if (questions.isEmpty()) {
            throw new IllegalStateException("No questions available");
        }
        int index = random.nextInt(questions.size());
        Iterator<Question> iterator = questions.iterator();
        for (int i = 0; i < index; i++) {
            iterator.next();
        }
        return iterator.next();
    }



}
