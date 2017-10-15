# FRC Scouting App [![Build Status](https://travis-ci.org/spencerng/Scouting-App.svg?branch=master)](https://travis-ci.org/spencerng/Scouting-App)

**Note: This branch contains a build with dummy fields intended for FIRST Stronghold. All updates for FIRST Steamworks will be located in the `master` branch until the 2018 kickoff.**

Team 25's base scouting app for the 2017 FRC season and beyond

Key features: 

* Material design UI and easy data input and verification
* Data pulled from [The Blue Alliance](http://www.thebluealliance.com/) used to auto-fill match data
* Easy JSON data export to PCs
* Easy access to Game Manual


# #To Do

**Features to implement**
* Finish `DataDownloader` class and implement methods from desktop client to download the data from TBA
  * Create methods to save the data
* Automate "Current event" `ListPreference` with events for the season
* Improve speed of saving match data by writing each `ScoutEntry` object individually, without loading the existing data as an `ArrayList`
  * Initialize file with an opening bracket
  * After each entry is written as JSON, write a comma to the file
  * When exporting data, create a temporary file with the closing bracket (`]`) to complete the JSON array
* Integrate a Preference item to download the data from TBA
* Integrate the "Import match data" Preference item
* "Materialize" main menu icons

