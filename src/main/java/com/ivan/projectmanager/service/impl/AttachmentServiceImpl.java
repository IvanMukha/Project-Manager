package com.ivan.projectmanager.service.impl;

import com.ivan.projectmanager.dto.AttachmentDTO;
import com.ivan.projectmanager.exeptions.HandleCustomIllegalArgumentException;
import com.ivan.projectmanager.exeptions.HandleCustomNotFoundException;
import com.ivan.projectmanager.exeptions.HandleCustomNullPointerException;
import com.ivan.projectmanager.model.Attachment;
import com.ivan.projectmanager.repository.AttachmentRepository;
import com.ivan.projectmanager.service.AttachmentService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class AttachmentServiceImpl implements AttachmentService {
    private final ModelMapper modelMapper;
    private final AttachmentRepository attachmentRepository;

    @Autowired
    public AttachmentServiceImpl(ModelMapper modelMapper, AttachmentRepository attachmentRepository) {
        this.modelMapper = modelMapper;
        this.attachmentRepository = attachmentRepository;
    }

    public List<AttachmentDTO> getAll() {
        return attachmentRepository.getAll().stream().map(this::mapAttachmentToDTO).collect(Collectors.toList());
    }

    @Transactional
    public AttachmentDTO save(AttachmentDTO attachmentDTO) {
        checkAttachmentDTO(attachmentDTO);
        return mapAttachmentToDTO(attachmentRepository.save(mapDTOToAttachment(attachmentDTO)));
    }

    public Optional<AttachmentDTO> getById(Long id) {
        checkId(id);
        Optional<Attachment> attachmentOptional = attachmentRepository.getById(id);
        if (attachmentOptional.isEmpty()) {
            throw new HandleCustomNotFoundException("Attachment with id " + id + " not found");
        }
        return attachmentOptional.map(this::mapAttachmentToDTO);
    }

    @Transactional
    public Optional<AttachmentDTO> update(Long id, AttachmentDTO updatedAttachmentDTO) {
        checkId(id);
        checkAttachmentDTO(updatedAttachmentDTO);
        Optional<Attachment> attachmentOptional = attachmentRepository.update(id, mapDTOToAttachment(updatedAttachmentDTO));
        if (attachmentOptional.isEmpty()) {
            throw new HandleCustomNotFoundException("Attachment with id " + id + " not found");
        }
        return attachmentOptional.map(this::mapAttachmentToDTO);
    }

    @Transactional
    public void delete(Long id) {
        checkId(id);
        attachmentRepository.delete(id);
    }

    private void checkAttachmentDTO(AttachmentDTO attachmentDTO) {
        if (attachmentDTO == null) {
            throw new HandleCustomNullPointerException("AttachmentDTO is null");
        }
        if (attachmentDTO.getTitle() == null) {
            throw new HandleCustomNullPointerException("Attachment title cannot be null");
        }
        if (attachmentDTO.getTitle().isEmpty()) {
            throw new HandleCustomIllegalArgumentException("Attachment title cannot be empty");
        }
        if (attachmentDTO.getTaskId() == null) {
            throw new HandleCustomNullPointerException("Task id cannot be null");
        }
    }

    private void checkId(Long id) {
        if (id == null) {
            throw new HandleCustomNullPointerException("Attachment id cannot be null");
        }
        if (id <= 0) {
            throw new HandleCustomIllegalArgumentException("Attachment id must be greater than 0");
        }
    }

    private Attachment mapDTOToAttachment(AttachmentDTO attachmentDTO) {
        return modelMapper.map(attachmentDTO, Attachment.class);
    }

    private AttachmentDTO mapAttachmentToDTO(Attachment attachment) {
        return modelMapper.map(attachment, AttachmentDTO.class);
    }
}

