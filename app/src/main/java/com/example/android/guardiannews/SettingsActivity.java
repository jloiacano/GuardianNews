package com.example.android.guardiannews;

import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.preference.Preference;
import android.preference.PreferenceFragment;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;

/**
 * Created by J on 4/5/2017.
 * Handles the settings.
 */
public class SettingsActivity extends AppCompatActivity {

    // A tag for log messages
    private static final String LOG_TAG = SettingsActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.settings_activity);
    }

    // Where all the preferences happen
    public static class GuardianNewsPreferenceFragment extends PreferenceFragment
            implements Preference.OnPreferenceChangeListener {

        @Override
        public void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            addPreferencesFromResource(R.xml.settings_main);

            // attribution preference
            Preference attribution = findPreference(getString(R.string.settings_guardian_api_key));
            attribution.setOnPreferenceClickListener(new Preference.OnPreferenceClickListener() {
                @Override
                public boolean onPreferenceClick(Preference preference) {
                    Intent openAPIgetterWebPage = new Intent(Intent.ACTION_VIEW);
                    openAPIgetterWebPage.setData(Uri.parse("http://open-platform.theguardian.com/"));
                    startActivity(openAPIgetterWebPage);

                    return true;
                }
            });

            // If the user doesn't have an API key (necessary) opens the website to get one
            Preference getApiKey = findPreference(getString(R.string.settings_get_api_key));
            getApiKey.setOnPreferenceClickListener(new Preference.OnPreferenceClickListener() {
                @Override
                public boolean onPreferenceClick(Preference preference) {
                    Intent openAPIgetterWebPage = new Intent(Intent.ACTION_VIEW);
                    openAPIgetterWebPage.setData(Uri.parse("https://bonobo.capi.gutools.co.uk/register/developer"));
                    startActivity(openAPIgetterWebPage);

                    return true;
                }
            });

            // Sets the summary to the API key value
            Preference enteredApiKey = findPreference(getString(R.string.settings_api_key_key));
            bindPreferenceSummaryToValue(enteredApiKey);

            // Sets the summary to the desired sort order
            Preference orderBy = findPreference(getString(R.string.settings_order_by_key));
            bindPreferenceSummaryToValue(orderBy);

        }

        @Override
        public boolean onPreferenceChange(Preference preference, Object value) {
            String stringValue = value.toString();
            preference.setSummary(stringValue);
            return true;
        }

        // For setting the summary value of the setting on the Settings page
        private void bindPreferenceSummaryToValue(Preference preference) {
            String preferenceString = null;
            preference.setOnPreferenceChangeListener(this);
            SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(preference.getContext());
            if (preference.equals(findPreference(getString(R.string.settings_api_key_key)))) {
                preferenceString = preferences.getString(preference.getKey(), "");
            } else if (preference.equals(findPreference(getString(R.string.settings_order_by_key)))) {
                preferenceString = preferences.getString(preference.getKey(), "");
            }
            onPreferenceChange(preference, preferenceString);
        }
    }
}