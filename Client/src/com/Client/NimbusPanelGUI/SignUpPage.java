package com.Client.NimbusPanelGUI;

import com.Client.User.UserService;
import java.net.HttpURLConnection;
import com.Client.User.CreateUser;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.*;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.stage.*;


public class SignUpPage {
	
    private Stage primaryStage;
    private TextField firstNameField;
    private TextField lastNameField;
    private TextField emailField;
    private PasswordField passwordField;
    private PasswordField passwordConfirmField;
    
    private UserService userService = new UserService();
    
    public SignUpPage(Stage primaryStage) {
        this.primaryStage = primaryStage;
    }
    
    public Scene createScene() {
        return createLandingScene();
    }
    
    private Scene createLandingScene() {

    	Image appLogo = new Image("file:img/app-logo.png");

        ImageView appLogoView = new ImageView(appLogo);
        appLogoView.setFitWidth(200);
        appLogoView.setFitHeight(200);
       
        Image landingBackgroundImg = new Image("file:img/bg.jpg");
        BackgroundImage landingBgImage = new BackgroundImage(
            landingBackgroundImg,
            BackgroundRepeat.NO_REPEAT,
            BackgroundRepeat.NO_REPEAT,
            BackgroundPosition.CENTER,
            new BackgroundSize(100, 100, true, true, false, true)
        );
        Background landingBackground = new Background(landingBgImage);
        
        HBox mainLayout = new HBox();
        mainLayout.setPrefSize(1200, 680);
        mainLayout.setMinSize(900, 600);
        
        // Main content VBox
        VBox loginContent = new VBox(10);
        loginContent.setAlignment(Pos.TOP_CENTER);
        loginContent.setPadding(new Insets(10));
        VBox.setVgrow(loginContent, Priority.ALWAYS);
        loginContent.prefWidthProperty().bind(mainLayout.widthProperty().multiply(0.4));
        loginContent.prefHeightProperty().bind(mainLayout.heightProperty());
        
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
        
        
        Label welcomeLabel = new Label("Welcome to NimbusPanel");
        welcomeLabel.setStyle("-fx-font-family: 'Arial'; -fx-font-size: 32px; -fx-text-fill: Black; -fx-font-weight: bold; -fx-wrap-text: true;");
        welcomeLabel.setWrapText(true);
        welcomeContainer.getChildren().add(welcomeLabel);
        
        VBox startContainer = new VBox();
        startContainer.setMaxWidth(400);
        startContainer.setPrefWidth(400);
        
        Label startLabel = new Label("Clouds, sun, rain — all at your fingertips. Sign up and stay ahead of the weather");
        startLabel.setStyle("-fx-font-family: 'Arial'; -fx-font-size: 20px; -fx-text-fill: Black; -fx-wrap-text: true;");
        startLabel.setWrapText(true);
        startContainer.getChildren().add(startLabel);
        
        headerSection.getChildren().addAll(welcomeContainer, startContainer);
        
        // Form section
        VBox formSection = new VBox(10);
        formSection.setAlignment(Pos.CENTER);
        
        HBox nameContainer = new HBox();
        nameContainer.setPrefWidth(400);
        nameContainer.setMaxWidth(400);
        
        // FirstName field container  
        firstNameField = new TextField();
        firstNameField.setPromptText("John");
        VBox firstNameContainer = UiComponents.createFormField("First Name", firstNameField, 190);
        
        // LastName field container  
        lastNameField = new TextField();
        lastNameField.setPromptText("Doe");
        VBox lastNameContainer = UiComponents.createFormField("Last Name", lastNameField, 190);
        
        Region spacer = new Region();
        spacer.setPrefWidth(40);
        spacer.setMinWidth(40);
    
        nameContainer.getChildren().addAll(firstNameContainer, spacer, lastNameContainer);
        
        // Email field container     
        emailField = new TextField();
        emailField.setPromptText("JohnDoe@email.com");
        VBox emailContainer = UiComponents.createFormField("Email", emailField, 400);

        // Password field container  
        passwordField = new PasswordField();
        passwordField.setPromptText("********");
        VBox passwordContainer = UiComponents.createFormField("Password", passwordField, 400);
        
        // PasswordConfirm field container
        passwordConfirmField = new PasswordField();
        passwordConfirmField.setPromptText("********");
        VBox passwordConfirmContainer = UiComponents.createFormField("Confirm Password", passwordConfirmField, 400);

        // Set alignment for the entire form section
        formSection.getChildren().addAll(nameContainer, emailContainer, passwordContainer, passwordConfirmContainer);
        formSection.setAlignment(Pos.CENTER);
        
        // Buttons section
        VBox buttonSection = new VBox(10);
        buttonSection.setAlignment(Pos.CENTER);
        VBox.setMargin(buttonSection, new Insets(20, 0, 0, 0));
        
        Button signUpButton = new Button("Confirm");
        signUpButton.setStyle(UiComponents.BUTTON_STYLE);
        signUpButton.setPrefSize(400, 24);
        
        signUpButton.setOnAction(event -> handleSignUp());
        
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
        
        
        Button backButton = new Button("Back");
        backButton.setStyle(UiComponents.BUTTON_STYLE);
        backButton.setPrefSize(400, 24);
        
        backButton.setOnAction(event -> {
            LoginPage loginPage = new LoginPage(primaryStage);
            primaryStage.setScene(loginPage.createScene());
        });
        
        
       
        buttonSection.getChildren().addAll(signUpButton, orContainer, backButton);
        

        
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
        VBox logoSection = new VBox();
        logoSection.setBackground(landingBackground);
        logoSection.setPrefSize(720, 450);
        logoSection.getChildren().addAll(appLogoView);
        logoSection.setAlignment(Pos.CENTER);
        logoSection.prefHeightProperty().bind(mainLayout.heightProperty());
//        logoSection.prefWidthProperty().bind(mainLayout.widthProperty().multiply(0.6));
        
        // Add everything to the main container
        mainLayout.getChildren().addAll(loginContent, logoSection);
        
        return new Scene(mainLayout, 1200, 680);
    }
    
    private boolean validateForm(String firstName, String lastName, String email, String password, String passwordConfirm) {
        String firstNameError = InputValidator.validateFirstName(firstName);
        String lastNameError  = InputValidator.validateLastName(lastName);
        String passwordError  = InputValidator.validatePasswords(password, passwordConfirm);
        boolean emailValid    = InputValidator.isValidEmail(email);

        if (firstNameError != null) {
            ApiCallErrorPopUp.showErrorPopUp(primaryStage, firstNameError, PopUpMessages.SERVER_ERROR_TITLE);
            return false;
        }

        if (lastNameError != null) {
            ApiCallErrorPopUp.showErrorPopUp(primaryStage, lastNameError, PopUpMessages.SERVER_ERROR_TITLE);
            return false;
        }

        if (!emailValid) {
            ApiCallErrorPopUp.showErrorPopUp(primaryStage, "Please use a valid email", PopUpMessages.SERVER_ERROR_TITLE);
            return false;
        }

        if (passwordError != null) {
            ApiCallErrorPopUp.showErrorPopUp(primaryStage, passwordError, PopUpMessages.SERVER_ERROR_TITLE);
            return false;
        }

        return true;
    }
    
    private String validatePasswords(String password, String passwordConfirm) {
        if (password.isEmpty()) {
            return "Please enter a password";
        }
        
        if (passwordConfirm.isEmpty()) {
            return "Please confirm your password";
        }
        
        if(password.length() < 8) {
        	return "Please enter a password with at least 8 characters";
        }
        
        return password.equals(passwordConfirm) ? null : "Password does not match";
    }
    
    private String isValidLastName(String lastName) {
    	if (lastName == null || lastName.trim().isEmpty()) {
    		return "Last Name is required";
    	}
    	if (!lastName.matches("[a-zA-Z]*")) {
    		return "Last name must only contain letters";
    	}
    	
    	return null;
    }
    
    private void handleSignUp() {
        String firstName = firstNameField.getText();
        String lastName  = lastNameField.getText();
        String email     = emailField.getText();
        String password  = passwordField.getText();
        String confirm   = passwordConfirmField.getText();

        if (!validateForm(firstName, lastName, email, password, confirm)) return;

        if (userService.isEmailDuplicate(email)) {
            ApiCallErrorPopUp.showErrorPopUp(primaryStage,
                PopUpMessages.DUPLICATE_EMAIL_MESSAGE, PopUpMessages.SERVER_ERROR_TITLE);
            return;
        }

        try {
            CreateUser.CreateUserResponse response = userService.createUser(firstName, lastName, email, password);
            if (response.code == HttpURLConnection.HTTP_OK || response.code == HttpURLConnection.HTTP_CREATED) {
                primaryStage.setScene(new HomePage(primaryStage, Main.favouriteLocations).createScene());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}