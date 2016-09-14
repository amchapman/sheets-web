package com.example;

import java.io.IOException;
import java.util.List;
import java.io.PrintWriter;
import java.lang.Integer;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.api.client.googleapis.auth.oauth2.GoogleAuthorizationCodeFlow;
import com.google.api.client.auth.oauth2.AuthorizationCodeRequestUrl;
import com.google.api.client.auth.oauth2.Credential;

import com.google.api.services.sheets.v4.Sheets;
import com.google.api.services.sheets.v4.model.ValueRange;

public class DemoServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;
    private String redirectURI="http://localhost:8080/sheets-web/callback";
    // demo spreadsheet https://docs.google.com/spreadsheets/d/1lPtkpv11Y50cxTPDXnOoOatz7Qrzo6y0K1DAwQjbveE
    private static final String SPREADSHEET_ID = "1BxiMVs0XRA5nFMdKvBdBZjgmUUqptlbs74OgvE2upms";

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
  		  GoogleAuthorizationCodeFlow flow = DemoAuth.newFlow();
        HttpSession session = req.getSession();
        String auth = (String) session.getAttribute("auth");
        // if we have an "auth" session attribute then we already have a saved credential
        if (auth != null) {
          // load the previously created credential from the GoogleAuthorizationCodeFlow's data store
          Credential credential = flow.loadCredential("demo");
          Sheets sheets = new Sheets.Builder(DemoAuth.getHttpTransport(), DemoAuth.getJsonFactory(), credential).build();
          ValueRange valueRange = sheets.spreadsheets().values().get(SPREADSHEET_ID, "Class Data!A2:E").execute();
          List<List<Object>> values = valueRange.getValues();
          req.setAttribute("values",values);
          req.getRequestDispatcher("/WEB-INF/demo.jsp").forward(req, resp);
        } else {
          // if we haven't performed the authorization flow yet, redirect to the Google approval page
    		  AuthorizationCodeRequestUrl authorizationUrl = flow.newAuthorizationUrl().setRedirectUri(redirectURI);
          resp.sendRedirect(authorizationUrl.build());
        }
    }
}
