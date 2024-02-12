package ca.cmpt213.asn5;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.BackgroundPosition;
import javafx.scene.layout.BackgroundRepeat;
import javafx.scene.layout.BackgroundSize;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class MainScene {

    private Scene scene;
    private Scene1 scene1;
    private Scene2 scene2;
    private Scene3 scene3;
    private Scene4 scene4;

    public MainScene(Stage primaryStage) {
        // vbox for buttons
        VBox options = new VBox(10);

        // for left-aligning the buttons
        BorderPane border = new BorderPane();

        // Main title
        Label title = new Label ("Welcome to the Superhero App");


        // Set style properties for the label
        title.setStyle("-fx-font-size: 24px; -fx-text-fill: white; -fx-font-family: 'Times New Roman'; -fx-font-weight: bold");

         // Set top padding
        title.setPadding(new Insets(10, 0, 0, 0));

        BorderPane.setAlignment(title, Pos.TOP_CENTER);

        // set top to the label
        border.setTop(title);

        // Create buttons to navigate to different scenes
        scene1 = new Scene1(primaryStage);
        Button scene1Button = new Button("Add New Superhero");
        scene1Button.setOnAction(e -> primaryStage.setScene(scene1.getScene1()));

        scene2 = new Scene2(primaryStage);
        Button scene2Button = new Button("Delete Superhero");
        scene2Button.setOnAction(e -> primaryStage.setScene(scene2.getScene2()));

        scene3 = new Scene3(primaryStage);
        Button scene3Button = new Button("Display Superheroes");
        scene3Button.setOnAction(e -> {
            primaryStage.setScene(scene3.getScene3());
        });

        scene4 = new Scene4(primaryStage);
        Button scene4Button = new Button("Get Superhero Info");
        scene4Button.setOnAction(e -> primaryStage.setScene(scene4.getScene4()));

        // add all buttons to vbox, options
        options.getChildren().addAll(scene1Button, scene2Button, scene3Button, scene4Button);
        // set padding
        options.setPadding(new Insets(80, 10, 10, 20));
       
        // set vbox to the left side of border
        border.setLeft(options);

        Image backgroundImage = new Image(getClass().getResourceAsStream("images/background.jpg"));
        BackgroundImage backgroundImg = new BackgroundImage(
            backgroundImage,
            BackgroundRepeat.NO_REPEAT,
            BackgroundRepeat.NO_REPEAT,
            BackgroundPosition.DEFAULT,
            new BackgroundSize(BackgroundSize.AUTO, BackgroundSize.AUTO, false, false, true, false)
        );
        Background background = new Background(backgroundImg);
        border.setBackground(background);

         // Main hero
        Image mainHero = new Image(getClass().getResourceAsStream("images/main2.png"));

        // set to to border's right side
        ImageView iv1 = new ImageView();
        iv1.setImage(mainHero);
        iv1.setFitWidth(450);
        iv1.setFitHeight(350);
        iv1.setPreserveRatio(true);
        iv1.setSmooth(true);
        iv1.setCache(true);
       

        // Center the image vertically and add some margin at the top
        VBox imageContainer = new VBox();
        imageContainer.getChildren().add(iv1);
        VBox.setMargin(iv1, new Insets(50, 70, 0, 0));
        imageContainer.setAlignment(Pos.CENTER);

        border.setRight(imageContainer);

        // create the scene
        scene = new Scene(border, 667, 500);
    }

    public Scene getScene() {
        return scene;
    }
}
