package crm.gobelins.materialplaylist.artists;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.echonest.api.v4.Artist;

import java.util.List;

import crm.gobelins.materialplaylist.R;

/**
 * A fragment representing a list of Items.
 * <p/>
 * Large screen devices (such as tablets) are supported by replacing the ListView
 * with a GridView.
 * <p/>
 * Activities containing this fragment MUST implement the {@link crm.gobelins.materialplaylist.artists.ArtistsFragment.OnArtistClickListener}
 * interface.
 */
public class ArtistsFragment extends Fragment implements AbsListView.OnItemClickListener, LoaderManager.LoaderCallbacks<List<Artist>> {

    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_RESULTS = "results";
    private static final String ARG_GENRE = "genre";
    private static final int ARTISTS_LOADER_ID = 12;

    private String mGenre;
    private int mResults;

    private OnArtistClickListener mListener;

    /**
     * The fragment's ListView/GridView.
     */
    private AbsListView mListView;

    /**
     * The Adapter which will be used to populate the ListView/GridView with
     * Views.
     */
    private ArtistsAdapter mAdapter;
    private ProgressBar mProgressBar;

    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public ArtistsFragment() {
    }

    public static ArtistsFragment newInstance(int results, String genre) {
        ArtistsFragment fragment = new ArtistsFragment();
        Bundle args = new Bundle();
        args.putString(ARG_GENRE, genre);
        args.putInt(ARG_RESULTS, results);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments() != null) {
            mResults = getArguments().getInt(ARG_RESULTS);
            mGenre = getArguments().getString(ARG_GENRE);
        }

        mAdapter = new ArtistsAdapter(getActivity());
        getLoaderManager().initLoader(ARTISTS_LOADER_ID, null, this);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_artists, container, false);

        // Set the adapter
        mListView = (AbsListView) view.findViewById(android.R.id.list);
        mListView.setAdapter(mAdapter);

        // Set OnItemClickListener so we can be notified on item clicks
        mListView.setOnItemClickListener(this);
        mListView.setDrawSelectorOnTop(true);

        mProgressBar = (ProgressBar) view.findViewById(R.id.progressbar);

        return view;
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);

        // Activities containing this fragment must implement its callbacks.
        if (!(activity instanceof OnArtistClickListener)) {
            throw new IllegalStateException(activity.toString() + "must implement OnArtistClickListener.");
        }

        mListener = (OnArtistClickListener) activity;
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }


    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        if (null != mListener) {
            // Notify the active callbacks interface (the activity, if the
            // fragment is attached to one) that an item has been selected.
            mListener.onArtistClick(mAdapter.getItem(position));
        }
    }

    /**
     * The default content for this Fragment has a TextView that is shown when
     * the list is empty. If you would like to change the text, call this method
     * to supply the text it should use.
     */
    public void setEmptyText(CharSequence emptyText) {
        View emptyView = mListView.getEmptyView();

        if (emptyView instanceof TextView) {
            ((TextView) emptyView).setText(emptyText);
        }
    }

    @Override
    public Loader<List<Artist>> onCreateLoader(int id, Bundle args) {
        ArtistsLoader loader = new ArtistsLoader(getActivity());

        loader.setResults(mResults);
        loader.setGenre(mGenre);

        return loader;
    }

    @Override
    public void onLoadFinished(Loader<List<Artist>> loader, List<Artist> artists) {
        mProgressBar.setVisibility(View.GONE);
        mAdapter.clear();
        if (null != artists && !artists.isEmpty()) {
            mAdapter.addAll(artists);
        } else {
            setEmptyText(getActivity().getString(R.string.error_empty_artists));
        }
    }

    @Override
    public void onLoaderReset(Loader<List<Artist>> loader) {
        mAdapter.clear();
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p/>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnArtistClickListener {
        public void onArtistClick(Artist artist);
    }

}
