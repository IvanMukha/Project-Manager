package com.ivan.projectmanager.repository;

import com.ivan.projectmanager.config.ApplicationConfig;
import com.ivan.projectmanager.model.Attachment;
import jakarta.transaction.Transactional;
import org.junit.Before;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.junit.runner.RunWith;
import org.junit.Test;
import java.util.List;
import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;


@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = ApplicationConfig.class)
@Transactional
public class AttachmentRepositoryImplTest {
    @Autowired
    private AttachmentRepository attachmentRepository;

    @Before
    void setUp() {
    }

    @Test
    public void testGetAll() {
        List<Attachment> attachments = attachmentRepository.getAll();
        assertFalse(attachments.isEmpty());
    }

    @Test
    public void testGetById() {
        Optional<Attachment> optionalAttachment = attachmentRepository.getById(1);
        assertTrue(optionalAttachment.isPresent());
        assertEquals(1, optionalAttachment.get().getId());
    }

    @Test
    public void testUpdate() {
        Optional<Attachment> optionalAttachment = attachmentRepository.getById(1);
        assertTrue(optionalAttachment.isPresent());
        Attachment attachment = optionalAttachment.get();
        attachment.setTitle("Updated Title");
        attachment.setPath("/attachments/updated.pdf");
        attachment.setTask(null);
        Optional<Attachment> updatedAttachment = attachmentRepository.update(1, attachment);
        assertTrue(updatedAttachment.isPresent());
        assertEquals("Updated Title", updatedAttachment.get().getTitle());
        assertEquals("/attachments/updated.pdf", updatedAttachment.get().getPath());
        assertNull(updatedAttachment.get().getTask());
    }
    @Test
    public void testDeleteAttachment() {
        Attachment attachment = new Attachment();
        attachment.setTitle("Test Attachment");
        attachment.setPath("/attachments/test.pdf");
        attachmentRepository.save(attachment);
        attachmentRepository.delete(attachment.getId());
        Optional<Attachment> deletedAttachment = attachmentRepository.getById(attachment.getId());
        assertFalse( "Attachment should be deleted",deletedAttachment.isPresent());
    }

    @Test
    public void testFindByTitleCriteria() {
        Attachment attachment = new Attachment();
        attachment.setTitle("Test Attachment");
        attachment.setPath("/attachments/test.pdf");
        attachmentRepository.save(attachment);
        List<Attachment> foundAttachments = attachmentRepository.findByTitleCriteria("Test Attachment");
        assertFalse( "No attachments found",foundAttachments.isEmpty());
        assertEquals(String.valueOf(1), foundAttachments.size(), "Incorrect number of attachments found");
        assertEquals(attachment.getTitle(), foundAttachments.get(0).getTitle(), "Incorrect attachment found");
    }

    @Test
    public void testFindByTitleJpql() {
        Attachment attachment = new Attachment();
        attachment.setTitle("Test Attachment");
        attachment.setPath("/attachments/test.pdf");
        attachmentRepository.save(attachment);
        List<Attachment> foundAttachments = attachmentRepository.findByTitleJpql("Test Attachment");
        assertFalse( "No attachments found",foundAttachments.isEmpty());
        assertEquals(String.valueOf(1), foundAttachments.size(), "Incorrect number of attachments found");
        assertEquals(attachment.getTitle(), foundAttachments.get(0).getTitle(), "Incorrect attachment found");
    }

    @Test
    public void testFindByTitleCriteriaFetch() {
        Attachment attachment = new Attachment();
        attachment.setTitle("Test Attachment");
        attachment.setPath("/attachments/test.pdf");
        attachmentRepository.save(attachment);
        List<Attachment> foundAttachments = attachmentRepository.findByTitleCriteriaFetch("Test Attachment");
        assertFalse( "No attachments found",foundAttachments.isEmpty());
        assertEquals(String.valueOf(1), foundAttachments.size(), "Incorrect number of attachments found");
        assertEquals(attachment.getTitle(), foundAttachments.get(0).getTitle(), "Incorrect attachment found");
        assertNotNull(String.valueOf(foundAttachments.get(0).getTask()), "Task not lazily fetched");
    }

    @Test
    public void testFindByTitleJpqlFetch() {
        Attachment attachment = new Attachment();
        attachment.setTitle("Test Attachment");
        attachment.setPath("/attachments/test.pdf");
        attachmentRepository.save(attachment);
        List<Attachment> foundAttachments = attachmentRepository.findByTitleJpqlFetch("Test Attachment");
        assertFalse("No attachments found",foundAttachments.isEmpty());
        assertEquals(String.valueOf(1), foundAttachments.size(), "Incorrect number of attachments found");
        assertEquals(attachment.getTitle(), foundAttachments.get(0).getTitle(), "Incorrect attachment found");
        assertNotNull(String.valueOf(foundAttachments.get(0).getTask()), "Task not lazily fetched");
    }

    @Test
    public void testFindByTitleWithEntityGraphFetch() {
        Attachment attachment = new Attachment();
        attachment.setTitle("Test Attachment");
        attachment.setPath("/attachments/test.pdf");
        attachmentRepository.save(attachment);
        List<Attachment> foundAttachments = attachmentRepository.findByTitleWithEntityGraphFetch("Test Attachment");
        assertFalse("No attachments found",foundAttachments.isEmpty());
        assertEquals(String.valueOf(1), foundAttachments.size(), "Incorrect number of attachments found");
        assertEquals(attachment.getTitle(), foundAttachments.get(0).getTitle(), "Incorrect attachment found");
        assertNotNull(String.valueOf(foundAttachments.get(0).getTask()), "Task not lazily fetched");
    }
}
