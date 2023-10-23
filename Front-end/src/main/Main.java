
package main;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.fxml.JavaFXBuilderFactory;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import model.User;
import security.Authenticator;
import org.json.JSONObject;

/**
 * Main Application. This class handles navigation and user session.
 */
public class Main extends Application {

    /*The JavaFX Stage class is the top level JavaFX container. 
    The primary Stage is constructed by the platform. 
    Additional Stage objects may be constructed by the application.*/
    private Stage stage;
    /*Initiate the POJO class user that contains 
    getters and setters for the user information.*/
    public User loggedUser;
    private final double MINIMUM_WINDOW_WIDTH = 390.0;
    private final double MINIMUM_WINDOW_HEIGHT = 500.0;

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        /* Equivalent to launch(args) but more targeted to the main class*/
        Application.launch(Main.class, (java.lang.String[])null);
    }

    @Override
    public void start(Stage primaryStage) {
        try {
            //assigning the primary stage to the global stage variable.
            stage = primaryStage;
            stage.setTitle("Translation tool");
            stage.setMinWidth(MINIMUM_WINDOW_WIDTH);
            stage.setMinHeight(MINIMUM_WINDOW_HEIGHT);
            goToTranslationPage();
            primaryStage.show();
        } catch (Exception ex) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public User getLoggedUser() {
        return loggedUser;
    }
        
    public boolean userLogging(String username, String password){
        int validation = Authenticator.validate(username, password);
        if (validation != 0) {
            loggedUser = User.of(username);
            loggedUser.setId(validation);
            goToTranslationPage();
            return true;
        } else {
            return false;
        }
    }
    
    private static final String API_URL = "http://localhost:8080/api/users";
    public void userSignUp(String username, String password, String email) {
        try {
            // Construct the API URL
            URL url = new URL(API_URL);

            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("POST");
            connection.setRequestProperty("Content-Type", "application/json");
            connection.setDoOutput(true);

            // Create the JSON payload for the user
            String jsonPayload = "{\"username\":\"" + username + "\", \"password\":\"" + password + "\", \"email\":\"" + email + "\" }" ;
            System.out.println(jsonPayload);

            // Write the JSON payload to the request body
            connection.getOutputStream().write(jsonPayload.getBytes());

            int responseCode = connection.getResponseCode();
            if (responseCode == HttpURLConnection.HTTP_CREATED) {
                // User creation successful
                // Process the response if needed
                BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                String response = reader.readLine();
                System.out.println("this is the new saved user: " + response);
                reader.close();
                
                JSONObject jsonResponse = new JSONObject(response);
                
                loggedUser = User.of(username);
                loggedUser.setId(jsonResponse.getInt("id"));

                goToTranslationPage();
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
                System.out.println("User signup failed. Error response: " + errorResponse.toString());
            } else {
                // Handle other response codes as needed
                throw new Exception("User signup failed. Response code: " + responseCode);
            }

            connection.disconnect();
        } catch (Exception e) {
            System.out.println(e);
        }
    }
    
    
    public void userLogout(){
        loggedUser = null;
        goToTranslationPage();
    }
    
    private void gotoProfile() {
        try {
            ProfileController profile = (ProfileController) replaceSceneContent("profile.fxml");
            profile.setApp(this);
        } catch (Exception ex) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void gotoUserTable() {
        try {
            FXMLTableUsersController users = (FXMLTableUsersController) replaceSceneContent("FXMLTableUsers.fxml");
            users.setApp(this);
        } catch (Exception ex) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void goToHistory() {
        try {
            HistoryController history = (HistoryController) replaceSceneContent("history.fxml");
            history.setApp(this);
        } catch (Exception ex) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void goToSaved() {
        try {
            SavedController saved = (SavedController) replaceSceneContent("saved.fxml");
            saved.setApp(this);
        } catch (Exception ex) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
   

    public void goToTranslationPage() {
        try {
            TranslationPageController translationPage = (TranslationPageController) replaceSceneContent("translationPage.fxml");
            translationPage.setApp(this);
        } catch (Exception ex) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    public void gotoLogin() {
        try {
            LoginController login = (LoginController) replaceSceneContent("login.fxml");
            login.setApp(this);
        } catch (Exception ex) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void goToSignUp(){
        try {
            SignUpController signUp = (SignUpController) replaceSceneContent("signUp.fxml");
            signUp.setApp(this);
        } catch (Exception ex) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private Initializable replaceSceneContent(String fxml) throws Exception {
        FXMLLoader loader = new FXMLLoader();
        InputStream in = Main.class.getResourceAsStream(fxml);
//        System.out.println("before: ");
//        System.out.println(in);
        loader.setBuilderFactory(new JavaFXBuilderFactory());
        loader.setLocation(Main.class.getResource(fxml));
        AnchorPane page;
        try {
            page = (AnchorPane) loader.load(in);
        } finally {
//            System.out.println("after: ");
//            System.out.println(in);
            in.close();
        } 
        Scene scene = new Scene(page, 600, 600);
        stage.setScene(scene);
        stage.sizeToScene();
        return (Initializable) loader.getController();
    }
}
