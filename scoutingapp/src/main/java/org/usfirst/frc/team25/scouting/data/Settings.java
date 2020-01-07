package org.usfirst.frc.team25.scouting.data;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceFragment;
import android.preference.PreferenceManager;
import android.util.Log;

import org.apache.commons.codec.binary.Hex;
import org.apache.commons.codec.digest.DigestUtils;
import org.usfirst.frc.team25.scouting.R;
import org.usfirst.frc.team25.scouting.data.models.PreMatch;

import java.util.Calendar;

/**
 * Object to access SharedPreferences and global user settings
 * Must be initialized with a Context
 */
public class Settings extends PreferenceFragment {

    private SharedPreferences sp;

    // Use getBaseContext() or getActivity() when calling this

    /**
     * Get a new instance of a Settings object to retrieve data
     *
     * @param c <code>Context</code> of the running stack
     * @return A Settings object ot read data from
     */
    public static Settings newInstance(Context c) {
        Settings set = new Settings();
        set.sp = PreferenceManager.getDefaultSharedPreferences(c);
        return set;
    }

    public String getScoutName() {
        return sp.getString("scout_name", "DEFAULT");
    }

    private void setScoutName(String name) {
        SharedPreferences.Editor editor = sp.edit();
        editor.putString("scout_name", name);
        editor.apply();
    }


    public float getTimerManualInc() {
        return (sp.getInt("timer_manual_inc", 4) + 1) / 10.0f;
    }

    public void setTimerManualInc(float incAmount) {
        SharedPreferences.Editor editor = sp.edit();
        editor.putInt("timer_manual_inc", (int) ((incAmount - 0.1) * 10));
        editor.apply();
    }


    public String getScoutPos() {
        return sp.getString("pos", "DEFAULT");
    }

    private void setScoutPos(String pos) {
        SharedPreferences.Editor editor = sp.edit();
        editor.putString("pos", pos);
        editor.apply();
    }

    public int getShiftDur() {
        return sp.getInt("shift_dur", 10);
    }

    public void setShiftDur(int shiftDur) {
        if (shiftDur < 1 || shiftDur > 150) {
            shiftDur = 1;
        }
        SharedPreferences.Editor editor = sp.edit();
        editor.putInt("shift_dur", shiftDur);
        editor.apply();
    }

    public int getMatchNum() {
        return sp.getInt("match_num", 1);
    }

    public void setMatchNum(int matchNum) {
        if (matchNum < 1 || matchNum > 200) {
            matchNum = 1;
        }
        SharedPreferences.Editor editor = sp.edit();
        editor.putInt("match_num", matchNum);
        editor.apply();

    }

    public String getMatchType() {
        return sp.getString("match_type", "DEFAULT");
    }

    public void setMatchType(String type) {
        SharedPreferences.Editor editor = sp.edit();
        editor.putString("match_type", type);
        editor.apply();
    }

    public String getCurrentEvent() {
        return sp.getString("event", "DEFAULT");
    }

    public void setCurrentEvent(String event) {
        SharedPreferences.Editor editor = sp.edit();
        editor.putString("event", event);
        editor.apply();
    }

    public boolean useTeamList() {
        return sp.getBoolean("use_team_list", false);
    }

    public int getMaxMatchNum() {
        return sp.getInt("max_match_num", 150);
    }

    public void setMaxMatchNum(int maxMatchNum) {
        SharedPreferences.Editor editor = sp.edit();
        editor.putInt("max_match_num", maxMatchNum);
        editor.apply();
        Log.i("match_num_set", Integer.toString(maxMatchNum));
    }

    //This is done automatically
    public void setYear() {
        SharedPreferences.Editor editor = sp.edit();
        editor.putString(getYear(), "DEFAULT");
        editor.apply();

    }


    public String getYear() {
        return String.valueOf(Calendar.getInstance().get(Calendar.YEAR));
    }

    //Note: the hashed password is the value of the "change password" preference
    //Method hashes plaintext with SHA-1, then sets it to the value
    public void setPassword(String plainPass) {
        SharedPreferences.Editor editor = sp.edit();
        editor.putString("change_pass",
                new String(Hex.encodeHex(DigestUtils.sha1(plainPass))).toLowerCase());
        editor.apply();
    }

    public boolean matchesPassword(String plainPass) {
        String hash = new String(Hex.encodeHex(DigestUtils.sha1(plainPass))).toLowerCase();
        return (hash.equals(getHashedPass()) || hash.equals(
                "f98960d9aae06c642bae4f24b5152e1bd5cc2c42")); //User-set password or master
        // password will work
    }

    public String getHashedPass() {
        return sp.getString("change_pass", "DEFAULT");
    }

    /**
     * Method to intelligently detect changes or irregularities in scouting pattern for the next
     * match
     *
     * @param preMatch - a PreMatch object with all data fields filled in
     */
    public void autoSetPreferences(PreMatch preMatch) {
        setScoutName(preMatch.getScoutName());
        setScoutPos(preMatch.getScoutPos());
        setMatchNum(preMatch.getMatchNum());


    }

    public String getAPIKey() {
        return getString(R.string.tba_api_key);
    }

    public void setAPIKey(String key) {
        SharedPreferences.Editor editor = sp.edit();
        editor.putString("api_key", key);
        editor.apply();
    }

    public String getLeftAlliance() {
        return sp.getString("leftStation", "Red Alliance");
    }


}
