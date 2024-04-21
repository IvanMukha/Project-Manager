package com.ivan.projectmanager.service.impl;

import com.ivan.projectmanager.dto.AttachmentDTO;
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
        return mapAttachmentToDTO(attachmentRepository.save(mapDTOToAttachment(attachmentDTO)));
    }

    public Optional<AttachmentDTO> getById(Long id) {
        Optional<Attachment> attachmentOptional = attachmentRepository.getById(id);
        return attachmentOptional.map(this::mapAttachmentToDTO);
    }

    @Transactional
    public Optional<AttachmentDTO> update(Long id, AttachmentDTO updatedAttachmentDTO) {
        Optional<Attachment> attachmentOptional = attachmentRepository.update(id, mapDTOToAttachment(updatedAttachmentDTO));
        return attachmentOptional.map(this::mapAttachmentToDTO);
    }

    @Transactional
    public void delete(Long id) {
        attachmentRepository.delete(id);
    }

    private Attachment mapDTOToAttachment(AttachmentDTO attachmentDTO) {
        return modelMapper.map(attachmentDTO, Attachment.class);
    }

    private AttachmentDTO mapAttachmentToDTO(Attachment attachment) {
        return modelMapper.map(attachment, AttachmentDTO.class);
    }
}

