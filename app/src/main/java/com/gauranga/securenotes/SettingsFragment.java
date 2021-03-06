package com.gauranga.securenotes;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.preference.DropDownPreference;
import androidx.preference.EditTextPreference;
import androidx.preference.ListPreference;
import androidx.preference.Preference;
import androidx.preference.PreferenceFragmentCompat;
import androidx.preference.PreferenceScreen;
import androidx.preference.SeekBarPreference;
import androidx.preference.SwitchPreference;

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
        // add a dropdown menu to set the text font size
        SeekBarPreference seekbar = new SeekBarPreference(context);
        seekbar.setKey("font_size");
        seekbar.setTitle("Text Font Size");
        seekbar.setMin(10);
        seekbar.setMax(24);
        seekbar.setDefaultValue(16);
        seekbar.setShowSeekBarValue(true);
        // add a switch to style the text bold
        SwitchPreference text_weight = new SwitchPreference(context);
        text_weight.setKey("text_weight");
        text_weight.setTitle("Text Font Weight");
        text_weight.setDefaultValue(false);
        text_weight.setSummaryOn("Bold");
        text_weight.setSummaryOff("Normal");
        // add a switch to underline the text
        SwitchPreference text_underline = new SwitchPreference(context);
        text_underline.setKey("text_underline");
        text_underline.setTitle("Text Underline");
        text_underline.setDefaultValue(false);
        text_underline.setSummaryOn("Yes");
        text_underline.setSummaryOff("No");
        // add a list to change the font family
        ListPreference heading_font = new ListPreference(context);
        heading_font.setKey("heading_font");
        heading_font.setTitle("Heading Font Family");
        heading_font.setEntries(new CharSequence[]{"Roboto Mono", "Quicksand", "Amatic", "Bebas", "Helvetica"});
        heading_font.setEntryValues(new CharSequence[]{"roboto", "quicksand", "amatic", "bebas", "helvetica"});
        heading_font.setSummaryProvider(new Preference.SummaryProvider<ListPreference>() {
            @Override
            public CharSequence provideSummary(ListPreference preference) {
                String ff = (String) heading_font.getEntry();
                if (ff == null) {
                    return "Default";
                }
                return ff;
            }
        });
        // add the preferences
        screen.addPreference(text_weight);
        screen.addPreference(text_underline);
        screen.addPreference(seekbar);
        screen.addPreference(heading_font);
        setPreferenceScreen(screen);
    }
}