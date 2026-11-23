package org.omoro.learning_springboot_git_k8s.controller;

import org.omoro.learning_springboot_git_k8s.model.Contact;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/learning")
public class ContactController {

    @GetMapping
    public List<Contact> getContacts() {
        return List.of(new Contact(1L, "Brian", "brian@org.com"),
                new Contact(2L, "Rania", "rania@org.com"));
    }
}
