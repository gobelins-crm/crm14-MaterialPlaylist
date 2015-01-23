package crm.gobelins.materialplaylist.songs;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.support.v7.app.ActionBarActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;

import com.echonest.api.v4.Song;
import com.squareup.picasso.Picasso;

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
        implements PlaylistFragment.PlaylistListener, View.OnClickListener {

    public static final String EXTRA_ARTIST_ID = "extraArtistId";
    public static final String EXTRA_ARTIST_NAME = "extraArtistName";
    public static final String EXTRA_ARTIST_IMAGE = "extraArtistImage";

    private static final int NB_SONGS_RESULTS = 3;
    private PlaylistFragment mFragment;
    private ProgressBar mProgress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_playlist);

        // Show the Up button in the action bar.
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        mProgress = (ProgressBar) findViewById(R.id.progressbar);
        findViewById(R.id.playlist_add_btn).setOnClickListener(this);

        String imageUrl = getIntent().getStringExtra(EXTRA_ARTIST_IMAGE);
        Picasso.with(this)
                .load(imageUrl)
                .fit()
                .centerCrop()
                .noFade()
                .into((android.widget.ImageView) findViewById(R.id.playlist_artist_image));


        String artistName = getIntent().getStringExtra(EXTRA_ARTIST_NAME);
        setTitle(artistName);

        if (null == savedInstanceState) {
            String artistId = getIntent().getStringExtra(EXTRA_ARTIST_ID);

            mFragment = PlaylistFragment.newInstance(NB_SONGS_RESULTS, artistId);

            getSupportFragmentManager().beginTransaction()
                    .add(R.id.playlist_container, mFragment, PlaylistFragment.TAG)
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
        // In single-pane mode, simply start the detail activity
        // for the selected item ID.
        Intent detailIntent = new Intent(this, SongDetailActivity.class);
        detailIntent.putExtra(SongDetailFragment.ARG_SONG_ID, song.getID());
        startActivity(detailIntent);
    }

    @Override
    public void onSongLoad() {
        mProgress.setVisibility(View.INVISIBLE);
    }

    @Override
    public void onClick(View v) {
        mProgress.setVisibility(View.VISIBLE);
        mFragment.addSomeSongs();
    }
}
