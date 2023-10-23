/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package main;

import connection.ClassConnection;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import model.Saved;
import model.User;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ResourceBundle;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TableView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import main.TranslationPageController;
import org.json.JSONArray;
import org.json.JSONObject;

/**
 * FXML Controller class
 *
 * @author abdelrahmankhodr
 */
public class SavedController extends AnchorPane implements Initializable {
    
    @FXML
    private TableView<Saved> tableView;
    
    @FXML
    private Button back, deleteSelected;

    private Main application;

    private ClassConnection classConnection;

    
    public void setApp(Main application) {
        this.application = application;
        
        deleteSelected.setDisable(true);
        
        fetchSaved();
    }
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        classConnection = new ClassConnection();
    } 
    
    private void fetchSaved() {
        
        try {
            tableView.getItems().clear();
            User loggedUser = application.getLoggedUser();
            System.out.println("this is name " + loggedUser.getUsername());
            System.out.println("this is id: " + loggedUser.getId());
            // Construct the API URL
            String apiUrl = "http://localhost:8080/api/getSaved?id=" + loggedUser.getId();
            URL url = new URL(apiUrl);

            // Open the connection
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            connection.setRequestProperty("Content-Type", "application/json");

            int responseCode = connection.getResponseCode();
            if (responseCode == HttpURLConnection.HTTP_OK) {
                // Read the response from the API
                BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                StringBuilder response = new StringBuilder();
                String line;
                while ((line = reader.readLine()) != null) {
                    response.append(line);
                }
                reader.close();

                // Parse the JSON response and update the table view
                JSONArray savedArray = new JSONArray(response.toString());
                ObservableList<Saved> data = tableView.getItems();
                for (int i = 0; i < savedArray.length(); i++) {
                    JSONObject savedObject = savedArray.getJSONObject(i);
                    int savedId = savedObject.getInt("savedId");
                    String savedTypeHere = savedObject.getString("savedTypeHere");
                    String savedTranslation = savedObject.getString("savedTranslation");
                    Saved saved = new Saved();
                    saved.setSavedId(savedId);
                    saved.setSavedTypeHere(savedTypeHere);
                    saved.setSavedTranslation(savedTranslation);
                    data.add(saved);
                }
            } else {
                // Handle error response codes if needed
                System.out.println("Failed to fetch saved. Response code: " + responseCode);
            }

            connection.disconnect();
        } catch (Exception e) {
            System.out.println(e);
        }
    }
    
    public void processDeleteSaved(Saved saved)throws Exception {
        //User loggedUser = application.getLoggedUser();
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
    
    public void deleteSelected(ActionEvent event) throws Exception{
        Saved saved = tableView.getSelectionModel().getSelectedItem();
        processDeleteSaved(saved);
        application.goToSaved();
    }
    
    @FXML
    protected void handleClickTableView(MouseEvent click) {
        deleteSelected.setDisable(false);
//        User user = tableView.getSelectionModel().getSelectedItem();
//        idField.setText(Integer.toString(user.getId()));
//        usernameField.setText(user.getUsername());
//        emailField.setText(user.getEmail());
    }
    
    public void processGoToTranslationPage(ActionEvent event){
        application.goToTranslationPage();
    }
    
}