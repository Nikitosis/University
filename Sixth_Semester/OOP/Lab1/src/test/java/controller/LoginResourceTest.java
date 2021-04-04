package controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import entities.request.LoginRequest;
import entities.response.TokenResponse;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.powermock.core.classloader.annotations.PowerMockIgnore;
import org.powermock.modules.junit4.PowerMockRunner;
import org.powermock.reflect.Whitebox;
import service.AuthorizationService;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringReader;
import java.io.StringWriter;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.verify;
import static org.powermock.api.mockito.PowerMockito.mock;
import static org.powermock.api.mockito.PowerMockito.verifyNew;
import static org.powermock.api.mockito.PowerMockito.when;

@RunWith(PowerMockRunner.class)
@PowerMockIgnore("jdk.internal.reflect.*")
public class LoginResourceTest {

    @Mock
    private AuthorizationService authorizationService;

    private LoginResource loginResource = new LoginResource();

    private ObjectMapper objectMapper;

    @Before
    public void init() {
        objectMapper = new ObjectMapper();
        Whitebox.setInternalState(loginResource, "authorizationService", authorizationService);
    }

    @Test
    public void post() throws ServletException, IOException {
        HttpServletRequest request = mock(HttpServletRequest.class);
        HttpServletResponse response = mock(HttpServletResponse.class);

        LoginRequest loginRequest = new LoginRequest();
        loginRequest.setUsername("usern");
        loginRequest.setPassword("pass");

        String authToken = "auth";

        String requestJson = objectMapper.writeValueAsString(loginRequest);

        StringReader stringReader = new StringReader(requestJson);
        BufferedReader bufferedReader = new BufferedReader(stringReader);

        StringWriter stringWriter = new StringWriter();
        PrintWriter writer = new PrintWriter(stringWriter);

        when(request.getReader()).thenReturn(bufferedReader);
        when(response.getWriter()).thenReturn(writer);
        when(authorizationService.authorize(loginRequest.getUsername(), loginRequest.getPassword())).thenReturn(authToken);

        loginResource.doPost(request, response);

        verify(authorizationService).authorize(loginRequest.getUsername(), loginRequest.getPassword());

        writer.flush();
        String responseStr = stringWriter.toString();

        TokenResponse tokenResponse = objectMapper.readValue(responseStr, TokenResponse.class);
        assertEquals(authToken, tokenResponse.getToken());
    }
}
