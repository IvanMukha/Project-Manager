package com.ivan.projectmanager.repository;

import com.ivan.projectmanager.model.Attachment;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@Transactional
@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = {TestRepositoryConfiguration.class})
@TestPropertySource("classpath:application-test.properties")
public class AttachmentRepositoryTest {

    @Autowired
    private AttachmentRepository attachmentRepository;

    @Test
    @Sql("classpath:data/attachmentrepositorytests/insert-attachments.sql")
    public void testGetAll() {
        List<Attachment> attachments = attachmentRepository.getAll(1L, 1L);
        assertThat(attachments).isNotEmpty();
    }

    @Test
    @Sql("classpath:data/attachmentrepositorytests/insert-attachments.sql")
    public void testGetById() {
        Attachment attachment = attachmentRepository.getById(1L, 1L, 1L).orElse(null);
        assertNotNull(attachment);
        assertEquals(1, attachment.getId());
    }

    @Test
    @Sql("classpath:data/attachmentrepositorytests/insert-attachments.sql")
    public void testDeleteAttachment() {
        Attachment attachment = attachmentRepository.getById(1L, 1L, 1L).orElse(null);
        assertNotNull(attachment);

        attachmentRepository.delete(1L, 1L, attachment.getId());
        assertFalse(attachmentRepository.getById(1L, 1L, 1L).isPresent());
    }

    @Test
    @Sql("classpath:data/attachmentrepositorytests/insert-attachments.sql")
    public void testUpdate() {
        Attachment attachment = attachmentRepository.getById(1L, 1L, 1L).orElse(null);
        assertNotNull(attachment);

        attachment.setTitle("Updated Title");
        attachment.setPath("/attachments/updated.pdf");
        Attachment updatedAttachment = attachmentRepository.update(1L, 1L, 1L, attachment).orElse(null);
        assertNotNull(updatedAttachment);
        assertEquals("Updated Title", updatedAttachment.getTitle());
        assertEquals("/attachments/updated.pdf", updatedAttachment.getPath());
        assertNotNull(updatedAttachment.getTask());
    }


    @Test
    @Sql("classpath:data/attachmentrepositorytests/insert-attachments.sql")
    public void testFindByTitleCriteria() {
        List<Attachment> foundAttachments = attachmentRepository.findByTitleCriteria("Test Attachment");
        assertThat(foundAttachments).isNotEmpty();
        assertThat(foundAttachments).hasSize(1);
        assertThat(foundAttachments.getFirst().getTitle()).isEqualTo("Test Attachment");
    }

    @Test
    @Sql("classpath:data/attachmentrepositorytests/insert-attachments.sql")
    public void testFindByTitleJpql() {
        List<Attachment> foundAttachments = attachmentRepository.findByTitleJpql("Test Attachment");
        assertThat(foundAttachments).isNotEmpty();
        assertThat(foundAttachments).hasSize(1);
        assertThat(foundAttachments.getFirst().getTitle()).isEqualTo("Test Attachment");
    }

    @Test
    @Sql("classpath:data/attachmentrepositorytests/insert-attachments.sql")
    public void testFindByTitleCriteriaFetch() {
        List<Attachment> foundAttachments = attachmentRepository.findByTitleCriteriaFetch("Test Attachment");
        assertThat(foundAttachments).isNotEmpty();
        assertThat(foundAttachments).hasSize(1);
        assertThat(foundAttachments.getFirst().getTitle()).isEqualTo("Test Attachment");
        assertThat(foundAttachments.getFirst().getTask()).isNotNull();
    }

    @Test
    @Sql("classpath:data/attachmentrepositorytests/insert-attachments.sql")
    public void testFindByTitleJpqlFetch() {
        List<Attachment> foundAttachments = attachmentRepository.findByTitleJpqlFetch("Test Attachment");
        assertThat(foundAttachments).isNotEmpty();
        assertThat(foundAttachments).hasSize(1);
        assertThat(foundAttachments.getFirst().getTitle()).isEqualTo("Test Attachment");
        assertThat(foundAttachments.getFirst().getTask()).isNotNull();
    }

    @Test
    @Sql("classpath:data/attachmentrepositorytests/insert-attachments.sql")
    public void testFindByTitleWithEntityGraphFetch() {
        List<Attachment> foundAttachments = attachmentRepository.findByTitleWithEntityGraphFetch("Test Attachment");
        assertThat(foundAttachments).isNotEmpty();
        assertThat(foundAttachments).hasSize(1);
        assertThat(foundAttachments.getFirst().getTitle()).isEqualTo("Test Attachment");
        assertThat(foundAttachments.getFirst().getTask()).isNotNull();
    }
}