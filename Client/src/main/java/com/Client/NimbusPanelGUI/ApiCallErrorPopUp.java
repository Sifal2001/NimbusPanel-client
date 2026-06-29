package com.Client.NimbusPanelGUI;

import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;


class ApiCallErrorPopUp{
	public static void showErrorPopUp(Stage ownerStage, String message, String title) {
        Stage popupStage = new Stage();
        popupStage.initModality(Modality.WINDOW_MODAL);
        popupStage.initOwner(ownerStage);
        
        Image error_icon = new Image("file:///C:/Users/Usuario/Desktop/Sifal/WeatherApp/Client/img/minus-button-16.png");
        popupStage.getIcons().add(error_icon);
        
        Label label = new Label(message);
        label.setWrapText(true);
        
        Button closeButton = new Button("Close");
        closeButton.setPrefSize(80, 28);
        closeButton.setOnAction(event -> popupStage.close());
        
        VBox popupContent = new VBox(label, closeButton);
        popupContent.setSpacing(20);
        popupContent.setStyle("-fx-padding: 10; -fx-alignment: center;");
        
        Scene popupScene = new Scene(popupContent, 400, 200);
        popupStage.setScene(popupScene);
        popupStage.setTitle(title);
        popupStage.show();
	}
}