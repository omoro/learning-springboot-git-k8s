package org.omoro.learning_springboot_git_k8s.exception;

import java.time.LocalDateTime;

public record ApiError(int status, String error, String message, LocalDateTime timestamp) {}
