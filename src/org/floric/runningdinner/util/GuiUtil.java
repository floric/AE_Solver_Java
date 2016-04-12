package org.floric.runningdinner.util;

import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory;

/**
 * Created by florian on 10.04.2016.
 */
public final class GuiUtil {

    private GuiUtil() {
    }

    public static void initSpinner(Spinner<Integer> spinner, int min, int max, int def, int step) {
        spinner.setMaxWidth(80.0);
        spinner.setEditable(true);
        spinner.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(min, max, def, step));
        spinner.focusedProperty().addListener((observable, oldValue, newValue) -> {
            String currentInput = spinner.editorProperty().getValue().getText();

            try {
                int currentValue = Integer.parseInt(currentInput);

                if (currentValue > max) {
                    currentValue = max;
                } else if (currentValue < min) {
                    currentValue = min;
                }

                spinner.editorProperty().getValue().setText(String.valueOf(currentValue - (currentValue % step)));
            } catch (NumberFormatException ex) {
                spinner.editorProperty().getValue().setText(String.valueOf(def));
            }
        });
    }
}
