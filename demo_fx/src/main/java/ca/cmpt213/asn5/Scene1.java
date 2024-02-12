package ca.cmpt213.asn5;

import java.io.IOException;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.BackgroundPosition;
import javafx.scene.layout.BackgroundRepeat;
import javafx.scene.layout.BackgroundSize;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;


public class Scene1 {

    private Scene scene1;

    public Scene1(Stage primaryStage) {

        BorderPane border = new BorderPane();

        // Background for scene1
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
        border.setBackground(background);
        

        // other labels
        Label nameLabel = new Label("Name:  ");
        Label heightLabel = new Label("Height:  ");
        Label weightLabel = new Label("Weight:  ");
        Label categoryLabel = new Label("Category:  ");
        Label overallAbilityLabel = new Label("Overall Ability:  ");
        Label picLinkLabel = new Label("Picture URL:  ");
        Label outputLabel = new Label();


        // Set text fill to white for all labels
        nameLabel.setTextFill(javafx.scene.paint.Color.WHITE);
        heightLabel.setTextFill(javafx.scene.paint.Color.WHITE);
        weightLabel.setTextFill(javafx.scene.paint.Color.WHITE);
        categoryLabel.setTextFill(javafx.scene.paint.Color.WHITE);
        overallAbilityLabel.setTextFill(javafx.scene.paint.Color.WHITE);
        picLinkLabel.setTextFill(javafx.scene.paint.Color.WHITE);
        outputLabel.setTextFill(javafx.scene.paint.Color.WHITE);

        // Textfields
        TextField nameField = new TextField();
        TextField heightField = new TextField();
        TextField weightField = new TextField();
        TextField categoryField = new TextField();
        TextField overallAbilityField = new TextField();
        TextField picLinkField = new TextField();

        //layout
        GridPane grid = new GridPane();
        grid.add(nameLabel, 0, 0);
        grid.add(nameField, 1, 0);
        grid.add(heightLabel, 0, 1);
        grid.add(heightField, 1, 1);

        grid.add(weightLabel, 0, 2);
        grid.add(weightField, 1, 2);
        grid.add(categoryLabel, 0, 3);
        grid.add(categoryField, 1, 3);
        grid.add(overallAbilityLabel, 0, 4);
        grid.add(overallAbilityField, 1, 4);
        grid.add(picLinkLabel, 0, 5);
        grid.add(picLinkField, 1, 5);

        // give some space between grids
        grid.setVgap(10);

        // give vbox some space and top padding
        VBox leftV = new VBox(20);
        leftV.setPadding(new Insets(20, 20, 20, 20));
        //add grid to vbox
        leftV.getChildren().addAll(grid);


        // middle section
        VBox centerContainer = new VBox(10);
        centerContainer.setAlignment(Pos.TOP_CENTER);

        // Title label
        Label scene1Title = new Label("Add A New Superhero");
        scene1Title.setStyle("-fx-font-size: 24px; -fx-text-fill: white; -fx-font-family: 'Times New Roman'; -fx-font-weight: bold");
        scene1Title.setPadding(new Insets(10, 0, 0, 0));

        //image 
        Image mask = new Image(getClass().getResourceAsStream("images/mask.png"));
        ImageView iv1 = new ImageView();
        iv1.setImage(mask);
        iv1.setFitWidth(250);
        iv1.setFitHeight(200);
        iv1.setPreserveRatio(true);
        iv1.setSmooth(true);
        iv1.setCache(true);

        centerContainer.getChildren().addAll(scene1Title, iv1);


        // RIGHT side: buttons
        VBox rightContainer = new VBox(10);
        rightContainer.setPadding(new Insets(20, 20, 20, 20));

        Button backButton = new Button("Back");
        backButton.setOnAction(e -> primaryStage.setScene(new MainScene(primaryStage).getScene()));
        Button addButton = new Button("Add");

        // Message label for feedback
        Label resultLabel = new Label();
        resultLabel.setTextFill(javafx.scene.paint.Color.WHITE);

        // button actions
        addButton.setOnAction(e -> {
            try {
                URL url = new URL("http://localhost:8080/superhuman");
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                connection.setRequestMethod("POST");
                connection.setDoOutput(true);
                connection.setRequestProperty("Content-Type", "application/json");
                
                // Fetch values from text fields
                String name = nameField.getText();
                double weight = Double.parseDouble(weightField.getText());
                double height = Double.parseDouble(heightField.getText());
                String pictureUrl = picLinkField.getText();
                String category = categoryField.getText();
                int overallAbility = Integer.parseInt(overallAbilityField.getText());

                // Construct the JSON string
                String jsonString = "{\"name\":\"" + name + "\",\"weight\":" + weight + ",\"height\":" + height +
                        ",\"pictureUrl\":\"" + pictureUrl + "\",\"category\":\"" + category +
                        "\",\"overallAbility\":" + overallAbility + "}";

                OutputStreamWriter writer = new OutputStreamWriter(connection.getOutputStream());
                writer.write(jsonString);

                writer.flush();
                writer.close();

                // connection.connect();

                int responseCode = connection.getResponseCode();

                // Check if addition was successful
                if (responseCode == HttpURLConnection.HTTP_CREATED) {
                    resultLabel.setText("Superhero added successfully!");
                } else {
                    resultLabel.setText("Failed to add superhero. Response code: " + responseCode);
                }

                System.out.println(connection.getResponseCode());
                connection.disconnect();
            } catch(IOException ioException) {
                ioException.printStackTrace();
            }
        });


        rightContainer.getChildren().addAll(addButton, backButton, resultLabel);

        // putting left, top, and right to border
        border.setLeft(leftV);
        border.setTop(centerContainer);
        BorderPane.setAlignment(centerContainer, Pos.CENTER);
        border.setRight(rightContainer);

        scene1 = new Scene(border, 667, 500);
        primaryStage.setScene(scene1);
        primaryStage.show();
    }

    Scene getScene1() {
        return scene1;
    }
}
