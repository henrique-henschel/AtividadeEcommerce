package com.unicesumar.model;

import java.util.UUID;

// Classe abstrata que representa todas as entidades
public abstract class Entity {
    private final UUID uuid;  // Unico atributo

    // Construtor sem parametros (gera um valor aleatorio para "uuid")
    protected Entity() {
        this.uuid = UUID.randomUUID();
    }

    // Construtor com um valor (nao aleatorio) para "uuid" como parametro
    protected Entity(UUID uuid) {
        this.uuid = uuid;
    }

    // Getter para "uuid"
    public UUID getUuid() {
        return uuid;
    }
}
