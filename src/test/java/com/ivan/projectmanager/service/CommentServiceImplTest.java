package com.ivan.projectmanager.service;

import com.ivan.projectmanager.dto.CommentDTO;
import com.ivan.projectmanager.model.Comment;
import com.ivan.projectmanager.model.Task;
import com.ivan.projectmanager.model.User;
import com.ivan.projectmanager.repository.CommentRepository;
import com.ivan.projectmanager.service.impl.CommentServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.web.WebAppConfiguration;

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
@WebAppConfiguration
public class CommentServiceImplTest {
    @Mock
    ModelMapper modelMapper;
    @Mock
    private CommentRepository commentRepository;
    @InjectMocks
    private CommentServiceImpl commentService;


    @Test
    public void testGetAll() {
        Task task = new Task();
        User user = new User();
        Comment comment = new Comment().setText("text").setTask(task).setUser(user);
        Comment comment2 = new Comment().setText("text2").setTask(task).setUser(user);
        CommentDTO commentDTO = new CommentDTO().setText("text");
        CommentDTO commentDTO2 = new CommentDTO().setText("text2");
        when(modelMapper.map(comment, CommentDTO.class)).thenReturn(commentDTO);
        when(modelMapper.map(comment2, CommentDTO.class)).thenReturn(commentDTO2);
        when(commentRepository.getAll()).thenReturn(List.of(comment, comment2));
        List<CommentDTO> result = commentService.getAll();
        assertEquals(2, result.size());
        assertEquals(comment.getText(), result.get(0).getText());
        assertEquals(comment2.getText(), result.get(1).getText());
        verify(commentRepository).getAll();
    }

    @Test
    public void testSave() {
        Comment comment = new Comment().setText("text");
        CommentDTO commentDTO = new CommentDTO().setText("text");
        when(modelMapper.map(comment, CommentDTO.class)).thenReturn(commentDTO);
        when(modelMapper.map(commentDTO, Comment.class)).thenReturn(comment);
        when(commentRepository.save(comment)).thenReturn(comment);
        CommentDTO commentDTO2 = commentService.save(commentDTO);
        assertNotNull(commentDTO2);
        assertEquals(commentDTO2.getText(), commentDTO.getText());
        verify(commentRepository).save(any());
    }

    @Test
    void testGetById() {
        long id = 1L;
        Comment comment = new Comment().setText("text");
        CommentDTO commentDTO = new CommentDTO().setText("text");
        when(modelMapper.map(comment, CommentDTO.class)).thenReturn(commentDTO);
        when(commentRepository.getById(id)).thenReturn(Optional.of(comment));
        Optional<CommentDTO> result = commentService.getById(id);
        assertTrue(result.isPresent());
        assertEquals(comment.getText(), result.get().getText());
        verify(commentRepository).getById(1L);
    }

    @Test
    void testUpdate() {
        Comment comment = new Comment().setText("text");
        CommentDTO commentDTO = new CommentDTO().setText("text");
        when(modelMapper.map(comment, CommentDTO.class)).thenReturn(commentDTO);
        when(modelMapper.map(commentDTO, Comment.class)).thenReturn(comment);
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
