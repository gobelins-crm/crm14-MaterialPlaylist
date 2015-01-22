package crm.gobelins.materialplaylist.server;

import android.content.Context;

import com.echonest.api.v4.Artist;
import com.echonest.api.v4.ArtistParams;
import com.echonest.api.v4.EchoNestAPI;
import com.echonest.api.v4.EchoNestException;
import com.echonest.api.v4.Playlist;
import com.echonest.api.v4.PlaylistParams;
import com.echonest.api.v4.Song;
import com.echonest.api.v4.SongParams;

import java.util.List;

import crm.gobelins.materialplaylist.R;

public class ENApi {
    private static ENApi sInstance;
    private EchoNestAPI mEchoNestApi;
    private List<String> mGenres;

    private ENApi(Context context) {
        mEchoNestApi = new EchoNestAPI(context.getString(R.string.echonest_api_key));
    }

    public static ENApi with(Context context) {
        if (null == sInstance) {
            sInstance = new ENApi(context);
        }
        return sInstance;
    }

    public void dumpStats() {
        mEchoNestApi.showStats();
    }

    public void syncAllGenres() throws EchoNestException {
        mGenres = mEchoNestApi.listGenres();
    }

    public List<Artist> getHotArtistsByGenre(int results, String genre) throws EchoNestException {
        ArtistParams params = new ArtistParams();

        params.includeImages();
        params.setResults(results);
        params.addGenre(genre);

        return mEchoNestApi.topHotArtists(params);
    }

    public List<String> getGenres() {
        return mGenres;
    }

    public Playlist getArtistRadioByArtistId(String artistId, int results) throws EchoNestException {
        PlaylistParams params = new PlaylistParams();

        params.addIDSpace("7digital-US");
        params.includeTracks();
        params.setType(PlaylistParams.PlaylistType.ARTIST_RADIO);

        params.addArtistID(artistId);
        params.setResults(results);

        return mEchoNestApi.createStaticPlaylist(params);
    }

    public Song getSongById(String songId) throws EchoNestException {
        SongParams params = new SongParams();

        params.addIDSpace("7digital-US");
        params.includeTracks();
        params.setResults(1);
        params.setID(songId);

        List<Song> songs = mEchoNestApi.getSongs(params);
        Song song = null;

        if (!songs.isEmpty()) {
            song = songs.get(0);
        }

        return song;
    }
}
