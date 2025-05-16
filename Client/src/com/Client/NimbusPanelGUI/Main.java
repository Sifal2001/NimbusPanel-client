package com.Client.NimbusPanelGUI;

import javafx.application.Application;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import java.util.ArrayList;

public class Main extends Application {
    public static String location;
    
    // Weather data static variables
    public static double[] hourly_temp = new double[24];
    public static String[] hourly_description = new String[24];
    public static double[] daily_temp = new double[6];
    public static long[] hours = new long[24];
    public static long[] days = new long[6];
    public static double[] daily_humidity = new double[6];
    public static String[] daily_description = new String[6];
    public static long current_time;
    public static String current_description;
    public static double current_temp;
    public static int current_humidity;
    public static double current_feelslike;
    public static double current_windspeed;
    public static long current_sunrise_timestamp;
    public static long current_sunset_timestamp;
    public static long current_time_timestamp;
    
    public static ArrayList<String> favourite_locations = new ArrayList<>();
    
    @Override
    public void start(Stage primaryStage) {
        // Initialize favorite locations in Main instead of HomePage
        favourite_locations.add("London");
        favourite_locations.add("Bejaia");
        favourite_locations.add("Miami");
        favourite_locations.add("testestestestest");
        
        // Set up the stage
        Image icon = new Image("file:///C:/Users/Usuario/Desktop/Sifal/WeatherApp/Client/img/app-logo-32.png");
        primaryStage.getIcons().add(icon);
        
        // Create HomePage instance and set its scene
        HomePage homePage = new HomePage(primaryStage, favourite_locations);
        primaryStage.setScene(homePage.createScene());
        primaryStage.setTitle("NimbusPanel");
        primaryStage.show();
    }
    
    public static String capitalizeFirstLetter(String city) {
        if(city == null || city.isEmpty()) {
            return city;
        }
        return city.substring(0,1).toUpperCase() + city.substring(1).toLowerCase();
    }
    
    public static String formatLocationInput(String location) {
        return location.replaceAll(" ", "&");
    }
    
    public static void main(String[] args) {
        launch(args);
    }
}