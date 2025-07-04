package com.sky.exam;

import com.sky.exam.model.Question;
import com.sky.exam.service.ExaminerServiceImpl;
import com.sky.exam.service.QuestionService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.server.ResponseStatusException;

import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class ExaminerServiceImplTest {

    @Mock
    private QuestionService questionServiceMock;

    @InjectMocks
    private ExaminerServiceImpl examinerService;

    @Test
    void getQuestions_shouldReturnRequestedAmount() {
        // Given
        when(questionServiceMock.getAll()).thenReturn(Set.of(
                new Question("Q1", "A1"),
                new Question("Q2", "A2"),
                new Question("Q3", "A3")
        ));

        when(questionServiceMock.getRandomQuestion())
                .thenReturn(new Question("Q1", "A1"))
                .thenReturn(new Question("Q2", "A2"));

        // When
        Collection<Question> result = examinerService.getQuestions(2);

        // Then
        assertEquals(2, result.size());
        verify(questionServiceMock, times(2)).getRandomQuestion();
    }

    @Test
    void getQuestions_shouldThrowWhenNotEnoughQuestions() {
        // Given
        when(questionServiceMock.getAll()).thenReturn(Set.of(new Question("Q1", "A1")));

        // When & Then
        assertThrows(ResponseStatusException.class,
                () -> examinerService.getQuestions(2));

        verify(questionServiceMock, never()).getRandomQuestion();
    }

    @Test
    void getQuestions_shouldReturnUniqueQuestions() {
        // Given
        List<Question> mockQuestions = List.of(
                new Question("Q1", "A1"),
                new Question("Q2", "A2"),
                new Question("Q3", "A3")
        );

        when(questionServiceMock.getAll()).thenReturn(new HashSet<>(mockQuestions));
        when(questionServiceMock.getRandomQuestion())
                .thenReturn(mockQuestions.get(0))
                .thenReturn(mockQuestions.get(1))
                .thenReturn(mockQuestions.get(2));

        // When
        Collection<Question> result = examinerService.getQuestions(3);

        // Then
        assertEquals(3, result.size());
        assertEquals(3, new HashSet<>(result).size()); // Verify uniqueness
        verify(questionServiceMock, times(3)).getRandomQuestion();
    }
}
