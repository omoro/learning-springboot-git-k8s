package org.omoro.learning_springboot_git_k8s.service;

import org.omoro.learning_springboot_git_k8s.model.Contact;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ContactService {

    public List<Contact> getContacts() {
        return List.of(new Contact(1L, "Brian", "brian@org.com"),
                new Contact(2L, "Rania", "rania@org.com"));
    }
}
