package org.example.application.service;

import org.example.application.dto.CommentDTO;
import org.example.application.model.Comment;
import org.example.application.repository.CommentRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Controller
public class CommentService {
    private final ModelMapper modelMapper;
    private final CommentRepository commentRepository;

    @Autowired
    public CommentService(ModelMapper modelMapper, CommentRepository commentRepository) {
        this.modelMapper = modelMapper;
        this.commentRepository = commentRepository;
    }

    public List<CommentDTO> getAll() {
        return commentRepository.getAll().stream().map(this::mapCommentToDTO).collect(Collectors.toList());
    }

    public void save(CommentDTO commentDTO) {
        commentRepository.save(mapDTOToComment(commentDTO));
    }

    public Optional<CommentDTO> getById(int id) {
        Optional<Comment> commentDTOOptional = commentRepository.getById(id);
        return commentDTOOptional.map(this::mapCommentToDTO);
    }


    public void update(int id, CommentDTO updatedCommentDTO) {
        commentRepository.update(id, mapDTOToComment(updatedCommentDTO));
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

