package com.app.controller;

import com.app.entity.User;
import com.app.security.auth.JwtAuthenticationRequest;
import com.app.security.auth.JwtAuthenticationResponse;
import com.app.security.auth.JwtTokenUtil;
import com.app.security.auth.JwtUser;
import com.app.service.UserService;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.Collections;

import static com.app.util.DummyDataGenerator.getUsers;
import static com.app.util.JsonMapper.toJson;

public class AuthControllerTest extends AbstractControllerTest {

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    @Mock
    private AuthenticationManager authenticationManager;

    @Mock
    private JwtTokenUtil jwtTokenUtil;

    @Mock
    private UserDetailsService userDetailsService;

    @Mock
    private UserService userService;

    @InjectMocks
    @Autowired
    private AuthController authController;

    private static final String TOKEN = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9" +
            ".eyJzdWIiOiJyYW5kb20tbmFtZSIsInJvbGUiOiJVU0VSIiwiY3JlYXRlZCI6MX0" +
            ".idLq2N5BJZiqkylavUVJTkGKiNlc_5xdFHISCoke3ss";

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        setUp(authController);
    }

    @Test(timeout=1000)
    public void signupTest() throws Exception {
        User user = getUsers(1).get(0);
        JwtUser jwtUser = new JwtUser(0L, user.getName(), user.getEmail(), user.getPassword(),
                Collections.singletonList(new SimpleGrantedAuthority(user.getRole().getLabel())));

        String password = passwordEncoder.encode(user.getPassword());
        JwtAuthenticationRequest request = new JwtAuthenticationRequest(
                user.getName(), user.getEmail(), password);

        JwtAuthenticationResponse expectedResponse = new JwtAuthenticationResponse(TOKEN);

        Mockito.when(userService.save(Mockito.any(User.class))).thenReturn(user);

        Mockito.when(userDetailsService.loadUserByUsername(Mockito.anyString()))
                .thenReturn(jwtUser);

        Mockito.when(jwtTokenUtil.generateToken(Mockito.any(UserDetails.class)))
                .thenReturn(TOKEN);

        Mockito.when(authenticationManager
                .authenticate(Mockito.any(UsernamePasswordAuthenticationToken.class)))
                .thenReturn(Mockito.any(Authentication.class));

        MvcResult result = mvc.perform(MockMvcRequestBuilders
                .post(AuthController.SIGNUP_URL)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(toJson(request)))
                .andReturn();

        String content = result.getResponse().getContentAsString();
        int status = result.getResponse().getStatus();

        Mockito.verify(userService, Mockito.times(1)).save(Mockito.any(User.class));
        Mockito.verify(userDetailsService, Mockito.times(1))
                .loadUserByUsername(Mockito.anyString());
        Mockito.verify(jwtTokenUtil, Mockito.times(1))
                .generateToken(Mockito.any(UserDetails.class));
        Mockito.verify(authenticationManager, Mockito.times(1))
                .authenticate(Mockito.any(UsernamePasswordAuthenticationToken.class));

        Assert.assertEquals("Expected HTTP status 200", 200, status);
        Assert.assertNotNull("Token shouldn't be NULL", content);
        Assert.assertTrue("Content shouldn't be empty", content.trim().length() > 0);
        Assert.assertEquals("Should return appropriate token", expectedResponse.toString(), content);
    }

    @Test(timeout=10000)
    public void signinTest() throws Exception {
        User user = getUsers(1).get(0);

        String password = passwordEncoder.encode(user.getPassword());
        JwtUser jwtUser = new JwtUser(0L, user.getName(), user.getEmail(), password,
                Collections.singletonList(new SimpleGrantedAuthority(user.getRole().getLabel())));

        JwtAuthenticationRequest request = new JwtAuthenticationRequest(
                user.getName(), user.getEmail(), user.getPassword());

        JwtAuthenticationResponse expectedResponse = new JwtAuthenticationResponse(TOKEN);

        Mockito.when(userDetailsService.loadUserByUsername(Mockito.anyString()))
                .thenReturn(jwtUser);

        Mockito.when(jwtTokenUtil.generateToken(Mockito.any(UserDetails.class)))
                .thenReturn(TOKEN);

        Mockito.when(authenticationManager
                .authenticate(Mockito.any(UsernamePasswordAuthenticationToken.class)))
                .thenReturn(Mockito.any(Authentication.class));

        MvcResult result = mvc.perform(MockMvcRequestBuilders
                .post(AuthController.SIGNIN_URL)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(toJson(request)))
                .andReturn();

        String content = result.getResponse().getContentAsString();
        int status = result.getResponse().getStatus();

        Mockito.verify(userDetailsService, Mockito.times(1))
                .loadUserByUsername(Mockito.anyString());
        Mockito.verify(authenticationManager, Mockito.times(1))
                .authenticate(Mockito.any(UsernamePasswordAuthenticationToken.class));

        Assert.assertEquals("Expected HTTP status 200", 200, status);
        Assert.assertNotNull("Token shouldn't be NULL", content);
        Assert.assertTrue("Content shouldn't be empty", content.trim().length() > 0);
        Assert.assertEquals("Should return appropriate token", expectedResponse.toString(), content);
    }

    @Test(timeout=10000)
    public void refreshTest() throws Exception {
        JwtAuthenticationResponse expectedResponse = new JwtAuthenticationResponse(TOKEN);

        Mockito.when(jwtTokenUtil.refreshToken(Mockito.anyString())).thenReturn(TOKEN);

        MvcResult result = mvc.perform(MockMvcRequestBuilders
                .post(AuthController.REFRESH_TOKEN_URL)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(toJson(TOKEN)))
                .andReturn();

        String content = result.getResponse().getContentAsString();
        int status = result.getResponse().getStatus();

        Mockito.verify(jwtTokenUtil, Mockito.times(1)).refreshToken(Mockito.anyString());

        Assert.assertEquals("Expected HTTP status 200", 200, status);
        Assert.assertNotNull("Token shouldn't be NULL", content);
        Assert.assertTrue("Content shouldn't be empty", content.trim().length() > 0);
        Assert.assertEquals("Should return appropriate token", expectedResponse.toString(), content);
    }

}