/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package main;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import model.User;
import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.Saved;
import org.json.JSONObject;

/**
 * FXML Controller class
 *
 * @author abdelrahmankhodr
 */
public class TranslationPageController extends AnchorPane implements Initializable {

    /**
     * Initializes the controller class.
     */
    
    @FXML
    private Label user;
    
    @FXML
    private Button login;
    
    @FXML
    private Button signUp;
    
    @FXML
    private Hyperlink logout, users;
    
    @FXML
    private TextArea translation;
    
    @FXML
    private TextArea typeHere;
    
    @FXML
    private ComboBox box, box2;
    
    @FXML
    private Button translate, history, saved;
    
    @FXML
    private CheckBox save;
    
    
//    @FXML
//    void Select(ActionEvent event) {
//        
//        String s = box.getSelectionModel().getSelectedItem().toString();
//        
//    }
    
    private Main application;
    
    public void setApp(Main application){
        
        this.application = application;
        
        User loggedUser = application.getLoggedUser();
//        System.out.println(loggedUser.getId());
        
        save.setDisable(true);
        
        if(loggedUser != null) {
            
            user.setText("Welcome " + loggedUser.getUsername());
            login.setVisible(false);
            signUp.setVisible(false);
            
        }
        else{
            user.setVisible(false);
            logout.setVisible(false);
            history.setDisable(true);
            saved.setDisable(true);
        }
        
        
        translation.setEditable(false);
        translation.setMouseTransparent(true);
        translation.setFocusTraversable(false);
        translation.setWrapText(true);
        typeHere.setWrapText(true);
        
        
    }
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
        ObservableList<String> list = FXCollections.observableArrayList("EN", "IT", "FR", "ES", "JA", "KO");
        box.setItems(list);
        box2.setItems(list);
    }  
    
    public void processLogin() {
        
        application.gotoLogin();
        
    }
    
    public void processGoToSignUp(ActionEvent event) {
        
        application.goToSignUp();
        
    }
    
    public void processGoToUsers(ActionEvent event) {
        
        application.gotoUserTable();
        
    }
    
    public void processGoToHistory(ActionEvent event) {
 
        application.goToHistory();
        
    }
    
    public void processGoToSaved(ActionEvent event) {
 
        application.goToSaved();
        
    }
    
    public void processDeleteSaved(Saved saved)throws Exception {
        User loggedUser = application.getLoggedUser();
        try {
            URL url = new URL("http://localhost:8080/api/deleteSave");

            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("DELETE");
            connection.setRequestProperty("Content-Type", "application/json");
            connection.setDoOutput(true);

            // Create the JSON payload for the delete operation
//            JSONObject savedJson = new JSONObject();
//            savedJson.put("savedId", translationId);

            System.out.println("this savedId: " + saved.getSavedId());
            JSONObject jsonPayload = new JSONObject();
            jsonPayload.put("savedId", saved.getSavedId());
            jsonPayload.put("savedTranslation", saved.getSavedTranslation());
            jsonPayload.put("savedTypeHere", saved.getSavedTypeHere());

            System.out.println(jsonPayload);
            // Write the JSON payload to the request body
            connection.getOutputStream().write(jsonPayload.toString().getBytes());

            int responseCode = connection.getResponseCode();
            if (responseCode == HttpURLConnection.HTTP_OK) {
                // Save entry deleted successfully
                System.out.println("Save entry deleted.");
            } else if (responseCode == HttpURLConnection.HTTP_BAD_REQUEST) {
                // Bad request, handle the error response
                BufferedReader errorReader = new BufferedReader(new InputStreamReader(connection.getErrorStream()));
                StringBuilder errorResponse = new StringBuilder();
                String line;
                while ((line = errorReader.readLine()) != null) {
                    errorResponse.append(line);
                }
                errorReader.close();

                // Process the error response and display appropriate message
                System.out.println("Failed to delete save entry. Error response: " + errorResponse.toString());
            } else {
                // Handle other response codes as needed
                throw new Exception("Failed to delete save entry. Response code: " + responseCode);
            }

            connection.disconnect();
        } catch (Exception e) {
            System.out.println(e);
        }
    }
    Saved savedTrans = new Saved();
    public void processSave(ActionEvent event)throws Exception {
            
        if(!save.isSelected()){
            processDeleteSaved(savedTrans);
        }
        else{
            User loggedUser = application.getLoggedUser();
            try {

                URL url = new URL("http://localhost:8080/api/save");

                HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                connection.setRequestMethod("POST");
                connection.setRequestProperty("Content-Type", "application/json");
                connection.setDoOutput(true);

                // Create the JSON payload for the save entry
                JSONObject userJson = new JSONObject();
                userJson.put("id", loggedUser.getId());
                userJson.put("username", loggedUser.getUsername());
                userJson.put("email", loggedUser.getEmail());

                JSONObject jsonPayload = new JSONObject();
                jsonPayload.put("id", userJson);
                jsonPayload.put("savedTypeHere", typeHere.getText());
                jsonPayload.put("savedTranslation", translation.getText());

                System.out.println(jsonPayload);
                // Write the JSON payload to the request body
                connection.getOutputStream().write(jsonPayload.toString().getBytes());

                int responseCode = connection.getResponseCode();
                if (responseCode == HttpURLConnection.HTTP_CREATED) {
                    // History entry created successfully
                    BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                    String response = reader.readLine();
                    System.out.println("this is the new saved translation: " + response);
                    reader.close();

                    JSONObject jsonResponse = new JSONObject(response);

                    
                    savedTrans.setSavedId(jsonResponse.getInt("savedId"));
                    savedTrans.setSavedTranslation(jsonResponse.getString("savedTranslation"));
                    savedTrans.setSavedTranslation(jsonResponse.getString("savedTypeHere"));


                    System.out.println("Save entry created.");
                } else if (responseCode == HttpURLConnection.HTTP_BAD_REQUEST) {
                    // Bad request, handle the error response
                    BufferedReader errorReader = new BufferedReader(new InputStreamReader(connection.getErrorStream()));
                    StringBuilder errorResponse = new StringBuilder();
                    String line;
                    while ((line = errorReader.readLine()) != null) {
                        errorResponse.append(line);
                    }
                    errorReader.close();

                    // Process the error response and display appropriate message
                    System.out.println("Failed to create save entry. Error response: " + errorResponse.toString());
                } else {
                    // Handle other response codes as needed
                    throw new Exception("Failed to create save entry. Response code: " + responseCode);
                }

                connection.disconnect();
            }catch (Exception e) {
                System.out.println(e);
            }
        }
    }
        
    
    
    private static String translate(String typeHereText, String sourceLanguage, String targetLanguage) throws IOException {
        
        String encodedText = URLEncoder.encode(typeHereText, StandardCharsets.UTF_8.toString());
        String urlParameters  = "text=" + encodedText
                + "&source_lang=" + sourceLanguage
                + "&target_lang=" + targetLanguage;
        byte[] postData = urlParameters.getBytes( StandardCharsets.UTF_8 );
        int postDataLength = postData.length;
        String request = "https://api-free.deepl.com/v2/translate";
        URL url = new URL( request );
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setDoOutput(true);
        conn.setInstanceFollowRedirects(false);
        conn.setRequestMethod("POST");
        conn.setRequestProperty("Authorization", "DeepL-Auth-Key 264a273b-8152-3d50-922f-4b5f8f868e2d:fx");
        conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
        conn.setRequestProperty("charset", "utf-8");
        conn.setRequestProperty("Content-Length", Integer.toString(postDataLength ));
        conn.setUseCaches(false);
        try(DataOutputStream wr = new DataOutputStream(conn.getOutputStream())) {
            wr.write(postData);
        }
        BufferedReader in = new BufferedReader(
                new InputStreamReader(conn.getInputStream()));
        String inputLine;
        StringBuffer content = new StringBuffer();
        while ((inputLine = in.readLine()) != null) {
            content.append(inputLine);
        }
        in.close();

        String data = content.toString();
        String textKey = "\"text\":\"";
        int textIdx = data.indexOf(textKey);
        String translation = data.substring(textIdx + textKey.length(), data.length() - 4);
        return translation;
    }
    
    public void processTranslate(ActionEvent event) throws Exception{
 
        save.setSelected(false);
        String translationText = "";
        
        User loggedUser = application.getLoggedUser();
        
        
        if("".equals(typeHere.getText())){
            System.out.println("No text to translate");
        }
        
        else if(box.getValue() == null || box2.getValue() == null){
            System.out.println("Choose language.");
        }
        else {
            
            translationText = translate(typeHere.getText(), box.getValue().toString(), box2.getValue().toString());
            translation.setText(translationText);
            
            if(loggedUser != null){
                save.setDisable(false);
                try {
            
                    URL url = new URL("http://localhost:8080/api/history");

                    HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                    connection.setRequestMethod("POST");
                    connection.setRequestProperty("Content-Type", "application/json");
                    connection.setDoOutput(true);

                    // Create the JSON payload for the history entry
                    JSONObject userJson = new JSONObject();
                    userJson.put("id", loggedUser.getId());
                    userJson.put("username", loggedUser.getUsername());
                    userJson.put("email", loggedUser.getEmail());
                
                    JSONObject jsonPayload = new JSONObject();
                    jsonPayload.put("id", userJson);
                    jsonPayload.put("historyTypeHere", typeHere.getText());
                    jsonPayload.put("historyTranslation", translation.getText());

                    System.out.println(jsonPayload);
                    // Write the JSON payload to the request body
                    connection.getOutputStream().write(jsonPayload.toString().getBytes());

                    int responseCode = connection.getResponseCode();
                    if (responseCode == HttpURLConnection.HTTP_OK) {
                        // History entry created successfully
                        System.out.println("History entry created.");
                    } else if (responseCode == HttpURLConnection.HTTP_BAD_REQUEST) {
                        // Bad request, handle the error response
                        BufferedReader errorReader = new BufferedReader(new InputStreamReader(connection.getErrorStream()));
                        StringBuilder errorResponse = new StringBuilder();
                        String line;
                        while ((line = errorReader.readLine()) != null) {
                            errorResponse.append(line);
                        }
                        errorReader.close();

                        // Process the error response and display appropriate message
                        System.out.println("Failed to create history entry. Error response: " + errorResponse.toString());
                    } else {
                        // Handle other response codes as needed
                        throw new Exception("Failed to create history entry. Response code: " + responseCode);
                    }

                    connection.disconnect();
                } catch (Exception e) {
                    System.out.println(e);
                }
            }
            
            
            
        }
        
        
    }
    
    public void processLogout(ActionEvent event) {
        if (application == null) {
            // We are running in isolated FXML, possibly in Scene Builder.
            // NO-OP.
            return;
        }

        application.userLogout();
    }
}
