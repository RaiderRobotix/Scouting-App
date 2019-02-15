# FRC Scouting App [![Build Status](https://travis-ci.org/RaiderRobotix/Scouting-App.svg?branch=master)](https://travis-ci.org/RaiderRobotix/Scouting-App)

Team 25's base scouting app for the 2017 FRC season and beyond

## Key features 

* Material design UI and easy data input and verification
* Data pulled from [The Blue Alliance](http://www.thebluealliance.com/) used to auto-fill match data and verify accuracy of recorded data
* Easy JSON data export to PCs
* Easy access to Game Manual

## Usage

Opening the app to the main menu will reveal four icons, the current game logo, and an info bar containing the current scout, the current scouting position, and the current match.

### Rules

A cheat sheet of the game rules (usually the one created by Andy Smith from Team 5546) and common fouls (created by Team 25 at the beginning of the season) is available to view here. The information should have nearly everything a scouter needs to know to add match data and scout accurately.

### Export data

* JSON data for the selected current event, located at `/Raider Robotix Scouting`and named `Data - <Scout Position> - <event key>.json`, will be exported
  * It is important that you do not accidentally delete or rename files through a file manager.
* How the data is exported (email, SMS, Slack, etc.) is up to the user and follows the standard Android interface.
* If no data exists for the current event (not necessarily for other events), a popup message will indicate so.

### Settings

#### General Settings

* **Scout name** - changes the name of the current scout 
* **Shift duration** - changes how long a scouting shift is, between 1 and 25 matches. If a shift is M matches long, the app will prompt for a new scout name after every M matches (e.g. the 11th, 21st, 31st matches if M=10).
* **Scout position** - the driver station whose robot is being scouted on the device. Options are the Red/Blue alliance, positions 1-3. Positions are given from left to right from the driver's point of view, same as FMS. This option should be constant for a particular device, and all six devices that are used to scout should have a different scout position.
* **Match number** - current (or next) match being played. This value automatically updates, and its maximum value depends on the event.
* **Current event** - current event that is being scouted. It is critical that this does not change throughout an event, as the event data is downloaded for and the file to which data is saved is dependent on this value.
* **Year** - current season, automatically updated based on the system calendar. The downloaded event data is dependent on this value.

#### Game-specific settings (Destination: Deep Space)

* **Left alliance color** - the left alliance station color, from the scouter's point of view. Determines the orientation of the plate lighting graphic when adding match data.


#### Data transfer

* **Use team lists** - enabling this option will automatically fill in the correct team number for a particular scouting position/match number when adding match data, if the match schedule for the event is downloaded. If only the team list is available, this verifies that the entered team number is playing at the event.
* **Download match schedule** - downloads the match schedule, team list, and score breakdowns from previous matches from The Blue Alliance, if internet is available.
* **Change password** - brings up menu to change the password used to delete scouting data
* **Delete scouting data** - deletes all scouting data (the JSON file) for the current event in `/Raider Robotix Scouting`. Retains a backup copy of the data in `/Downloads`. The user-set password or the master password is required to delete.

An info section is also present, with the current game name and version number, which should be consistent with the tag of the lastest release of the app in the master branch of the GitHub repository. If the version number is different, the app will attempt to download the lastest APK release from GitHub (if internet is available) upon startup.

### Add match

Contains several user-friendly inputs to add and save match data, across different screens. Data is saved when going between different screens, though certain fields must be filled in to progress to the next screen.

#### Prematch

Contains all information that can be obtained before the match starts, with all fields required to proceed to sandstorm. Usually consistent between seasons.

* **Scout name** - pulled from the settings. Left blank at the start of a new rotation.
* **Match number** - pulled from the settings and incremented after match data is saved. Determines team number verification and auto-fill.
* **Scouting position** - pulled from the settings. Determines team number verification and auto-fill.
* **Team number** - current team being scouted. May be auto-filled or verified if the option is selected in the settings
* **Robot starting position** - physical starting location of the robot, relative to the drive team behind the center driver station. Options are left (near driver station 1), center, and right (near driver station 3).

Changing the scout name, match number, and scouting position will save those settings for future matches.

#### Sandstorm

Contains all robot actions during the sandstorm. Usually specific to each game, though tasks may be similar between seasons (such as reaching a baseline/auto line)


#### Tele-Op

Contains all robot actions during tele-op, including the endgame. Also includes general field info that must be determined during the match, not before or after, as well as increment adjustment settings.



#### Postmatch

Contains subjective options that reflect on the robot's performance as a whole

* Tele-op primary focus(es) 
* Robot and driver comments - contain common, "quick" comments as well as a free-response input
* Pick number - would the robot be a good 1st, 2nd, or not a good pick? Criteria changes throughout the season, as the level of competition increases.
* Robot comparison - prompts the scout to compare the robot playing in the current match to the one that he/she scouted in the previous match. Prompt is hidden during the first match.

Upon pressing finish, the data is converted into JSON and appended to the existing JSON data file, or a new JSON data file is created it is the first match.
