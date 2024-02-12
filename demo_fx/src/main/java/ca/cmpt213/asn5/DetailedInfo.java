package ca.cmpt213.asn5;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.BackgroundPosition;
import javafx.scene.layout.BackgroundRepeat;
import javafx.scene.layout.BackgroundSize;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import com.google.gson.Gson;
import com.google.gson.JsonObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class DetailedInfo {

    private Scene getInfoScene;

    public DetailedInfo(Stage primaryStage, String selectedId) {
        BorderPane root = new BorderPane();

        // Create a button to go back to the main scene
        Button backButton = new Button("Back");
        backButton.setOnAction(e -> primaryStage.setScene(new Scene4(primaryStage).getScene4()));
        BorderPane.setMargin(backButton, new Insets(10, 10, 10, 10));
        backButton.setStyle("-fx-background-color: #66bb6a; -fx-text-fill: white;");

        // Create labels to display detailed information
        List<Label> detailLabels = getSuperheroDetails(selectedId);

        // Create a VBox to hold all the labels
        VBox labelsVBox = new VBox();
        labelsVBox.getChildren().addAll(detailLabels);
        labelsVBox.setAlignment(Pos.CENTER);
        labelsVBox.setStyle("-fx-background-color: transparent;");
        for (Label label : detailLabels) {
            label.setStyle("-fx-text-fill: white;");
}

        root.setCenter(labelsVBox);
        root.setBottom(backButton);

        // Background for the whole scene
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
        root.setBackground(background);

        getInfoScene = new Scene(root, 667, 500);
        primaryStage.setScene(getInfoScene);
    }

    // Method to get detailed information based on the selected ID
    private List<Label> getSuperheroDetails(String selectedId) {
        List<Label> detailLabels = new ArrayList<>();

        try {
            URL url = new URL("http://localhost:8080/superhuman/" + selectedId);
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
                JsonObject superhumanObject = gson.fromJson(response.toString(), JsonObject.class);

                // Check if the response is valid
                if (superhumanObject.has("data") && !superhumanObject.get("data").isJsonNull()) {
                    JsonObject data = superhumanObject.getAsJsonObject("data");

                    // Extract attributes and create labels
                    long id = data.get("id").getAsLong();
                    String name = String.valueOf(data.get("name"));
                    double weight = data.get("weight").getAsDouble();
                    double height = data.get("height").getAsDouble();
                    String pictureUrl = String.valueOf(data.get("pictureUrl"));
                    String category = String.valueOf(data.get("category"));
                    int overallAbility = data.get("overallAbility").getAsInt();

                    Label idLabel = new Label ("ID: " + id);
                    Label nameLabel = new Label("Name: " + name);
                    Label weightLabel = new Label("Weight: " + weight);
                    Label heightLabel = new Label("Height: " + height);
                    Label pictureUrlLabel = new Label("Picture URL: " + pictureUrl);
                    Label categoryLabel = new Label("Category: " + category);
                    Label overallAbilityLabel = new Label("Overall Ability: " + overallAbility);

                    // Add labels to the list
                    detailLabels.add(idLabel);
                    detailLabels.add(nameLabel);
                    detailLabels.add(weightLabel);
                    detailLabels.add(heightLabel);
                    detailLabels.add(pictureUrlLabel);
                    detailLabels.add(categoryLabel);
                    detailLabels.add(overallAbilityLabel);
                } else {
                    System.out.println("Error retrieving detailed information for Superhuman ID " + selectedId);
                }
            }

            System.out.println(connection.getResponseCode());
            connection.disconnect();
        } catch (IOException ioException) {
            ioException.printStackTrace();
        }

        return detailLabels;
    }
    Scene getSuperheroInfo() {
        return getInfoScene;
    }
}
