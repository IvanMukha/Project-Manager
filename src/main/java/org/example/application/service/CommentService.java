package org.example.application.service;

import org.example.application.RepositoryInterfaces.CommentRepositoryInterface;
import org.example.application.ServiceInterfaces.CommentServiceInterface;
import org.example.application.dto.CommentDTO;
import org.example.application.model.Comment;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Controller
public class CommentService implements CommentServiceInterface {
    private final ModelMapper modelMapper;
    private final CommentRepositoryInterface commentRepository;

    @Autowired
    public CommentService(ModelMapper modelMapper, CommentRepositoryInterface commentRepository) {
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

