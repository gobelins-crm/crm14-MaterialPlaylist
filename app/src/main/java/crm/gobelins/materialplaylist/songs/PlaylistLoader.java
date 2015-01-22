package crm.gobelins.materialplaylist.songs;

import android.content.Context;
import android.support.v4.content.AsyncTaskLoader;

import com.echonest.api.v4.EchoNestException;
import com.echonest.api.v4.Playlist;

import crm.gobelins.materialplaylist.server.ENApi;

public class PlaylistLoader extends AsyncTaskLoader<Playlist> {

    private Playlist mPlaylist;
    private String mArtistId;
    private int mResults;

    public PlaylistLoader(Context context) {
        super(context);
    }

    @Override
    public Playlist loadInBackground() {
        try {
            mPlaylist = ENApi.with(getContext()).getArtistRadioByArtistId(mArtistId, mResults);
        } catch (EchoNestException e) {
            mPlaylist = null;
            e.printStackTrace();
        }

        return mPlaylist;
    }

    @Override
    protected void onStartLoading() {
        if (null != mPlaylist) {
            deliverResult(mPlaylist);
        } else {
            forceLoad();
        }
    }

    public void setArtistId(String artistId) {
        mArtistId = artistId;
    }

    public void setResults(int results) {
        mResults = results;
    }
}
