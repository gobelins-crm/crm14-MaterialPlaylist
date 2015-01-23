package crm.gobelins.materialplaylist.songs;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;

import crm.gobelins.materialplaylist.R;

/**
 * An activity representing a single Song detail screen. This
 * activity is only used on handset devices. On tablet-size devices,
 * item details are presented side-by-side with a list of items
 * in a {@link PlaylistActivity}.
 * <p/>
 * This activity is mostly just a 'shell' activity containing nothing
 * more than a {@link SongDetailFragment}.
 */
public class SongDetailActivity extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_song_detail);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getWindow().setStatusBarColor(getResources().getColor(android.R.color.transparent));
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
            getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN | View.SYSTEM_UI_FLAG_LAYOUT_STABLE);
        }

        Toolbar actionbar = (Toolbar) findViewById(R.id.actionbar);
        actionbar.setBackgroundColor(getResources().getColor(R.color.primary));
        actionbar.getBackground().setAlpha(64);
        setSupportActionBar(actionbar);

        // Show the Up button in the action bar.
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        if (savedInstanceState == null) {
            String songId = getIntent().getStringExtra(SongDetailFragment.ARG_SONG_ID);
            SongDetailFragment fragment = SongDetailFragment.newInstance(songId);

            getSupportFragmentManager().beginTransaction()
                    .add(R.id.song_detail_container, fragment)
                    .commit();
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == android.R.id.home) {
            NavUtils.navigateUpTo(this, new Intent(this, PlaylistActivity.class));
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
