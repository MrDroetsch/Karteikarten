package me.bunnykick.utils;

import javafx.scene.control.Alert;

import java.util.Arrays;

public class Dialogues {

    public static void showDialog(String message) {
        showDialog(message, Alert.AlertType.INFORMATION);
    }

    public static void showDialog(String message, Alert.AlertType type) {
        Alert alert = new Alert(type, message);
        alert.show();
    }

    public static void showErrorMessage(String message, Exception exception) {
        Alert alert = new Alert(Alert.AlertType.ERROR, message + " " + exception.getMessage());
        exception.printStackTrace();
        alert.show();
    }

    public static void showError(Exception exception) {
        Alert alert = new Alert(Alert.AlertType.ERROR, exception.getMessage());
        alert.show();
    }

    public static void showErrorStackTrace(Exception exception) {
        exception.printStackTrace();

        StackTraceElement[] elements = exception.getStackTrace();
        final StringBuilder builder = new StringBuilder();
        Arrays.stream(elements).forEachOrdered(element -> {
            builder.append(element.toString());
            builder.append("\n");
        });
        Alert alert = new Alert(Alert.AlertType.ERROR, builder.toString());
        alert.setResizable(true);
        alert.show();
    }

}
