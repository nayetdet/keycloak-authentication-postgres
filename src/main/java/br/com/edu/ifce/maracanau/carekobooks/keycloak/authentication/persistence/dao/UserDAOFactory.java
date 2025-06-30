package br.com.edu.ifce.maracanau.carekobooks.keycloak.authentication.persistence.dao;

import br.com.edu.ifce.maracanau.carekobooks.keycloak.authentication.persistence.config.UserPersistenceUnitInfo;
import com.zaxxer.hikari.HikariDataSource;
import jakarta.persistence.EntityManagerFactory;
import org.hibernate.jpa.HibernatePersistenceProvider;

import java.util.HashMap;

public class UserDAOFactory {

    private final HikariDataSource dataSource;
    private final EntityManagerFactory entityManagerFactory;

    public UserDAOFactory(String jdbcUrl, String username, String password) {
        dataSource = new HikariDataSource();
        dataSource.setJdbcUrl(jdbcUrl);
        dataSource.setUsername(username);
        dataSource.setPassword(password);
        entityManagerFactory = new HibernatePersistenceProvider()
                .createContainerEntityManagerFactory(new UserPersistenceUnitInfo(dataSource), new HashMap<String, Object>());
    }

    public UserDAO getUserDAO() {
        return new UserDAO(entityManagerFactory.createEntityManager());
    }

    public void close() {
        if (entityManagerFactory != null && entityManagerFactory.isOpen()) {
            entityManagerFactory.close();
        }

        if (dataSource != null && !dataSource.isClosed()) {
            dataSource.close();
        }
    }

}
