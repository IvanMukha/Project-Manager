package com.ivan.projectmanager.service.impl;

import com.ivan.projectmanager.dto.CommentDTO;
import com.ivan.projectmanager.exeptions.CustomNotFoundException;
import com.ivan.projectmanager.model.Comment;
import com.ivan.projectmanager.model.Project;
import com.ivan.projectmanager.model.Task;
import com.ivan.projectmanager.model.User;
import com.ivan.projectmanager.repository.CommentRepository;
import com.ivan.projectmanager.repository.TaskRepository;
import com.ivan.projectmanager.repository.UserRepository;
import com.ivan.projectmanager.service.CommentService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
public class CommentServiceImpl implements CommentService {
    private final ModelMapper modelMapper;
    private final CommentRepository commentRepository;
    private final TaskRepository taskRepository;
    private final UserRepository userRepository;

    @Autowired
    public CommentServiceImpl(ModelMapper modelMapper, CommentRepository commentRepository, TaskRepository taskRepository, UserRepository userRepository) {
        this.modelMapper = modelMapper;
        this.commentRepository = commentRepository;
        this.taskRepository = taskRepository;
        this.userRepository = userRepository;
    }

    public Page<CommentDTO> getAll(Long projectId, Long taskId, Integer page, Integer size) {
        if (page < 0) {
            page = 0;
        }
        if (size <= 0 || size > 100) {
            size = 10;
        }
        return commentRepository.getAll(projectId, taskId, PageRequest.of(page, size))
                .map(this::mapCommentToDTO);
    }

    @Transactional
    public CommentDTO save(Long projectId, Long taskId, CommentDTO commentDTO) {
        Task task = taskRepository.getById(projectId, taskId)
                .orElseThrow(() -> new CustomNotFoundException(taskId, Task.class));

        if (!task.getProject().getId().equals(projectId)) {
            throw new CustomNotFoundException(projectId, Project.class);
        }

        User user = userRepository.getById(commentDTO.getUserId()).orElseThrow();
        Comment comment = modelMapper.map(commentDTO, Comment.class);
        comment.setText(commentDTO.getText()).setTask(task).setAddTime(LocalDateTime.now()).setUser(user);
        return mapCommentToDTO(commentRepository.save(comment));
    }

    public Optional<CommentDTO> getById(Long projectId, Long taskId, Long id) {
        Optional<Comment> commentOptional = commentRepository.getById(projectId, taskId, id);
        if (commentOptional.isEmpty()) {
            throw new CustomNotFoundException(id, Comment.class);
        }
        return commentOptional.map(this::mapCommentToDTO);
    }

    @Transactional
    public Optional<CommentDTO> update(Long projectId, Long taskId, Long id, CommentDTO updatedCommentDTO) {
        Optional<Comment> commentOptional = commentRepository.update(projectId, taskId, id, mapDTOToComment(updatedCommentDTO));
        if (commentOptional.isEmpty()) {
            throw new CustomNotFoundException(id, Comment.class);
        }
        return commentOptional.map(this::mapCommentToDTO);
    }

    @Transactional
    public void delete(Long projectId, Long taskId, Long id) {
        commentRepository.delete(projectId, taskId, id);
    }


    private Comment mapDTOToComment(CommentDTO commentDTO) {
        return modelMapper.map(commentDTO, Comment.class);
    }

    private CommentDTO mapCommentToDTO(Comment comment) {
        return modelMapper.map(comment, CommentDTO.class);
    }
}
