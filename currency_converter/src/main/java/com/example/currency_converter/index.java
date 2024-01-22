package com.example.currency_converter;


import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.html.Image;
import com.vaadin.flow.component.icon.SvgIcon;
import com.vaadin.flow.component.notification.Notification;
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


        String imagePath = "frontend/images/logo.png";

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
                } else {
                    Notification.show("Please fill in the amount you want in correct format");
                }
            } else {
                Notification.show("Please Select Two Different Currencies Before continuing");
            }
        });
        add(convertButton);

        add(resultField);

        add(rateField);

    }

}
