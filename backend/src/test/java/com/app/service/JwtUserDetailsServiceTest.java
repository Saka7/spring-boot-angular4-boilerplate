package com.app.service;

import com.app.BaseTest;
import com.app.entity.User;
import com.app.repository.UserRepository;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import static com.app.util.DummyDataGenerator.getUsers;

public class JwtUserDetailsServiceTest extends BaseTest {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    @Autowired
    private UserDetailsService jwtUserDetailsService;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test(timeout = 3000)
    public void loadUserByUsernameTest() {
        User user = getUsers(1).get(0);
        Mockito.when(userRepository.findByName(Mockito.anyString())).thenReturn(user);
        UserDetails fetchedUserDetails = jwtUserDetailsService.loadUserByUsername("random name");

        Mockito.verify(userRepository, Mockito.times(1)).findByName(Mockito.anyString());
        Assert.assertNotNull("Fetched user details shouldn't be NULL", fetchedUserDetails);
        Assert.assertEquals("Should return appropriate username",
                user.getName(), fetchedUserDetails.getUsername());
        Assert.assertEquals("Should return appropriate password",
                user.getPassword(), fetchedUserDetails.getPassword());
    }

    @Test(timeout = 3000, expected = UsernameNotFoundException.class)
    public void loadUserWhichNotExistsTest() {
        Mockito.when(userRepository.findByName(Mockito.anyString()))
                .thenThrow(UsernameNotFoundException.class);

        jwtUserDetailsService.loadUserByUsername("random name");

        Mockito.verify(userRepository, Mockito.times(1)).findByName(Mockito.anyString());
        Mockito.verifyNoMoreInteractions();
    }

}
