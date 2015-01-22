package crm.gobelins.materialplaylist.artists;

import android.content.Context;
import android.support.v4.content.AsyncTaskLoader;

import com.echonest.api.v4.Artist;
import com.echonest.api.v4.EchoNestException;

import java.util.List;

import crm.gobelins.materialplaylist.server.ENApi;

public class ArtistsLoader extends AsyncTaskLoader<List<Artist>> {

    private List<Artist> mArtists;
    private int mResults;
    private String mGenre;

    public ArtistsLoader(Context context) {
        super(context);
    }

    @Override
    public List<Artist> loadInBackground() {
        try {
            mArtists = ENApi.with(getContext()).getHotArtistsByGenre(mResults, mGenre);
        } catch (EchoNestException e) {
            mArtists = null;
            e.printStackTrace();
        }

        return mArtists;
    }

    @Override
    protected void onStartLoading() {
        if (null != mArtists) {
            deliverResult(mArtists);
        } else {
            forceLoad();
        }
    }

    public void setResults(int results) {
        mResults = results;
    }

    public void setGenre(String genre) {
        mGenre = genre;
    }
}
