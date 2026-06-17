package com.Client.NimbusPanelGUI;

import com.Client.CoordResponse.Location;
import com.Client.User.AddFavourite;
import com.Client.User.DeleteFavourite;
import com.Client.User.FavouriteLocations;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.*;
import javafx.scene.layout.*;
import javafx.stage.*;
import java.util.ArrayList;
import java.util.List;

import javafx.concurrent.Task;

public class HomePage {
    private Stage primaryStage;
    private List<FavouriteLocations> favouriteLocations; 
    
    private static final String CARD_STYLE =
    	    "-fx-background-color: rgba(28, 45, 80, 0.9); -fx-background-radius: 10; "
    	    + "-fx-cursor: hand; -fx-text-fill: white; -fx-font-weight: bold; "
    	    + "-fx-effect: dropshadow(gaussian, rgba(0, 0, 0, 0.4), 12, 0, 0, 4);";
    
    public HomePage(Stage primaryStage) {
        this.primaryStage = primaryStage;
        this.favouriteLocations = Main.currentUser.getFavouriteLocations() != null
        		? Main.currentUser.getFavouriteLocations()
        				: new ArrayList<>();
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
        
        for (FavouriteLocations favourite: favouriteLocations) {
            createFavouriteButton(favourite, favouriteCardsBox);
        }
        
        HBox favouritesContainer = new HBox();
        favouritesContainer.setMinSize(1040, 220);
        favouritesContainer.setSpacing(12);
        favouritesContainer.setAlignment(Pos.CENTER);
        favouritesContainer.getChildren().addAll(favouritesScrollPane, addFavouriteBox);
        
        addFavouriteButton.setOnAction(event -> {
            createFavouriteScene(primaryStage);
        });
        
        return favouritesContainer;
    }
    
    private void createFavouriteButton(FavouriteLocations favourite, HBox favouriteCardsBox) {
        Image deleteIcon = new Image("file:img/delete-icon.png");
        ImageView deleteIconView = new ImageView(deleteIcon);
        deleteIconView.setFitHeight(24);
        deleteIconView.setFitWidth(24);
        
        Button favouriteButton = new Button(favourite.getLocation());
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
        
        favouriteButton.setOnAction(event -> openWeatherPage(favourite.getLocation()));
        
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
            String locationName = Main.capitalizeFirstLetter(locationTextField.getText());

            if (isDuplicateFavourite(locationName)) {
                ApiCallErrorPopUp.showErrorPopUp(popupStage,
                    "This location is already in your favourites.", "Duplicate");
                return;   
            }

            Task<FavouriteLocations> addTask = new Task<>() {
                @Override
                protected FavouriteLocations call() throws Exception {

                    return AddFavourite.addFavourite(Main.currentUser.getId(), locationName);
                }
            };

            addTask.setOnSucceeded(e -> {
                FavouriteLocations saved = addTask.getValue();
                favouriteLocations.add(saved);

                popupStage.close();
                primaryStage.setScene(createScene());   
            });

            addTask.setOnFailed(e -> {
                Throwable cause = addTask.getException();
                if (cause != null) cause.printStackTrace();
                ApiCallErrorPopUp.showErrorPopUp(popupStage,
                    "Could not add favourite. Please try again.", "Error");
            });

            new Thread(addTask).start();
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
    
    private void createDeleteConfirmationPopUp(Stage ownerStage, FavouriteLocations favourite) {
        Stage popupStage = new Stage();
        Image icon = new Image("file:img/minus-button-16.png");
        popupStage.getIcons().add(icon);
        popupStage.initModality(Modality.WINDOW_MODAL);
        popupStage.initOwner(ownerStage);
        
        Label label = new Label("Are you sure You want to remove " + favourite.getLocation() + " from you favourites?");
        
        Button closeButton = new Button("Close");
        closeButton.setOnAction(event -> popupStage.close());
        
        Button confirmButton = new Button("Confirm");
        confirmButton.setOnAction(event -> {
        	Task<Void> deleteTask = new Task<>() {
        		@Override 
        		protected Void call() throws Exception{
        			DeleteFavourite.deleteFavourite(Main.currentUser.getId(), favourite.getId());
        			return null;
        		}
        	};
        	
        	deleteTask.setOnSucceeded(e -> {
        		removeItemFromFavourites(favourite);
        		popupStage.close();
        		primaryStage.setScene(createScene());
        	});
        	
        	deleteTask.setOnFailed(e -> {
        		Throwable cause = deleteTask.getException();
        		if (cause != null) cause.printStackTrace();
        		ApiCallErrorPopUp.showErrorPopUp(popupStage, 
        				"Could not remove favourite. Please try again.", "Error");  
    		});
        	
        	new Thread(deleteTask).start();
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
    
    private void removeItemFromFavourites(FavouriteLocations favourite) {
        favouriteLocations.removeIf(f -> f.getId().equals(favourite.getId()));
    }
    
    private void openWeatherPage(String cityName) {
        Main.location = Main.capitalizeFirstLetter(cityName);

        Task<Void> loadWeatherTask = new Task<>() {
            @Override
            protected Void call() throws Exception {

                Location.GetLocation(new String[]{}, Main.formatLocationInput(Main.location));
                return null;  
            }
        };

        loadWeatherTask.setOnSucceeded(event -> {
            WeatherPage weatherPage = new WeatherPage(primaryStage);
            primaryStage.setScene(weatherPage.createScene());
        });

        loadWeatherTask.setOnFailed(event -> {
        	Throwable cause = loadWeatherTask.getException();

            if (cause != null) {
                cause.printStackTrace();
            }
            ApiCallErrorPopUp.showErrorPopUp(primaryStage,
                PopUpMessages.LOCATION_NOT_FOUND_MESSAGE,
                PopUpMessages.LOCATION_NOT_FOUND_TITLE);
        });

        new Thread(loadWeatherTask).start();
    }
    
    private boolean isDuplicateFavourite(String locationName) {
    	return favouriteLocations.stream()
    			.anyMatch(fav -> fav.getLocation().equals(locationName));
    }
}