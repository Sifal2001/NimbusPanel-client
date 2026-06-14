package com.Client.NimbusPanelGUI;

import com.Client.CoordResponse.Location;
import com.Client.CoordResponse.Location.ApiException;
import com.Client.WeatherCast.WeatherCastController.weatherCallException;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.*;
import javafx.scene.layout.*;
import javafx.stage.*;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.ArrayList;
import javafx.concurrent.Task;

public class HomePage {
    private Stage primaryStage;
    private ArrayList<String> favouriteLocations; 
    
    private static final String CARD_STYLE =
    	    "-fx-background-color: rgba(28, 45, 80, 0.9); -fx-background-radius: 10; "
    	    + "-fx-cursor: hand; -fx-text-fill: white; -fx-font-weight: bold; "
    	    + "-fx-effect: dropshadow(gaussian, rgba(0, 0, 0, 0.4), 12, 0, 0, 4);";
    
    public HomePage(Stage primaryStage, ArrayList<String> favouriteLocations) {
        this.primaryStage = primaryStage;
        this.favouriteLocations = favouriteLocations; 
    }
    
    public Scene createScene() {
        return createLandingScene();
    }
    
    private Scene createLandingScene() {
        Image searchIcon = new Image("file:img/search-icon.png");
        ImageView searchImageView = new ImageView(searchIcon);
        searchImageView.setFitWidth(28);
        searchImageView.setFitHeight(28);
        
        Image appLogo = new Image("file:img/app-logo.png");
        ImageView appLogoView = new ImageView(appLogo);
        appLogoView.setFitWidth(200);
        appLogoView.setFitHeight(200);
        
        Button searchButton = new Button();
        searchButton.setGraphic(searchImageView);
        searchButton.setStyle("-fx-background-color: white; -fx-border-color: transparent; -fx-cursor: hand");
        searchButton.setMaxSize(6, 6);
        
        TextField nameField = new TextField();
        nameField.setPromptText("Location");
        nameField.setStyle("-fx-font-size: 16px");
        nameField.setPrefSize(480, 42);
        
        Image landingBackgroundImg = new Image("file:img/bg.jpg");
        
        BackgroundImage landingBgImage = new BackgroundImage(
            landingBackgroundImg,
            BackgroundRepeat.NO_REPEAT,
            BackgroundRepeat.NO_REPEAT,
            BackgroundPosition.CENTER,
            new BackgroundSize(100, 100, true, true, false, true)
        );
        Background landingBackground = new Background(landingBgImage);
        
        HBox searchBox = new HBox(-46);
        searchBox.getChildren().addAll(nameField, searchButton);
        searchBox.setAlignment(Pos.CENTER);
        
        VBox logoSection = new VBox(60);
        logoSection.setPrefSize(1080, 450);
        VBox.setMargin(appLogoView, new Insets(60, 0, 0, 0));
        logoSection.getChildren().addAll(appLogoView, searchBox);
        logoSection.setAlignment(Pos.TOP_CENTER);
        
        VBox mainLayout = new VBox();
        mainLayout.setBackground(landingBackground);
        mainLayout.setPrefSize(1200, 680);
        mainLayout.getChildren().addAll(logoSection, createFavouritesHbox());
        
     // The lambda's only job now: harvest the input, delegate the work
        searchButton.setOnAction(event -> openWeatherPage(nameField.getText()));
        
        return new Scene(mainLayout, 1200, 680);
    }
    
    private HBox createFavouritesHbox() {
        Button addFavouriteButton = new Button("+");
        addFavouriteButton.setMinWidth(140);
        addFavouriteButton.setMaxHeight(220);
        addFavouriteButton.setStyle(CARD_STYLE + "-fx-font-size: 40px;");
        
        HBox addFavouriteBox = new HBox();
        addFavouriteBox.setPrefSize(140, 220);
        addFavouriteBox.getChildren().add(addFavouriteButton);
        
        HBox favouriteCardsBox = new HBox();
        favouriteCardsBox.setPrefSize(1200, 220);
        favouriteCardsBox.setSpacing(16);
        
        ScrollPane favouritesScrollPane = new ScrollPane();
        favouritesScrollPane.setContent(favouriteCardsBox);
        favouritesScrollPane.setFitToWidth(true);
        favouritesScrollPane.setVbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        favouritesScrollPane.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        favouritesScrollPane.setMaxHeight(230);
        favouritesScrollPane.setMaxWidth(1040);
        favouritesScrollPane.setStyle("-fx-background-color: transparent; -fx-background: transparent");
        
        for (String favourite: favouriteLocations) {
            createFavouriteButton(favourite, favouriteCardsBox);
        }
        
        HBox favouritesContainer = new HBox();
        favouritesContainer.setMinSize(1040, 220);
        favouritesContainer.setSpacing(12);
        favouritesContainer.setAlignment(Pos.CENTER);
        //favouritesContainer.setPadding(new Insets(0, 20, 24, 20));
        favouritesContainer.getChildren().addAll(favouritesScrollPane, addFavouriteBox);
        
        addFavouriteButton.setOnAction(event -> {
            createFavouriteScene(primaryStage);
        });
        
        return favouritesContainer;
    }
    
    private void createFavouriteButton(String favourite, HBox favouriteCardsBox) {
        Image deleteIcon = new Image("file:img/delete-icon.png");
        ImageView deleteIconView = new ImageView(deleteIcon);
        deleteIconView.setFitHeight(24);
        deleteIconView.setFitWidth(24);
        
        Button favouriteButton = new Button(favourite);
        favouriteButton.setPrefWidth(160);
        favouriteButton.setPrefHeight(180);
        favouriteButton.setMaxHeight(220);
        favouriteButton.setWrapText(true);
        favouriteButton.setAlignment(Pos.CENTER);
        favouriteButton.setTextAlignment(javafx.scene.text.TextAlignment.CENTER);
        favouriteButton.setStyle(CARD_STYLE + "-fx-font-size: 28px;");
        
        Button deleteFavouriteButton = new Button();
        deleteFavouriteButton.setGraphic(deleteIconView);
        deleteFavouriteButton.setStyle("-fx-background-color: transparent");
        deleteFavouriteButton.setPrefSize(28, 28);
        
        StackPane stackPane = new StackPane();
        stackPane.getChildren().addAll(favouriteButton, deleteFavouriteButton);
        StackPane.setAlignment(deleteFavouriteButton, Pos.BOTTOM_RIGHT);
        
        favouriteCardsBox.getChildren().add(stackPane);
        
        favouriteButton.setOnAction(event -> openWeatherPage(favouriteButton.getText()));
        
        deleteFavouriteButton.setOnAction(e -> {
            createDeleteConfirmationPopUp(primaryStage, favourite);
        });
    }
    
    private void createFavouriteScene(Stage ownerStage) {
        Stage popupStage = new Stage();
        Image icon = new Image("file:///C:/Users/Usuario/Desktop/Sifal/WeatherApp/Client/img/plus-button-16.png");
        popupStage.getIcons().add(icon);
        popupStage.initModality(Modality.WINDOW_MODAL);
        popupStage.initOwner(ownerStage);
        
        Label label = new Label("Enter a location to add");
        TextField locationTextField = new TextField();
        
        Button closeButton = new Button("Close");
        closeButton.setOnAction(event -> popupStage.close());
        
        Button addButton = new Button("Add");
        addButton.setOnAction(event -> {
            favouriteLocations.add(Main.capitalizeFirstLetter(locationTextField.getText()));
            popupStage.close();
            primaryStage.setScene(createScene());
        });
        
        HBox popupButtons = new HBox(closeButton, addButton);
        popupButtons.setSpacing(100);
        popupButtons.setAlignment(Pos.CENTER);
        
        VBox popupContent = new VBox(label, locationTextField, popupButtons);
        popupContent.setSpacing(20);
        popupContent.setStyle("-fx-padding: 10; -fx-alignment: center;");
        
        Scene popupScene = new Scene(popupContent, 400, 200);
        popupStage.setScene(popupScene);
        popupStage.setTitle("Add Location");
        popupStage.show();
    }
    
    private void createDeleteConfirmationPopUp(Stage ownerStage, String favouriteLocation) {
        Stage popupStage = new Stage();
        Image icon = new Image("file:img/minus-button-16.png");
        popupStage.getIcons().add(icon);
        popupStage.initModality(Modality.WINDOW_MODAL);
        popupStage.initOwner(ownerStage);
        
        Label label = new Label("Are you sure You want to remove " + favouriteLocation + " from you favourites?");
        
        Button closeButton = new Button("Close");
        closeButton.setOnAction(event -> popupStage.close());
        
        Button confirmButton = new Button("Confirm");
        confirmButton.setOnAction(event -> {
            removeItemFromFavourites(favouriteLocation);
            popupStage.close();
            primaryStage.setScene(createScene());
        });
        
        HBox popupButtons = new HBox(closeButton, confirmButton);
        popupButtons.setSpacing(100);
        popupButtons.setAlignment(Pos.CENTER);
        
        VBox popupContent = new VBox(label, popupButtons);
        popupContent.setSpacing(20);
        popupContent.setStyle("-fx-padding: 10; -fx-alignment: center;");
        
        Scene popupScene = new Scene(popupContent, 400, 200);
        popupStage.setScene(popupScene);
        popupStage.setTitle("Warning");
        popupStage.show();
    }
    
    private void removeItemFromFavourites(String itemToRemove) {
        favouriteLocations.removeIf(city -> city.equals(itemToRemove));
    }
    
    private void openWeatherPage(String cityName) {
        // Prepare the location name on the UI thread — this part is instant,
        // no network, so it's fine to do here before going background
        Main.location = Main.capitalizeFirstLetter(cityName);

        // A Task<Void> is a unit of background work. <Void> is the generic type
        // saying "this task doesn't RETURN a value" — it just does work (the
        // navigation happens in the success hook, not as a return value).
        // We build it from an anonymous class: 'new Task<Void>() { ... }' creates
        // a one-off object that overrides call() with what we want done
        Task<Void> loadWeatherTask = new Task<>() {
            @Override
            protected Void call() throws Exception {
                // THIS runs on a BACKGROUND thread — the slow network call lives
                // here, off the UI thread, so the window stays responsive.
                // If GetLocation throws, the Task catches it automatically and
                // routes it to setOnFailed below — no try/catch needed here
                Location.GetLocation(new String[]{}, Main.formatLocationInput(Main.location));
                return null;   // Void's required "I return nothing" return
            }
        };

        // setOnSucceeded runs AUTOMATICALLY and ON THE UI THREAD when call()
        // finishes without throwing. So touching the scene here is legal —
        // this is the safe "hand back to the UI thread" half of the dance.
        // 'event ->' is a lambda; the event param is unused but required by the signature
        loadWeatherTask.setOnSucceeded(event -> {
            WeatherPage weatherPage = new WeatherPage(primaryStage);
            primaryStage.setScene(weatherPage.createScene());
        });

        // setOnFailed runs (also on the UI thread) if call() threw anything.
        // getException() returns the Throwable that was thrown — useful if you
        // later want to show different popups for different failures
        loadWeatherTask.setOnFailed(event -> {
        	Throwable cause = loadWeatherTask.getException();
            // For now both paths show the same popup, but this structure lets you
            // give a real "server is down" message later instead of blaming the city.
            // printStackTrace keeps the technical detail in your console for debugging
            if (cause != null) {
                cause.printStackTrace();
            }
            ApiCallErrorPopUp.showErrorPopUp(primaryStage,
                PopUpMessages.LOCATION_NOT_FOUND_MESSAGE,
                PopUpMessages.LOCATION_NOT_FOUND_TITLE);
        });

        // Nothing has run yet — we only DEFINED the task and its hooks.
        // A Thread is the actual worker; we hand it the task and start() it.
        // 'new Thread(task)' wraps the work, start() launches it in the background
        // and immediately returns control here, so the UI thread flows on, free
        new Thread(loadWeatherTask).start();
    }
}