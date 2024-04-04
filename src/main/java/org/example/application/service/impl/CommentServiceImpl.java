package org.example.application.service.impl;

import org.example.application.repository.CommentRepository;
import org.example.application.service.CommentService;
import org.example.application.dto.CommentDTO;
import org.example.application.model.Comment;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Controller
public class CommentServiceImpl implements CommentService {
    private final ModelMapper modelMapper;
    private final CommentRepository commentRepositoryImpl;

    @Autowired
    public CommentServiceImpl(ModelMapper modelMapper, CommentRepository commentRepositoryImpl) {
        this.modelMapper = modelMapper;
        this.commentRepositoryImpl = commentRepositoryImpl;
    }

    public List<CommentDTO> getAll() {
        return commentRepositoryImpl.getAll().stream().map(this::mapCommentToDTO).collect(Collectors.toList());
    }

    public CommentDTO save(CommentDTO commentDTO) {
        return mapCommentToDTO(commentRepositoryImpl.save(mapDTOToComment(commentDTO)));
    }

    public Optional<CommentDTO> getById(int id) {
        Optional<Comment> commentOptional = commentRepositoryImpl.getById(id);
        return commentOptional.map(this::mapCommentToDTO);
    }


    public Optional<CommentDTO> update(int id, CommentDTO updatedCommentDTO) {
        Optional<Comment> commentOptional = commentRepositoryImpl.update(id, mapDTOToComment(updatedCommentDTO));
        return commentOptional.map(this::mapCommentToDTO);
    }

    public void delete(int id) {
        commentRepositoryImpl.delete(id);
    }

    private Comment mapDTOToComment(CommentDTO commentDTO) {
        return modelMapper.map(commentDTO, Comment.class);
    }

    private CommentDTO mapCommentToDTO(Comment comment) {
        return modelMapper.map(comment, CommentDTO.class);
    }
}

