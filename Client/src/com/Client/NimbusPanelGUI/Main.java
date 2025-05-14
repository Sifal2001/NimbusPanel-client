package com.Client.NimbusPanelGUI;

import java.io.IOException;
import java.net.URISyntaxException;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import com.Client.CoordResponse.Location;
import com.Client.CoordResponse.Location.ApiException;
import com.Client.WeatherCast.WeatherCastController.weatherCallException;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.BackgroundPosition;
import javafx.scene.layout.BackgroundRepeat;
import javafx.scene.layout.BackgroundSize;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.TextAlignment;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;


public class Main extends Application {

	private Stage primaryStage;
	
	public static String location;
	
	
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
	
	ArrayList <String> favourite_locations = new ArrayList<>();

	public Label[] hourly_temp_label = new Label[24];
	public Label[] hours_label = new Label[24];
	public Label[] daily_date_label = new Label[5];
	public Label[] daily_temp_label = new Label[5];
	public Label[] daily_humidity_label = new Label[5];
	
	
	
	@Override
	public void start(Stage primaryStage) {
		
    	favourite_locations.add("London");
    	favourite_locations.add("Bejaia");
    	favourite_locations.add("Miami");
    	favourite_locations.add("testestestestest");
		
        Image icon = new Image("file:///C:/Users/Usuario/Desktop/Sifal/WeatherApp/Client/img/app-logo-32.png");
        primaryStage.getIcons().add(icon);
    	
		this.primaryStage = primaryStage;	
        primaryStage.setScene(createLandingScene());
        primaryStage.setTitle("NimbusPanel");
        primaryStage.show();
	}
	
    private Scene createLandingScene() {
    	
        Image search_icon = new Image("file:///C:/Users/Usuario/Desktop/Sifal/WeatherApp/Client/img/search-50.png");
        ImageView search_imageView = new ImageView(search_icon);
        search_imageView.setFitWidth(28);
        search_imageView.setFitHeight(28);
		
        Image app_logo = new Image("file:///C:/Users/Usuario/Desktop/Sifal/WeatherApp/Client/img/app-logo.png");
        ImageView app_logo_view = new ImageView(app_logo);
        if (app_logo.isError()) {
            System.out.println("Error loading image: " + app_logo.getException());
        }

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
                new BackgroundSize(
                        100,
                        100,
                        true,
                        true,
                        false,
                        true
                )
        );
        Background landing_background = new Background(landing_bg_image);
     
        HBox search_hbox = new HBox(-46);
        search_hbox.getChildren().addAll(nameField, searchButton);
        search_hbox.setAlignment(Pos.CENTER);
        
        VBox request_hbox = new VBox(60);
        request_hbox.setPrefSize(1080, 450);
        VBox.setMargin(app_logo_view, new Insets(60, 0, 0, 0));
        request_hbox.getChildren().addAll(app_logo_view ,search_hbox);
        request_hbox.setAlignment(Pos.TOP_CENTER);  
 
        VBox landing_vbox = new VBox();
        landing_vbox.setBackground(landing_background);
        landing_vbox.setPrefSize(1200, 680);
        landing_vbox.getChildren().addAll(request_hbox, createFavouritesHbox());
    
        searchButton.setOnAction(event ->  {	
        	try {
	    		location = capitalizeFirstLetter(nameField.getText());
	            Location.GetLocation(new String[]{}, formatLocationInput(location));
	            primaryStage.setScene(createWeatherScene());
        	} catch(ApiException| weatherCallException | IOException ex) {
        		apiCallErrorPopUp(primaryStage);
			} catch (URISyntaxException e) {
				// TODO Auto-generated catch block
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
		
        for (String favourite: favourite_locations) {
        	
        	Image delete_icon = new Image("file:///C:/Users/Usuario/Desktop/Sifal/WeatherApp/Client/img/delete-24.png");
        	ImageView delete_icon_imageView = new ImageView(delete_icon);
        	delete_icon_imageView.setFitHeight(24);
        	delete_icon_imageView.setFitWidth(24);
        	
        	Button favourite_button = new Button(favourite);
			favourite_button.setPrefWidth(160);
        	favourite_button.setMaxHeight(220);
        	favourite_button.setWrapText(true);
        	favourite_button.setAlignment(Pos.CENTER);
        	favourite_button.setTextAlignment(TextAlignment.CENTER);
        	favourite_button.setStyle("-fx-background-color: rgba(178, 190, 181, 0.8); -fx-background-radius: 10; -fx-cursor: hand; -fx-font-size:28px; -fx-text-fill: white; -fx-font-weight: bold");
        	
        	
            // Inner button
            Button delete_favourite_button = new Button();
            delete_favourite_button.setGraphic(delete_icon_imageView);
            delete_favourite_button.setStyle("-fx-background-color: rgba(178, 190, 181, 0.8)"); // Style the inner button
            delete_favourite_button.setPrefSize(28, 28);
            delete_favourite_button.setLayoutX(favourite_button.getPrefWidth() - delete_favourite_button.getPrefWidth());
            delete_favourite_button.setLayoutY(favourite_button.getPrefHeight() - delete_favourite_button.getPrefHeight());
            
            StackPane stackPane = new StackPane();
            stackPane.getChildren().addAll(favourite_button, delete_favourite_button);
            StackPane.setAlignment(delete_favourite_button, javafx.geometry.Pos.BOTTOM_RIGHT);
        	
            favourite_hbox.getChildren().add(stackPane);
            
        	favourite_button.setOnAction(event->{        	
                try {
                	location = capitalizeFirstLetter(favourite_button.getText());
					Location.GetLocation(new String[]{}, formatLocationInput(location));
					primaryStage.setScene(createWeatherScene());
				} catch (ApiException| weatherCallException |IOException ex) {
					apiCallErrorPopUp(primaryStage);
				} catch (URISyntaxException ex) {
					// TODO Auto-generated catch block
					ex.printStackTrace();
				}
            	
        		
        	});
        	
        	delete_favourite_button.setOnAction(e->{
        		createDeleteConfirmationPopUp(primaryStage, favourite);
        		primaryStage.setScene(createLandingScene());
        	});
        	}
        	
        
        HBox favourites_container = new HBox();
        favourites_container.setMinSize(1040, 220); 
        favourites_container.setSpacing(12);
        favourites_container.setAlignment(Pos.CENTER);
        favourites_container.getChildren().addAll(favourites_scrollPane, add_favourites_hbox);
        
        add_favourite_button.setOnAction(event->{
        	createFavouriteScene(primaryStage);
        });
        
    	return favourites_container;
    }
    
 
    private Scene createWeatherScene() {

        Image background_img = new Image("file:///C:/Users/Usuario/Desktop/Sifal/WeatherApp/Client/img/bg-cut.jpg");
        BackgroundImage backgroundImage = new BackgroundImage(
        		background_img,
                BackgroundRepeat.NO_REPEAT,
                BackgroundRepeat.NO_REPEAT,
                BackgroundPosition.CENTER,
                new BackgroundSize(
                        100,
                        100,
                        true,
                        true,
                        false,
                        true
                )
        );
        Background background = new Background(backgroundImage);
        
		ScrollPane hourly_scroll_pane = new ScrollPane();
		hourly_scroll_pane.setContent(createHourlyHbox());
		hourly_scroll_pane.setFitToWidth(true);
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
    
    
    /* DAILY GRIDPANE
     * create a grid to display 5 day forecast
     * 
     */
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
        
        HBox daily_cast_hbox = new HBox();
        daily_cast_hbox.getChildren().addAll(daily_cast_grid);
        daily_cast_hbox.setAlignment(Pos.TOP_LEFT);
        
        for (int i = 0; i < 5; i++) {
        	
			daily_temp_label[i] = new Label(String.valueOf((int) Math.round(daily_temp[i+1])) + "°c");
			daily_temp_label[i].setStyle("-fx-font-family: 'Arial'; -fx-font-size: 36px; -fx-text-fill: white;");
			daily_temp_label[i].setPrefWidth(120);
			
			daily_humidity_label[i] = new Label(String.valueOf((int) Math.round(daily_humidity[i+1])) + "%");
			daily_humidity_label[i].setStyle("-fx-font-family: 'Arial'; -fx-font-size: 16px; -fx-text-fill: white;");
			daily_humidity_label[i].setPrefWidth(120);
			
			daily_date_label[i] = new Label(toDate(days[i+1]));
			daily_date_label[i].setStyle("-fx-font-family: 'Arial'; -fx-font-size: 16px; -fx-text-fill: white;");
			daily_date_label[i].setPrefWidth(120);

			daily_cast_grid.add(get_daily_weather_icon(daily_description[i]), 0, i*2);
	        daily_cast_grid.add(daily_temp_label[i], 1, i*2);
	        daily_cast_grid.add(daily_date_label[i], 0, i*2+1);
	        daily_cast_grid.add(daily_humidity_label[i], 1, i*2+1);

	        daily_temp_label[i].setAlignment(Pos.BASELINE_RIGHT);
	        daily_humidity_label[i].setAlignment(Pos.BASELINE_RIGHT);
		} 
        
        backButton.setOnAction(event -> {
            primaryStage.setScene(createLandingScene());
            
        });
    	return daily_cast_grid;
    }
    
    
    /* CURRENT GRIDPANE
     * create a grid to display in-time data about the weather 
     * split into four boxes each displaying corresponding data 
     */
    private GridPane createCurrentGrid() {
    	
        Image sunset_icon = new Image("file:///C:/Users/Usuario/Desktop/Sifal/WeatherApp/Client/img/sunset.png");
        ImageView sunset_imageView = new ImageView(sunset_icon);
        sunset_imageView.setFitWidth(40);
        sunset_imageView.setFitHeight(40);
        
        Image sunrise_icon = new Image("file:///C:/Users/Usuario/Desktop/Sifal/WeatherApp/Client/img/sunrise.png");
        ImageView sunrise_imageView = new ImageView(sunrise_icon);
        sunrise_imageView.setFitWidth(40);
        sunrise_imageView.setFitHeight(40);
    	
        Label location_name = new Label(location);
        location_name.setStyle("-fx-font-family: 'Arial'; -fx-font-size: 80px; -fx-text-fill: white; -fx-font-weight: bold");
        
        Label current_temp_label = new Label(String.valueOf((int) Math.round(current_temp)) + "°c");
        current_temp_label.setStyle("-fx-font-family: 'Arial'; -fx-font-size: 48px; -fx-text-fill: white; -fx-font-weight: bold");  
        
    	Label current_description_label = new Label(current_description);
        current_description_label.setStyle("-fx-font-family: 'Arial'; -fx-font-size: 48px; -fx-text-fill: white; -fx-font-weight: bold");
        
        Label current_sunrise_label = new Label(toHours(current_sunrise_timestamp));
        current_sunrise_label.setStyle("-fx-font-family: 'Arial'; -fx-font-size: 24px; -fx-text-fill: white");
        
        Label current_sunset_label = new Label(toHours(current_sunset_timestamp));
        current_sunset_label.setStyle("-fx-font-family: 'Arial'; -fx-font-size: 24px; -fx-text-fill: white");
        
        Label current_time_label = new Label(toHours(current_time));
        current_time_label.setStyle("-fx-font-family: 'Arial'; -fx-font-size: 28px; -fx-text-fill: white");
        
        Label current_humidity_label = new Label("Humidity: " + String.valueOf((int) Math.round(current_humidity)) + "%");
        current_humidity_label.setStyle("-fx-font-family: 'Arial'; -fx-font-size: 24px; -fx-text-fill: white");
        
        Label current_feelslike_label = new Label("Feels like: " + String.valueOf((int) Math.round(current_feelslike)) + "°c");
        current_feelslike_label.setStyle("-fx-font-family: 'Arial'; -fx-font-size: 24px; -fx-text-fill: white");
        
        Label current_windspeed_label = new Label("Wind speed: " + String.valueOf((int) Math.round(current_windspeed)) + "km/h");
        current_windspeed_label.setStyle("-fx-font-family: 'Arial'; -fx-font-size: 24px; -fx-text-fill: white");
        
		HBox sunset_hbox = new HBox();
		sunset_hbox.getChildren().addAll(current_sunset_label, sunset_imageView);
		sunset_hbox.setAlignment(Pos.BOTTOM_RIGHT);
		
		HBox sunrise_hbox = new HBox();
		sunrise_hbox.getChildren().addAll(current_sunrise_label, sunrise_imageView);
		sunrise_hbox.setAlignment(Pos.BOTTOM_RIGHT);
    	
        VBox top_left_hbox = new VBox(get_current_weather_icon(current_description), 
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
        bottom_right_hbox.setStyle("-fx-font-color: black");
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
    
    
    /* HOURLY HBOX
     * create an HBox containing hourly weather VBoxs
     * displaying time, icon, temp
     */
	private HBox createHourlyHbox() {
        HBox hourly_cast_hbox = new HBox();
        hourly_cast_hbox.setPadding(new Insets(8));
        hourly_cast_hbox.setMaxHeight(220);
        hourly_cast_hbox.setSpacing(16);
		
        for(int i = 0; i < 24; i++) {     
	        hourly_temp_label[i] = new Label(String.valueOf((int) Math.round(hourly_temp[i])) + "°c");
	        hourly_temp_label[i].setStyle("-fx-font-size: 40px; -fx-text-fill: white");
	        
	        
	        hours_label[i] = new Label(toHours(hours[i]));
	        hours_label[i].setStyle("-fx-font-size: 24px; -fx-text-fill: white");
	        
        	VBox hourly_Vbox = new VBox();
        	hourly_Vbox.setMinWidth(100);
        	hourly_Vbox.setMinHeight(180);
        	hourly_Vbox.setAlignment(Pos.CENTER);
        	hourly_Vbox.setStyle("-fx-background-color: rgba(178, 190, 181, 0.8); -fx-background-radius: 10");
        	hourly_Vbox.getChildren().addAll(hours_label[i], get_daily_weather_icon(hourly_description[i]), hourly_temp_label[i]);
        	hourly_cast_hbox.getChildren().addAll(hourly_Vbox);   	
        } 
        return hourly_cast_hbox;
	}
	
    private void createFavouriteScene(Stage ownerStage) {
    	
        // Create a new stage for the pop-up
        Stage popupStage = new Stage();
        
        Image icon = new Image("file:///C:/Users/Usuario/Desktop/Sifal/WeatherApp/Client/img/plus-button-16.png");
        popupStage.getIcons().add(icon);

        popupStage.initModality(Modality.WINDOW_MODAL);
        popupStage.initOwner(ownerStage);

        // Create content for the pop-up
        Label label = new Label("Enter a location to add");
        
        TextField location_text_field = new TextField();
        
        Button close_button = new Button("Close");
        close_button.setOnAction(event -> popupStage.close());
        
        Button add_button = new Button("Add");
        add_button.setOnAction(event -> {     
        	favourite_locations.add(capitalizeFirstLetter(location_text_field.getText()));
        	popupStage.close();
        	primaryStage.setScene(createLandingScene());       	
        });
        
        HBox popup_buttons = new HBox(close_button, add_button);
        popup_buttons.setSpacing(100);
        popup_buttons.setAlignment(Pos.CENTER);
        
        VBox popupContent = new VBox(label, location_text_field, popup_buttons);
        popupContent.setSpacing(20);
        popupContent.setStyle("-fx-padding: 10; -fx-alignment: center;");        

        Scene popupScene = new Scene(popupContent, 400, 200);
        popupStage.setScene(popupScene);

        // Set the title and show the pop-up
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
        close_button.setOnAction(event -> 
        	popupStage.close()
        );
        
        Button confirm_button = new Button("Confirm");
        confirm_button.setOnAction(event -> {     
        	removeItemFromFavourites(favourite_locations, favourite_location);
        	popupStage.close();
        	primaryStage.setScene(createLandingScene());       	
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
        close_button.setOnAction(event -> 
        	popupStage.close()
        );
        
        Button confirm_button = new Button("Confirm");
        confirm_button.setOnAction(event -> {     
        	popupStage.close();
        	primaryStage.setScene(createLandingScene());       	
        });
        
        VBox popupContent = new VBox(label, close_button);
        popupContent.setSpacing(20);
        popupContent.setStyle("-fx-padding: 10; -fx-alignment: center;");        

        Scene popupScene = new Scene(popupContent, 400, 200);
        popupStage.setScene(popupScene);

        popupStage.setTitle("Warning");
        popupStage.show();
    }
	
	
	/*Converting time stamp to printable string 
	 * 
	 */
	private String toHours(long timestamp) {
        LocalDateTime dateTime = LocalDateTime.ofInstant(Instant.ofEpochSecond(timestamp), ZoneOffset.UTC);

        int hours = dateTime.getHour();
        int minutes = dateTime.getMinute();
        String time = String.format("%02d:%02d", hours, minutes); 
        System.out.println("LocalDateTime: " + dateTime);
        
        return time;	
	}
	
	private String toDate(long timestamp) {
        LocalDateTime dateTime = LocalDateTime.ofInstant(Instant.ofEpochSecond(timestamp), ZoneOffset.UTC);

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("EEEE dd");
        
        String time = dateTime.format(formatter); 
        
        System.out.println("Day: " + time);
        
        return time;	
	}
	
	private static final Map<String, String> weather_icon_map = new HashMap<>();
	
	static{
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
		
	private ImageView get_current_weather_icon(String current_description) {
		String weather_icon_path = weather_icon_map.getOrDefault(current_description.toLowerCase(), "file:///C:/Users/Usuario/Desktop/Sifal/WeatherApp/Client/img/image-not-available-96.png");
		Image weather_icon = new Image(weather_icon_path);
		ImageView imageView = new ImageView(weather_icon);
        imageView.setFitWidth(96);
        imageView.setFitHeight(96); 
		return imageView;
	}
	
	private ImageView get_daily_weather_icon(String daily_description) {
		String weather_icon_path = weather_icon_map.getOrDefault(daily_description.toLowerCase(), "file:///C:/Users/Usuario/Desktop/Sifal/WeatherApp/Client/img/image-not-available-96.png");
		Image weather_icon = new Image(weather_icon_path);
		ImageView imageView = new ImageView(weather_icon);
        imageView.setFitWidth(56);
        imageView.setFitHeight(56); 
		return imageView;
	}
	
	private static String capitalizeFirstLetter(String city) {
		
		if(city == null || city.isEmpty()) {
			return city;
		}
		return city.substring(0,1).toUpperCase() + city.substring(1).toLowerCase();
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
	
	private static String formatLocationInput(String location) {
		
		String formattedLocationInput = location.replaceAll(" ", "&");
		
		return formattedLocationInput;
	}
	
	public static void main(String[] args) {
		launch(args);
	}
}
