package com.example.android.guardiannews;

import android.app.LoaderManager;
import android.content.Context;
import android.content.Intent;
import android.content.Loader;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<List<Article>> {

    // Tag for the log messages
    private final static String LOG_TAG = MainActivity.class.getSimpleName();

    private static final int ARTICLE_LOADER_ID = 1;

    // An Adapter to handle the list of articles
    private ArticleAdapter mArticleAdapter;

    private ImageView mEmptyImageView;
    private TextView mEmptyTextView;
    private ProgressBar mSpinner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Find a reference to the {@link ListView} in the layout
        ListView articleListView = (ListView) findViewById(R.id.article_listview);

        // Find a reference to the Layout, ImageView and TextView supplied in case there is no data
        // retrieved from the the api to populate the ListView
        RelativeLayout mEmptyLayout = (RelativeLayout) findViewById(R.id.alternate_results_layout);
        mEmptyImageView = (ImageView) findViewById(R.id.alternate_results_imageview);
        mEmptyTextView = (TextView) findViewById(R.id.alternate_results_textview);

        // Find the reference to the ProgressBar spinner and set it to the global member variable
        mSpinner = (ProgressBar) findViewById(R.id.article_loading_spinner);

        // let Android set the empty view for us
        articleListView.setEmptyView(mEmptyLayout);

        // Create a new adapter that takes an empty list of articles as input
        mArticleAdapter = new ArticleAdapter(this, new ArrayList<Article>());

        // Set the adapter on the {@link ListView}
        // so the list can be populated in the user interface
        articleListView.setAdapter(mArticleAdapter);

        ConnectivityManager cm =
                (ConnectivityManager) getApplicationContext().
                        getSystemService(Context.CONNECTIVITY_SERVICE);

        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        boolean isConnected = activeNetwork != null &&
                activeNetwork.isConnectedOrConnecting();

        if (isConnected) {

            // Get a reference to the LoaderManager, in order to interact with loaders.
            LoaderManager loaderManager = getLoaderManager();

            // Initialize the loader. Pass in the int ID constant defined above and pass in null for
            // the bundle. Pass in this activity for the LoaderCallbacks parameter (which is valid
            // because this activity implements the LoaderCallbacks interface).
            loaderManager.initLoader(ARTICLE_LOADER_ID, null, this);
        } else {
            mSpinner.setVisibility(View.GONE);
            mEmptyImageView.setImageResource(R.drawable.no_net);
            mEmptyTextView.setTextColor(ContextCompat.getColor(getApplicationContext(),
                    R.color.noNetColor));
            mEmptyTextView.setText(R.string.no_net);
        }
    }

    @Override
    public Loader<List<Article>> onCreateLoader(int i, Bundle bundle) {

        SharedPreferences sharedPrefs = PreferenceManager.getDefaultSharedPreferences(this);

        String orderBy = sharedPrefs.getString(
                getString(R.string.settings_order_by_key),
                getString(R.string.settings_order_by_default));

        String apiKey = sharedPrefs.getString(
                getString(R.string.settings_api_key_key),
                getString(R.string.settings_enter_api_key));

        Uri.Builder uriBuilder = new Uri.Builder();
        uriBuilder.scheme(getString(R.string.uri_scheme))
                .authority(getString(R.string.uri_authority))
                .appendPath(getString(R.string.uri_path))
                .appendQueryParameter(getString(R.string.uri_query_key),
                        getString(R.string.uri_query_value))
                .appendQueryParameter(getString(R.string.uri_api_key_key),
                        apiKey)
                .appendQueryParameter(getString(R.string.uri_show_fields_key),
                        getString(R.string.uri_show_fields_value))
                .appendQueryParameter(getString(R.string.uri_order_by_key), orderBy);

        return new ArticleLoader(this, uriBuilder.toString());
    }


    @Override
    public void onLoadFinished(Loader<List<Article>> loader, List<Article> articles) {

        //clear the spinner when there is data to populate the list.
        mSpinner.setVisibility(View.GONE);

        // Clear the adapter of previous data
        mArticleAdapter.clear();

        // set the emptyState text
        mEmptyImageView.setImageResource(R.drawable.search);
        mEmptyTextView.setTextColor(ContextCompat.getColor(getApplicationContext(),
                R.color.noResultsColor));
        mEmptyTextView.setText(R.string.no_articles);

        // If there is a valid list of {@link Article}, then add them to the adapter's
        // data set. This will trigger the ListView to update.
        if (articles != null && !articles.isEmpty()) {
            mArticleAdapter.addAll(articles);
        } else {
            Log.v(LOG_TAG, getResources().getString(R.string.null_results));
        }
    }

    @Override
    public void onLoaderReset(Loader<List<Article>> loader) {
        // Loader reset, so we can clear out our existing data.
        mArticleAdapter.clear();
    }

    // for the settings menu
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return true;
    }

    // also for the settings menu
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            Intent settingsIntent = new Intent(this, SettingsActivity.class);
            startActivity(settingsIntent);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
