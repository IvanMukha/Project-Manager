package com.ivan.projectmanager.service.impl;

import com.ivan.projectmanager.dto.CommentDTO;
import com.ivan.projectmanager.exeptions.HandleCustomIllegalArgumentException;
import com.ivan.projectmanager.exeptions.HandleCustomNotFoundException;
import com.ivan.projectmanager.exeptions.HandleCustomNullPointerException;
import com.ivan.projectmanager.model.Comment;
import com.ivan.projectmanager.repository.CommentRepository;
import com.ivan.projectmanager.service.CommentService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Controller
public class CommentServiceImpl implements CommentService {
    private final ModelMapper modelMapper;
    private final CommentRepository commentRepository;

    @Autowired
    public CommentServiceImpl(ModelMapper modelMapper, CommentRepository commentRepository) {
        this.modelMapper = modelMapper;
        this.commentRepository = commentRepository;
    }

    public List<CommentDTO> getAll() {
        return commentRepository.getAll().stream().map(this::mapCommentToDTO).collect(Collectors.toList());
    }

    @Transactional
    public CommentDTO save(CommentDTO commentDTO) {
        checkCommentDTO(commentDTO);
        return mapCommentToDTO(commentRepository.save(mapDTOToComment(commentDTO)));
    }

    public Optional<CommentDTO> getById(Long id) {
        checkId(id);
        Optional<Comment> commentOptional = commentRepository.getById(id);
        if (commentOptional.isEmpty()) {
            throw new HandleCustomNotFoundException("Comment with id " + id + " not found");
        }
        return commentOptional.map(this::mapCommentToDTO);
    }

    @Transactional
    public Optional<CommentDTO> update(Long id, CommentDTO updatedCommentDTO) {
        checkId(id);
        checkCommentDTO(updatedCommentDTO);
        Optional<Comment> commentOptional = commentRepository.update(id, mapDTOToComment(updatedCommentDTO));
        if (commentOptional.isEmpty()) {
            throw new HandleCustomNotFoundException("Comment with id " + id + " not found");
        }
        return commentOptional.map(this::mapCommentToDTO);
    }

    @Transactional
    public void delete(Long id) {
        checkId(id);
        commentRepository.delete(id);
    }

    private void checkCommentDTO(CommentDTO commentDTO) {
        if (commentDTO == null) {
            throw new HandleCustomNullPointerException("CommentDTO is null");
        }
        if (commentDTO.getText() == null) {
            throw new HandleCustomNullPointerException("Comment text cannot be null");
        }
        if (commentDTO.getText().isEmpty()) {
            throw new HandleCustomIllegalArgumentException("Comment text cannot be empty");
        }
    }

    private void checkId(Long id) {
        if (id == null) {
            throw new HandleCustomNullPointerException("Comment id cannot be null");
        }
        if (id <= 0) {
            throw new HandleCustomIllegalArgumentException("Comment id must be greater than 0");
        }
    }

    private Comment mapDTOToComment(CommentDTO commentDTO) {
        return modelMapper.map(commentDTO, Comment.class);
    }

    private CommentDTO mapCommentToDTO(Comment comment) {
        return modelMapper.map(comment, CommentDTO.class);
    }
}
