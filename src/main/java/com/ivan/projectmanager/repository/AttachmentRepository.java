package com.ivan.projectmanager.repository;

import com.ivan.projectmanager.model.Attachment;

import java.util.List;
import java.util.Optional;

public interface AttachmentRepository {

    List<Attachment> getAll(Long projectId, Long taskId);

    Optional<Attachment> getById(Long projectId, Long taskId, Long id);

    Optional<Attachment> update(Long projectId, Long taskId, Long id, Attachment updatedAttachment);

    Attachment save(Attachment attachment);

    void delete(Long projectId, Long taskId, Long id);


    List<Attachment> findByTitleCriteria(String title);

    List<Attachment> findByTitleJpql(String title);

    List<Attachment> findByTitleCriteriaFetch(String title);

    List<Attachment> findByTitleJpqlFetch(String title);

    List<Attachment> findByTitleWithEntityGraphFetch(String title);


}
