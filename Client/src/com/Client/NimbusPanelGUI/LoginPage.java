package com.Client.NimbusPanelGUI;

import com.Client.CoordResponse.Location;
import com.Client.CoordResponse.Location.ApiException;
import com.Client.WeatherCast.WeatherCastController.weatherCallException;

import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.geometry.VPos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.*;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.stage.*;
import java.io.IOException;
import java.net.URISyntaxException;

public class LoginPage {
    private Stage primaryStage;
    
    public LoginPage(Stage primaryStage) {
        this.primaryStage = primaryStage;
    }
    
    public Scene createScene() {
        return createLandingScene();
    }
    
    private Scene createLandingScene() {

        Image app_logo = new Image("file:///C:/Users/Usuario/Desktop/Sifal/WeatherApp/Client/img/app-logo.png");
        ImageView app_logo_view = new ImageView(app_logo);
        app_logo_view.setFitWidth(200);
        app_logo_view.setFitHeight(200);
        
        Image landing_background_img = new Image("file:///C:/Users/Usuario/Desktop/Sifal/WeatherApp/Client/img/bg.jpg");
        
        BackgroundImage landing_bg_image = new BackgroundImage(
            landing_background_img,
            BackgroundRepeat.NO_REPEAT,
            BackgroundRepeat.NO_REPEAT,
            BackgroundPosition.CENTER,
            new BackgroundSize(100, 100, true, true, false, true)
        );
        Background landing_background = new Background(landing_bg_image);
        
        HBox landing_vbox = new HBox();
        landing_vbox.setPrefSize(1200, 680);
        landing_vbox.setMinSize(900, 600);
        
        // Main content VBox
        VBox loginContent = new VBox(20);
        loginContent.setAlignment(Pos.TOP_CENTER);
        loginContent.setPadding(new Insets(10));
        VBox.setVgrow(loginContent, Priority.ALWAYS);
        loginContent.prefWidthProperty().bind(landing_vbox.widthProperty().multiply(0.4));
        loginContent.prefHeightProperty().bind(landing_vbox.heightProperty());
        
        VBox welcomeContainer = new VBox();
        welcomeContainer.setAlignment(Pos.TOP_CENTER);
        welcomeContainer.setMaxWidth(400);
        welcomeContainer.setPrefWidth(400);
        
        // Header section
        VBox headerSection = new VBox(15);
        headerSection.setAlignment(Pos.TOP_CENTER);
        VBox.setVgrow(headerSection, Priority.ALWAYS);
        headerSection.setMaxWidth(400);
        headerSection.setPrefWidth(400);
        
        
        Label welcome_label = new Label("Welcome to NimbusPanel");
        welcome_label.setStyle("-fx-font-family: 'Arial'; -fx-font-size: 32px; -fx-text-fill: Black; -fx-font-weight: bold; -fx-wrap-text: true;");
        welcome_label.setWrapText(true);
        welcomeContainer.getChildren().add(welcome_label);
        
        VBox startContainer = new VBox();
        startContainer.setAlignment(Pos.CENTER);
        startContainer.setMaxWidth(400);
        startContainer.setPrefWidth(400);
        
        Label start_label = new Label("Clouds, sun, rain — all at your fingertips. Sign in and stay ahead of the weather");
        start_label.setStyle("-fx-font-family: 'Arial'; -fx-font-size: 20px; -fx-text-fill: Black; -fx-wrap-text: true;");
        start_label.setWrapText(true);
        startContainer.getChildren().add(start_label);
        
        headerSection.getChildren().addAll(welcomeContainer, startContainer);
        
        // Form section
        VBox formSection = new VBox(15);
        formSection.setAlignment(Pos.CENTER);

        // Email field container
        VBox emailContainer = new VBox(5);
        emailContainer.setAlignment(Pos.CENTER); // Center the email label and field
        Label email_label = new Label("Email");
        email_label.setStyle("-fx-font-family: 'Arial'; -fx-font-size: 16px; -fx-text-fill: Black;");// Center the label text

        TextField emailField = new TextField();
        emailField.setPromptText("JhonDoe@email.com");
        emailField.setStyle("-fx-font-size: 16px; -fx-border-color: transparent transparent black transparent; "
                + "-fx-border-width: 0 0 1 0; -fx-background-color: transparent; "
                + "-fx-alignment: center;"); // Center the text inside the field
        emailField.setPrefWidth(400);
        emailField.setMaxWidth(400);

        emailContainer.getChildren().addAll(email_label, emailField);

        // Password field container
        VBox passwordContainer = new VBox(5);
        passwordContainer.setAlignment(Pos.CENTER); // Center the password label and field
        Label password_label = new Label("Password");
        password_label.setStyle("-fx-font-family: 'Arial'; -fx-font-size: 16px; -fx-text-fill: Black;");
        password_label.setAlignment(Pos.CENTER); // Center the label text

        TextField passwordField = new TextField();
        passwordField.setPromptText("********");
        passwordField.setStyle("-fx-font-size: 16px; -fx-border-color: transparent transparent black transparent; "
                + "-fx-border-width: 0 0 1 0; -fx-background-color: transparent; "
                + "-fx-alignment: center;"); // Center the text inside the field
        passwordField.setPrefWidth(400);
        passwordField.setMaxWidth(400);

        passwordContainer.getChildren().addAll(password_label, passwordField);

        // Set alignment for the entire form section
        formSection.getChildren().addAll(emailContainer, passwordContainer);
        formSection.setAlignment(Pos.CENTER);
        
        // Buttons section
        VBox buttonSection = new VBox(10);
        buttonSection.setAlignment(Pos.CENTER);
        VBox.setMargin(buttonSection, new Insets(20, 0, 0, 0));
        
        Button loginButton = new Button("Login");
        loginButton.setStyle("-fx-background-color: STEELBLUE; -fx-border-color: transparent; -fx-cursor: hand; -fx-font-size: 20px; -fx-text-fill: white;");
        loginButton.setPrefSize(400, 24);
        
        HBox orRow = new HBox(10);
        orRow.setAlignment(Pos.CENTER);
        orRow.setMaxWidth(Double.MAX_VALUE);
        
        Line leftLine = new Line();
        leftLine.setStartX(0);
        leftLine.setEndX(120);  // Width of each line
        leftLine.setStroke(Color.rgb(0, 0, 0, 0.3));  // Semi-transparent black
        leftLine.setStrokeWidth(1);
        
        Label orLabel = new Label("OR");
        orLabel.setStyle("-fx-font-family: 'Arial'; -fx-font-size: 14px; -fx-text-fill: rgba(0, 0, 0, 0.5);");

        Line rightLine = new Line();
        rightLine.setStartX(0);
        rightLine.setEndX(120);  // Width of each line
        rightLine.setStroke(Color.rgb(0, 0, 0, 0.3));  // Semi-transparent black
        rightLine.setStrokeWidth(1);

  
        orRow.getChildren().addAll(leftLine, orLabel, rightLine);
        
        HBox orContainer = new HBox(orRow);
        orContainer.setAlignment(Pos.CENTER);
        orContainer.setPadding(new Insets(10, 0, 10, 0)); 
        
        
        Button signUpButton = new Button("Sign Up");
        signUpButton.setStyle("-fx-background-color: STEELBLUE; -fx-border-color: transparent; -fx-cursor: hand; -fx-font-size: 20px; -fx-text-fill: white;");
        signUpButton.setPrefSize(400, 24);
        
        
       
        buttonSection.getChildren().addAll(loginButton, orContainer, signUpButton);
        

        
        // Add some flexible spacing
        Region topSpacer = new Region();
        VBox.setVgrow(topSpacer, Priority.ALWAYS);
        Region bottomSpacer = new Region();
        VBox.setVgrow(bottomSpacer, Priority.ALWAYS);
        
        // Combine all sections
        loginContent.getChildren().addAll(
            topSpacer,
            headerSection,
            formSection,
            buttonSection,
            bottomSpacer
        );
        
        // Logo section
        VBox request_hbox = new VBox(60);
        request_hbox.setBackground(landing_background);
        request_hbox.setPrefSize(1200, 450);
        request_hbox.getChildren().addAll(app_logo_view);
        request_hbox.setAlignment(Pos.CENTER);
        request_hbox.prefHeightProperty().bind(landing_vbox.heightProperty());
        request_hbox.prefWidthProperty().bind(landing_vbox.widthProperty().multiply(0.6));
        
        // Add everything to the main container
        landing_vbox.getChildren().addAll(loginContent, request_hbox);
        
        return new Scene(landing_vbox, 1200, 680);
    }
    
    private Region createSpacer(double width) {
        Region spacer = new Region();
        spacer.setPrefWidth(width);
        return spacer;
    }
    
//    private void apiCallErrorPopUp(Stage ownerStage) {
//        Stage popupStage = new Stage();
//        popupStage.initModality(Modality.WINDOW_MODAL);
//        popupStage.initOwner(ownerStage);
//        
//        Label label = new Label("The Location you have entered cannot be found");
//        
//        Button close_button = new Button("Close");
//        close_button.setPrefSize(80, 28);
//        close_button.setOnAction(event -> popupStage.close());
//        
//        VBox popupContent = new VBox(label, close_button);
//        popupContent.setSpacing(20);
//        popupContent.setStyle("-fx-padding: 10; -fx-alignment: center;");
//        
//        Scene popupScene = new Scene(popupContent, 400, 200);
//        popupStage.setScene(popupScene);
//        popupStage.setTitle("Warning");
//        popupStage.show();
//    }
}