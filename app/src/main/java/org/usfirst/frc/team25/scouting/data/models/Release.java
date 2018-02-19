package org.usfirst.frc.team25.scouting.data.models;

/**
 * Created by sng on 2/18/2018.
 */

public class Release {

    public String getTag_name() {
        return tag_name;
    }

    public Asset[] getAssets() {
        return assets;
    }

    String tag_name;
    Asset[] assets;

    public class Asset{
        public String getBrowser_download_url() {
            return browser_download_url;
        }

        String browser_download_url;

        public String getName() {
            return name;
        }

        String name;
    }
}
