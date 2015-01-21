package crm.gobelins.materialplaylist.home;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;

import crm.gobelins.materialplaylist.BuildConfig;
import crm.gobelins.materialplaylist.R;
import crm.gobelins.materialplaylist.about.AboutActivity;
import crm.gobelins.materialplaylist.server.ENApi;

public class HomeActivity extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        if (BuildConfig.DEBUG) {
            // Check if EchoNest API is working in debug mode
            ENApi.with(this).dumpStats();
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_home, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        if (id == R.id.action_about) {
            startActivity(new Intent(this, AboutActivity.class));

            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
