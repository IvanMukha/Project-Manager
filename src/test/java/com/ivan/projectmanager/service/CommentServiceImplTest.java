package com.ivan.projectmanager.service;

import com.ivan.projectmanager.dto.CommentDTO;
import com.ivan.projectmanager.model.Comment;
import com.ivan.projectmanager.model.Project;
import com.ivan.projectmanager.model.Task;
import com.ivan.projectmanager.model.User;
import com.ivan.projectmanager.repository.CommentRepository;
import com.ivan.projectmanager.repository.TaskRepository;
import com.ivan.projectmanager.service.impl.CommentServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class CommentServiceImplTest {
    @Mock
    ModelMapper modelMapper;
    @Mock
    private TaskRepository taskRepository;
    @Mock
    private CommentRepository commentRepository;
    @InjectMocks
    private CommentServiceImpl commentService;


    @Test
    public void testGetAll() {
        Project project = new Project().setId(1L).setTitle("title");
        Task task = new Task().setId(1L).setTitle("task").setProject(project);
        User user = new User().setId(1L).setUsername("username");
        Comment comment = new Comment().setText("text").setTask(task).setUser(user);
        Comment comment2 = new Comment().setText("text2").setTask(task).setUser(user);
        CommentDTO commentDTO = new CommentDTO().setText("text");
        CommentDTO commentDTO2 = new CommentDTO().setText("text2");
        when(modelMapper.map(comment, CommentDTO.class)).thenReturn(commentDTO);
        when(modelMapper.map(comment2, CommentDTO.class)).thenReturn(commentDTO2);
        when(commentRepository.getAll(1L, 1L)).thenReturn(List.of(comment, comment2));
        List<CommentDTO> result = commentService.getAll(1L, 1L);
        assertEquals(2, result.size());
        assertEquals(comment.getText(), result.get(0).getText());
        assertEquals(comment2.getText(), result.get(1).getText());
        verify(commentRepository).getAll(1L, 1L);
    }

    @Test
    public void testSave() {
        Project project = new Project().setId(1L).setTitle("title");
        Task task = new Task().setId(1L).setTitle("task").setProject(project);
        User user = new User().setId(1L).setUsername("username");
        Comment comment = new Comment().setText("text").setTask(task).setUser(user);
        CommentDTO commentDTO = new CommentDTO().setText("text");
        when(modelMapper.map(comment, CommentDTO.class)).thenReturn(commentDTO);
        when(modelMapper.map(commentDTO, Comment.class)).thenReturn(comment);
        when(taskRepository.getById(1L, 1L)).thenReturn(Optional.ofNullable(task));
        when(commentRepository.save(comment)).thenReturn(comment);
        CommentDTO commentDTO2 = commentService.save(1L, 1L, commentDTO);
        assertNotNull(commentDTO2);
        assertEquals(commentDTO2.getText(), commentDTO.getText());
        verify(commentRepository).save(any());
    }

    @Test
    void testGetById() {
        Comment comment = new Comment().setText("text");
        CommentDTO commentDTO = new CommentDTO().setText("text");
        when(modelMapper.map(comment, CommentDTO.class)).thenReturn(commentDTO);
        when(commentRepository.getById(1L, 1L, 1L)).thenReturn(Optional.of(comment));
        Optional<CommentDTO> result = commentService.getById(1L, 1L, 1L);
        assertTrue(result.isPresent());
        assertEquals(comment.getText(), result.get().getText());
        verify(commentRepository).getById(1L, 1L, 1L);
    }

    @Test
    void testUpdate() {
        Comment comment = new Comment().setText("text");
        CommentDTO commentDTO = new CommentDTO().setText("text");
        when(modelMapper.map(comment, CommentDTO.class)).thenReturn(commentDTO);
        when(modelMapper.map(commentDTO, Comment.class)).thenReturn(comment);
        when(commentRepository.update(1L, 1L, 1L, comment)).thenReturn(Optional.of(comment));
        Optional<CommentDTO> result = commentService.update(1L, 1L, 1L, commentDTO);
        assertTrue(result.isPresent());
        assertEquals(comment.getText(), result.get().getText());
        verify(commentRepository).update(1L, 1L, 1L, comment);
    }

    @Test
    void testDelete() {
        commentService.delete(1L, 1L, 1L);
        verify(commentRepository).delete(1L, 1L, 1L);
    }

}
