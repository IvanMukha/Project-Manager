package com.ivan.projectmanager.service.impl;

import com.ivan.projectmanager.dto.AttachmentDTO;
import com.ivan.projectmanager.exeptions.CustomNotFoundException;
import com.ivan.projectmanager.model.Attachment;
import com.ivan.projectmanager.model.Project;
import com.ivan.projectmanager.model.Task;
import com.ivan.projectmanager.repository.AttachmentRepository;
import com.ivan.projectmanager.repository.TaskRepository;
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
    private final TaskRepository taskRepository;

    @Autowired
    public AttachmentServiceImpl(ModelMapper modelMapper, AttachmentRepository attachmentRepository, TaskRepository taskRepository) {
        this.modelMapper = modelMapper;
        this.attachmentRepository = attachmentRepository;
        this.taskRepository = taskRepository;
    }

    public List<AttachmentDTO> getAll(Long projectId, Long taskId) {
        return attachmentRepository.getAll(projectId, taskId).stream().map(this::mapAttachmentToDTO).collect(Collectors.toList());
    }

    @Transactional
    public AttachmentDTO save(Long projectId, Long taskId, AttachmentDTO attachmentDTO) {
        Task task = taskRepository.getById(projectId, taskId)
                .orElseThrow(() -> new CustomNotFoundException(taskId, Task.class));

        if (!task.getProject().getId().equals(projectId)) {
            throw new CustomNotFoundException(projectId, Project.class);
        }
        attachmentDTO.setTaskId(taskId);
        return mapAttachmentToDTO(attachmentRepository.save(mapDTOToAttachment(attachmentDTO)));
    }

    public Optional<AttachmentDTO> getById(Long projectId, Long taskId, Long id) {
        Optional<Attachment> attachmentOptional = attachmentRepository.getById(projectId, taskId, id);
        if (attachmentOptional.isEmpty()) {
            throw new CustomNotFoundException(id, Attachment.class);
        }
        return attachmentOptional.map(this::mapAttachmentToDTO);
    }

    @Transactional
    public Optional<AttachmentDTO> update(Long projectId, Long taskId, Long id, AttachmentDTO updatedAttachmentDTO) {
        Optional<Attachment> attachmentOptional = attachmentRepository.update(projectId, taskId, id, mapDTOToAttachment(updatedAttachmentDTO));
        if (attachmentOptional.isEmpty()) {
            throw new CustomNotFoundException(id, Attachment.class);
        }
        return attachmentOptional.map(this::mapAttachmentToDTO);
    }

    @Transactional
    public void delete(Long projectId, Long taskId, Long id) {
        attachmentRepository.delete(projectId, taskId, id);
    }

    private Attachment mapDTOToAttachment(AttachmentDTO attachmentDTO) {
        return modelMapper.map(attachmentDTO, Attachment.class);
    }

    private AttachmentDTO mapAttachmentToDTO(Attachment attachment) {
        return modelMapper.map(attachment, AttachmentDTO.class);
    }
}

