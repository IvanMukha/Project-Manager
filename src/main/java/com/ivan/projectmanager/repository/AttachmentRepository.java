package com.ivan.projectmanager.repository;

import com.ivan.projectmanager.model.Attachment;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

public interface AttachmentRepository extends CrudRepository<Attachment, Long> {

    Page<Attachment> getAll(Long projectId, Long taskId, Pageable pageable);

    Optional<Attachment> getById(Long projectId, Long taskId, Long id);

    Optional<Attachment> update(Long projectId, Long taskId, Long id, Attachment updatedAttachment);

    Attachment save(Attachment attachment);

    void delete(Long projectId, Long taskId, Long id);

    List<Attachment> findByTitle(String title);


}
