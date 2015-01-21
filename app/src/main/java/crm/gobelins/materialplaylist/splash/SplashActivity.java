package crm.gobelins.materialplaylist.splash;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.widget.Toast;

import com.echonest.api.v4.EchoNestException;

import crm.gobelins.materialplaylist.R;
import crm.gobelins.materialplaylist.home.HomeActivity;
import crm.gobelins.materialplaylist.server.ENApi;


public class SplashActivity extends FragmentActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // The layout already contains an instance
        // of the AppInfoFragment
        setContentView(R.layout.activity_splash);

        // Test if we have access to the EchoNest API
        new SyncAllGenresTask().execute();
    }

    private class SyncAllGenresTask extends AsyncTask<String, Void, Boolean> {

        @Override
        protected Boolean doInBackground(String... params) {
            boolean hasFailed = false;
            try {
                ENApi.with(SplashActivity.this).syncAllGenres();
            } catch (EchoNestException e) {
                hasFailed = true;
            }
            return hasFailed;
        }

        @Override
        protected void onPostExecute(Boolean hasFailed) {
            if (hasFailed) {
                Toast.makeText(SplashActivity.this, getString(R.string.error_api_unreachable), Toast.LENGTH_LONG)
                        .show();
            }

            Intent i = new Intent(SplashActivity.this, HomeActivity.class);

            // start Home Activity
            startActivity(i);

            // close the splashscreen
            finish();
        }
    }
}
