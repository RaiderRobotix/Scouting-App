package org.usfirst.frc.team25.scouting.data.models;

/**
 * Created by sng on 2/18/2018.
 */

public class Release {

    private String tag_name;
    private Asset[] assets;

    public String getTagName() {
        return tag_name;
    }

    public Asset[] getAssets() {
        return assets;
    }

    public class Asset {
        String browser_download_url;
        String name;

        public String getBrowserDownloadUrl() {
            return browser_download_url;
        }

        public String getName() {
            return name;
        }
    }
}
