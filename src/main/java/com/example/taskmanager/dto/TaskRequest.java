package com.example.taskmanager.dto;

import jakarta.validation.constraints.NotBlank;

public record TaskRequest(
        @NotBlank(message = "Le titre est obligatoire")
        String title,
        boolean completed
) {}