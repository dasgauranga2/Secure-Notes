package com.gauranga.securenotes;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.preference.DropDownPreference;
import androidx.preference.EditTextPreference;
import androidx.preference.Preference;
import androidx.preference.PreferenceFragmentCompat;
import androidx.preference.PreferenceScreen;
import androidx.preference.SeekBarPreference;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import es.dmoral.toasty.Toasty;

public class SettingsFragment extends PreferenceFragmentCompat {

    @Override
    public void onCreatePreferences(Bundle savedInstanceState, String rootKey) {
        setPreferencesFromResource(R.xml.preferences, rootKey);

        Context context = getPreferenceManager().getContext();
        PreferenceScreen screen = getPreferenceManager().createPreferenceScreen(context);
        // add a dropdown menu to set the font size
        SeekBarPreference seekbar = new SeekBarPreference(context);
        seekbar.setKey("font_size");
        seekbar.setTitle("Font Size");
        seekbar.setMin(10);
        seekbar.setMax(24);
        seekbar.setDefaultValue(12);
        seekbar.setShowSeekBarValue(true);
        // add the preferences
        screen.addPreference(seekbar);
        setPreferenceScreen(screen);
    }
}