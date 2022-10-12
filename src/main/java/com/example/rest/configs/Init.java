package com.example.rest.configs;

import com.example.rest.model.Role;
import com.example.rest.model.User;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.PostConstruct;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import java.util.Collections;

@Component
@Transactional
public class Init {
    private final EntityManagerFactory entityManagerFactory;

    public Init(EntityManagerFactory entityManagerFactory) {
        this.entityManagerFactory = entityManagerFactory;
    }

    @PostConstruct
    public void postConstruct() {
        User admin = new User();
        admin.setEmail("admin@admin.com");
        admin.setName("Vasya");
        admin.setSurname("Vasin");
        admin.setAge("34");
        admin.setPassword("$2a$12$sn9KvEVkIANLssoCvEnh0.XqIxsE3BwaLt5qSltxaOj11eQoLCj8i"); //Password: user

        User user = new User();
        user.setEmail("user@user.com");
        user.setName("Petya");
        user.setSurname("Sidorov");
        user.setAge("18");

        user.setPassword("$2a$12$sn9KvEVkIANLssoCvEnh0.XqIxsE3BwaLt5qSltxaOj11eQoLCj8i"); //Password: user

        User user2 = new User();
        user2.setEmail("user2@user2.com");
        user2.setName("Pasha");
        user2.setSurname("Petrov");
        user2.setAge("55");
        user2.setPassword("$2a$12$sn9KvEVkIANLssoCvEnh0.XqIxsE3BwaLt5qSltxaOj11eQoLCj8i"); //Password: user

        Role role = new Role();
        role.setRole("ROLE_ADMIN");
        Role role2 = new Role();
        role2.setRole("ROLE_USER");

        admin.setRoles(Collections.singleton(role));
        user.setRoles(Collections.singleton(role2));
        user2.setRoles(Collections.singleton(role2));

        EntityManager entityManager = entityManagerFactory.createEntityManager();
        entityManager.getTransaction().begin();

        entityManager.persist(admin);
        entityManager.persist(user);
        entityManager.persist(user2);

        entityManager.persist(role);
        entityManager.persist(role2);
        entityManager.getTransaction().commit();
        entityManager.close();
    }
}
