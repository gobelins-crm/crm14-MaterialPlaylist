package crm.gobelins.materialplaylist.songs;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.support.v7.app.ActionBarActivity;
import android.view.MenuItem;

import com.echonest.api.v4.Song;

import crm.gobelins.materialplaylist.R;

/**
 * An activity representing a list of Songs. This activity
 * has different presentations for handset and tablet-size devices. On
 * handsets, the activity presents a list of items, which when touched,
 * lead to a {@link crm.gobelins.materialplaylist.songs.SongDetailActivity} representing
 * item details. On tablets, the activity presents the list of items and
 * item details side-by-side using two vertical panes.
 * <p/>
 * The activity makes heavy use of fragments. The list of items is a
 * {@link PlaylistFragment} and the item details
 * (if present) is a {@link crm.gobelins.materialplaylist.songs.SongDetailFragment}.
 * <p/>
 */
public class PlaylistActivity extends ActionBarActivity
        implements PlaylistFragment.OnSongClickListener {

    public static final String EXTRA_ARTIST_ID = "extraArtistId";
    public static final String EXTRA_ARTIST_NAME = "extraArtistName";

    private static final int NB_SONGS_RESULTS = 100;
    /**
     * Whether or not the activity is in two-pane mode, i.e. running on a tablet
     * device.
     */
    private boolean mTwoPane;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_playlist);

        // Show the Up button in the action bar.
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        String artistName = getIntent().getStringExtra(EXTRA_ARTIST_NAME);
        setTitle(artistName);

        if (null == savedInstanceState) {
            String artistId = getIntent().getStringExtra(EXTRA_ARTIST_ID);

            PlaylistFragment fragment = PlaylistFragment.newInstance(NB_SONGS_RESULTS, artistId);

            if (findViewById(R.id.song_detail_container) != null) {
                // The detail container view will be present only in the
                // large-screen layouts (res/values-large and
                // res/values-sw600dp). If this view is present, then the
                // activity should be in two-pane mode.
                mTwoPane = true;

                // In two-pane mode, list items should be given the
                // 'activated' state when touched.
                fragment.setActivateOnItemClick(true);
            }

            getSupportFragmentManager().beginTransaction()
                    .add(R.id.playlist_container, fragment)
                    .commit();
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == android.R.id.home) {
            NavUtils.navigateUpFromSameTask(this);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onSongClick(Song song) {
        if (mTwoPane) {
            // In two-pane mode, show the detail view in this activity by
            // adding or replacing the detail fragment using a
            // fragment transaction.
            Bundle arguments = new Bundle();
            arguments.putString(SongDetailFragment.ARG_ITEM_ID, song.getID());
            SongDetailFragment fragment = new SongDetailFragment();
            fragment.setArguments(arguments);
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.song_detail_container, fragment)
                    .commit();

        } else {
            // In single-pane mode, simply start the detail activity
            // for the selected item ID.
            Intent detailIntent = new Intent(this, SongDetailActivity.class);
            detailIntent.putExtra(SongDetailFragment.ARG_ITEM_ID, song.getID());
            startActivity(detailIntent);
        }
    }
}
