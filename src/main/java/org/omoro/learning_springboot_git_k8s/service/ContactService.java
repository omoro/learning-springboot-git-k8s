package org.omoro.learning_springboot_git_k8s.service;

import org.omoro.learning_springboot_git_k8s.model.Contact;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ContactService {

    private final List<Contact> contacts = new ArrayList<>();

    public ContactService() {
        contacts.add(new Contact(1L, "Brian", "brian@org.com"));
        contacts.add(new Contact(2L, "Rania", "rania@org.com"));
    }

    public List<Contact> getContacts() {
        return contacts;
    }

    public Contact getContactById(Long id) {
        return contacts.stream()
                .filter(c -> c.getId().equals(id))
                .findFirst()
                .orElse(null);
    }

    public Contact addContact(Contact contact) {
        contacts.add(contact);
        return contact;
    }

    public Contact updateContact(Long id, Contact updatedContact) {
        for (int i = 0; i < contacts.size(); i++) {
            if (contacts.get(i).getId().equals(id)) {
                Contact contact = new Contact(id, updatedContact.getName(), updatedContact.getEmailAddress());
                contacts.set(i, contact);

                return contact;
            }
        }

        return null;
    }

    public boolean deleteContact(Long id) {
        return contacts.removeIf(c -> c.getId().equals(id));
    }
}
