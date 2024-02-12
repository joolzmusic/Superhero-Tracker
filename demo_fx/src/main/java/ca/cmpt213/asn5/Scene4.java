package ca.cmpt213.asn5;

import javafx.collections.FXCollections;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.image.Image;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.BackgroundPosition;
import javafx.scene.layout.BackgroundRepeat;
import javafx.scene.layout.BackgroundSize;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class Scene4 {

    private Scene scene4;
    private List<String> superhumanIds;
    private ComboBox<String> idComboBox;

    public Scene4(Stage primaryStage) {
        // Call the method to update superhuman display and collect IDs
        updateSuperhumanDisplay();

        // Create a button to go back to the main scene
        Button backButton = new Button("Back");
        backButton.setOnAction(e -> {
            primaryStage.setScene(new MainScene(primaryStage).getScene());
        });

        // Create a ComboBox for selecting a Superhuman ID
        idComboBox = new ComboBox<>();
        idComboBox.setPromptText("Select Superhero ID");
        idComboBox.setItems(FXCollections.observableArrayList(superhumanIds));

        // Create a button for the "Get Info" operation
        Button getInfoButton = new Button("Get Info");
        getInfoButton.setOnAction(e -> getSuperheroInfo(primaryStage));

        // Styling for the buttons
        backButton.setStyle("-fx-background-color: #66bb6a; -fx-text-fill: white;");
        getInfoButton.setStyle("-fx-background-color: #64b5f6; -fx-text-fill: white;");

        // Create an HBox for the ComboBox and center it
        HBox comboBoxBox = new HBox(idComboBox);
        comboBoxBox.setAlignment(Pos.CENTER);

        // Styling for the ComboBox
        idComboBox.setStyle("-fx-background-color: #81c784; -fx-text-fill: white;");

        // Create an HBox for the back button and left-align it
        HBox backButtonBox = new HBox(backButton);
        backButtonBox.setAlignment(Pos.BOTTOM_LEFT);

        // Create an HBox for the "Get Info" button and right-align it
        HBox getInfoButtonBox = new HBox(getInfoButton);
        getInfoButtonBox.setAlignment(Pos.BOTTOM_RIGHT);

        // Create a VBox to hold all the HBox elements
        VBox vBox = new VBox(comboBoxBox, backButtonBox, getInfoButtonBox);
        vBox.setAlignment(Pos.CENTER); // Center the VBox in the StackPane

        // Background for scene4
        Image backgroundImage = new Image(getClass().getResourceAsStream("images/background2.jpg"));
        BackgroundImage backgroundImg = new BackgroundImage(
                backgroundImage,
                BackgroundRepeat.NO_REPEAT,
                BackgroundRepeat.NO_REPEAT,
                BackgroundPosition.DEFAULT,
                new BackgroundSize(
                        1.0,
                        1.0,
                        true,
                        true,
                        false,
                        false
                )
        );
        Background background = new Background(backgroundImg);
        vBox.setBackground(background);

        scene4 = new Scene(vBox, 667, 500);
    }

    Scene getScene4() {
        return scene4;
    }

    // Method to get an array of IDs and store it as a private attribute
    private void updateSuperhumanDisplay() {
        try {
            URL url = new URL("http://localhost:8080/superhuman/all");
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");

            try (BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()))) {
                StringBuilder response = new StringBuilder();
                String line;
                while ((line = reader.readLine()) != null) {
                    response.append(line).append("\n");
                }

                // Parse JSON using Gson
                Gson gson = new Gson();
                JsonObject jsonResponse = gson.fromJson(response.toString(), JsonObject.class);

                // Check if "data" is present and not null
                if (jsonResponse.has("data") && !jsonResponse.get("data").isJsonNull()) {
                    JsonArray superhumans = jsonResponse.getAsJsonArray("data");

                    // Initialize the list to store IDs
                    superhumanIds = new ArrayList<>();

                    // Process superhumans data and collect IDs
                    for (JsonElement superhumanElement : superhumans) {
                        JsonObject superhumanObject = superhumanElement.getAsJsonObject();
                        String id = String.valueOf(superhumanObject.get("id"));
                        superhumanIds.add(id);
                    }
                } else {
                    // Handle the case when "data" is null or not present
                    System.out.println("No superheroes available for deletion. ");
                    superhumanIds = new ArrayList<>(); // Initialize an empty list or handle it accordingly
                }
            }

            System.out.println(connection.getResponseCode());
            connection.disconnect();
        } catch (IOException ioException) {
            ioException.printStackTrace();
        }
    }

    // Method to handle the "Get Info" button click
    private void getSuperheroInfo(Stage primaryStage) {
        String selectedId = idComboBox.getValue();
        if (selectedId != null) {
            try {
                primaryStage.setScene(new DetailedInfo(primaryStage, selectedId).getSuperheroInfo());

            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            System.out.println("Please select a Superhero ID to get info.");
        }
    }
}
