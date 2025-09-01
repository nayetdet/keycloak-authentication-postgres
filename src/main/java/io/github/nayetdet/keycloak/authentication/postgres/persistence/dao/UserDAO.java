package io.github.nayetdet.keycloak.authentication.postgres.persistence.dao;

import io.github.nayetdet.keycloak.authentication.postgres.persistence.model.User;
import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class UserDAO {

    private final EntityManager em;

    @Transactional
    public void save(User user) {
        em.persist(user);
    }

}
