package ivan.projectManager.application.service.impl;

import ivan.projectManager.application.dto.AttachmentDTO;
import ivan.projectManager.application.model.Attachment;
import ivan.projectManager.application.repository.AttachmentRepository;
import ivan.projectManager.application.service.AttachmentService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class AttachmentServiceImpl implements AttachmentService {
    private final ModelMapper modelMapper;
    private final AttachmentRepository attachmentRepositoryImpl;

    @Autowired
    public AttachmentServiceImpl(ModelMapper modelMapper, AttachmentRepository attachmentRepositoryImpl) {
        this.modelMapper = modelMapper;
        this.attachmentRepositoryImpl = attachmentRepositoryImpl;
    }

    public List<AttachmentDTO> getAll() {
        return attachmentRepositoryImpl.getAll().stream().map(this::mapAttachmentToDTO).collect(Collectors.toList());
    }

    public AttachmentDTO save(AttachmentDTO attachmentDTO) {
        return mapAttachmentToDTO(attachmentRepositoryImpl.save(mapDTOToAttachment(attachmentDTO)));
    }

    public Optional<AttachmentDTO> getById(int id) {
        Optional<Attachment> attachmentOptional = attachmentRepositoryImpl.getById(id);
        return attachmentOptional.map(this::mapAttachmentToDTO);
    }


    public Optional<AttachmentDTO> update(int id, AttachmentDTO updatedAttachmentDTO) {
        Optional<Attachment> attachmentOptional = attachmentRepositoryImpl.update(id, mapDTOToAttachment(updatedAttachmentDTO));
        return attachmentOptional.map(this::mapAttachmentToDTO);
    }

    public void delete(int id) {
        attachmentRepositoryImpl.delete(id);
    }

    private Attachment mapDTOToAttachment(AttachmentDTO attachmentDTO) {
        return modelMapper.map(attachmentDTO, Attachment.class);
    }

    private AttachmentDTO mapAttachmentToDTO(Attachment attachment) {
        return modelMapper.map(attachment, AttachmentDTO.class);
    }
}

