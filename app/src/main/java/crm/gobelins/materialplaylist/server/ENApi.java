package crm.gobelins.materialplaylist.server;

import android.content.Context;

import com.echonest.api.v4.EchoNestAPI;

import crm.gobelins.materialplaylist.R;

public class ENApi {
    private static ENApi sInstance;
    private EchoNestAPI mEchoNestApi;

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
}
