package Dashboard.Controller;

import Dashboard.Model.Currency;
import Dashboard.Model.Alarm;
import Dashboard.Util.XmlReader;
import Database.DBHandler;
import Login.Model.CurrentUser;
import javafx.application.Platform;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.chart.*;
import javafx.scene.control.*;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Dictionary;
import java.util.Enumeration;

public class DashboardController {
    @FXML
    private Label usdBuying, usdSelling, eurBuying, eurSelling, gbpBuying, gbpSelling, sekBuying, sekSelling, cadBuying, cadSelling,audBuying,audSelling,dkkBuying,dkkSelling,chfBuying,chfSelling,kwdBuying,kwdSelling,nokBuying,nokSelling,sarBuying,sarSelling,jpyBuying,jpySelling,bgnBuying,bgnSelling,ronBuying,ronSelling,rubBuying,rubSelling,irrBuying,irrSelling,cnyBuying,cnySelling,pkrBuying,pkrSelling,qarBuying,qarSelling,usd_aud,usd_dkk,usd_chf,usd_sek,usd_jpy,usd_cad,usd_nok,usd_sar, eur_usd, gbp_usd, kwd_usd,usd_bgn,usd_ron,usd_rub,usd_irr,usd_cny,usd_pkr,usd_qar,lblShowBuying,lblShowSelling;
    @FXML
    private Label currentUsername, balanceTry, balanceUsd,balanceAud,balanceDkk, balanceEur, balanceGbp, balanceChf, balanceSek, balanceCad, balanceKwd, balanceNok,balanceSar,balanceJpy,balanceBgn,balanceRon,balanceRub,balanceIrr,balanceCny,balancePkr,balanceQar, alarmState, calculatedValue;
    @FXML
    private PieChart balanceChart;
    @FXML
    private ChoiceBox convertFrom, convertTo, alarmChoicebox, alarmRateType, updateBalanceChoicebox;
    @FXML
    private TextField convertAmount, alarmUpperLimit, alarmLowerLimit, updateBalanceField;
    @FXML
    private ComboBox lineChartCombobox,lineChartCombobox1,lineChartUSDCombobox;
    @FXML
    private LineChart rateHistoryLineChart, rateHistoryLineChartSelling,rateHistoryUSDLineChart;

    public void initialize() {
        drawExchangeRates(); // Populate the exchange rates table on the top-left of the window
        drawBalanceTable();
        drawBalancePieChart(); // Populate the pie chart with the amount of money the user has on each currency
        drawConvertPanel();
        drawLineChart("USD"); // Initialize the line chart, USD/TRY by default
        drawLineChartSelling("USD");
        drawLineChartUSD("EUR");
        drawAlarmChoicebox();
        drawUpdateBalanceChoicebox();

        currentUsername.setText(CurrentUser.username);

        // Start a thread to update the UI (Scene) every 5 seconds to always show accurate exchange rates
        Task refreshUI = new Task<Void>() {
            @Override
            public Void call() throws Exception {
                while (true) {
                    Platform.runLater(new Runnable() {
                        @Override
                        public void run() {
                            drawExchangeRates();

                            // Check the user if we should fire up the alarm if the user set an alarm
                            if(Alarm.active) {
                                checkUserAlarm();
                            }
                        }
                    });
                    Thread.sleep(5000);
                }
            }
        };
        Thread th = new Thread(refreshUI);
        th.setDaemon(true);
        th.start();

    }

    // Populate the exchange rates table on the top-left of the window
    public void drawExchangeRates() {
        System.out.println("Setting exchange rates...");

        // Fetch live exchange rates for currencies, and populate our ArrayList with Currency objects
        ArrayList<Currency> list = XmlReader.getInstance().getCurrencies();

        // Add currencies to the database after they are fetched
        // USD
        DBHandler.addCurrency(list.get(0).getCurrencyCode(), Float.toString(list.get(0).getBanknoteBuying()), Float.toString(list.get(0).getBanknoteSelling()));
        // AUD
        DBHandler.addCurrency(list.get(1).getCurrencyCode(), Float.toString(list.get(1).getBanknoteBuying()), Float.toString(list.get(1).getBanknoteSelling()));
        // DKK
        DBHandler.addCurrency(list.get(2).getCurrencyCode(), Float.toString(list.get(2).getBanknoteBuying()), Float.toString(list.get(2).getBanknoteSelling()));
        // EUR
        DBHandler.addCurrency(list.get(3).getCurrencyCode(), Float.toString(list.get(3).getBanknoteBuying()), Float.toString(list.get(3).getBanknoteSelling()));
        // GBP
        DBHandler.addCurrency(list.get(4).getCurrencyCode(), Float.toString(list.get(4).getBanknoteBuying()), Float.toString(list.get(4).getBanknoteSelling()));
        // CHF
        DBHandler.addCurrency(list.get(5).getCurrencyCode(), Float.toString(list.get(5).getBanknoteBuying()), Float.toString(list.get(5).getBanknoteSelling()));
        // SEK
        DBHandler.addCurrency(list.get(6).getCurrencyCode(), Float.toString(list.get(6).getBanknoteBuying()), Float.toString(list.get(6).getBanknoteSelling()));
        // CAD
        DBHandler.addCurrency(list.get(7).getCurrencyCode(), Float.toString(list.get(7).getBanknoteBuying()), Float.toString(list.get(7).getBanknoteSelling()));
        // KWD
        DBHandler.addCurrency(list.get(8).getCurrencyCode(), Float.toString(list.get(8).getBanknoteBuying()), Float.toString(list.get(8).getBanknoteSelling()));
        // NOK
        DBHandler.addCurrency(list.get(9).getCurrencyCode(), Float.toString(list.get(9).getBanknoteBuying()), Float.toString(list.get(9).getBanknoteSelling()));
        // SAR
        DBHandler.addCurrency(list.get(10).getCurrencyCode(), Float.toString(list.get(10).getBanknoteBuying()), Float.toString(list.get(10).getBanknoteSelling()));
        // JPY
        DBHandler.addCurrency(list.get(11).getCurrencyCode(), Float.toString(list.get(11).getBanknoteBuying()), Float.toString(list.get(11).getBanknoteSelling()));
        // BGN
        DBHandler.addCurrency(list.get(12).getCurrencyCode(), Float.toString(list.get(12).getBanknoteBuyingForex()), Float.toString(list.get(12).getBanknoteSellingForex()));
        // RON
        DBHandler.addCurrency(list.get(13).getCurrencyCode(), Float.toString(list.get(13).getBanknoteBuyingForex()), Float.toString(list.get(13).getBanknoteSellingForex()));
        // RUB
        DBHandler.addCurrency(list.get(14).getCurrencyCode(), Float.toString(list.get(14).getBanknoteBuyingForex()), Float.toString(list.get(14).getBanknoteSellingForex()));
        // IRR
        DBHandler.addCurrency(list.get(15).getCurrencyCode(), Float.toString(list.get(15).getBanknoteBuyingForex()), Float.toString(list.get(15).getBanknoteSellingForex()));
        // CNY
        DBHandler.addCurrency(list.get(16).getCurrencyCode(), Float.toString(list.get(16).getBanknoteBuyingForex()), Float.toString(list.get(16).getBanknoteSellingForex()));
        // PKR
        DBHandler.addCurrency(list.get(17).getCurrencyCode(), Float.toString(list.get(17).getBanknoteBuyingForex()), Float.toString(list.get(17).getBanknoteSellingForex()));
        // QAR
        DBHandler.addCurrency(list.get(18).getCurrencyCode(), Float.toString(list.get(18).getBanknoteBuyingForex()), Float.toString(list.get(18).getBanknoteSellingForex()));


        //****Crossrate
        // AUD
        DBHandler.addCrossrateCurrency(list.get(1).getCurrencyCode(), Float.toString(list.get(1).getCrossrateUSD()));
        // DKK
        DBHandler.addCrossrateCurrency(list.get(2).getCurrencyCode(), Float.toString(list.get(2).getCrossrateUSD()));
        // CHF
        DBHandler.addCrossrateCurrency(list.get(5).getCurrencyCode(), Float.toString(list.get(5).getCrossrateUSD()));
        // SEK
        DBHandler.addCrossrateCurrency(list.get(6).getCurrencyCode(), Float.toString(list.get(6).getCrossrateUSD()));
        // JPY
        DBHandler.addCrossrateCurrency(list.get(11).getCurrencyCode(), Float.toString(list.get(11).getCrossrateUSD()));
        // CAD
        DBHandler.addCrossrateCurrency(list.get(7).getCurrencyCode(), Float.toString(list.get(7).getCrossrateUSD()));
        // NOK
        DBHandler.addCrossrateCurrency(list.get(9).getCurrencyCode(), Float.toString(list.get(9).getCrossrateUSD()));
        // SAR
        DBHandler.addCrossrateCurrency(list.get(10).getCurrencyCode(), Float.toString(list.get(10).getCrossrateUSD()));
        // BGN
        DBHandler.addCrossrateCurrency(list.get(12).getCurrencyCode(), Float.toString(list.get(12).getCrossrateUSD()));
        // RON
        DBHandler.addCrossrateCurrency(list.get(13).getCurrencyCode(), Float.toString(list.get(13).getCrossrateUSD()));
        // RUB
        DBHandler.addCrossrateCurrency(list.get(14).getCurrencyCode(), Float.toString(list.get(14).getCrossrateUSD()));
        // IRR
        DBHandler.addCrossrateCurrency(list.get(15).getCurrencyCode(), Float.toString(list.get(15).getCrossrateUSD()));
        // CNY
        DBHandler.addCrossrateCurrency(list.get(16).getCurrencyCode(), Float.toString(list.get(16).getCrossrateUSD()));
        // PKR
        DBHandler.addCrossrateCurrency(list.get(17).getCurrencyCode(), Float.toString(list.get(17).getCrossrateUSD()));
        // QAR
        DBHandler.addCrossrateCurrency(list.get(18).getCurrencyCode(), Float.toString(list.get(18).getCrossrateUSD()));

        // EUR
        DBHandler.addCrossrateCurrency(list.get(3).getCurrencyCode(), Float.toString(list.get(3).getCrossrateOther()));
        // GBP
        DBHandler.addCrossrateCurrency(list.get(4).getCurrencyCode(), Float.toString(list.get(4).getCrossrateOther()));
        // KWD
        DBHandler.addCrossrateCurrency(list.get(8).getCurrencyCode(), Float.toString(list.get(8).getCrossrateOther()));



        //*****



        usdBuying.setText(Float.toString(list.get(0).getBanknoteBuying()));
        usdSelling.setText(Float.toString(list.get(0).getBanknoteSelling()));

        audBuying.setText(Float.toString(list.get(1).getBanknoteBuying()));
        audSelling.setText(Float.toString(list.get(1).getBanknoteSelling()));

        dkkBuying.setText(Float.toString(list.get(2).getBanknoteBuying()));
        dkkSelling.setText(Float.toString(list.get(2).getBanknoteSelling()));

        eurBuying.setText(Float.toString(list.get(3).getBanknoteBuying()));
        eurSelling.setText(Float.toString(list.get(3).getBanknoteSelling()));

        gbpBuying.setText(Float.toString(list.get(4).getBanknoteBuying()));
        gbpSelling.setText(Float.toString(list.get(4).getBanknoteSelling()));

        chfBuying.setText(Float.toString(list.get(5).getBanknoteBuying()));
        chfSelling.setText(Float.toString(list.get(5).getBanknoteSelling()));

        sekBuying.setText(Float.toString(list.get(6).getBanknoteBuying()));
        sekSelling.setText(Float.toString(list.get(6).getBanknoteSelling()));

        cadBuying.setText(Float.toString(list.get(7).getBanknoteBuying()));
        cadSelling.setText(Float.toString(list.get(7).getBanknoteSelling()));

        kwdBuying.setText(Float.toString(list.get(8).getBanknoteBuying()));
        kwdSelling.setText(Float.toString(list.get(8).getBanknoteSelling()));

        nokBuying.setText(Float.toString(list.get(9).getBanknoteBuying()));
        nokSelling.setText(Float.toString(list.get(9).getBanknoteSelling()));

        sarBuying.setText(Float.toString(list.get(10).getBanknoteBuying()));
        sarSelling.setText(Float.toString(list.get(10).getBanknoteSelling()));

        jpyBuying.setText(Float.toString(list.get(11).getBanknoteBuying()));
        jpySelling.setText(Float.toString(list.get(11).getBanknoteSelling()));

        bgnBuying.setText(Float.toString(list.get(12).getBanknoteBuyingForex())); //-----
        bgnSelling.setText(Float.toString(list.get(12).getBanknoteSellingForex()));

        ronBuying.setText(Float.toString(list.get(13).getBanknoteBuyingForex()));
        ronSelling.setText(Float.toString(list.get(13).getBanknoteSellingForex()));

        rubBuying.setText(Float.toString(list.get(14).getBanknoteBuyingForex()));
        rubSelling.setText(Float.toString(list.get(14).getBanknoteSellingForex()));

        irrBuying.setText(Float.toString(list.get(15).getBanknoteBuyingForex()));
        irrSelling.setText(Float.toString(list.get(15).getBanknoteSellingForex()));

        cnyBuying.setText(Float.toString(list.get(16).getBanknoteBuyingForex()));
        cnySelling.setText(Float.toString(list.get(16).getBanknoteSellingForex()));

        pkrBuying.setText(Float.toString(list.get(17).getBanknoteBuyingForex()));
        pkrSelling.setText(Float.toString(list.get(17).getBanknoteSellingForex()));

        qarBuying.setText(Float.toString(list.get(18).getBanknoteBuyingForex()));
        qarSelling.setText(Float.toString(list.get(18).getBanknoteSellingForex()));





        // Populate crossrates table
        usd_aud.setText(Float.toString(list.get(1).getCrossrateUSD())); // USD / AUD
        usd_dkk.setText(Float.toString(list.get(2).getCrossrateUSD())); // USD / DKK
        usd_chf.setText(Float.toString(list.get(5).getCrossrateUSD())); // USD / CHF
        usd_sek.setText(Float.toString(list.get(6).getCrossrateUSD())); // USD / SEK
        usd_jpy.setText(Float.toString(list.get(11).getCrossrateUSD())); // USD / JPY
        usd_cad.setText(Float.toString(list.get(7).getCrossrateUSD())); // USD / CAD
        usd_nok.setText(Float.toString(list.get(9).getCrossrateUSD())); // USD / NOK
        usd_sar.setText(Float.toString(list.get(10).getCrossrateUSD())); // USD / SAR

        eur_usd.setText(Float.toString(list.get(3).getCrossrateOther())); // EUR / USD
        gbp_usd.setText(Float.toString(list.get(4).getCrossrateOther())); // GBP / USD
        kwd_usd.setText(Float.toString(list.get(8).getCrossrateOther())); // KWD / USD

        usd_bgn.setText(Float.toString(list.get(12).getCrossrateUSD())); // USD / BGN
        usd_ron.setText(Float.toString(list.get(13).getCrossrateUSD())); // USD / RON
        usd_rub.setText(Float.toString(list.get(14).getCrossrateUSD())); // USD / RUB
        usd_irr.setText(Float.toString(list.get(15).getCrossrateUSD())); // USD / IRR
        usd_cny.setText(Float.toString(list.get(16).getCrossrateUSD())); // USD / CNY
        usd_pkr.setText(Float.toString(list.get(17).getCrossrateUSD())); // USD / PKR
        usd_qar.setText(Float.toString(list.get(18).getCrossrateUSD())); // USD / QAR









    }

    // Populates the balance table, top-right corner of the window
    public void drawBalanceTable() {
        Dictionary userBalance = DBHandler.getBalanceOfUser(CurrentUser.username);

        balanceTry.setText("TRY: " + userBalance.get("TRY"));
        balanceUsd.setText("USD: " + userBalance.get("USD"));
        balanceAud.setText("AUD: " + userBalance.get("AUD"));
        balanceDkk.setText("DKK: " + userBalance.get("DKK"));
        balanceEur.setText("EUR: " + userBalance.get("EUR"));
        balanceGbp.setText("GBP: " + userBalance.get("GBP"));
        balanceChf.setText("CHF: " + userBalance.get("CHF"));
        balanceSek.setText("SEK: " + userBalance.get("SEK"));
        balanceCad.setText("CAD: " + userBalance.get("CAD"));
        balanceKwd.setText("KWD: " + userBalance.get("KWD"));
        balanceNok.setText("NOK: " + userBalance.get("NOK"));
        balanceSar.setText("SAR: " + userBalance.get("SAR"));
        balanceJpy.setText("JPY: " + userBalance.get("JPY"));
        balanceBgn.setText("BGN: " + userBalance.get("BGN"));
        balanceRon.setText("RON: " + userBalance.get("RON"));
        balanceRub.setText("RUB: " + userBalance.get("RUB"));
        balanceIrr.setText("IRR: " + userBalance.get("IRR"));
        balanceCny.setText("CNY: " + userBalance.get("CNY"));
        balancePkr.setText("PKR: " + userBalance.get("PKR"));
        balanceQar.setText("QAR: " + userBalance.get("QAR"));





    }

    // Populates the pie chart with the amount of money the user has on each currency
    public void drawBalancePieChart() {
        Dictionary userBalance = DBHandler.getBalanceOfUser(CurrentUser.username);

        Enumeration keys = userBalance.keys();
        // Iterate over each key-value in the userBalance dictionary and add them to the pie chart
        for (Enumeration elements = userBalance.elements(); elements.hasMoreElements();) {
            PieChart.Data data = new PieChart.Data(keys.nextElement().toString(), Double.parseDouble(elements.nextElement().toString()));
            balanceChart.getData().add(data);
        }

    }

    // Simply clear the pie chart and redraw it
    public void updatePieChart()
    {
        balanceChart.getData().clear();
        drawBalancePieChart();
    }

    // Populates the choicebox choices on the 'convert currency' panel etc.
    public void drawConvertPanel() {
        Dictionary userBalance = DBHandler.getBalanceOfUser(CurrentUser.username);

        // Iterate over each key (currency code) in the userBalance dictionary and add them to the choiceboxes on the convert panel
        for (Enumeration keys = userBalance.keys(); keys.hasMoreElements();) {
            String currencyCode = keys.nextElement().toString();
            convertFrom.getItems().add(currencyCode);
            convertFrom.getSelectionModel().select(18);
            convertTo.getItems().add(currencyCode);
            convertTo.getSelectionModel().select(10);
        }
    }


    // Handler for the calculate button. Same function with the convertBtnAction, unless this one doesn't change user balance values on DB
    public void calculateBtnAction(ActionEvent event) {
        System.out.println("Calculating " + convertAmount.getText() + convertFrom.getValue() + " to " + convertTo.getValue());

        // Convert currency
        // Using the approach below when converting money since we don't have crossRates from every currency pair
        // If the 'convertFrom' currency is not TRY, convert the amount to TRY first, then convert that TRY value to 'convertTo' currency.
        // In other words, sell the 'convertFrom' currency to buy TRY, then buy 'convertTo' currency with that TRY.
        // If 'convertFrom' currency is TRY, buy the 'convertTo' currency directly with it

        Float amount = Float.parseFloat(convertAmount.getText());


        // Pop up error if the two selected currencies are the same
        if ( convertFrom.getValue().toString().equals(convertTo.getValue().toString())) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Warning");
            alert.setContentText("Please select two different currencies ");

            alert.showAndWait();
        }

        else if(convertAmount.getText().isEmpty()){
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Warning");
            alert.setContentText("Please select two different currencies OR Enter Convert Amount");

            alert.showAndWait();
        }


        else if (convertFrom.getValue().toString().equals("TRY")) { // If 'convertFrom' currency is TRY, buy the 'convertTo' currency directly with it
            Float buyingPrice = DBHandler.getCurrencyBuying(convertTo.getValue().toString());
            Float newValue = amount/buyingPrice;

            calculatedValue.setText(newValue.toString());
        }
        else if (convertTo.getValue().toString().equals("TRY")) { // If the user is buying TRY with another currency
            Float sellingPrice = DBHandler.getCurrencySelling(convertFrom.getValue().toString()); // Selling price against TRY
            Float tryValue = amount*sellingPrice;

            calculatedValue.setText(tryValue.toString());
        }
        //--

        //--
        else { //If the 'convertFrom' currency is not TRY, convert the amount to TRY first, then convert that TRY value to 'convertTo' currency.
            Float buyingPrice = DBHandler.getCurrencySelling(convertTo.getValue().toString());
            Float sellingPrice = DBHandler.getCurrencySelling(convertFrom.getValue().toString()); // Selling price against TRY
            Float tryValue = amount*sellingPrice;
            //Float newValue = tryValue;
            Float newValue = tryValue/buyingPrice;

            calculatedValue.setText(newValue.toString());
        }

    }

    // Handler for the convert button
    @FXML
    private void convertBtnAction(ActionEvent event) {
        System.out.println("Converting " + convertAmount.getText() + convertFrom.getValue() + " to " + convertTo.getValue());

        // Convert currency
        // Using the approach below when converting money since we don't have crossRates from every currency pair
        // If the 'convertFrom' currency is not TRY, convert the amount to TRY first, then convert that TRY value to 'convertTo' currency.
        // In other words, sell the 'convertFrom' currency to buy TRY, then buy 'convertTo' currency with that TRY.
        // If 'convertFrom' currency is TRY, buy the 'convertTo' currency directly with it
        Float amount = Float.parseFloat(convertAmount.getText());

         if(convertAmount.getText().isEmpty()){
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Warning");
            alert.setContentText("Please select two different currencies OR Enter Convert Amount");

            alert.showAndWait();
        }

        // Pop up error if the two selected currencies are the same
        if (convertFrom.getValue().toString().equals(convertTo.getValue().toString())) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Warning");
            alert.setContentText("Please select two different currencies");

            alert.showAndWait();
        }
        // Pop up an error if the user doesn't have enough money to convert
        if (amount > Float.parseFloat(DBHandler.getBalanceOfUser(CurrentUser.username).get(convertFrom.getValue().toString()).toString())) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Error");
            alert.setContentText("Insufficient funds");

            alert.showAndWait();
            return; // Exit without doing anything
        }


        else if (convertFrom.getValue().toString().equals("TRY")) { // If 'convertFrom' currency is TRY, buy the 'convertTo' currency directly with it
            Float buyingPrice = DBHandler.getCurrencyBuying(convertTo.getValue().toString());
            Float newValue = amount/buyingPrice;

            DBHandler.decreaseBalanceOfUser(CurrentUser.username, convertFrom.getValue().toString(), amount);
            DBHandler.increaseBalanceOfUser(CurrentUser.username, convertTo.getValue().toString(), newValue);
        }
        else if (convertTo.getValue().toString().equals("TRY")) { // If the user is buying TRY with another currency
            Float sellingPrice = DBHandler.getCurrencySelling(convertFrom.getValue().toString()); // Selling price against TRY
            Float tryValue = amount*sellingPrice;

            DBHandler.decreaseBalanceOfUser(CurrentUser.username, convertFrom.getValue().toString(), amount);
            DBHandler.increaseBalanceOfUser(CurrentUser.username, convertTo.getValue().toString(), tryValue);
        }
        else { //If the 'convertFrom' currency is not TRY, convert the amount to TRY first, then convert that TRY value to 'convertTo' currency.
            Float buyingPrice = DBHandler.getCurrencyBuying(convertTo.getValue().toString());
            Float sellingPrice = DBHandler.getCurrencySelling(convertFrom.getValue().toString()); // Selling price against TRY
            Float tryValue = amount*sellingPrice;
            Float newValue = tryValue/buyingPrice;

            DBHandler.decreaseBalanceOfUser(CurrentUser.username, convertFrom.getValue().toString(), amount);
            DBHandler.increaseBalanceOfUser(CurrentUser.username, convertTo.getValue().toString(), newValue);

        }

        // Update the piechart and the balance table again with the new values
        drawBalanceTable();
        updatePieChart();
    }


    public void drawLineChart(String currencyCode) {
        rateHistoryLineChart.getData().clear(); // Clear the LineChart first in case of any bug or memory leftover etc.


        ResultSet historicValues = DBHandler.getCurrencyHistoryBuying(currencyCode);
        // ResultSet userAge=DBHandler.getUserAge(userAge);
        XYChart.Series series = new XYChart.Series();

        series.setName(currencyCode + "/TRY Exchange Rate");

        try {
            while (historicValues.next()) {
                series.getData().add(new XYChart.Data(historicValues.getString("DATE(created_at)"), historicValues.getFloat("buying")));
                // series.getData().add(new XYChart.Data(historicValues.getString("AGE(age)"),historicValues.getInt("TRY"))); {
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        rateHistoryLineChart.getData().add(series);

        // Populate the choices on the combobox on the right-hand side of the linechart
        // This combobox will allow the user to switch between different currencies' histories
        if(lineChartCombobox.getItems().isEmpty()) {
            lineChartCombobox.getItems().addAll("USD","AUD","DKK","EUR","GBP","CHF","SEK","CAD","KWD","NOK","SAR","JPY","BGN","RON","RUB","IRR","CNY","PKR","QAR");
            lineChartCombobox.getSelectionModel().select(0);
        }

    }
    //---------
    public void drawLineChartSelling(String currencyCode) {
        rateHistoryLineChartSelling.getData().clear(); // Clear the LineChart first in case of any bug or memory leftover etc.


        ResultSet historicValues1 = DBHandler.getCurrencyHistorySelling(currencyCode);
        // ResultSet userAge=DBHandler.getUserAge(userAge);
        XYChart.Series series = new XYChart.Series();

        series.setName(currencyCode + "/TRY Exchange Rate");

        try {
            while (historicValues1.next()) {
                series.getData().add(new XYChart.Data(historicValues1.getString("DATE(created_at)"), historicValues1.getFloat("selling")));
                // series.getData().add(new XYChart.Data(historicValues.getString("AGE(age)"),historicValues.getInt("TRY"))); {
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        rateHistoryLineChartSelling.getData().add(series);

        // Populate the choices on the combobox on the right-hand side of the linechart
        // This combobox will allow the user to switch between different currencies' histories
        if(lineChartCombobox1.getItems().isEmpty()) {
            lineChartCombobox1.getItems().addAll("USD","AUD","DKK","EUR","GBP","CHF","SEK","CAD","KWD","NOK","SAR","JPY","BGN","RON","RUB","IRR","CNY","PKR","QAR");
            lineChartCombobox1.getSelectionModel().select(0);
        }

    }
    //---------

    //---------
    public void drawLineChartUSD(String currencyCode) {
        rateHistoryUSDLineChart.getData().clear(); // Clear the LineChart first in case of any bug or memory leftover etc.


        ResultSet historicValues1 = DBHandler.getCurrencyHistoryUSD(currencyCode);
        // ResultSet userAge=DBHandler.getUserAge(userAge);
        XYChart.Series series = new XYChart.Series();

        series.setName(currencyCode + "/TRY Exchange Rate");

        try {
            while (historicValues1.next()) {
                series.getData().add(new XYChart.Data(historicValues1.getString("DATE(created_at)"), historicValues1.getFloat("crossrate")));
                // series.getData().add(new XYChart.Data(historicValues.getString("AGE(age)"),historicValues.getInt("TRY"))); {
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        rateHistoryUSDLineChart.getData().add(series);

        // Populate the choices on the combobox on the right-hand side of the linechart
        // This combobox will allow the user to switch between different currencies' histories
        if(lineChartUSDCombobox.getItems().isEmpty()) {
            lineChartUSDCombobox.getItems().addAll("USD","AUD","DKK","EUR","GBP","CHF","SEK","CAD","KWD","NOK","SAR","JPY","BGN","RON","RUB","IRR","CNY","PKR","QAR");
            lineChartUSDCombobox.getSelectionModel().select(0);
        }

    }
    //---------



    // This method will be fired when the user selects a currency from the combobox on the right-hand side of the linechart
    public void lineChartBoxAction (ActionEvent event) {

        drawLineChart(lineChartCombobox.getValue().toString());
    }
    public void lineChartBoxAction1 (ActionEvent event) {

        drawLineChartSelling(lineChartCombobox1.getValue().toString());
    }

    public void lineChartUSDBoxAction (ActionEvent event) {

        drawLineChartUSD(lineChartUSDCombobox.getValue().toString());
    }





    public void drawAlarmChoicebox (){
        alarmChoicebox.getItems().addAll("USD/TRY", "EUR/TRY", "GBP/TRY", "SEK/TRY","CAD/TRY","AUD/TRY","DKK/TRY","CHF/TRY","KWD/TRY","NOK/TRY","SAR/TRY","JPY/TRY","BGN/TRY","RON/TRY","RUB/TRY","IRR/TRY","CNY/TRY","PKR/TRY","QAR/TRY");
        alarmChoicebox.getSelectionModel().select(0);
        alarmRateType.getItems().addAll("Buying","Selling");
        alarmRateType.getSelectionModel().select(0);
        if(Alarm.active) { //

        }
        else {
            alarmState.setText("Off");
        }
    }

    public void drawUpdateBalanceChoicebox() {
        updateBalanceChoicebox.getItems().addAll("TRY","USD","AUD","DKK","EUR","GBP","CHF","SEK","CAD","KWD","NOK","SAR","JPY","BGN","RON","RUB","IRR","CNY","PKR","QAR");
        updateBalanceChoicebox.getSelectionModel().select(0);
    }


    public void setAlarmBtnAction (ActionEvent event) {
        Float currentRate = 0f;
        // Filling in our static alarm model located in ../Models/Alarm
        Alarm.currencyCode = alarmChoicebox.getValue().toString().substring(0,3); // Get the first 3 characters of the choicebox choice, (Get only "USD" from "USD/TRY" string)
        Alarm.rateType = alarmRateType.getValue().toString();
        if(!alarmUpperLimit.getText().isEmpty()) {
            Alarm.exchangeRateUpperLimit = Float.parseFloat(alarmUpperLimit.getText());
        }
        if(!alarmLowerLimit.getText().isEmpty()) {
            Alarm.exchangeRateLowerLimit = Float.parseFloat(alarmLowerLimit.getText());
        }
        Alarm.active = true;

        // Change the text and the color of the little "On/Off" label below the "Set Alarm" button to indicate that the alarm is set.
        alarmState.setText("ON");
        alarmState.setTextFill(Color.GREEN);
        switch (Alarm.rateType) {
            case "Buying":
                currentRate = DBHandler.getCurrencyBuying(Alarm.currencyCode);
                lblShowBuying.setText(String.valueOf(currentRate));
                break;
            case "Selling":
                currentRate = DBHandler.getCurrencySelling(Alarm.currencyCode);
                lblShowSelling.setText(String.valueOf(currentRate));
                break;
        }


    }

    // Method that checks if we need to fire up the alarm
    public void checkUserAlarm () {
        Float currentRate = 0f;

        switch (Alarm.rateType) {
            case "Buying":
                currentRate = DBHandler.getCurrencyBuying(Alarm.currencyCode);
                break;
            case "Selling":
                currentRate = DBHandler.getCurrencySelling(Alarm.currencyCode);
                break;
        }
        System.out.print("Currency" + " " + "Code is : "  + Alarm.currencyCode + " "+ " Alarm Rate Type is :"+ " " +Alarm.rateType);
        System.out.println("Amount is" + " " +currentRate);

        // Case if the user set both an upper limit and a lower limit
        // We will check if the current rate exceeds the upper or lower limit
        if(Alarm.exchangeRateUpperLimit != null && Alarm.exchangeRateLowerLimit != null) {
            if(currentRate >= Alarm.exchangeRateUpperLimit || currentRate <= Alarm.exchangeRateLowerLimit) {
                fireAlarm(currentRate);
            }
        }
        // If the user set only set an upper limit
        else if(Alarm.exchangeRateUpperLimit != null && Alarm.exchangeRateLowerLimit == null) {
            if(currentRate >= Alarm.exchangeRateUpperLimit) {
                fireAlarm(currentRate);
            }
        }

        // If the user set only set a lower limit
        else if(Alarm.exchangeRateUpperLimit == null && Alarm.exchangeRateLowerLimit != null) {
            if(currentRate <= Alarm.exchangeRateLowerLimit) {
                fireAlarm(currentRate);
            }
        }

    }

    // Display a popup
    public void fireAlarm(Float currentRate) {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle("Exchange Rate Alarm");
        alert.setContentText(Alarm.currencyCode + ":" + Alarm.rateType + " rate is now " + currentRate + "\nThe alarm was set to:  \n\tUpper limit: " + Alarm.exchangeRateUpperLimit + "\n\tLower limit: " + Alarm.exchangeRateLowerLimit);

        Alarm.active = false; // Deactivate the alarm

        // Change the text and the color of the little "On/Off" label below the "Set Alarm" button to indicate that the alarm is now off.
        alarmState.setText("OFF");
        alarmState.setTextFill(Color.RED);

        alert.showAndWait();

    }


    public void logoutBtnAction(ActionEvent event) {
        // Switch the view to the login window
        Node node=(Node) event.getSource();
        Stage stage=(Stage) node.getScene().getWindow();
        Parent root = null;
        try {
            root = FXMLLoader.load(getClass().getResource("/Login/View/login.fxml"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();

    }

    public void updateBalanceBtnAction(ActionEvent event) {
        if(!updateBalanceField.getText().isEmpty()) {
            DBHandler.setBalanceOfUser(CurrentUser.username, updateBalanceChoicebox.getValue().toString(), Float.parseFloat(updateBalanceField.getText()));

            drawBalanceTable(); // Draw balance table again
            updatePieChart();


        }
        else { // Pop up error if the field is blank
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Warning");
            alert.setContentText("Please select two different currencies");

            alert.showAndWait();

        }

    }
    public void decreaseUserBalanceButton(ActionEvent event) {
        if(!updateBalanceField.getText().isEmpty()) {
            DBHandler.decreaseUserBalance(CurrentUser.username, updateBalanceChoicebox.getValue().toString(), Float.parseFloat(updateBalanceField.getText()));

            drawBalanceTable(); // Draw balance table again
            updatePieChart();
        }
        else { // Pop up error if the field is blank
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Warning");
            alert.setContentText("Please select two different currencies");

            alert.showAndWait();

        }

    }
    public void increaseUserBalanceButton(ActionEvent event) {
        if(!updateBalanceField.getText().isEmpty()) {
            DBHandler.increaseUserBalance(CurrentUser.username, updateBalanceChoicebox.getValue().toString(), Float.parseFloat(updateBalanceField.getText()));
            drawBalanceTable(); // Draw balance table again
            updatePieChart();
        }
        else { // Pop up error if the field is blank
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Warning");
            alert.setContentText("Please select two different currencies");

            alert.showAndWait();

        }

    }
}
