package com.Client.NimbusPanelGUI;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import com.Client.User.User;

public class Main extends Application {
	
	public static User currentUser;
    public static String location;
    public static int timeZoneOffset;
    
    @Override
    public void start(Stage primaryStage) {
    	UiComponents.loadFonts();
    	
    	primaryStage.setResizable(false);
        Image icon = UiComponents.loadImage("img/app-logo-32.png");
        primaryStage.getIcons().add(icon);
        
        LoginPage loginPage = new LoginPage(primaryStage);
        primaryStage.setScene(loginPage.createScene());
        primaryStage.setTitle("NimbusPanel");
        primaryStage.setMinWidth(900);
        primaryStage.setMinHeight(700);
        primaryStage.show();
    }
    
    public static Scene styled(Scene scene) {
    	scene.getStylesheets().add(Main.class.getResource("application.css").toExternalForm());
        return scene;
    }
    
    public static String capitalizeFirstLetter(String city) {
        if(city == null || city.isEmpty()) {
            return city;
        }
        return city.substring(0,1).toUpperCase() + city.substring(1).toLowerCase();
    }
    
    public static String formatLocationInput(String location) {
        return java.net.URLEncoder.encode(location, java.nio.charset.StandardCharsets.UTF_8);
    }
    
    public static void main(String[] args) {
        launch(args);
    }
}