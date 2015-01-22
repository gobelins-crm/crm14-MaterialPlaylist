package crm.gobelins.materialplaylist.songs;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.echonest.api.v4.Song;

import crm.gobelins.materialplaylist.R;

/**
 * A fragment representing a single Song detail screen.
 * This fragment is either contained in a {@link PlaylistActivity}
 * in two-pane mode (on tablets) or a {@link crm.gobelins.materialplaylist.songs.SongDetailActivity}
 * on handsets.
 */
public class SongDetailFragment extends Fragment implements LoaderManager.LoaderCallbacks<Song> {
    /**
     * The fragment argument representing the item ID that this fragment
     * represents.
     */
    public static final String ARG_SONG_ID = "songId";
    private static final int SONG_LOADER_ID = 121212;
    private String mSongId;
    private TextView mTitleTv;

    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public SongDetailFragment() {
    }

    public static SongDetailFragment newInstance(String songId) {
        SongDetailFragment fragment = new SongDetailFragment();
        Bundle args = new Bundle();

        args.putString(ARG_SONG_ID, songId);
        fragment.setArguments(args);

        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (null != getArguments()) {
            mSongId = (String) getArguments().get(ARG_SONG_ID);
        }

        getLoaderManager().initLoader(SONG_LOADER_ID, null, this);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_song_detail, container, false);

        mTitleTv = (TextView) rootView.findViewById(R.id.song_detail);

        return rootView;
    }

    @Override
    public Loader<Song> onCreateLoader(int id, Bundle args) {
        SongLoader loader = new SongLoader(getActivity());

        loader.setSongId(mSongId);

        return loader;
    }

    @Override
    public void onLoadFinished(Loader<Song> loader, Song song) {
        if (null != song) {
            mTitleTv.setText(song.getTitle());
        } else {
            Toast.makeText(getActivity(), getActivity().getString(R.string.error_loading_song), Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public void onLoaderReset(Loader<Song> loader) {

    }
}
