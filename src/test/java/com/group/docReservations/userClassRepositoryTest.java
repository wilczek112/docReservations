package com.group.docReservations;

import com.group.docReservations.classes.userClass;
import com.group.docReservations.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.annotation.Rollback;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@AutoConfigureTestDatabase(replace = Replace.NONE)
@Rollback(false)
public class userClassRepositoryTest {
    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private UserRepository repo;
    @Test
    public void testCreateUser() {
        //privileges.addUser(user);
        userClass userClass = new userClass();
        userClass.setLogin("user");
        userClass.setPassword("user");
        userClass.setEmail("null@test.pl");

        userClass savedUserClass = repo.save(userClass);

        userClass existUserClass = entityManager.find(userClass.class, savedUserClass.getId());

        assertThat(userClass.getEmail()).isEqualTo(existUserClass.getEmail());

    }
    // test methods go below
}