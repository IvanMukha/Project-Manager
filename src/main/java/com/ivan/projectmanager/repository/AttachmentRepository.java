package com.ivan.projectmanager.repository;

import com.ivan.projectmanager.dto.AttachmentDTO;
import com.ivan.projectmanager.model.Attachment;

import java.util.List;

public interface AttachmentRepository extends CrudRepository<Attachment,Integer> {

    List<Attachment> findByTitleCriteria(String title);

    List<Attachment> findByTitleJpql(String title);

    List<Attachment> findByTitleCriteriaFetch(String title);

    List<Attachment> findByTitleJpqlFetch(String title);

    List<Attachment> findByTitleWithEntityGraphFetch(String title);

}
