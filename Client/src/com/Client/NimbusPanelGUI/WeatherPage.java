package com.Client.NimbusPanelGUI;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;

public class WeatherPage {
    private Stage primaryStage;
    private Label[] hourly_temp_label = new Label[24];
    private Label[] hours_label = new Label[24];
    private Label[] daily_date_label = new Label[5];
    private Label[] daily_temp_label = new Label[5];
    private Label[] daily_humidity_label = new Label[5];
    
    private static final Map<String, String> weather_icon_map = new HashMap<>();
    
    static {
        weather_icon_map.put("clouds", "file:///C:/Users/Usuario/Desktop/Sifal/WeatherApp/Client/img/cloudy-96.png");
        weather_icon_map.put("clear", "file:///C:/Users/Usuario/Desktop/Sifal/WeatherApp/Client/img/clear-96.png");
        weather_icon_map.put("rain", "file:///C:/Users/Usuario/Desktop/Sifal/WeatherApp/Client/img/rain-96.png");
        weather_icon_map.put("drizzle", "file:///C:/Users/Usuario/Desktop/Sifal/WeatherApp/Client/img/drizzle-96.png");
        weather_icon_map.put("thunderstorm", "file:///C:/Users/Usuario/Desktop/Sifal/WeatherApp/Client/img/thunderstorm-96.png");
        weather_icon_map.put("snow", "file:///C:/Users/Usuario/Desktop/Sifal/WeatherApp/Client/img/snow-96.png");
        weather_icon_map.put("mist", "file:///C:/Users/Usuario/Desktop/Sifal/WeatherApp/Client/img/mist-96.png");
        weather_icon_map.put("fog", "file:///C:/Users/Usuario/Desktop/Sifal/WeatherApp/Client/img/mist-96.png");
        weather_icon_map.put("smoke", "file:///C:/Users/Usuario/Desktop/Sifal/WeatherApp/Client/img/smoke-96.png");
        weather_icon_map.put("haze", "file:///C:/Users/Usuario/Desktop/Sifal/WeatherApp/Client/img/haze-96.png");
        weather_icon_map.put("dust", "file:///C:/Users/Usuario/Desktop/Sifal/WeatherApp/Client/img/dust-96.png");
        weather_icon_map.put("sand", "file:///C:/Users/Usuario/Desktop/Sifal/WeatherApp/Client/img/sand-96.png");
        weather_icon_map.put("ash", "file:///C:/Users/Usuario/Desktop/Sifal/WeatherApp/Client/img/ash-96.png");
        weather_icon_map.put("squall", "file:///C:/Users/Usuario/Desktop/Sifal/WeatherApp/Client/img/tornado-96.png");
        weather_icon_map.put("tornado", "file:///C:/Users/Usuario/Desktop/Sifal/WeatherApp/Client/img/tornado-96.png");
    }
    
    public WeatherPage(Stage primaryStage) {
        this.primaryStage = primaryStage;
    }
    
    public Scene createScene() {
        Image background_img = new Image("file:///C:/Users/Usuario/Desktop/Sifal/WeatherApp/Client/img/bg-cut.jpg");
        BackgroundImage backgroundImage = new BackgroundImage(
            background_img,
            BackgroundRepeat.NO_REPEAT,
            BackgroundRepeat.NO_REPEAT,
            BackgroundPosition.CENTER,
            new BackgroundSize(100, 100, true, true, false, true)
        );
        Background background = new Background(backgroundImage);
        
        ScrollPane hourly_scroll_pane = new ScrollPane();
        hourly_scroll_pane.setContent(createHourlyHbox());
        hourly_scroll_pane.setFitToWidth(true);
        hourly_scroll_pane.setVbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        hourly_scroll_pane.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        hourly_scroll_pane.setMaxHeight(220);
        hourly_scroll_pane.setMaxWidth(1080);
        hourly_scroll_pane.setStyle("-fx-background-color: transparent; -fx-background: transparent");
        
        VBox current_cast_container = new VBox();
        current_cast_container.setBackground(background);
        current_cast_container.getChildren().addAll(createCurrentGrid(), hourly_scroll_pane);
        current_cast_container.setPrefWidth(1080);
        current_cast_container.setPrefHeight(600);
        
        HBox container_hbox = new HBox();
        container_hbox.getChildren().addAll(createDailyGrid(), current_cast_container);
        
        return new Scene(container_hbox, 1200, 680);
    }
    
    private GridPane createDailyGrid() {
        Button backButton = new Button("Back");
        backButton.setStyle("-fx-padding: 8px; -fx-border: none; -fx-background-color:black; -fx-text-fill:white; -fx-font-size: 36px; -fx-padding-bottom:16px");
        backButton.setPrefWidth(220);
        
        GridPane daily_cast_grid = new GridPane();
        daily_cast_grid.setPrefWidth(364);
        daily_cast_grid.setStyle("-fx-background-color: black;");
        daily_cast_grid.setPadding(new Insets(8));
        daily_cast_grid.setHgap(16);
        daily_cast_grid.setVgap(20);
        daily_cast_grid.add(backButton, 0, 11, 2, 1);
        
        for (int i = 0; i < 5; i++) {
            daily_temp_label[i] = new Label(String.valueOf((int) Math.round(Main.daily_temp[i+1])) + "°c");
            daily_temp_label[i].setStyle("-fx-font-family: 'Arial'; -fx-font-size: 36px; -fx-text-fill: white;");
            daily_temp_label[i].setPrefWidth(120);
            
            daily_humidity_label[i] = new Label(String.valueOf((int) Math.round(Main.daily_humidity[i+1])) + "%");
            daily_humidity_label[i].setStyle("-fx-font-family: 'Arial'; -fx-font-size: 16px; -fx-text-fill: white;");
            daily_humidity_label[i].setPrefWidth(120);
            
            daily_date_label[i] = new Label(toDate(Main.days[i+1]));
            daily_date_label[i].setStyle("-fx-font-family: 'Arial'; -fx-font-size: 16px; -fx-text-fill: white;");
            daily_date_label[i].setPrefWidth(120);
            
            daily_cast_grid.add(get_daily_weather_icon(Main.daily_description[i]), 0, i*2);
            daily_cast_grid.add(daily_temp_label[i], 1, i*2);
            daily_cast_grid.add(daily_date_label[i], 0, i*2+1);
            daily_cast_grid.add(daily_humidity_label[i], 1, i*2+1);
            
            daily_temp_label[i].setAlignment(Pos.BASELINE_RIGHT);
            daily_humidity_label[i].setAlignment(Pos.BASELINE_RIGHT);
        }
        
        backButton.setOnAction(event -> {
            HomePage homePage = new HomePage(primaryStage, Main.favourite_locations);
            primaryStage.setScene(homePage.createScene());
        });
        
        return daily_cast_grid;
    }
    
    private GridPane createCurrentGrid() {
        Image sunset_icon = new Image("file:///C:/Users/Usuario/Desktop/Sifal/WeatherApp/Client/img/sunset.png");
        ImageView sunset_imageView = new ImageView(sunset_icon);
        sunset_imageView.setFitWidth(40);
        sunset_imageView.setFitHeight(40);
        
        Image sunrise_icon = new Image("file:///C:/Users/Usuario/Desktop/Sifal/WeatherApp/Client/img/sunrise.png");
        ImageView sunrise_imageView = new ImageView(sunrise_icon);
        sunrise_imageView.setFitWidth(40);
        sunrise_imageView.setFitHeight(40);
        
        Label location_name = new Label(Main.location);
        location_name.setStyle("-fx-font-family: 'Arial'; -fx-font-size: 80px; -fx-text-fill: white; -fx-font-weight: bold");
        
        Label current_temp_label = new Label(String.valueOf((int) Math.round(Main.current_temp)) + "°c");
        current_temp_label.setStyle("-fx-font-family: 'Arial'; -fx-font-size: 48px; -fx-text-fill: white; -fx-font-weight: bold");
        
        Label current_description_label = new Label(Main.current_description);
        current_description_label.setStyle("-fx-font-family: 'Arial'; -fx-font-size: 48px; -fx-text-fill: white; -fx-font-weight: bold");
        
        Label current_sunrise_label = new Label(toHours(Main.current_sunrise_timestamp));
        current_sunrise_label.setStyle("-fx-font-family: 'Arial'; -fx-font-size: 24px; -fx-text-fill: white");
        
        Label current_sunset_label = new Label(toHours(Main.current_sunset_timestamp));
        current_sunset_label.setStyle("-fx-font-family: 'Arial'; -fx-font-size: 24px; -fx-text-fill: white");
        
        Label current_time_label = new Label(toHours(Main.current_time));
        current_time_label.setStyle("-fx-font-family: 'Arial'; -fx-font-size: 28px; -fx-text-fill: white");
        
        Label current_humidity_label = new Label("Humidity: " + String.valueOf((int) Math.round(Main.current_humidity)) + "%");
        current_humidity_label.setStyle("-fx-font-family: 'Arial'; -fx-font-size: 24px; -fx-text-fill: white");
        
        Label current_feelslike_label = new Label("Feels like: " + String.valueOf((int) Math.round(Main.current_feelslike)) + "°c");
        current_feelslike_label.setStyle("-fx-font-family: 'Arial'; -fx-font-size: 24px; -fx-text-fill: white");
        
        Label current_windspeed_label = new Label("Wind speed: " + String.valueOf((int) Math.round(Main.current_windspeed)) + "km/h");
        current_windspeed_label.setStyle("-fx-font-family: 'Arial'; -fx-font-size: 24px; -fx-text-fill: white");
        
        HBox sunset_hbox = new HBox();
        sunset_hbox.getChildren().addAll(current_sunset_label, sunset_imageView);
        sunset_hbox.setAlignment(Pos.BOTTOM_RIGHT);
        
        HBox sunrise_hbox = new HBox();
        sunrise_hbox.getChildren().addAll(current_sunrise_label, sunrise_imageView);
        sunrise_hbox.setAlignment(Pos.BOTTOM_RIGHT);
        
        VBox top_left_hbox = new VBox(get_current_weather_icon(Main.current_description),
            current_description_label, current_temp_label);
        top_left_hbox.setPrefSize(530, 250);
        top_left_hbox.setAlignment(Pos.TOP_LEFT);
        
        VBox top_right_hbox = new VBox();
        top_right_hbox.setPrefSize(530, 250);
        top_right_hbox.getChildren().addAll(location_name, current_time_label);
        top_right_hbox.setAlignment(Pos.TOP_RIGHT);
        
        VBox bottom_left_hbox = new VBox(8);
        bottom_left_hbox.setPrefSize(530, 250);
        bottom_left_hbox.getChildren().addAll(current_feelslike_label, current_humidity_label, current_windspeed_label);
        bottom_left_hbox.setAlignment(Pos.BOTTOM_LEFT);
        
        VBox bottom_right_hbox = new VBox();
        bottom_right_hbox.setPrefSize(530, 250);
        bottom_right_hbox.getChildren().addAll(sunrise_hbox, sunset_hbox);
        bottom_right_hbox.setAlignment(Pos.BOTTOM_RIGHT);
        
        GridPane current_cast_grid = new GridPane();
        current_cast_grid.setPrefSize(1080, 482);
        current_cast_grid.setPadding(new Insets(8));
        
        current_cast_grid.add(top_left_hbox, 0, 0);
        current_cast_grid.add(top_right_hbox, 1, 0);
        current_cast_grid.add(bottom_left_hbox, 0, 1);
        current_cast_grid.add(bottom_right_hbox, 1, 1);
        
        return current_cast_grid;
    }
    
    private HBox createHourlyHbox() {
        HBox hourly_cast_hbox = new HBox();
        hourly_cast_hbox.setPadding(new Insets(8));
        hourly_cast_hbox.setMaxHeight(220);
        hourly_cast_hbox.setSpacing(16);
        
        for(int i = 0; i < 24; i++) {
            hourly_temp_label[i] = new Label(String.valueOf((int) Math.round(Main.hourly_temp[i])) + "°c");
            hourly_temp_label[i].setStyle("-fx-font-size: 40px; -fx-text-fill: white");
            
            hours_label[i] = new Label(toHours(Main.hours[i]));
            hours_label[i].setStyle("-fx-font-size: 24px; -fx-text-fill: white");
            
            VBox hourly_Vbox = new VBox();
            hourly_Vbox.setMinWidth(100);
            hourly_Vbox.setMinHeight(180);
            hourly_Vbox.setAlignment(Pos.CENTER);
            hourly_Vbox.setStyle("-fx-background-color: rgba(178, 190, 181, 0.8); -fx-background-radius: 10");
            hourly_Vbox.getChildren().addAll(hours_label[i], get_daily_weather_icon(Main.hourly_description[i]), hourly_temp_label[i]);
            hourly_cast_hbox.getChildren().addAll(hourly_Vbox);
        }
        return hourly_cast_hbox;
    }
    
    private ImageView get_current_weather_icon(String current_description) {
        String weather_icon_path = weather_icon_map.getOrDefault(current_description.toLowerCase(), 
            "file:///C:/Users/Usuario/Desktop/Sifal/WeatherApp/Client/img/image-not-available-96.png");
        Image weather_icon = new Image(weather_icon_path);
        ImageView imageView = new ImageView(weather_icon);
        imageView.setFitWidth(96);
        imageView.setFitHeight(96);
        return imageView;
    }
    
    private ImageView get_daily_weather_icon(String daily_description) {
        String weather_icon_path = weather_icon_map.getOrDefault(daily_description.toLowerCase(), 
            "file:///C:/Users/Usuario/Desktop/Sifal/WeatherApp/Client/img/image-not-available-96.png");
        Image weather_icon = new Image(weather_icon_path);
        ImageView imageView = new ImageView(weather_icon);
        imageView.setFitWidth(56);
        imageView.setFitHeight(56);
        return imageView;
    }
    
    private String toHours(long timestamp) {
        LocalDateTime dateTime = LocalDateTime.ofInstant(Instant.ofEpochSecond(timestamp), ZoneOffset.UTC);
        int hours = dateTime.getHour();
        int minutes = dateTime.getMinute();
        String time = String.format("%02d:%02d", hours, minutes);
        return time;
    }
    
    private String toDate(long timestamp) {
        LocalDateTime dateTime = LocalDateTime.ofInstant(Instant.ofEpochSecond(timestamp), ZoneOffset.UTC);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("EEEE dd");
        String time = dateTime.format(formatter);
        return time;
    }
}