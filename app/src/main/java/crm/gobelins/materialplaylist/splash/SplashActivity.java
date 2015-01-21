package crm.gobelins.materialplaylist.splash;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.FragmentActivity;

import crm.gobelins.materialplaylist.R;
import crm.gobelins.materialplaylist.home.HomeActivity;


public class SplashActivity extends FragmentActivity {

    private static final int SPLASH_TIME_OUT = 1500;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // The layout already contains an instance
        // of the AppInfoFragment
        setContentView(R.layout.activity_splash);

        new Handler().postDelayed(new Runnable() {

            /*
             * Showing splash screen with a timer. This will be useful when you
             * want to show case your app logo / company
             */

            @Override
            public void run() {
                Intent i = new Intent(SplashActivity.this, HomeActivity.class);

                // start Home Activity
                startActivity(i);

                // close the splashscreen
                finish();
            }
        }, SPLASH_TIME_OUT);
    }
}
