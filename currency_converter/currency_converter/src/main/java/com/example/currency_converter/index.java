package com.example.currency_converter;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.html.Image;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.server.InputStreamFactory;
import com.vaadin.flow.server.StreamResource;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

@Route("")
public class index extends VerticalLayout {
    index() {
        CurrencyConverterController controller = new CurrencyConverterController();
        H1 header = new H1("Currency Converter");
        add(header);


        String imagePath = "currency_converter/frontend/images/logo.png";

        InputStreamFactory inputStreamFactory = () -> {
            try {
                return new FileInputStream(imagePath);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
                return null;
            }
        };

        StreamResource streamResource = new StreamResource("logo.png", inputStreamFactory);
        Image image = new Image(streamResource, "Alternative text for the image");

        image.setHeight("100px");
        image.setWidth("100px");
        add(image);


        TextField amountField = new TextField("Amount");
        add(amountField);


        ComboBox<String> fromComboBox = new ComboBox<>("From");
        fromComboBox.setItems(controller.getCurrencies());
        add(fromComboBox);


        ComboBox<String> toComboBox = new ComboBox<>("To");
        toComboBox.setItems(controller.getCurrencies());
        add(toComboBox);

        TextField resultField = new TextField("Result");
        resultField.setEnabled(true);

        TextField rateField = new TextField("Rate Used");
        rateField.setEnabled(true);

        Span errorText = new Span("");
        errorText.setId("errorText");
        errorText.setVisible(false);

        Button convertButton = new Button("Convert", event -> {
            String amount = amountField.getValue();
            String base_currency = fromComboBox.getValue();
            String target_currency = toComboBox.getValue();

            if (controller.isValidNumber(amount)) {
                if (controller.isValidChoice(base_currency, target_currency)) {
                    Double num_amount = Double.valueOf(amount);
                    String[] result_array = controller.convertCurrency(base_currency, target_currency, num_amount);
                    resultField.setValue(result_array[0]);
                    rateField.setValue(result_array[1]);
                    errorText.setVisible(false);
                } else {
                    errorText.setText("Please Select Two Valid Currencies!");
                    errorText.setVisible(true);
                }
            } else {
                errorText.setText("Please type in a valid amount!");
                errorText.setVisible(true);
            }
        });

        amountField.setId("amountField");
        fromComboBox.setId("comboBox1");
        toComboBox.setId("comboBox2");
        convertButton.setId("convertButton");
        resultField.setId("resultField");

        add(convertButton);
        add(resultField);
        add(rateField);
        add(errorText);

    }

}
