package crm.gobelins.materialplaylist.songs;

import android.content.Context;
import android.support.v4.content.AsyncTaskLoader;

import com.echonest.api.v4.EchoNestException;
import com.echonest.api.v4.Song;

import crm.gobelins.materialplaylist.server.ENApi;

public class SongLoader extends AsyncTaskLoader<Song> {

    private String mSongId;
    private Song mSong;

    public SongLoader(Context context) {
        super(context);
    }

    @Override
    public Song loadInBackground() {
        try {
            mSong = ENApi.with(getContext()).getSongById(mSongId);
        } catch (EchoNestException e) {
            e.printStackTrace();
            mSong = null;
        }

        return mSong;
    }

    @Override
    protected void onStartLoading() {
        if (null != mSong) {
            deliverResult(mSong);
        } else {
            forceLoad();
        }
    }

    public void setSongId(String songId) {
        mSongId = songId;
    }
}
