package com.example;

import java.util.Arrays;
import java.util.List;
import java.io.IOException;
import java.io.InputStreamReader;

import com.google.api.client.googleapis.auth.oauth2.GoogleClientSecrets;
import com.google.api.client.googleapis.auth.oauth2.GoogleClientSecrets.Details;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.http.HttpTransport;
import com.google.api.client.googleapis.auth.oauth2.GoogleAuthorizationCodeFlow;
import com.google.api.client.util.store.FileDataStoreFactory;

import com.google.api.services.sheets.v4.SheetsScopes;

public class DemoAuth {

    // data store used by GoogleAuthorizationCodeFlow to save credentials ~/.credentials/sheets-web/StoredCredential
    private static final java.io.File DATA_STORE_DIR = new java.io.File(System.getProperty("user.home"), ".credentials/sheets-web");
    // define permission scopes to request
    private static final List<String> SCOPES = Arrays.asList(SheetsScopes.SPREADSHEETS_READONLY);
    // client id and secret from oauth2 credentials created at https://console.developers.google.com
    private static final String CLIENT_ID="{YOUR_CLIENT_ID}";
  	private static final String CLIENT_SECRET="{YOUR_CLIENT_SECRET}";

    public static GoogleAuthorizationCodeFlow newFlow() throws IOException {
      Details web=new Details();
      web.setClientId(CLIENT_ID);
      web.setClientSecret(CLIENT_SECRET);
      GoogleClientSecrets clientSecrets = new GoogleClientSecrets().setWeb(web);
      FileDataStoreFactory DATA_STORE_FACTORY = new FileDataStoreFactory(DATA_STORE_DIR);
      GoogleAuthorizationCodeFlow flow = new GoogleAuthorizationCodeFlow.Builder(DemoAuth.getHttpTransport(),
      DemoAuth.getJsonFactory(), clientSecrets,SCOPES).setDataStoreFactory(DATA_STORE_FACTORY).build();
      return flow;
    }

    public static HttpTransport getHttpTransport() {
      try {
        return GoogleNetHttpTransport.newTrustedTransport();
      } catch (Exception e) {
        return null;
      }
    }

    public static JsonFactory getJsonFactory() {
      return JacksonFactory.getDefaultInstance();
    }

}
