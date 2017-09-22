package com.scrabble.controller;

import com.scrabble.MainApp;

public class MainUIController {

    public MainUIController() {

    }

    public MainApp mainApp;


    /**
     * Is called by the main application to give a reference back to itself.
     *
     * @param mainApp
     */
    public void setMainApp(MainApp mainApp) {
        this.mainApp = mainApp;

    }




}
