package org.usfirst.frc.team25.scouting;


import java.util.HashMap;

public class Constants {

    public static final String GAME_NAME = "FIRST Power Up";

    /**
     * Follows a modified version of {@link <a href="https://semver.org/">Semantic Versioning</a>}, where
     * there is no dot separator between minor and patch version numbers. Patch versions are incremented whenever
     * a bug is fixed or non-metric related features are added, minor versions when metrics are added/removed,
     * and major versions between seasons. If a release is the first minor version (e.g. v2.1),
     * note that there is no trailing zero for the patch version. Tags on GitHub follow the same scheme,
     * except the letter "v" is appended to the version number.
     * Version 1.0 was created for the 2017 FRC season (FIRST Steamworks).
     */
    public static final double VERSION_NUMBER = 2.12;
    public static final String RULES_FILEPATH = "Power Up Cheatsheet.pdf";
    /**
     * Contains the GitHub username of the repository owner, followed by a forward slash
     * and the repository name ("Scouting-App")
     */
    public static final String REPOSITORY_NAME = "spencerng/Scouting-App";
    public static HashMap<String, String> EVENT_KEY = new HashMap<>();
}
