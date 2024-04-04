package org.example.application.service;

import org.example.application.repositoryInterfaces.AttachmentRepositoryInterface;
import org.example.application.serviceInterfaces.AttachmentServiceInterface;
import org.example.application.dto.AttachmentDTO;
import org.example.application.model.Attachment;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class AttachmentService implements AttachmentServiceInterface {
    private final ModelMapper modelMapper;
    private final AttachmentRepositoryInterface attachmentRepository;

    @Autowired
    public AttachmentService(ModelMapper modelMapper, AttachmentRepositoryInterface attachmentRepository) {
        this.modelMapper = modelMapper;
        this.attachmentRepository = attachmentRepository;
    }

    public List<AttachmentDTO> getAll() {
        return attachmentRepository.getAll().stream().map(this::mapAttachmentToDTO).collect(Collectors.toList());
    }

    public AttachmentDTO save(AttachmentDTO attachmentDTO) {
        return mapAttachmentToDTO(attachmentRepository.save(mapDTOToAttachment(attachmentDTO)));
    }

    public Optional<AttachmentDTO> getById(int id) {
        Optional<Attachment> attachmentOptional = attachmentRepository.getById(id);
        return attachmentOptional.map(this::mapAttachmentToDTO);
    }


    public Optional<AttachmentDTO> update(int id, AttachmentDTO updatedAttachmentDTO) {
        Optional<Attachment> attachmentOptional = attachmentRepository.update(id, mapDTOToAttachment(updatedAttachmentDTO));
        return attachmentOptional.map(this::mapAttachmentToDTO);
    }

    public void delete(int id) {
        attachmentRepository.delete(id);
    }

    private Attachment mapDTOToAttachment(AttachmentDTO attachmentDTO) {
        return modelMapper.map(attachmentDTO, Attachment.class);
    }

    private AttachmentDTO mapAttachmentToDTO(Attachment attachment) {
        return modelMapper.map(attachment, AttachmentDTO.class);
    }
}

