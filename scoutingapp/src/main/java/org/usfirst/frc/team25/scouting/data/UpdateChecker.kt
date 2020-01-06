package org.usfirst.frc.team25.scouting.data

import android.app.AlertDialog
import android.app.ProgressDialog
import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.net.Uri
import android.os.AsyncTask
import android.os.Environment
import android.util.Log
import android.widget.Toast
import com.google.gson.Gson
import org.usfirst.frc.team25.scouting.R
import org.usfirst.frc.team25.scouting.data.models.Asset
import org.usfirst.frc.team25.scouting.data.models.Release
import java.io.BufferedReader
import java.io.File
import java.io.FileOutputStream
import java.io.InputStreamReader
import java.net.HttpURLConnection
import java.net.URL

/**
 * Created by sng on 2/18/2018.
 * Checks for scouting app updates via GitHub releases
 */
class UpdateChecker(private val context: Context) : AsyncTask<String?, Int?, Boolean>() {
	private var release: Release? = null
	protected override fun doInBackground(vararg arg0: String): Boolean {
		try {
			val url = URL("https://api.github.com/repos/"
					+ context.getString(R.string.repository_name) + "/releases/latest")
			val con = url.openConnection() as HttpURLConnection
			con.requestMethod = "GET"
			val `in` = BufferedReader(InputStreamReader(con.inputStream))
			var inputLine: String?
			val response = StringBuilder()
			while (`in`.readLine().also { inputLine = it } != null) {
				response.append(inputLine)
			}
			`in`.close()
			val respStr = response.toString()
			release = Gson().fromJson(respStr, Release::class.java)
			if (release.tagName != context.getString(R.string.version_number)) {
				return true
			} else {
				File(Environment.getExternalStorageDirectory().toString() + "/Download/" +
						release.assets[0].name).delete()
			}
		} catch (e: Exception) {
			e.printStackTrace()
			return false
		}
		// Returns if download was successful or not
		return false
	}

	override fun onPostExecute(result: Boolean) {
		if (result) {
			AlertDialog.Builder(context)
					.setTitle("App update available")
					.setMessage("A new version of the app is available. Would you like to update " +
							"it now?")
					.setPositiveButton("Get update") { dialog: DialogInterface?, which: Int -> AppDownloader(context, release!!.assets[0]).execute() }
					.setNegativeButton("Remind me later") { dialog: DialogInterface?, which: Int ->
						Toast.makeText(context, "Reminder set for next app launch",
								Toast.LENGTH_SHORT).show()
					}
					.create()
					.show()
		}
	}

}

internal class AppDownloader(private val context: Context, private val asset: Asset) : AsyncTask<String?, Int?, Boolean>() {
	private var progressBar: ProgressDialog? = null
	override fun onPreExecute() {
		super.onPreExecute()
		progressBar = ProgressDialog(context)
		progressBar!!.setCancelable(false)
		progressBar!!.setMessage("Downloading...")
		progressBar!!.isIndeterminate = true
		progressBar!!.setCanceledOnTouchOutside(false)
		progressBar!!.show()
	}

	protected override fun onProgressUpdate(vararg progress: Int) {
		super.onProgressUpdate(*progress)
		progressBar!!.isIndeterminate = false
		progressBar!!.max = 100
		progressBar!!.progress = progress[0]
		val msg: String
		msg = if (progress[0] > 99) {
			"Finishing... "
		} else {
			"Downloading... " + progress[0] + "%"
		}
		progressBar!!.setMessage(msg)
	}

	protected override fun doInBackground(vararg strings: String): Boolean {
		return try {
			val url = URL(asset.browserDownloadUrl)
			val c = url.openConnection() as HttpURLConnection
			c.requestMethod = "GET"
			c.connect()
			val downloadPath = Environment.getExternalStorageDirectory().toString() + "/Download/"
			val file = File(downloadPath)
			file.mkdirs()
			val fileName = asset.name
			val outputFile = File(file, fileName)
			if (outputFile.exists()) {
				outputFile.delete()
			}
			val fos = FileOutputStream(outputFile)
			if (c.responseCode >= 400 && c.responseCode <= 499) {
				Log.i("tag", " " + c.responseCode)
				Log.i("tag", " " + c.url)
				throw Exception("Bad authorization")
			}
			val `is` = c.inputStream
			val buffer = ByteArray(1024)
			var len1: Int
			var downloaded = 0
			while (`is`.read(buffer).also { len1 = it } != -1) {
				fos.write(buffer, 0, len1)
				downloaded += len1
			}
			fos.close()
			`is`.close()
			openNewVersion(downloadPath, fileName)
			true
		} catch (e: Exception) {
			e.printStackTrace()
			false
		}
	}

	private fun openNewVersion(location: String, fileName: String) {
		val uri = Uri.parse("file://$location$fileName")
		val intent = Intent(Intent.ACTION_VIEW)
		intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP
		intent.setDataAndType(uri, "application/vnd.android.package-archive")
		context.startActivity(intent)
	}

	override fun onPostExecute(aBoolean: Boolean) {
		super.onPostExecute(aBoolean)
		progressBar!!.dismiss()
	}

}