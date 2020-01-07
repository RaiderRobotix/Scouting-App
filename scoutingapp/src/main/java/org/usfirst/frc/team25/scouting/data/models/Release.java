package org.usfirst.frc.team25.scouting.data.models;

import lombok.Data;

/**
 * Created by sng on 2/18/2018.
 */

@Data
public class Release {

    private String tagName;
    private Asset[] assets;

    @Data
    public class Asset {
        String browserDownloadUrl;
        String name;
    }
}
