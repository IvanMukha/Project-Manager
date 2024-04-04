package org.example.application.repository;

import org.example.application.RepositoryInterfaces.AttachmentRepositoryInterface;
import org.example.application.model.Attachment;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Repository
public class AttachmentRepository implements AttachmentRepositoryInterface {
    private static final Logger log = LoggerFactory.getLogger(AttachmentRepository.class);
    List<Attachment> attachments = new ArrayList<>();

    public List<Attachment> getAll() {
        return attachments;
    }

    public Attachment save(Attachment attachment) {
        attachments.add(attachment);
        return attachment;
    }

    public Optional<Attachment> getById(int id) {
        Optional<Attachment> optionalAttachment = attachments.stream()
                .filter(attachment -> attachment.getId() == id)
                .findFirst();
        if (optionalAttachment.isEmpty()) {
            log.error("Object with id " + id + " does not exist");
        }
        return optionalAttachment;
    }

    public Optional<Attachment> update(int id, Attachment updatedAttachment) {
        Optional<Attachment> optionalAttachment = getById(id);
        optionalAttachment.ifPresent(attachment -> attachment.setTitle(updatedAttachment.getTitle()));
        return optionalAttachment;
    }

    public void delete(int id) {
        attachments.removeIf(attachment -> attachment.getId() == id);
    }
}
