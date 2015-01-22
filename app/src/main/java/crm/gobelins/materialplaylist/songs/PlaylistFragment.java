package crm.gobelins.materialplaylist.songs;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.view.View;
import android.widget.ListView;

import com.echonest.api.v4.Playlist;
import com.echonest.api.v4.Song;

/**
 * A list fragment representing a list of Songs. This fragment
 * also supports tablet devices by allowing list items to be given an
 * 'activated' state upon selection. This helps indicate which item is
 * currently being viewed in a {@link crm.gobelins.materialplaylist.songs.SongDetailFragment}.
 * <p/>
 * Activities containing this fragment MUST implement the {@link crm.gobelins.materialplaylist.songs.PlaylistFragment.OnSongClickListener}
 * interface.
 */
public class PlaylistFragment extends ListFragment implements LoaderManager.LoaderCallbacks<Playlist> {

    public static final String TAG = "PlaylistFragmentTag";
    /**
     * The serialization (saved instance state) Bundle key representing the
     * activated item position. Only used on tablets.
     */
    private static final String STATE_ACTIVATED_POSITION = "activatedPosition";
    private static final String ARG_ARTIST_ID = "artistId";
    private static final String ARG_RESULTS = "results";
    private static final int PLAYLIST_LOADER_ID = 1212;
    private String mArtistId;
    private int mResults;

    private OnSongClickListener mListener;
    private SongsAdapter mAdapter;

    /**
     * The current activated item position. Only used on tablets.
     */
    private int mActivatedPosition = ListView.INVALID_POSITION;
    private boolean mActivateOnItemClick = false;

    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public PlaylistFragment() {
    }

    public static PlaylistFragment newInstance(int results, String artistId) {
        PlaylistFragment fragment = new PlaylistFragment();
        Bundle args = new Bundle();

        args.putString(ARG_ARTIST_ID, artistId);
        args.putInt(ARG_RESULTS, results);

        fragment.setArguments(args);

        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (null != getArguments()) {
            mArtistId = getArguments().getString(ARG_ARTIST_ID);
            mResults = getArguments().getInt(ARG_RESULTS);
        }

        mAdapter = new SongsAdapter(getActivity());
        setListAdapter(mAdapter);

        getLoaderManager().initLoader(PLAYLIST_LOADER_ID, null, this);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        setActivateOnItemClick();

        // Restore the previously serialized activated item position.
        if (savedInstanceState != null
                && savedInstanceState.containsKey(STATE_ACTIVATED_POSITION)) {
            setActivatedPosition(savedInstanceState.getInt(STATE_ACTIVATED_POSITION));
        }
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);

        // Activities containing this fragment must implement its callbacks.
        if (!(activity instanceof OnSongClickListener)) {
            throw new IllegalStateException(activity.toString() + "must implement OnSongClickListener.");
        }

        mListener = (OnSongClickListener) activity;
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    @Override
    public void onListItemClick(ListView listView, View view, int position, long id) {
        super.onListItemClick(listView, view, position, id);

        // Notify the active callbacks interface (the activity, if the
        // fragment is attached to one) that an item has been selected.
        mListener.onSongClick(mAdapter.getItem(position));
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        if (mActivatedPosition != ListView.INVALID_POSITION) {
            // Serialize and persist the activated item position.
            outState.putInt(STATE_ACTIVATED_POSITION, mActivatedPosition);
        }
    }

    /**
     * Turns on activate-on-click mode. When this mode is on, list items will be
     * given the 'activated' state when touched.
     */
    public void setActivateOnItemClick(boolean activateOnItemClick) {
        mActivateOnItemClick = activateOnItemClick;
    }

    private void setActivateOnItemClick() {
        // When setting CHOICE_MODE_SINGLE, ListView will automatically
        // give items the 'activated' state when touched.
        getListView().setChoiceMode(mActivateOnItemClick
                ? ListView.CHOICE_MODE_SINGLE
                : ListView.CHOICE_MODE_NONE);
    }

    private void setActivatedPosition(int position) {
        if (position == ListView.INVALID_POSITION) {
            getListView().setItemChecked(mActivatedPosition, false);
        } else {
            getListView().setItemChecked(position, true);
        }

        mActivatedPosition = position;
    }

    @Override
    public Loader<Playlist> onCreateLoader(int id, Bundle args) {
        PlaylistLoader loader = new PlaylistLoader(getActivity());

        loader.setResults(mResults);
        loader.setArtistId(mArtistId);

        return loader;
    }

    @Override
    public void onLoadFinished(Loader<Playlist> loader, Playlist playlist) {
        mAdapter.clear();
        if (null != playlist) {
            mAdapter.addAll(playlist.getSongs());
        } else {
            // TODO: handle playlist loading error
        }
    }

    @Override
    public void onLoaderReset(Loader<Playlist> loader) {
        mAdapter.clear();
    }

    public interface OnSongClickListener {
        public void onSongClick(Song song);
    }
}
