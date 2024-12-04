package com.group.docReservations;

import com.group.docReservations.classes.User;
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
public class userRepositoryTest {
    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private UserRepository repo;
    @Test
    public void testCreateUser() {
        //privileges.addUser(user);
        User User = new User();
        User.setLogin("user");
        User.setPassword("user");
        User.setEmail("null@test.pl");

        User savedUser = repo.save(User);

        User existUser = entityManager.find(User.class, savedUser.getId());

        assertThat(User.getEmail()).isEqualTo(existUser.getEmail());

    }
    // test methods go below
}