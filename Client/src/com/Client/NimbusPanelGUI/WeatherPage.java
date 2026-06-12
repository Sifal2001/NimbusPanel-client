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
	private Label[] hourlyTempLabels = new Label[24];
	private Label[] hoursLabels = new Label[24];
	private Label[] dailyDateLabels = new Label[5];
	private Label[] dailyTempLabels = new Label[5];
	private Label[] dailyHumidityLabels = new Label[5];

	private static final Map<String, String> WEATHER_ICON_MAP = new HashMap<>();

	static {
		WEATHER_ICON_MAP.put("clouds", "file:img/cloudy-96.png");
		WEATHER_ICON_MAP.put("clear", "file:img/clear-96.png");
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

	public WeatherPage(Stage primaryStage) {
		this.primaryStage = primaryStage;
	}

	public Scene createScene() {
		Image backgroundImg = new Image("file:img/bg-cut.jpg");
		BackgroundImage backgroundImage = new BackgroundImage(backgroundImg, BackgroundRepeat.NO_REPEAT,
				BackgroundRepeat.NO_REPEAT, BackgroundPosition.CENTER,
				new BackgroundSize(100, 100, true, true, false, true));
		Background background = new Background(backgroundImage);

		ScrollPane hourlyScrollPane = new ScrollPane();
		hourlyScrollPane.setContent(createHourlyHBox());
		hourlyScrollPane.setFitToWidth(true);
		hourlyScrollPane.setVbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
		hourlyScrollPane.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
		hourlyScrollPane.setMaxHeight(220);
		hourlyScrollPane.setMaxWidth(1080);
		hourlyScrollPane.setStyle("-fx-background-color: transparent; -fx-background: transparent");

		VBox currentWeatherPanel = new VBox();
		currentWeatherPanel.setBackground(background);
		currentWeatherPanel.getChildren().addAll(createCurrentGrid(), hourlyScrollPane);
		currentWeatherPanel.setPrefWidth(920);
		currentWeatherPanel.setPrefHeight(600);

		HBox rootLayout = new HBox();
		rootLayout.getChildren().addAll(createDailyGrid(), currentWeatherPanel);

		return new Scene(rootLayout, 1200, 680);
	}

	private GridPane createDailyGrid() {
		Button backButton = new Button("Back");
		backButton.setStyle(
				"-fx-padding: 8px; -fx-border: none; -fx-background-color:black; -fx-text-fill:white; -fx-font-size: 36px; -fx-padding-bottom:16px");
		backButton.setPrefWidth(380);

		GridPane dailyCastGrid = new GridPane();
		dailyCastGrid.setPrefWidth(364);
		dailyCastGrid.setStyle("-fx-background-color: black;");
		dailyCastGrid.setPadding(new Insets(8));
		dailyCastGrid.setHgap(60);
		dailyCastGrid.setVgap(20);
		dailyCastGrid.add(backButton, 0, 11, 2, 1);

		for (int i = 0; i < 5; i++) {
			dailyTempLabels[i] = createWhiteLabel(Math.round(Main.daily_temp[i + 1]) + "°c", 36, false);
			dailyTempLabels[i].setPrefWidth(120);

			dailyHumidityLabels[i] = createWhiteLabel(Math.round(Main.daily_humidity[i + 1]) + "%", 16, false);
			dailyHumidityLabels[i].setPrefWidth(120);

			dailyDateLabels[i] = createWhiteLabel(toDate(Main.days[i + 1]), 16, false);
			dailyDateLabels[i].setPrefWidth(120);

			dailyCastGrid.add(createWeatherIcon(Main.daily_description[i], 56), 0, i * 2);
			dailyCastGrid.add(dailyTempLabels[i], 1, i * 2);
			dailyCastGrid.add(dailyDateLabels[i], 0, i * 2 + 1);
			dailyCastGrid.add(dailyHumidityLabels[i], 1, i * 2 + 1);

			dailyTempLabels[i].setAlignment(Pos.BASELINE_RIGHT);
			dailyHumidityLabels[i].setAlignment(Pos.BASELINE_RIGHT);
		}

		backButton.setOnAction(event -> {
			HomePage homePage = new HomePage(primaryStage, Main.favouriteLocations);
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

		Label locationName = createWhiteLabel(Main.location, 68, true);
		Label currentTempLabel = createWhiteLabel(Math.round(Main.current_temp) + "°c", 38, false);
		Label currentDescriptionLabel = createWhiteLabel(Main.current_description, 38, true);
		Label currentSunriseLabel = createWhiteLabel(toHours(Main.current_sunrise_timestamp), 24, false);
		Label currentSunsetLabel = createWhiteLabel(toHours(Main.current_sunset_timestamp), 24, false);
		Label currentTimeLabel = createWhiteLabel(toHours(Main.current_time), 28, false);
		Label currentHumidityLabel = createWhiteLabel("Humidity: " + Math.round(Main.current_humidity) + "%", 24,
				false);
		Label currentFeelslikeLabel = createWhiteLabel("Feels like: " + Math.round(Main.current_feelslike) + "°c", 24,
				false);
		Label currentWindspeedLabel = createWhiteLabel("Wind speed: " + Math.round(Main.current_windspeed) + "km/h", 24,
				false);

		HBox sunsetHbox = new HBox();
		sunsetHbox.getChildren().addAll(currentSunsetLabel, sunsetImageView);
		sunsetHbox.setAlignment(Pos.BOTTOM_RIGHT);

		HBox sunriseHbox = new HBox();
		sunriseHbox.getChildren().addAll(currentSunriseLabel, sunriseImageView);
		sunriseHbox.setAlignment(Pos.BOTTOM_RIGHT);

		VBox topLeftBox = new VBox(createWeatherIcon(Main.current_description, 96), currentDescriptionLabel,
				currentTempLabel);
		topLeftBox.setPrefSize(530, 250);
		topLeftBox.setAlignment(Pos.TOP_LEFT);

		VBox topRightBox = new VBox();
		topRightBox.setPrefSize(530, 250);
		topRightBox.getChildren().addAll(locationName, currentTimeLabel);
		topRightBox.setAlignment(Pos.TOP_RIGHT);

		VBox bottomLeftBox = new VBox(8);
		bottomLeftBox.setPrefSize(530, 250);
		bottomLeftBox.getChildren().addAll(currentFeelslikeLabel, currentHumidityLabel, currentWindspeedLabel);
		bottomLeftBox.setAlignment(Pos.BOTTOM_LEFT);

		VBox bottomRightBox = new VBox();
		bottomRightBox.setPrefSize(530, 250);
		bottomRightBox.getChildren().addAll(sunriseHbox, sunsetHbox);
		bottomRightBox.setAlignment(Pos.BOTTOM_RIGHT);

		GridPane currentCastGrid = new GridPane();
		currentCastGrid.setPrefSize(1080, 482);
		currentCastGrid.setPadding(new Insets(8));

		currentCastGrid.add(topLeftBox, 0, 0);
		currentCastGrid.add(topRightBox, 1, 0);
		currentCastGrid.add(bottomLeftBox, 0, 1);
		currentCastGrid.add(bottomRightBox, 1, 1);

		return currentCastGrid;
	}

	private HBox createHourlyHBox() {
		HBox hourlyHBox = new HBox();
		hourlyHBox.setPadding(new Insets(8));
		hourlyHBox.setMaxHeight(220);
		hourlyHBox.setSpacing(16);

		for (int i = 0; i < 24; i++) {
			hourlyTempLabels[i] = createWhiteLabel(Math.round(Main.hourly_temp[i]) + "°c", 40, false);
			hoursLabels[i] = createWhiteLabel(toHours(Main.hours[i]), 24, false);

			VBox hourlyVbox = new VBox();
			hourlyVbox.setMinWidth(100);
			hourlyVbox.setMinHeight(180);
			hourlyVbox.setAlignment(Pos.CENTER);
			hourlyVbox.setStyle("-fx-background-color: rgba(28, 45, 80, 0.75); -fx-background-radius: 10");
			hourlyVbox.getChildren().addAll(hoursLabels[i], createWeatherIcon(Main.hourly_description[i], 56),
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
		label.setStyle("-fx-font-family: 'Arial'; -fx-font-size: " + fontSize + "px; -fx-text-fill: white;" + weight);
		return label;
	}

	private String toHours(long timestamp) {
		LocalDateTime dateTime = LocalDateTime.ofInstant(Instant.ofEpochSecond(timestamp),
				ZoneOffset.ofTotalSeconds(Main.timeZoneOffset));
		return String.format("%02d:%02d", dateTime.getHour(), dateTime.getMinute());
	}

	private String toDate(long timestamp) {
		LocalDateTime dateTime = LocalDateTime.ofInstant(Instant.ofEpochSecond(timestamp),
				ZoneOffset.ofTotalSeconds(Main.timeZoneOffset));
		return dateTime.format(DateTimeFormatter.ofPattern("EEEE dd"));
	}
}