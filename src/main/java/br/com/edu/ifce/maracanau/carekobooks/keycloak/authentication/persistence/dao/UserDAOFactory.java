package br.com.edu.ifce.maracanau.carekobooks.keycloak.authentication.persistence.dao;

import br.com.edu.ifce.maracanau.carekobooks.keycloak.authentication.persistence.config.UserPersistenceUnitInfo;
import jakarta.persistence.EntityManagerFactory;
import org.hibernate.jpa.HibernatePersistenceProvider;

import java.util.HashMap;
import java.util.Map;

public class UserDAOFactory {

    private final EntityManagerFactory entityManagerFactory;

    public UserDAOFactory(String jdbcUrl, String username, String password) {
        Map<String, String> properties = new HashMap<>();
        properties.put("hibernate.connection.url", jdbcUrl);
        properties.put("hibernate.connection.username", username);
        properties.put("hibernate.connection.password", password);
        properties.put("hibernate.connection.driver_class", "org.postgresql.Driver");
        properties.put("hibernate.dialect", "org.hibernate.dialect.PostgreSQLDialect");
        properties.put("hibernate.hbm2ddl.auto", "none");

        entityManagerFactory = new HibernatePersistenceProvider()
                .createContainerEntityManagerFactory(new UserPersistenceUnitInfo(), properties);
    }

    public UserDAO getUserDAO() {
        return new UserDAO(entityManagerFactory.createEntityManager());
    }

    public void close() {
        if (entityManagerFactory != null && entityManagerFactory.isOpen()) {
            entityManagerFactory.close();
        }
    }

}
