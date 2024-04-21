package com.ivan.projectmanager.repository;

import com.ivan.projectmanager.model.Attachment;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = TestRepositoryConfiguration.class)
public class AttachmentRepositoryImplTest {

    @Autowired
    private AttachmentRepository attachmentRepository;

    @Test
    @Sql(scripts = {"classpath:data/attachmentrepositorytests/delete-attachments.sql"}, executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    @Sql("classpath:data/attachmentrepositorytests/insert-attachments.sql")
    public void testGetAll() {
        List<Attachment> attachments = attachmentRepository.getAll();
        assertThat(attachments).isNotEmpty();
    }

    @Test
    @Sql(scripts = {"classpath:data/attachmentrepositorytests/delete-attachments.sql"}, executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    @Sql("classpath:data/attachmentrepositorytests/insert-attachments.sql")
    public void testGetById() {
        Attachment attachment = attachmentRepository.getById(1L).orElse(null);
        assertNotNull(attachment);
        assertEquals(1, attachment.getId());
    }

    @Test
    @DirtiesContext
    @Sql("classpath:data/attachmentrepositorytests/insert-attachments.sql")
    @Sql(scripts = {"classpath:data/attachmentrepositorytests/delete-attachments.sql"}, executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    public void testDeleteAttachment() {
        Attachment attachment = attachmentRepository.getById(1L).orElse(null);
        assertNotNull(attachment);

        attachmentRepository.delete(attachment.getId());
        assertFalse(attachmentRepository.getById(1L).isPresent());
    }

    @Test
    @Sql("classpath:data/attachmentrepositorytests/insert-attachments.sql")
    @Sql(scripts = {"classpath:data/attachmentrepositorytests/delete-attachments.sql"}, executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    public void testUpdate() {
        Attachment attachment = attachmentRepository.getById(1L).orElse(null);
        assertNotNull(attachment);

        attachment.setTitle("Updated Title");
        attachment.setPath("/attachments/updated.pdf");
        Attachment updatedAttachment = attachmentRepository.update(1L, attachment).orElse(null);
        assertNotNull(updatedAttachment);
        assertEquals("Updated Title", updatedAttachment.getTitle());
        assertEquals("/attachments/updated.pdf", updatedAttachment.getPath());
        assertNotNull(updatedAttachment.getTask());
    }


    @Test
    @Sql("classpath:data/attachmentrepositorytests/insert-attachments.sql")
    @Sql(scripts = {"classpath:data/attachmentrepositorytests/delete-attachments.sql"}, executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    public void testFindByTitleCriteria() {
        List<Attachment> foundAttachments = attachmentRepository.findByTitleCriteria("Test Attachment");
        assertThat(foundAttachments).isNotEmpty();
        assertThat(foundAttachments).hasSize(1);
        assertThat(foundAttachments.getFirst().getTitle()).isEqualTo("Test Attachment");
    }

    @Test
    @Sql("classpath:data/attachmentrepositorytests/insert-attachments.sql")
    @Sql(scripts = {"classpath:data/attachmentrepositorytests/delete-attachments.sql"}, executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    public void testFindByTitleJpql() {
        List<Attachment> foundAttachments = attachmentRepository.findByTitleJpql("Test Attachment");
        assertThat(foundAttachments).isNotEmpty();
        assertThat(foundAttachments).hasSize(1);
        assertThat(foundAttachments.getFirst().getTitle()).isEqualTo("Test Attachment");
    }

    @Test
    @Sql("classpath:data/attachmentrepositorytests/insert-attachments.sql")
    @Sql(scripts = {"classpath:data/attachmentrepositorytests/delete-attachments.sql"}, executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    public void testFindByTitleCriteriaFetch() {
        List<Attachment> foundAttachments = attachmentRepository.findByTitleCriteriaFetch("Test Attachment");
        assertThat(foundAttachments).isNotEmpty();
        assertThat(foundAttachments).hasSize(1);
        assertThat(foundAttachments.getFirst().getTitle()).isEqualTo("Test Attachment");
        assertThat(foundAttachments.getFirst().getTask()).isNotNull();
    }

    @Test
    @Sql("classpath:data/attachmentrepositorytests/insert-attachments.sql")
    @Sql(scripts = {"classpath:data/attachmentrepositorytests/delete-attachments.sql"}, executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    public void testFindByTitleJpqlFetch() {
        List<Attachment> foundAttachments = attachmentRepository.findByTitleJpqlFetch("Test Attachment");
        assertThat(foundAttachments).isNotEmpty();
        assertThat(foundAttachments).hasSize(1);
        assertThat(foundAttachments.getFirst().getTitle()).isEqualTo("Test Attachment");
        assertThat(foundAttachments.getFirst().getTask()).isNotNull();
    }

    @Test
    @Sql("classpath:data/attachmentrepositorytests/insert-attachments.sql")
    @Sql(scripts = {"classpath:data/attachmentrepositorytests/delete-attachments.sql"}, executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    public void testFindByTitleWithEntityGraphFetch() {
        List<Attachment> foundAttachments = attachmentRepository.findByTitleWithEntityGraphFetch("Test Attachment");
        assertThat(foundAttachments).isNotEmpty();
        assertThat(foundAttachments).hasSize(1);
        assertThat(foundAttachments.getFirst().getTitle()).isEqualTo("Test Attachment");
        assertThat(foundAttachments.getFirst().getTask()).isNotNull();
    }
}