package com.Client.NimbusPanelGUI;

import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.control.TextInputControl;
import javafx.scene.layout.VBox;

public final class UiComponents {

    public static final String LABEL_STYLE =
        "-fx-font-family: 'Arial'; -fx-font-size: 16px; -fx-text-fill: Black;";
    public static final String FIELD_STYLE =
        "-fx-font-size: 16px; -fx-border-color: transparent transparent black transparent; "
        + "-fx-border-width: 0 0 1 0; -fx-background-color: transparent; -fx-alignment: center;";
    public static final String BUTTON_STYLE =
        "-fx-background-color: STEELBLUE; -fx-border-color: transparent; "
        + "-fx-cursor: hand; -fx-font-size: 20px; -fx-text-fill: white;";

    private UiComponents() {}

    public static VBox createFormField(String labelText, TextInputControl field, double width) {
        VBox container = new VBox(5);              
        container.setAlignment(Pos.CENTER);

        Label label = new Label(labelText);
        label.setStyle(LABEL_STYLE);              

        field.setStyle(FIELD_STYLE);
        field.setPrefWidth(width);
        field.setMaxWidth(width);

        container.getChildren().addAll(label, field);
        return container;                         
    }
}
