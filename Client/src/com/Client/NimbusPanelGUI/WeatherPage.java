package com.Client.NimbusPanelGUI;

import com.Client.WeatherCast.WeatherCast;
import com.Client.WeatherCast.CurrentCast;
import com.Client.WeatherCast.DailyCast;
import com.Client.WeatherCast.HourlyCast;
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
import java.util.List;
import java.util.Map;

public class WeatherPage {
	private Stage primaryStage;
	private WeatherCast weatherCast;
	private Label[] hourlyTempLabels = new Label[24];
	private Label[] hoursLabels = new Label[24];
	private Label[] dailyDateLabels = new Label[5];
	private Label[] dailyTempLabels = new Label[5];
	private Label[] dailyHumidityLabels = new Label[5];

	private static final Map<String, String> WEATHER_ICON_MAP = new HashMap<>();

	static {
		WEATHER_ICON_MAP.put("clouds", "file:img/cloudy-96.png");
		WEATHER_ICON_MAP.put("clear", "file:img/clear-124.png");
		WEATHER_ICON_MAP.put("rain", "file:img/rain-96.png");
		WEATHER_ICON_MAP.put("drizzle", "file:img/drizzle-96.png");
		WEATHER_ICON_MAP.put("thunderstorm", "file:img/thunderstorm-96.png");
		WEATHER_ICON_MAP.put("snow", "file:img/snow-96.png");
		WEATHER_ICON_MAP.put("mist", "file:img/mist-96.png");
		WEATHER_ICON_MAP.put("fog", "file:img/mist-96.png");
		WEATHER_ICON_MAP.put("smoke", "file:img/smoke-96.png");
		WEATHER_ICON_MAP.put("haze", "file:img/haze-96.png");
		WEATHER_ICON_MAP.put("dust", "file:img/dust-96.png");
		WEATHER_ICON_MAP.put("sand", "file:img/sand-96.png");
		WEATHER_ICON_MAP.put("ash", "file:img/ash-96.png");
		WEATHER_ICON_MAP.put("squall", "file:img/tornado-96.png");
		WEATHER_ICON_MAP.put("tornado", "file:img/tornado-96.png");
	}

	public WeatherPage(Stage primaryStage, WeatherCast weatherCast) {
		this.primaryStage = primaryStage;
		this.weatherCast = weatherCast;
	}

	public Scene createScene() {
		ScrollPane hourlyScrollPane = new ScrollPane();
		hourlyScrollPane.setContent(createHourlyHBox());
		//hourlyScrollPane.setFitToWidth(true);
		hourlyScrollPane.setVbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
		hourlyScrollPane.setHbarPolicy(ScrollPane.ScrollBarPolicy.ALWAYS);
		hourlyScrollPane.setMinHeight(164);
		hourlyScrollPane.setMaxHeight(164);
		hourlyScrollPane.setMaxWidth(1080);
		hourlyScrollPane.setStyle("-fx-background-color: transparent; -fx-background: transparent");
		

		VBox currentWeatherPanel = new VBox();
		// currentWeatherPanel.setBackground(background);
		currentWeatherPanel.getChildren().addAll(createCurrentGrid(), hourlyScrollPane);
		currentWeatherPanel
				.setStyle("-fx-background-color: " + "linear-gradient(to left, #0c2747 0%, #123150 85%, #133454 100%);"
						+ "-fx-border-color: rgba(255,255,255,0.15);" + "-fx-border-width: 0;");
		currentWeatherPanel.setPrefWidth(920);
		currentWeatherPanel.setPrefHeight(660);
		currentWeatherPanel.setPadding(new Insets(0, 20, 0, 20));
		

		Region divider = new Region();
		divider.setMinWidth(3);
		divider.setMaxWidth(3);
		divider.setPrefHeight(640);
		divider.setMaxHeight(640);
		divider.setStyle("-fx-background-color: rgb(227, 227, 227, 0.3); -fx-background-radius: 3");

		VBox dividerWrap = new VBox(divider);
		dividerWrap.setAlignment(Pos.CENTER);
		dividerWrap.setStyle("-fx-background-color: #133454;");

		HBox rootLayout = new HBox();
		rootLayout.getChildren().addAll(createDailyGrid(), dividerWrap, currentWeatherPanel);

		return Main.styled(new Scene(rootLayout, 1200, 660));
	}

	private GridPane createDailyGrid() {
		Button backButton = new Button("Back");
		backButton.setStyle(
				"-fx-padding: 8px; -fx-background-color: rgb(227, 227, 227, 0.3); -fx-text-fill:white; -fx-font-size: 26px;");
		backButton.setPrefWidth(380);

		GridPane dailyCastGrid = new GridPane();
		dailyCastGrid.setPrefWidth(364);
		// dailyCastGrid.setStyle("-fx-background-color: linear-gradient(to left,
		// #192f61 0%, #1e3569 80%, #002375 100%)");
		dailyCastGrid.setStyle(
				"-fx-background-color: " + "linear-gradient(to left, #133454 0%, #15385a 85%, #15395b 100%);");
		dailyCastGrid.setPadding(new Insets(8));
		dailyCastGrid.setHgap(60);
		dailyCastGrid.setVgap(20);
		
		dailyCastGrid.add(backButton, 0, 10, 2, 1);
		GridPane.setMargin(backButton, new Insets(-18, 0, 0, 0));
		
		List<DailyCast> daily = weatherCast.getDaily();
		for (int i = 0; i < 5; i++) {
			DailyCast day = daily.get(i + 1);

			dailyTempLabels[i] = createWhiteLabel(Math.round(day.getTemp().getDay()) + "°", 26, false);
			dailyTempLabels[i].setPrefWidth(120);

			dailyHumidityLabels[i] = createWhiteLabel(day.getHumidity() + "%", 14, false);
			dailyHumidityLabels[i].setPrefWidth(120);

			dailyDateLabels[i] = createWhiteLabel(toDate(day.getDt()), 16, false);
			dailyDateLabels[i].setPrefWidth(120);

			dailyCastGrid.add(createWeatherIcon(day.getDescription(), 56), 0, i * 2);
			dailyCastGrid.add(dailyTempLabels[i], 1, i * 2);
			dailyCastGrid.add(dailyDateLabels[i], 0, i * 2 + 1);
			dailyCastGrid.add(dailyHumidityLabels[i], 1, i * 2 + 1);

			dailyTempLabels[i].setAlignment(Pos.BASELINE_RIGHT);
			dailyHumidityLabels[i].setAlignment(Pos.BASELINE_RIGHT);
		}

		backButton.setOnAction(event -> {
			HomePage homePage = new HomePage(primaryStage);
			primaryStage.setScene(homePage.createScene());
		});

		return dailyCastGrid;
	}

	private GridPane createCurrentGrid() {

		Image sunsetIcon = new Image("file:img/sunset.png");
		ImageView sunsetImageView = new ImageView(sunsetIcon);
		sunsetImageView.setFitWidth(40);
		sunsetImageView.setFitHeight(40);

		Image sunriseIcon = new Image("file:img/sunrise.png");
		ImageView sunriseImageView = new ImageView(sunriseIcon);
		sunriseImageView.setFitWidth(40);
		sunriseImageView.setFitHeight(40);
		
		Image locationIcon = new Image("file:img/pin-70.png");
		ImageView locationImageView = new ImageView(locationIcon);
		locationImageView.setFitWidth(48);
		locationImageView.setFitHeight(48);

		CurrentCast current = weatherCast.getCurrent();
		Label locationName = createWhiteLabel(Main.location, 60, true);
		

		Label currentDescriptionLabel = createWhiteLabel(current.getDescription(), 38, true);
		Label currentSunriseLabel = createWhiteLabel(toHours(current.getSunrise()), 18, false);
		Label currentSunsetLabel = createWhiteLabel(toHours(current.getSunset()), 18, false);
		Label currentTimeLabel = createWhiteLabel(toHours(current.getDt()), 20, false);
		
		Label currentHumidityLabel = createWhiteLabel("Humidity", 100, false);
		currentHumidityLabel.setStyle(currentHumidityLabel.getStyle() + " -fx-font-family: 'Outfit '; -fx-font-size: 18px;");
		Label currentHumidity = createWhiteLabel(Math.round(current.getHumidity()) + "%", 18,
				true);
		currentHumidity.setStyle(currentHumidity.getStyle() + " -fx-font-family: 'Outfit Regular '; -fx-font-size: 18px;");
		
		Label currentFeelslikeLabel = createWhiteLabel("Feels like", 100, false);
		currentFeelslikeLabel.setStyle(currentFeelslikeLabel.getStyle() + " -fx-font-family: 'Outfit '; -fx-font-size: 18px;");
		Label currentFeelslike = createWhiteLabel( Math.round(current.getFeels_like()) + "°", 18,
				true);
		currentFeelslike.setStyle(currentFeelslike.getStyle() + " -fx-font-family: 'Outfit Regular '; -fx-font-size: 18px;");
		
		Label currentWindspeedLabel = createWhiteLabel("Wind speed", 100, false);
		currentWindspeedLabel.setStyle(currentWindspeedLabel.getStyle() + " -fx-font-family: 'Outfit '; -fx-font-size: 18px;");
		Label currentWindspeed = createWhiteLabel( Math.round(current.getWind_speed()) + "km/h",
				18, true);
		currentWindspeed.setStyle(currentWindspeed.getStyle() + " -fx-font-family: 'Outfit Regular '; -fx-font-size: 18px;");
		
		Label currentTempNumber = createWhiteLabel(Math.round(current.getTemp()) + "", 220, false);
		currentTempNumber.setStyle(currentTempNumber.getStyle() + " -fx-font-family: 'Outfit Thin';");

		Label degreeSymbol = createWhiteLabel("°", 100, false);
		degreeSymbol.setStyle(degreeSymbol.getStyle() + " -fx-font-family: 'Outfit Thin';");

		Region divider = new Region();
		divider.setMinWidth(3);
		divider.setMaxWidth(3);
		divider.setPrefHeight(30);
		divider.setMaxHeight(30);
		divider.setStyle("-fx-background-color: transparent");
		
		HBox tempBox = new HBox(currentTempNumber, degreeSymbol);
		tempBox.setAlignment(Pos.TOP_LEFT);
		VBox.setMargin(tempBox, new Insets(-40, 0, 0, 0));

		HBox sunsetHbox = new HBox();
		sunsetHbox.getChildren().addAll(currentSunsetLabel, sunsetImageView);
		sunsetHbox.setAlignment(Pos.BOTTOM_RIGHT);

		HBox sunriseHbox = new HBox();
		sunriseHbox.getChildren().addAll(currentSunriseLabel, sunriseImageView);
		sunriseHbox.setAlignment(Pos.BOTTOM_RIGHT);

		HBox location = new HBox();
		location.getChildren().addAll(locationImageView, locationName);
		location.setAlignment(Pos.CENTER_LEFT);
		
		VBox topLeftBox = new VBox(location, divider, tempBox);
		topLeftBox.setPrefSize(530, 500);
		topLeftBox.setAlignment(Pos.TOP_LEFT);

		
		VBox topRightBox = new VBox();
		topRightBox.setPrefSize(500, 100);
		topRightBox.getChildren().addAll( createWeatherIcon(current.getDescription(), 114),horizontalDivider(12) ,currentDescriptionLabel,
				currentTimeLabel);
		topRightBox.setAlignment(Pos.TOP_RIGHT);
		
		
		
		VBox feelsLike = new VBox();
		feelsLike.getChildren().addAll(currentFeelslikeLabel, horizontalDivider(12),currentFeelslike);
		feelsLike.setAlignment(Pos.BOTTOM_CENTER);
		
		VBox humidity = new VBox();
		humidity.getChildren().addAll(currentHumidityLabel, horizontalDivider(12), currentHumidity);
		humidity.setAlignment(Pos.BOTTOM_CENTER);
		
		VBox windSpeed = new VBox();
		windSpeed.getChildren().addAll(currentWindspeedLabel, horizontalDivider(12), currentWindspeed);
		windSpeed.setAlignment(Pos.BOTTOM_CENTER);
			
		
		HBox bottomLeftBox = new HBox(8);
		bottomLeftBox.setPrefSize(730, 130);
		bottomLeftBox.getChildren().addAll(feelsLike, horizontalDivider(12), verticalDivider(62) , horizontalDivider(12), humidity, horizontalDivider(12), verticalDivider(62), horizontalDivider(12), windSpeed);
		bottomLeftBox.setAlignment(Pos.BOTTOM_LEFT);
		
		
		VBox bottomRightBox = new VBox();
		bottomRightBox.setPrefSize(330, 230);
		bottomRightBox.getChildren().addAll(sunriseHbox, sunsetHbox);
		bottomRightBox.setAlignment(Pos.BOTTOM_RIGHT);

		GridPane currentCastGrid = new GridPane();
		currentCastGrid.setPrefSize(1080, 660); 
		currentCastGrid.setPadding(new Insets(8));



		currentCastGrid.add(topLeftBox, 0, 0);
		currentCastGrid.add(topRightBox, 1, 0);
		currentCastGrid.add(bottomLeftBox, 0, 1);
		currentCastGrid.add(bottomRightBox, 1, 1);
		
		return currentCastGrid;
	}

	private HBox createHourlyHBox() {
		HBox hourlyHBox = new HBox();
		hourlyHBox.setPadding(new Insets(8, 8, 8, -16));
		hourlyHBox.setMaxHeight(140);
		hourlyHBox.setSpacing(10);
		hourlyHBox.setAlignment(Pos.BOTTOM_LEFT);

		List<HourlyCast> hourly = weatherCast.getHourly();
		for (int i = 0; i < 24; i++) {
			HourlyCast hour = hourly.get(i);
			hourlyTempLabels[i] = createWhiteLabel(Math.round(hour.getTemp()) + "°", 24, false);
			hoursLabels[i] = createWhiteLabel(toHours(hour.getDt()), 20, false);

			VBox hourlyVbox = new VBox();
			hourlyVbox.setMinWidth(100);
			hourlyHBox.setMinHeight(148); 
			hourlyHBox.setMaxHeight(148); 
			hourlyVbox.setAlignment(Pos.CENTER);
			hourlyVbox.setStyle("-fx-background-radius: 10");
			hourlyVbox.getChildren().addAll(hoursLabels[i], createWeatherIcon(hour.getDescription(), 56),
					hourlyTempLabels[i]);
			 
			hourlyHBox.getChildren().addAll(hourlyVbox);
		}
		return hourlyHBox;
	}

	private ImageView createWeatherIcon(String description, double size) {
		String key = (description == null) ? "" : description.toLowerCase();
		String iconPath = WEATHER_ICON_MAP.getOrDefault(key, "file:img/image-not-available-96.png");
		ImageView imageView = new ImageView(new Image(iconPath));
		imageView.setFitWidth(size);
		imageView.setFitHeight(size);
		return imageView;
	}

	private Label createWhiteLabel(String text, int fontSize, boolean bold) {
		Label label = new Label(text);
		String weight = bold ? " -fx-font-weight: bold;" : "";
		label.setStyle(" -fx-font-size: " + fontSize + "px; -fx-text-fill: white;" + weight);
		return label;
	}

	private String toHours(long timestamp) {
	    LocalDateTime dateTime = LocalDateTime.ofInstant(Instant.ofEpochSecond(timestamp),
	            ZoneOffset.ofTotalSeconds(weatherCast.getTimeZoneOffset()));
	    return String.format("%02d:%02d", dateTime.getHour(), dateTime.getMinute());
	}

	private String toDate(long timestamp) {
	    LocalDateTime dateTime = LocalDateTime.ofInstant(Instant.ofEpochSecond(timestamp),
	            ZoneOffset.ofTotalSeconds(weatherCast.getTimeZoneOffset()));
	    System.out.println("offset: " + weatherCast.getTimeZoneOffset());
	    return dateTime.format(DateTimeFormatter.ofPattern("EEEE dd", java.util.Locale.ENGLISH));
	}
	
	private Region verticalDivider(double height) {
	    Region d = new Region();
	    d.setMinWidth(3);
	    d.setMaxWidth(3);
	    d.setPrefHeight(height);
	    d.setMaxHeight(height);
	    d.setStyle("-fx-background-color: rgb(227, 227, 227, 0.3); -fx-background-radius: 3");
	    return d;
	}
	
	private Region horizontalDivider(double height) {
	    Region d = new Region();
	    d.setMinWidth(3);
	    d.setMaxWidth(3);
	    d.setPrefHeight(height);
	    d.setMaxHeight(height);
	    d.setStyle("-fx-background-color: transparent;");
	    return d;
	}
}