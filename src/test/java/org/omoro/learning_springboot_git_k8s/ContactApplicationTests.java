package org.omoro.learning_springboot_git_k8s;

import org.junit.jupiter.api.Test;
import org.omoro.learning_springboot_git_k8s.exception.ContactNotFoundException;
import org.omoro.learning_springboot_git_k8s.model.Contact;
import org.omoro.learning_springboot_git_k8s.repository.ContactRepository;
import org.omoro.learning_springboot_git_k8s.service.ContactService;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@SpringBootTest
class ContactApplicationTests {

    @Test
    void shouldReturnAllContacts() {

        // Arrange: Create a mocked repository
        ContactRepository repository = mock(ContactRepository.class);

        Contact contact = new Contact(null, "First Contact", "fc@a.org");

        when(repository.findAll()).thenReturn(List.of(contact));

        // Create the service using the mocked repository
        ContactService service = new ContactService(repository);

        // Act: Call the method we want to test
        List<Contact> result = service.getContacts();

        // Assert: verify the result
        assertEquals(1, result.size());
        assertEquals("First Contact", result.getFirst().getName());
    }

    @Test
    void shouldReturnContactById() {

        // Arrange
        ContactRepository repository = mock(ContactRepository.class);

        Contact contact = new Contact(1L, "First Contact", "fc@a.org");

        when(repository.findById(1L))
                .thenReturn(Optional.of(contact));

        ContactService service = new ContactService(repository);

        // Act
        Contact result = service.getContactById(1L);

        // Assert
        assertEquals(1L, result.getId());
        assertEquals("First Contact", result.getName());

        // Verify
        verify(repository).findById(1L);
    }

    @Test
    void shouldThrowExceptionWhenContactNotFound() {

        // Arrange
        ContactRepository repository = mock(ContactRepository.class);

        when(repository.findById(99L))
                .thenReturn(Optional.empty());

        ContactService service = new ContactService(repository);

        // Act & Assert
        assertThrows(
                ContactNotFoundException.class,
                () -> service.getContactById(99L)
        );
    }

    @Test
    void shouldAddContact() {

        // Arrange
        ContactRepository repository = mock(ContactRepository.class);

        Contact contact = new Contact(null, "Better", "bet@a.org");
        Contact savedContact = new Contact(1L, "Better", "bet@a.org");

        when(repository.save(contact)).thenReturn(savedContact);

        ContactService service = new ContactService(repository);

        // Act
        Contact result = service.addContact(contact);

        // Assert
        assertEquals(1L, result.getId());
        assertEquals("Better", result.getName());

        verify(repository).save(contact);
    }

    @Test
    void shouldUpdateContact() {

        // Arrange
        ContactRepository repository = mock(ContactRepository.class);

        Contact existingContact = new Contact(1L, "Old", "a@org.com");
        Contact updatedContact = new Contact(null, "New", "a@org.com");

        when(repository.findById(1L))
                .thenReturn(Optional.of(existingContact));

        when(repository.save(existingContact))
                .thenReturn(existingContact);

        ContactService service = new ContactService(repository);

        // Act
        Contact result = service.updateContact(1L, updatedContact);

        // Assert
        assertEquals("New", result.getName());
        assertEquals("a@org.com", result.getEmailAddress());

        verify(repository).findById(1L);
        verify(repository).save(existingContact);
    }

    @Test
    void shouldDeleteContact() {

        // Arrange
        ContactRepository repository = mock(ContactRepository.class);

        when(repository.existsById(1L)).thenReturn(true);

        ContactService service = new ContactService(repository);

        // Act
        boolean result = service.deleteContact(1L);

        // Assert
        assertTrue(result);
        verify(repository).existsById(1L);
        verify(repository).deleteById(1L);
    }

    @Test
    void shouldNotDeleteNonExistentContact() {

        // Arrange
        ContactRepository repository = mock(ContactRepository.class);

        when(repository.existsById(99L)).thenReturn(false);

        ContactService service = new ContactService(repository);

        // Act
        boolean result = service.deleteContact(99L);

        // Assert
        assertFalse(result);
        verify(repository).existsById(99L);
        verify(repository, never())
				.deleteById(99L);
    }
}
