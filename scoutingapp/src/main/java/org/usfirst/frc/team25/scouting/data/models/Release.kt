package org.usfirst.frc.team25.scouting.data.models

/**
 * Created by sng on 2/18/2018.
 */
data class Release(val tagName: String,
                   val assets: Array<Asset>)

data class Asset(val browserDownloadUrl: String,
                 val name: String)