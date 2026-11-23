package org.omoro.learning_springboot_git_k8s.controller;

import jakarta.validation.Valid;
import org.omoro.learning_springboot_git_k8s.model.Contact;
import org.omoro.learning_springboot_git_k8s.service.ContactService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

import static org.springframework.http.ResponseEntity.badRequest;

@RestController
@RequestMapping("/api/contacts")
public class ContactController {

    private final ContactService contactService;

    public ContactController(ContactService contactService) {
        this.contactService = contactService;
    }

    @GetMapping
    public ResponseEntity<List<Contact>> getContacts() {
        List<Contact> contacts = contactService.getContacts();

        if(contacts == null) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(contacts);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Contact> getContact(@PathVariable Long id) {

        Contact contact  = contactService.getContactById(id);

        if (contact == null) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(contact);
    }

    @PostMapping
    public ResponseEntity<Contact> createContact(@Valid @RequestBody Contact contact) {
        Contact createdContact = contactService.addContact(contact);

        return ResponseEntity
                .created(URI.create("/api/contacts/" + createdContact.getId()))
                .body(createdContact);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Contact> updateContact(@PathVariable Long id, @Valid @RequestBody Contact contact) {

        if (contact.getId() != null && !contact.getId().equals(id)) {
            return ResponseEntity.badRequest().build();
        }

        Contact updatedContact = contactService.updateContact(id, contact);

        if(updatedContact == null){
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(updatedContact);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteContact(@PathVariable Long id) {
        boolean deleted = contactService.deleteContact(id);

        if(!deleted){
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.noContent().build();
    }
}
