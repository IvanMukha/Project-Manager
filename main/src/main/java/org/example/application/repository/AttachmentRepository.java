package org.example.application.repository;

import org.example.application.model.Attachment;
import org.springframework.stereotype.Repository;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Repository
public class AttachmentRepository {
    List<Attachment> attachments = new ArrayList<>();

    public List<Attachment> getAll() {
        return new ArrayList<>(attachments);
    }

    public void save(Attachment attachment) {
        attachments.add(attachment);
    }

    public Optional<Attachment> getById(int id) {
        return attachments.stream().filter(attachment -> attachment.getId() == id).findFirst();
    }

    public void update(int id, Attachment updatedAttachment) {
        Optional<Attachment> optionalAttachment = getById(id);
        optionalAttachment.ifPresent(attachment -> attachment.setTitle(updatedAttachment.getTitle()));
    }

    public void delete(int id) {
        attachments.removeIf(attachment -> attachment.getId() == id);
    }
}
