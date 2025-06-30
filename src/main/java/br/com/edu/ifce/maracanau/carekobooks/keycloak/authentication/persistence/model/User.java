package br.com.edu.ifce.maracanau.carekobooks.keycloak.authentication.persistence.model;

import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(
        name = "users",
        indexes = {
                @Index(name = "idx_keycloak_id", columnList = "keycloakId", unique = true),
                @Index(name = "idx_username", columnList = "username", unique = true)
        }
)
public class User implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "keycloak_id", unique = true, nullable = false)
    private UUID keycloakId;

    @Column(length = 50, unique = true, nullable = false)
    private String username;

    @Column(name = "display_name", length = 50)
    private String displayName;

    @Column(length = 1000)
    private String description;

    @Column(name = "created_at")
    protected LocalDateTime createdAt;

    @Column(name = "updated_at")
    protected LocalDateTime updatedAt;

}
