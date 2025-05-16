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
import java.util.Iterator;

public class HomePage {
    private Stage primaryStage;
    
    public HomePage(Stage primaryStage, ArrayList<String> favourite_locations) {
        this.primaryStage = primaryStage;
    }
    
    public Scene createScene() {
        return createLandingScene();
    }
    
    private Scene createLandingScene() {
        Image search_icon = new Image("file:///C:/Users/Usuario/Desktop/Sifal/WeatherApp/Client/img/search-50.png");
        ImageView search_imageView = new ImageView(search_icon);
        search_imageView.setFitWidth(28);
        search_imageView.setFitHeight(28);
        
        Image app_logo = new Image("file:///C:/Users/Usuario/Desktop/Sifal/WeatherApp/Client/img/app-logo.png");
        ImageView app_logo_view = new ImageView(app_logo);
        app_logo_view.setFitWidth(200);
        app_logo_view.setFitHeight(200);
        
        Button searchButton = new Button();
        searchButton.setGraphic(search_imageView);
        searchButton.setStyle("-fx-background-color: white; -fx-border-color: transparent; -fx-cursor: hand");
        searchButton.setMaxSize(6, 6);
        
        TextField nameField = new TextField();
        nameField.setPromptText("Location");
        nameField.setStyle("-fx-font-size: 16px");
        nameField.setPrefSize(480,42);
        
        Image landing_background_img = new Image("file:///C:/Users/Usuario/Desktop/Sifal/WeatherApp/Client/img/bg.jpg");
        
        BackgroundImage landing_bg_image = new BackgroundImage(
            landing_background_img,
            BackgroundRepeat.NO_REPEAT,
            BackgroundRepeat.NO_REPEAT,
            BackgroundPosition.CENTER,
            new BackgroundSize(100, 100, true, true, false, true)
        );
        Background landing_background = new Background(landing_bg_image);
        
        HBox search_hbox = new HBox(-46);
        search_hbox.getChildren().addAll(nameField, searchButton);
        search_hbox.setAlignment(Pos.CENTER);
        
        VBox request_hbox = new VBox(60);
        request_hbox.setPrefSize(1080, 450);
        VBox.setMargin(app_logo_view, new Insets(60, 0, 0, 0));
        request_hbox.getChildren().addAll(app_logo_view, search_hbox);
        request_hbox.setAlignment(Pos.TOP_CENTER);
        
        VBox landing_vbox = new VBox();
        landing_vbox.setBackground(landing_background);
        landing_vbox.setPrefSize(1200, 680);
        landing_vbox.getChildren().addAll(request_hbox, createFavouritesHbox());
        
        searchButton.setOnAction(event -> {
            try {
                Main.location = Main.capitalizeFirstLetter(nameField.getText());
                Location.GetLocation(new String[]{}, Main.formatLocationInput(Main.location));
                WeatherPage weatherPage = new WeatherPage(primaryStage);
                primaryStage.setScene(weatherPage.createScene());
            } catch(ApiException| weatherCallException | IOException ex) {
                apiCallErrorPopUp(primaryStage);
            } catch (URISyntaxException e) {
                e.printStackTrace();
            }
        });
        
        return new Scene(landing_vbox, 1200, 680);
    }
    
    private HBox createFavouritesHbox() {
        Button add_favourite_button = new Button("+");
        add_favourite_button.setMinWidth(140);
        add_favourite_button.setMaxHeight(220);
        add_favourite_button.setStyle("-fx-background-color: rgba(178, 190, 181); -fx-background-radius: 10; -fx-cursor: hand; -fx-font-size:40px; -fx-text-fill: white; -fx-font-weight: bold");
        
        HBox add_favourites_hbox = new HBox();
        add_favourites_hbox.setPrefSize(140, 220);
        add_favourites_hbox.getChildren().add(add_favourite_button);
        
        HBox favourite_hbox = new HBox();
        favourite_hbox.setPrefSize(1200, 220);
        favourite_hbox.setSpacing(16);
        
        ScrollPane favourites_scrollPane = new ScrollPane();
        favourites_scrollPane.setContent(favourite_hbox);
        favourites_scrollPane.setFitToWidth(true);
        favourites_scrollPane.setVbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        favourites_scrollPane.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        favourites_scrollPane.setMaxHeight(230);
        favourites_scrollPane.setMaxWidth(1040);
        favourites_scrollPane.setStyle("-fx-background-color: transparent; -fx-background: transparent");
        
        for (String favourite: Main.favourite_locations) {
            createFavouriteButton(favourite, favourite_hbox);
        }
        
        HBox favourites_container = new HBox();
        favourites_container.setMinSize(1040, 220);
        favourites_container.setSpacing(12);
        favourites_container.setAlignment(Pos.CENTER);
        favourites_container.getChildren().addAll(favourites_scrollPane, add_favourites_hbox);
        
        add_favourite_button.setOnAction(event -> {
            createFavouriteScene(primaryStage);
        });
        
        return favourites_container;
    }
    
    private void createFavouriteButton(String favourite, HBox favourite_hbox) {
        Image delete_icon = new Image("file:///C:/Users/Usuario/Desktop/Sifal/WeatherApp/Client/img/delete-24.png");
        ImageView delete_icon_imageView = new ImageView(delete_icon);
        delete_icon_imageView.setFitHeight(24);
        delete_icon_imageView.setFitWidth(24);
        
        Button favourite_button = new Button(favourite);
        favourite_button.setPrefWidth(160);
        favourite_button.setMaxHeight(220);
        favourite_button.setWrapText(true);
        favourite_button.setAlignment(Pos.CENTER);
        favourite_button.setTextAlignment(javafx.scene.text.TextAlignment.CENTER);
        favourite_button.setStyle("-fx-background-color: rgba(178, 190, 181, 0.8); -fx-background-radius: 10; -fx-cursor: hand; -fx-font-size:28px; -fx-text-fill: white; -fx-font-weight: bold");
        
        Button delete_favourite_button = new Button();
        delete_favourite_button.setGraphic(delete_icon_imageView);
        delete_favourite_button.setStyle("-fx-background-color: rgba(178, 190, 181, 0.8)");
        delete_favourite_button.setPrefSize(28, 28);
        
        StackPane stackPane = new StackPane();
        stackPane.getChildren().addAll(favourite_button, delete_favourite_button);
        StackPane.setAlignment(delete_favourite_button, Pos.BOTTOM_RIGHT);
        
        favourite_hbox.getChildren().add(stackPane);
        
        favourite_button.setOnAction(event -> {
            try {
                Main.location = Main.capitalizeFirstLetter(favourite_button.getText());
                Location.GetLocation(new String[]{}, Main.formatLocationInput(Main.location));
                WeatherPage weatherPage = new WeatherPage(primaryStage);
                primaryStage.setScene(weatherPage.createScene());
            } catch (ApiException| weatherCallException |IOException ex) {
                apiCallErrorPopUp(primaryStage);
            } catch (URISyntaxException ex) {
                ex.printStackTrace();
            }
        });
        
        delete_favourite_button.setOnAction(e -> {
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
        TextField location_text_field = new TextField();
        
        Button close_button = new Button("Close");
        close_button.setOnAction(event -> popupStage.close());
        
        Button add_button = new Button("Add");
        add_button.setOnAction(event -> {
            Main.favourite_locations.add(Main.capitalizeFirstLetter(location_text_field.getText()));
            popupStage.close();
            primaryStage.setScene(createScene());
        });
        
        HBox popup_buttons = new HBox(close_button, add_button);
        popup_buttons.setSpacing(100);
        popup_buttons.setAlignment(Pos.CENTER);
        
        VBox popupContent = new VBox(label, location_text_field, popup_buttons);
        popupContent.setSpacing(20);
        popupContent.setStyle("-fx-padding: 10; -fx-alignment: center;");
        
        Scene popupScene = new Scene(popupContent, 400, 200);
        popupStage.setScene(popupScene);
        popupStage.setTitle("Add Location");
        popupStage.show();
    }
    
    private void createDeleteConfirmationPopUp(Stage ownerStage, String favourite_location) {
        Stage popupStage = new Stage();
        Image icon = new Image("file:///C:/Users/Usuario/Desktop/Sifal/WeatherApp/Client/img/minus-button-16.png");
        popupStage.getIcons().add(icon);
        popupStage.initModality(Modality.WINDOW_MODAL);
        popupStage.initOwner(ownerStage);
        
        Label label = new Label("Are you sure You want to remove " + favourite_location + " from you favourites?");
        
        Button close_button = new Button("Close");
        close_button.setOnAction(event -> popupStage.close());
        
        Button confirm_button = new Button("Confirm");
        confirm_button.setOnAction(event -> {
            removeItemFromFavourites(Main.favourite_locations, favourite_location);
            popupStage.close();
            primaryStage.setScene(createScene());
        });
        
        HBox popup_buttons = new HBox(close_button, confirm_button);
        popup_buttons.setSpacing(100);
        popup_buttons.setAlignment(Pos.CENTER);
        
        VBox popupContent = new VBox(label, popup_buttons);
        popupContent.setSpacing(20);
        popupContent.setStyle("-fx-padding: 10; -fx-alignment: center;");
        
        Scene popupScene = new Scene(popupContent, 400, 200);
        popupStage.setScene(popupScene);
        popupStage.setTitle("Warning");
        popupStage.show();
    }
    
    private void apiCallErrorPopUp(Stage ownerStage) {
        Stage popupStage = new Stage();
        popupStage.initModality(Modality.WINDOW_MODAL);
        popupStage.initOwner(ownerStage);
        
        Label label = new Label("The Location you have entered cannot be found");
        
        Button close_button = new Button("Close");
        close_button.setPrefSize(80, 28);
        close_button.setOnAction(event -> popupStage.close());
        
        VBox popupContent = new VBox(label, close_button);
        popupContent.setSpacing(20);
        popupContent.setStyle("-fx-padding: 10; -fx-alignment: center;");
        
        Scene popupScene = new Scene(popupContent, 400, 200);
        popupStage.setScene(popupScene);
        popupStage.setTitle("Warning");
        popupStage.show();
    }
    
    private static void removeItemFromFavourites(ArrayList<String> favourite_locations, String item_to_remove) {
        Iterator<String> iterator = favourite_locations.iterator();
        while(iterator.hasNext()) {
            String current_item = iterator.next();
            if (current_item.equals(item_to_remove)) {
                iterator.remove();
            }
        }
    }
}