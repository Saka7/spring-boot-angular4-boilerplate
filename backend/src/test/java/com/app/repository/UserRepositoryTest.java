package com.app.repository;

import com.app.entity.User;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.Sql.ExecutionPhase;
import org.springframework.test.context.jdbc.SqlGroup;

import java.util.List;

import static com.app.util.DummyDataGenerator.getUsers;
import static org.junit.Assert.*;

@SqlGroup({
    @Sql(executionPhase = ExecutionPhase.BEFORE_TEST_METHOD, scripts = {
        "classpath:data/hsql/init-roles.sql",
        "classpath:data/hsql/init-users.sql"
    }),
    @Sql(executionPhase = ExecutionPhase.AFTER_TEST_METHOD, scripts = "classpath:data/hsql/clear.sql")
})
public class UserRepositoryTest extends BaseRepositoryTest {

    @Autowired
    private UserRepository userRepository;

    @Test(timeout = 3000)
    public void findByIdTest() {
        User user = getUsers(1, 1001).get(0);
        User fetchedUser = userRepository.findOne(user.getId());
        assertNotNull("User shouldn't be NULL", fetchedUser);
        assertEquals("Should return appropriate user", user, fetchedUser);
    }

    @Test(timeout = 5000)
    public void findByEmailTest() {
        User user = getUsers(1, 1001).get(0);
        User fetchedUser = userRepository.findByEmail(user.getEmail());
        assertNotNull("User shouldn't be NULL", fetchedUser);
        assertEquals("Should return appropriate user", user, fetchedUser);
    }

    @Test(timeout = 5000)
    public void findByNameTest() {
        User user = getUsers(1, 1001).get(0);
        User fetchedUser = userRepository.findByName(user.getName());
        assertNotNull("User shouldn't be NULL", fetchedUser);
        assertEquals("Should return appropriate user", user, fetchedUser);
    }

    @Test(timeout = 2000)
    public void existsTest() {
        assertTrue("User should exists", userRepository.exists(1001L));
        assertFalse("User shouldn't exists", userRepository.exists(Long.MAX_VALUE));
    }

    @Test(timeout = 3000)
    public void countTest() {
        assertEquals("Should fetch 3 users", 3, userRepository.count());
    }

    @Test(timeout=5000)
    public void findAllTest() {
        List<User> users = getUsers(3);
        List<User> fetchedUsers = userRepository.findAll();

        assertNotNull("Fetched users shouldn't be NULL", fetchedUsers);
        assertEquals("Should return appropriate amount of users", users.size(), fetchedUsers.size());
        fetchedUsers.forEach(user -> assertNotNull("Any of the users shouldn't be NULL", user));
    }

    @Test(timeout=5000)
    public void saveTest() {
        User user = getUsers(1).get(0);
        userRepository.save(user);
        User fetchedUser = userRepository.findByName(user.getName());
        assertNotNull("User shouldn't be NULL", fetchedUser);
        assertEquals("User should have appropriate role", user.getRole(), fetchedUser.getRole());
        assertEquals("User should have appropriate name", user.getName(), fetchedUser.getName());
    }

    @Test(timeout=5000)
    public void changeUsernameTest() {
        User user = userRepository.findOne(1001L);
        user.setName("another random name");
        User changedUser = userRepository.findOne(1001L);

        assertNotNull("Changed user shouldn't be NULL", changedUser);
        assertEquals("Should return appropriate user", user, changedUser);
        assertEquals("Should return user with appropriate name", user.getName(), changedUser.getName());
    }

    @Test(timeout=5000)
    public void changeEmailTest() {
        User user = userRepository.findOne(1001L);
        user.setEmail("another.random@email.com");
        User changedUser = userRepository.findOne(1001L);

        assertNotNull("Changed user shouldn't be NULL", changedUser);
        assertEquals("Should return appropriate user", user, changedUser);
        assertEquals("Should return user with appropriate email", user.getEmail(), changedUser.getEmail());
    }

    @Test(timeout=5000)
    public void changePasswordTest() {
        User user = userRepository.findOne(1001L);
        user.setPassword("another-password");
        User changedUser = userRepository.findOne(1001L);

        assertNotNull("Changed user shouldn't be NULL", changedUser);
        assertEquals("Should return appropriate user", user, changedUser);
        assertEquals("Should return user with appropriate password",
                user.getPassword(), changedUser.getPassword());
    }

    @Test(timeout=5000)
    public void deleteTest() {
        userRepository.delete(1001L);
        assertFalse("User with id 1001 should not exists", userRepository.exists(1001L));
    }

}