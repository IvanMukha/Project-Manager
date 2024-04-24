package com.ivan.projectmanager.service;

import com.ivan.projectmanager.dto.CommentDTO;
import com.ivan.projectmanager.model.Comment;
import com.ivan.projectmanager.model.Task;
import com.ivan.projectmanager.model.User;
import com.ivan.projectmanager.repository.CommentRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith({SpringExtension.class, MockitoExtension.class})
@ContextConfiguration(classes = TestServiceConfiguration.class)
public class CommentServiceImplTest {
    @Mock
    private CommentRepository commentRepository;
    @InjectMocks
    private CommentService commentService;

    @Test
    public void testGetAll() {
        Task task = new Task();
        User user = new User();
        Comment comment = new Comment();
        comment.setText("text").setTask(task).setUser(user);
        Comment comment2 = new Comment();
        comment2.setText("text2").setTask(task).setUser(user);

        Mockito.when(commentRepository.getAll()).thenReturn(List.of(comment, comment2));
        List<CommentDTO> result = commentService.getAll();
        assertEquals(2, result.size());
        assertEquals(comment.getText(), result.get(0).getText());
        assertEquals(comment2.getText(), result.get(1).getText());
        verify(commentRepository).getAll();
    }

    @Test
    public void testSave() {
        Comment comment = new Comment();
        comment.setText("text");
        CommentDTO commentDTO = new CommentDTO();
        commentDTO.setText("text");
        Mockito.when(commentRepository.save(comment)).thenReturn(comment);
        CommentDTO commentDTO2 = commentService.save(commentDTO);
        assertNotNull(commentDTO2);
        assertEquals(commentDTO2.getText(), commentDTO.getText());
        verify(commentRepository).save(any());
    }

    @Test
    void testGetById() {
        long id = 1L;
        Comment comment = new Comment();
        comment.setText("text");
        when(commentRepository.getById(id)).thenReturn(Optional.of(comment));
        Optional<CommentDTO> result = commentService.getById(id);
        assertTrue(result.isPresent());
        assertEquals(comment.getText(), result.get().getText());
        verify(commentRepository.getById(1L));
    }

    @Test
    void testUpdate() {
        Comment comment = new Comment();
        comment.setText("text");
        CommentDTO commentDTO = new CommentDTO();
        commentDTO.setText("text");
        when(commentRepository.update(1L, comment)).thenReturn(Optional.of(comment));
        Optional<CommentDTO> result = commentService.update(1L, commentDTO);
        assertTrue(result.isPresent());
        assertEquals(comment.getText(), result.get().getText());
        verify(commentRepository).update(1L, comment);
    }

    @Test
    void testDelete() {
        Long id = 1L;
        commentService.delete(id);
        verify(commentRepository).delete(id);
    }

}
