package com.pedroabtome.secure_quiz_app.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

import org.springframework.stereotype.Service;

import com.pedroabtome.secure_quiz_app.model.Question;

@Service
public class QuestionsService {

    // Stores questions in memory, keyed by id
    private final Map<Integer, Question> questionsById = new ConcurrentHashMap<>();

    // Simple in-memory id generator (only used if you add a Question with id <= 0)
    private final AtomicInteger nextId = new AtomicInteger(1);

    /**
     * Returns all questions as a List<Question>.
     */
    public List<Question> loadQuizzes() {
        return new ArrayList<>(questionsById.values());
    }

    /**
     * Adds a new Question to the in-memory collection.
     */
    public Question addQuiz(Question question) {
        if (question == null) {
            throw new IllegalArgumentException("Question cannot be null");
        }

        // If id isn't set, generate one
        if (question.getId() <= 0) {
            question.setId(nextId.getAndIncrement());
        }

        questionsById.put(question.getId(), question);
        return question;
    }

    /**
     * Updates an existing Question in the in-memory collection.
     */
    public Question editQuiz(Question question) {
        if (question == null) {
            throw new IllegalArgumentException("Question cannot be null");
        }

        int id = question.getId();
        if (!questionsById.containsKey(id)) {
            throw new IllegalArgumentException("Question not found with id: " + id);
        }

        questionsById.put(id, question);
        return question;
    }

    /**
     * Deletes a Question by id.
     */
    public void deleteQuiz(int id) {
        questionsById.remove(id);
    }
}
