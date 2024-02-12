package ca.cmpt213.asn5;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonElement;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;


public class Scene3 {

    private Scene scene3;
    private List<List<String>> superhumanList;

    public Scene3(Stage primaryStage) {
        StackPane root = new StackPane();

        // Create a VBox to hold all the elements
        VBox vBox = new VBox(10); // Set the spacing between elements

        // Create a button to go back to the main scene
        Button backButton = new Button("Back");
        backButton.setOnAction(e -> primaryStage.setScene(new MainScene(primaryStage).getScene()));

        // Create a ScrollPane to contain the VBox elements
        ScrollPane scrollPane = new ScrollPane();
        scrollPane.setFitToWidth(true);

        // Create an HBox to hold all the VBox elements horizontally
        HBox hbox = new HBox(10);

        // update the array
        updateSuperhumanDisplay();

        if (superhumanList.isEmpty()) {
            // Display a message to the user when the list is empty
            Label noSuperhumansLabel = new Label("No superhero found.");
            VBox messageVBox = new VBox(noSuperhumansLabel);
            hbox.getChildren().add(messageVBox);
        } else {
            // Create a VBox for each superhuman and add it to the HBox
            for (List<String> superhumanInfo : superhumanList) {
                VBox superhumanVBox = createSuperhumanVBox(superhumanInfo);
                hbox.getChildren().add(superhumanVBox);
            }
        }

        // Add the HBox to the ScrollPane
        scrollPane.setContent(hbox);
        scrollPane.setHbarPolicy(ScrollPane.ScrollBarPolicy.ALWAYS);

        // Add the ScrollPane to the VBox and set vertical alignment
        VBox.setVgrow(scrollPane, Priority.ALWAYS);
        VBox.setMargin(scrollPane, new Insets(10));
        vBox.getChildren().addAll(scrollPane);

        // Add the back button to the bottom of the VBox
        VBox.setVgrow(backButton, Priority.ALWAYS);
        VBox.setMargin(backButton, new Insets(0, 0, 10, 10));
        vBox.getChildren().add(backButton);

        // Add the VBox to the root
        root.getChildren().addAll(vBox);

         // Background for scene3
         InputStream inputStream = getClass().getResourceAsStream("images/background2.jpg");
         javafx.scene.image.Image backgroundImage = new javafx.scene.image.Image(inputStream);
         root.setBackground(new javafx.scene.layout.Background(new javafx.scene.layout.BackgroundImage(
                 backgroundImage,
                 javafx.scene.layout.BackgroundRepeat.NO_REPEAT,
                 javafx.scene.layout.BackgroundRepeat.NO_REPEAT,
                 javafx.scene.layout.BackgroundPosition.DEFAULT,
                 new javafx.scene.layout.BackgroundSize(
                         1.0,
                         1.0,
                         true,
                         true,
                         false,
                         false
                 )
         )));


        scene3 = new Scene(root, 667, 500);
    }

    // Method to create a VBox for a superhuman with all attributes
    private VBox createSuperhumanVBox(List<String> superhumanInfo) {
        VBox superhumanVBox = new VBox(5); // Set the spacing between elements

        // Add an ImageView for the picture URL
        javafx.scene.image.ImageView imageView = new javafx.scene.image.ImageView();
        StackPane.setAlignment(imageView, Pos.TOP_CENTER);  // Center the image

        // Attempt to set the image from the URL; use a default image if the URL is invalid
        try {
            String imageUrl = superhumanInfo.get(4);
        
            // Check if the URL has the "http" protocol
            if (imageUrl.trim().toLowerCase().startsWith("https://") || imageUrl.trim().toLowerCase().startsWith("http://")) {
                // Use ImageIO to read the image from the URL
                // Use Image constructor with InputStream
                URL url = new URL(imageUrl);
                try (InputStream inputStream = url.openStream()) {
                    javafx.scene.image.Image image = new javafx.scene.image.Image(inputStream, 200, 200, true, true);
                    imageView.setImage(image);
                } catch (IOException e) {
                    // Handle IOException if needed
                    e.printStackTrace();
                }
            } else {
                // Use the default image ("main2.png") as a fallback
                javafx.scene.image.Image defaultImage = new javafx.scene.image.Image(getClass().getResourceAsStream("images/main2.png"), 200, 200, true, true);
                imageView.setImage(defaultImage);
            }

        } catch (IllegalArgumentException e) {
            // Print a more informative error message
            System.err.println("Error loading image from URL: " + superhumanInfo.get(4) + ". Using default image.");
            e.printStackTrace(); // Print the stack trace to identify the cause of the exception
        } catch (Exception e) {
            // Handle other exceptions if needed
            e.printStackTrace();
        }
        // Add labels and values for each attribute
        superhumanVBox.getChildren().addAll(
                imageView,
                new javafx.scene.control.Label("ID: " + superhumanInfo.get(0)),
                new javafx.scene.control.Label("Name: " + superhumanInfo.get(1)),
                new javafx.scene.control.Label("Weight: " + superhumanInfo.get(2)),
                new javafx.scene.control.Label("Height: " + superhumanInfo.get(3)),
                new javafx.scene.control.Label("URL: " + superhumanInfo.get(4)),
                new javafx.scene.control.Label("Category: " + superhumanInfo.get(5)),
                new javafx.scene.control.Label("Overall Ability: " + superhumanInfo.get(6))
        );

        return superhumanVBox;
    }


    // Method to get an array of arrays where each array is a superhuman info
    public void updateSuperhumanDisplay() {
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

                    // Process superhumans data and create an array of arrays
                    superhumanList = new ArrayList<>();
                    for (JsonElement superhumanElement : superhumans) {
                        JsonObject superhumanObject = superhumanElement.getAsJsonObject();
                        List<String> superhuman = new ArrayList<>();
                        superhuman.add(String.valueOf(superhumanObject.get("id")));
                        superhuman.add(superhumanObject.get("name").getAsString());
                        superhuman.add(String.valueOf(superhumanObject.get("weight")));
                        superhuman.add(String.valueOf(superhumanObject.get("height")));
                        superhuman.add(String.valueOf(superhumanObject.get("pictureUrl")));
                        superhuman.add(String.valueOf(superhumanObject.get("category")));
                        superhuman.add(String.valueOf(superhumanObject.get("overallAbility")));

                        superhumanList.add(superhuman);
                    }
                } else {
                    // Handle the case when "data" is null or not present
                    System.out.println("No superheroes found or invalid data format.");
                    superhumanList = new ArrayList<>(); // Initialize an empty list or handle it accordingly
                }
            }

            System.out.println(connection.getResponseCode());
            connection.disconnect();
        } catch (IOException ioException) {
            ioException.printStackTrace();
        }
    }


    Scene getScene3() {
        return scene3;
    }
}


// import java.io.BufferedReader;
// import java.io.IOException;
// import java.io.InputStreamReader;
// import java.net.HttpURLConnection;
// import java.net.URL;

// import javafx.scene.Scene;
// import javafx.scene.control.Button;
// import javafx.scene.control.TextArea;
// import javafx.scene.layout.StackPane;
// import javafx.stage.Stage;
// import com.google.gson.Gson;
// import com.google.gson.JsonArray;
// import com.google.gson.JsonObject;

// public class Scene3 {

//     private Scene scene3;
//     private TextArea superhumansTextArea;

//     public Scene3(Stage primaryStage) {
//         StackPane root = new StackPane();

//         // Create a TextArea to display superhumans data
//         superhumansTextArea = new TextArea();
//         superhumansTextArea.setEditable(false);

//         // Create a button to go back to the main scene
//         Button backButton = new Button("Back");
//         backButton.setOnAction(e -> primaryStage.setScene(new MainScene(primaryStage).getScene()));

//         root.getChildren().addAll(superhumansTextArea, backButton);

//         scene3 = new Scene(root, 300, 200);

//     }

//     // Method to fetch and display superhumans data
//     public void displaySuperhumans() {
//         try {
//             URL url = new URL("http://localhost:8080/superhuman/all");
//             HttpURLConnection connection = (HttpURLConnection) url.openConnection();
//             connection.setRequestMethod("GET");

//             try (BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()))) {
//                 StringBuilder response = new StringBuilder();
//                 String line;
//                 while ((line = reader.readLine()) != null) {
//                     response.append(line).append("\n");
//                 }
//                 superhumansTextArea.setText(response.toString());
//             }

//             System.out.println(connection.getResponseCode());
//             connection.disconnect();
//         } catch (IOException ioException) {
//             ioException.printStackTrace();
//         }
//     }

//     Scene getScene3() {
//         return scene3;
//     }
// }

