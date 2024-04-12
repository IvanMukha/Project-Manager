package com.ivan.projectManager.service.impl;

import com.ivan.projectManager.dto.CommentDTO;
import com.ivan.projectManager.model.Comment;
import com.ivan.projectManager.repository.CommentRepository;
import com.ivan.projectManager.service.CommentService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

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

    public CommentDTO save(CommentDTO commentDTO) {
        return mapCommentToDTO(commentRepository.save(mapDTOToComment(commentDTO)));
    }

    public Optional<CommentDTO> getById(int id) {
        Optional<Comment> commentOptional = commentRepository.getById(id);
        return commentOptional.map(this::mapCommentToDTO);
    }


    public Optional<CommentDTO> update(int id, CommentDTO updatedCommentDTO) {
        Optional<Comment> commentOptional = commentRepository.update(id, mapDTOToComment(updatedCommentDTO));
        return commentOptional.map(this::mapCommentToDTO);
    }

    public void delete(int id) {
        commentRepository.delete(id);
    }

    private Comment mapDTOToComment(CommentDTO commentDTO) {
        return modelMapper.map(commentDTO, Comment.class);
    }

    private CommentDTO mapCommentToDTO(Comment comment) {
        return modelMapper.map(comment, CommentDTO.class);
    }
}

