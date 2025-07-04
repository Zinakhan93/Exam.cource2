package com.sky.exam;

import com.sky.exam.model.Question;
import com.sky.exam.service.JavaQuestionService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class JavaQuestionServiceTest {
    @Mock
    private Set<Question> questions;

    @InjectMocks
    private JavaQuestionService questionService;

    @Test
    void add_shouldAddQuestionAndReturnIt() {
        // Given
        String questionText = "Q1";
        String answerText = "A1";
        Question expected = new Question(questionText, answerText);

        // When
        Question result = questionService.add(questionText, answerText);

        // Then
        assertEquals(expected, result);
        verify(questions).add(expected);
    }

    @Test
    void remove_shouldRemoveQuestionAndReturnIt() {
        // Given
        Question question = new Question("Q1", "A1");
        when(questions.remove(question)).thenReturn(true);

        // When
        Question result = questionService.remove("Q1", "A1");

        // Then
        assertEquals(question, result);
        verify(questions).remove(question);
    }

    @Test
    void remove_shouldReturnNullWhenQuestionNotExists() {
        // Given
        Question question = new Question("Q1", "A1");
        when(questions.remove(question)).thenReturn(false);

        // When
        Question result = questionService.remove("Q1", "A1");

        // Then
        assertNull(result);
        verify(questions).remove(question);
    }

    @Test
    void getAll_shouldReturnAllQuestions() {
        // Given
        Set<Question> expectedQuestions = new HashSet<>(Arrays.asList(
                new Question("Q1", "A1"),
                new Question("Q2", "A2")
        ));
        when(questions.iterator()).thenReturn(expectedQuestions.iterator());

        // When
        Collection<Question> result = questionService.getAll();

        // Then
        assertEquals(2, result.size());
        assertTrue(result.containsAll(expectedQuestions));
    }

    @Test
    void getRandomQuestion_shouldReturnQuestion() {
        // Given
        Question expectedQuestion = new Question("Q1", "A1");
        when(questions.isEmpty()).thenReturn(false);
        when(questions.size()).thenReturn(1);
        when(questions.iterator()).thenReturn(Collections.singleton(expectedQuestion).iterator());

        // When
        Question result = questionService.getRandomQuestion();

        // Then
        assertEquals(expectedQuestion, result);
    }

    @Test
    void getRandomQuestion_shouldThrowWhenNoQuestions() {
        // Given
        when(questions.isEmpty()).thenReturn(true);

        // When & Then
        assertThrows(IllegalStateException.class,
                () -> questionService.getRandomQuestion());
    }
}
