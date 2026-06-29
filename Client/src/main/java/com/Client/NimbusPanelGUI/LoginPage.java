package com.Client.NimbusPanelGUI;

import com.Client.User.AuthenticateUser;
import com.Client.User.User;
import com.google.gson.Gson;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.*;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.stage.*;
import javafx.concurrent.Task;

import java.net.HttpURLConnection;

public class LoginPage {

	private Stage primaryStage;
	private TextField emailField;
	private PasswordField passwordField;

	public LoginPage(Stage primaryStage) {
		this.primaryStage = primaryStage;
	}

	public Scene createScene() {
		return createLandingScene();
	}

	private Scene createLandingScene() {

		Image appLogo = UiComponents.loadImage("img/app-logo.png");
		
		ImageView appLogoView = new ImageView(appLogo);
		appLogoView.setFitWidth(200);
		appLogoView.setFitHeight(200);

		Image landingBackgroundImg = UiComponents.loadImage("img/bg.jpg");
		BackgroundImage landingBgImage = new BackgroundImage(landingBackgroundImg, BackgroundRepeat.NO_REPEAT,
				BackgroundRepeat.NO_REPEAT, BackgroundPosition.CENTER,
				new BackgroundSize(100, 100, true, true, false, true));
		Background landingBackground = new Background(landingBgImage);

		HBox mainLayout = new HBox();
		mainLayout.setMaxSize(1200, 660);
		mainLayout.setMinSize(900, 660);

		VBox loginContent = new VBox(20);
		loginContent.setAlignment(Pos.TOP_CENTER);
		loginContent.setPadding(new Insets(10));
		VBox.setVgrow(loginContent, Priority.ALWAYS);
		loginContent.prefWidthProperty().bind(mainLayout.widthProperty().multiply(0.4));
		loginContent.prefHeightProperty().bind(mainLayout.heightProperty());

		VBox welcomeContainer = new VBox();
		welcomeContainer.setAlignment(Pos.TOP_CENTER);
		welcomeContainer.setMaxWidth(400);
		welcomeContainer.setPrefWidth(400);

		VBox headerSection = new VBox(15);
		headerSection.setAlignment(Pos.TOP_CENTER);
		VBox.setVgrow(headerSection, Priority.ALWAYS);
		headerSection.setMaxWidth(400);
		headerSection.setPrefWidth(400);

		Label welcomeLabel = new Label("Welcome to NimbusPanel");
		welcomeLabel
				.setStyle(" -fx-font-size: 32px; -fx-text-fill: Black; -fx-font-weight: bold; -fx-wrap-text: true;");
		welcomeLabel.setWrapText(true);
		welcomeContainer.getChildren().add(welcomeLabel);

		VBox startContainer = new VBox();
		startContainer.setAlignment(Pos.CENTER);
		startContainer.setMaxWidth(400);
		startContainer.setPrefWidth(400);

		Label startLabel = new Label(
				"Clouds, sun, rain — all at your fingertips. Log in and stay ahead of the weather");
		startLabel.setStyle("-fx-font-size: 20px; -fx-text-fill: Black; -fx-wrap-text: true;");
		startLabel.setWrapText(true);
		startContainer.getChildren().add(startLabel);

		headerSection.getChildren().addAll(welcomeContainer, startContainer);

		VBox formSection = new VBox(15);
		formSection.setAlignment(Pos.CENTER);

		emailField = new TextField();
		emailField.setPromptText("JohnDoe@email.com");
		VBox emailContainer = UiComponents.createFormField("Email", emailField, 400);

		passwordField = new PasswordField();
		passwordField.setPromptText("********");
		VBox passwordContainer = UiComponents.createFormField("Password", passwordField, 400);

		formSection.getChildren().addAll(emailContainer, passwordContainer);
		formSection.setAlignment(Pos.CENTER);

		VBox buttonSection = new VBox(10);
		buttonSection.setAlignment(Pos.CENTER);
		VBox.setMargin(buttonSection, new Insets(20, 0, 0, 0));

		Button loginButton = new Button("Log in");
		loginButton.setStyle(UiComponents.BUTTON_STYLE);
		loginButton.setPrefSize(400, 24);

		HBox orRow = new HBox(10);
		orRow.setAlignment(Pos.CENTER);
		orRow.setMaxWidth(Double.MAX_VALUE);

		Line leftLine = new Line();
		leftLine.setStartX(0);
		leftLine.setEndX(120);
		leftLine.setStroke(Color.rgb(0, 0, 0, 0.3));
		leftLine.setStrokeWidth(1);

		Label orLabel = new Label("OR");
		orLabel.setStyle("-fx-font-size: 14px; -fx-text-fill: rgba(0, 0, 0, 0.5);");

		Line rightLine = new Line();
		rightLine.setStartX(0);
		rightLine.setEndX(120);
		rightLine.setStroke(Color.rgb(0, 0, 0, 0.3));
		rightLine.setStrokeWidth(1);

		orRow.getChildren().addAll(leftLine, orLabel, rightLine);

		HBox orContainer = new HBox(orRow);
		orContainer.setAlignment(Pos.CENTER);
		orContainer.setPadding(new Insets(10, 0, 10, 0));

		loginButton.setOnAction(event -> handleLogin());

		Button goToSignUpButton = new Button("Sign Up");
		goToSignUpButton.setStyle(UiComponents.BUTTON_STYLE);
		goToSignUpButton.setPrefSize(400, 24);

		goToSignUpButton.setOnAction(event -> {
			SignUpPage signUpPage = new SignUpPage(primaryStage);
			primaryStage.setScene(signUpPage.createScene());
		});

		buttonSection.getChildren().addAll(loginButton, orContainer, goToSignUpButton);

		Region topSpacer = new Region();
		VBox.setVgrow(topSpacer, Priority.ALWAYS);
		Region bottomSpacer = new Region();
		VBox.setVgrow(bottomSpacer, Priority.ALWAYS);

		// Combine all sections
		loginContent.getChildren().addAll(topSpacer, headerSection, formSection, buttonSection, bottomSpacer);

		VBox logoSection = new VBox(60);
		logoSection.setBackground(landingBackground);
		logoSection.setPrefSize(1200, 450);
		logoSection.getChildren().addAll(appLogoView);
		logoSection.setAlignment(Pos.CENTER);
		logoSection.prefHeightProperty().bind(mainLayout.heightProperty());
		logoSection.prefWidthProperty().bind(mainLayout.widthProperty().multiply(0.6));

		mainLayout.getChildren().addAll(loginContent, logoSection);

		return Main.styled(new Scene(mainLayout, 1200, 660));
	}

	private void handleLogin() {
		String email = emailField.getText();
		String password = passwordField.getText();

		Task<String> loadUserTask = new Task<>() {
			@Override
			protected String call() throws Exception {

				AuthenticateUser.AuthenticateUserResponse loginResp = AuthenticateUser.authenticateUser(email,
						password);

				return loginResp.body;
			}
		};

		loadUserTask.setOnSucceeded(event -> {

			String body = loadUserTask.getValue();

			Main.currentUser = new Gson().fromJson(body, User.class);

			HomePage homePage = new HomePage(primaryStage);
			primaryStage.setScene(homePage.createScene());
		});

		loadUserTask.setOnFailed(event -> {
			Throwable cause = loadUserTask.getException();

			String errorMessage;
			String errorTitle;

			if (cause instanceof AuthenticateUser.ApiException) {
				AuthenticateUser.ApiException apiEx = (AuthenticateUser.ApiException) cause;

				if (apiEx.statusCode == HttpURLConnection.HTTP_UNAUTHORIZED) {
					errorMessage = PopUpMessages.LOGIN_FAILED_MESSAGE;
					errorTitle = PopUpMessages.LOGIN_FAILED_TITLE;
				} else if (apiEx.statusCode == HttpURLConnection.HTTP_INTERNAL_ERROR) {
					errorMessage = PopUpMessages.SERVER_ERROR_MESSAGE;
					errorTitle = PopUpMessages.SERVER_ERROR_TITLE;
				} else {
					errorMessage = "An unexpected error has occurred. Please contact customer service if it persists.";
					errorTitle = "Error";
				}
			} else {
				if (cause != null)
					cause.printStackTrace();
				errorMessage = "An unexpected error has occurred. Please contact customer service if it persists.";
				errorTitle = "Error";
			}

			ApiCallErrorPopUp.showErrorPopUp(primaryStage, errorMessage, errorTitle);
		});

		new Thread(loadUserTask).start();
	}
}