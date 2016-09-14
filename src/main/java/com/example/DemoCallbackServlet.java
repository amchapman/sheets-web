package com.example;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.api.client.googleapis.auth.oauth2.GoogleClientSecrets;
import com.google.api.client.auth.oauth2.TokenResponse;
import com.google.api.client.googleapis.auth.oauth2.GoogleTokenResponse;
import com.google.api.client.googleapis.auth.oauth2.GoogleAuthorizationCodeFlow;
import com.google.api.client.auth.oauth2.Credential;

public class DemoCallbackServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;
    private String redirectURI="http://localhost:8080/sheets-web/callback";

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        GoogleAuthorizationCodeFlow flow = DemoAuth.newFlow();
        // Google redirects to our callback with a code that we need to request a token
        String code = req.getParameter("code");
        TokenResponse tokenResponse = flow.newTokenRequest(code).setRedirectUri(redirectURI).execute();
        // we use the token to create a credential that is saved in the GoogleAuthorizationCodeFlow's data store
        Credential credential = flow.createAndStoreCredential(tokenResponse,"demo");
        // we're using this session attribute to tell the other servlet that we've completed the flow
        req.getSession().setAttribute("auth", "yes");
        resp.sendRedirect("http://localhost:8080/sheets-web/");
    }
}
