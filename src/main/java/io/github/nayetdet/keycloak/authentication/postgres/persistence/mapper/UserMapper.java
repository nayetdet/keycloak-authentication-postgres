package io.github.nayetdet.keycloak.authentication.postgres.persistence.mapper;

import io.github.nayetdet.keycloak.authentication.postgres.persistence.model.User;

import java.time.LocalDateTime;
import java.util.UUID;

public class UserMapper {

    private UserMapper() {
    }

    public static User from(UUID keycloakId, String username) {
        var now = LocalDateTime.now();
        return User
                .builder()
                .keycloakId(keycloakId)
                .username(username)
                .createdAt(now)
                .updatedAt(now)
                .build();
    }

}
