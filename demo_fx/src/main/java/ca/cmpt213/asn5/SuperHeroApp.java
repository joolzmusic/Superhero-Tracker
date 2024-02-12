package ca.cmpt213.asn5;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;


public class SuperHeroApp extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Superhero App");

        // Create and set the main scene
        Scene mainScene = new MainScene(primaryStage).getScene();
        primaryStage.setScene(mainScene);

        primaryStage.show();
    }
}















// BOBBY's STUFF
// import javafx.application.Application;
// import javafx.scene.Scene;
// import javafx.scene.control.Button;
// import javafx.scene.control.Label;
// import javafx.scene.control.TextField;
// import javafx.scene.layout.GridPane;
// import javafx.scene.layout.VBox;
// import javafx.stage.Stage;

// import java.io.BufferedReader;
// import java.io.IOException;
// import java.io.InputStreamReader;
// import java.io.OutputStreamWriter;
// import java.net.HttpURLConnection;
// import java.net.URL;


// /**
//  * JavaFX App
//  */
// public class App extends Application {


//     public static void main(String[] args) {
//         launch();
//     }

//     @Override
//     public void start(Stage primaryStage) throws IOException {
//         Label nameLabel = new Label("Name: ");
//         Label ageLabel = new Label("Age: ");
//         Label outputLabel = new Label();

//         //textfields
//         TextField nameField = new TextField();
//         TextField ageField = new TextField();

//          //buttons
//         Button addButton = new Button("Add");
//         Button getButton = new Button("Get");

//         getButton.setOnAction(e -> {
//             try {
//                 URL url = new URL("http://localhost:8080/superhuman/all");
//                 HttpURLConnection connection = (HttpURLConnection) url.openConnection();
//                 connection.setRequestMethod("GET");
//                 connection.getInputStream();
//                 BufferedReader reader = new BufferedReader(
//                     new InputStreamReader(connection.getInputStream()));
//                 String line = reader.readLine();
//                 System.out.println(line);
//                 System.out.println(connection.getResponseCode());
//                 connection.disconnect();
//             } catch(IOException ioException) {
//                 ioException.printStackTrace();
//             }
//         });

//         addButton.setOnAction(e -> {
//             try {
//                 URL url = new URL("http://localhost:8080/superhuman");
//                 HttpURLConnection connection = (HttpURLConnection) url.openConnection();
//                 connection.setRequestMethod("POST");
//                 connection.setDoOutput(true);
//                 connection.setRequestProperty("Content-Type", "application/json");
//                 OutputStreamWriter writer = new OutputStreamWriter(connection.getOutputStream());
//                 writer.write("{\"id\":2,\"name\":\"" + nameField.getText() + "\",\"weight\":24.0,\"height\":13323.0,\"pictureUrl\":\"https://photos.app.goo.gl/NPPc6cYuERAT3S8i7\",\"category\":\"Water\",\"overallAbility\":6}");

//                 writer.flush();
//                 writer.close();

//                 connection.connect();
//                 System.out.println(connection.getResponseCode());
//                 connection.disconnect();
//             } catch(IOException ioException) {
//                 ioException.printStackTrace();
//             }
//         });

//         GridPane grid = new GridPane();


//         grid.add(nameLabel, 0, 0);
//         grid.add(nameField, 1, 0);
//         grid.add(ageLabel, 0, 1);
//         grid.add(ageField, 1, 1);
//         grid.add(addButton, 0, 2);
//         grid.add(getButton, 0, 3);



//         VBox vbox = new VBox(20, grid, outputLabel);

//         Scene scene = new Scene(vbox, 640, 480);

//         primaryStage.setScene(scene);

//         primaryStage.show();
//     }

// }