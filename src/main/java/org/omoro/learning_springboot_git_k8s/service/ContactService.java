package org.omoro.learning_springboot_git_k8s.service;

import org.omoro.learning_springboot_git_k8s.exception.ContactNotFoundException;
import org.omoro.learning_springboot_git_k8s.model.Contact;
import org.omoro.learning_springboot_git_k8s.repository.ContactRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ContactService {

    private final ContactRepository contactRepository;

    public ContactService(ContactRepository contactRepository) {
        this.contactRepository = contactRepository;
    }

    public List<Contact> getContacts() {
        return contactRepository.findAll();
    }

    public Contact getContactById(Long id) {
        return contactRepository.findById(id)
                .orElseThrow(() -> new ContactNotFoundException(id));
    }

    public Contact addContact(Contact contact) {
        return contactRepository.save(contact);
    }

    public Contact updateContact(Long id, Contact updatedContact) {
        Contact contact = getContactById(id);

        contact.setName(updatedContact.getName());
        contact.setEmailAddress(updatedContact.getEmailAddress());

        return contactRepository.save(contact);
    }

    public boolean deleteContact(Long id) {
        if (!contactRepository.existsById(id)) {
            return false;
        }

        contactRepository.deleteById(id);
        return true;
    }
}
