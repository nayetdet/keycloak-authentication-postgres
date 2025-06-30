package br.com.edu.ifce.maracanau.carekobooks.keycloak.authentication.mapper;

import br.com.edu.ifce.maracanau.carekobooks.keycloak.authentication.persistence.model.User;
import org.keycloak.models.UserModel;

import java.time.LocalDateTime;
import java.util.UUID;

public class UserMapper {

    private UserMapper() {
    }

    public static User from(UserModel userModel) {
        var now = LocalDateTime.now();
        return User
                .builder()
                .keycloakId(UUID.fromString(userModel.getId()))
                .username(userModel.getUsername())
                .createdAt(now)
                .updatedAt(now)
                .build();
    }

}
