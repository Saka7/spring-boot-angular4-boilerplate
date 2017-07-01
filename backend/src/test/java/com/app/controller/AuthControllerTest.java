package com.app.controller;

import com.app.entity.User;
import com.app.security.auth.JwtAuthenticationRequest;
import com.app.security.auth.JwtAuthenticationResponse;
import com.app.security.auth.JwtUtil;
import com.app.security.auth.JwtUser;
import com.app.service.UserService;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
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

import java.util.Collections;

import static com.app.util.DummyDataGenerator.getUsers;
import static com.app.util.JsonMapper.toJson;
import static org.junit.Assert.*;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

public class AuthControllerTest extends BaseControllerTest {

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    @Mock
    private AuthenticationManager authenticationManager;

    @Mock
    private JwtUtil jwtTokenUtil;

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
    public void signUpTest() throws Exception {
        User user = getUsers(1).get(0);
        JwtUser jwtUser = new JwtUser(0L, user.getName(), user.getEmail(), user.getPassword(),
                Collections.singletonList(new SimpleGrantedAuthority(user.getRole().getLabel())));

        String password = passwordEncoder.encode(user.getPassword());
        JwtAuthenticationRequest request = new JwtAuthenticationRequest(
                user.getName(), user.getEmail(), password);

        JwtAuthenticationResponse expectedResponse = new JwtAuthenticationResponse(TOKEN);

        when(userService.save(any(User.class))).thenReturn(user);

        when(userDetailsService.loadUserByUsername(anyString()))
                .thenReturn(jwtUser);

        when(jwtTokenUtil.generateToken(any(UserDetails.class)))
                .thenReturn(TOKEN);

        when(authenticationManager.authenticate(any(UsernamePasswordAuthenticationToken.class)))
                .thenReturn(any(Authentication.class));

        MvcResult result = mvc.perform(post(AuthController.SIGNUP_URL)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(toJson(request)))
                .andReturn();

        String content = result.getResponse().getContentAsString();
        int status = result.getResponse().getStatus();

        verify(userService, times(1)).save(any(User.class));
        verify(userDetailsService, times(1)).loadUserByUsername(anyString());
        verify(jwtTokenUtil, times(1)).generateToken(any(UserDetails.class));
        verify(authenticationManager, times(1)).authenticate(any(UsernamePasswordAuthenticationToken.class));

        assertEquals("Expected HTTP status 200", 200, status);
        assertNotNull("Token shouldn't be NULL", content);
        assertTrue("Content shouldn't be empty", content.trim().length() > 0);
        assertEquals("Should return appropriate token", expectedResponse.toString(), content);
    }

    @Test(timeout=10000)
    public void signInTest() throws Exception {
        User user = getUsers(1).get(0);

        String password = passwordEncoder.encode(user.getPassword());
        JwtUser jwtUser = new JwtUser(0L, user.getName(), user.getEmail(), password,
                Collections.singletonList(new SimpleGrantedAuthority(user.getRole().getLabel())));

        JwtAuthenticationRequest request = new JwtAuthenticationRequest(
                user.getName(), user.getEmail(), user.getPassword());

        JwtAuthenticationResponse expectedResponse = new JwtAuthenticationResponse(TOKEN);

        when(userDetailsService.loadUserByUsername(anyString())).thenReturn(jwtUser);
        when(jwtTokenUtil.generateToken(any(UserDetails.class))).thenReturn(TOKEN);
        when(authenticationManager.authenticate(any(UsernamePasswordAuthenticationToken.class)))
                .thenReturn(any(Authentication.class));

        MvcResult result = mvc.perform(post(AuthController.SIGNIN_URL)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(toJson(request)))
                .andReturn();

        String content = result.getResponse().getContentAsString();
        int status = result.getResponse().getStatus();

        verify(userDetailsService, times(1)).loadUserByUsername(anyString());
        verify(authenticationManager, times(1)).authenticate(any(UsernamePasswordAuthenticationToken.class));

        assertEquals("Expected HTTP status 200", 200, status);
        assertNotNull("Token shouldn't be NULL", content);
        assertTrue("Content shouldn't be empty", content.trim().length() > 0);
        assertEquals("Should return appropriate token", expectedResponse.toString(), content);
    }

    @Test(timeout=10000)
    public void refreshTest() throws Exception {
        JwtAuthenticationResponse expectedResponse = new JwtAuthenticationResponse(TOKEN);

        when(jwtTokenUtil.refreshToken(anyString())).thenReturn(TOKEN);

        MvcResult result = mvc.perform(post(AuthController.REFRESH_TOKEN_URL)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(toJson(TOKEN)))
                .andReturn();

        String content = result.getResponse().getContentAsString();
        int status = result.getResponse().getStatus();

        verify(jwtTokenUtil, times(1)).refreshToken(anyString());

        assertEquals("Expected HTTP status 200", 200, status);
        assertNotNull("Token shouldn't be NULL", content);
        assertTrue("Content shouldn't be empty", content.trim().length() > 0);
        assertEquals("Should return appropriate token", expectedResponse.toString(), content);
    }

}