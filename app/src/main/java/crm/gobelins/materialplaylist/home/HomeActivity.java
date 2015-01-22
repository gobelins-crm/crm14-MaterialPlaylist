package crm.gobelins.materialplaylist.home;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.SearchView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import com.echonest.api.v4.Artist;

import crm.gobelins.materialplaylist.BuildConfig;
import crm.gobelins.materialplaylist.R;
import crm.gobelins.materialplaylist.about.AboutActivity;
import crm.gobelins.materialplaylist.artists.ArtistsFragment;
import crm.gobelins.materialplaylist.server.ENApi;

public class HomeActivity extends ActionBarActivity implements ArtistsFragment.OnArtistClickListener {

    private static final String TAG = "HomeActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        getSupportFragmentManager()
                .beginTransaction()
                .add(R.id.artists_container, ArtistsFragment.newInstance(100, "electro"))
                .commit();

        if (BuildConfig.DEBUG) {
            // Check if EchoNest API is working in debug mode
            ENApi.with(this).dumpStats();
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_home, menu);

        MenuItem searchItem = menu.findItem(R.id.action_search);
        final SearchView searchView = (SearchView) MenuItemCompat.getActionView(searchItem);

        searchView.setSubmitButtonEnabled(true);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                Log.d(TAG, s);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                return false;
            }
        });

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        if (id == R.id.action_about) {
            startActivity(new Intent(this, AboutActivity.class));

            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onArtistClick(Artist artist) {
        Log.d(TAG, artist.getID());
    }
}
