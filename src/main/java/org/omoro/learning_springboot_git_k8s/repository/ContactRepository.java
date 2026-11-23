package org.omoro.learning_springboot_git_k8s.repository;

import org.omoro.learning_springboot_git_k8s.model.Contact;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ContactRepository extends JpaRepository<Contact, Long> {
}
