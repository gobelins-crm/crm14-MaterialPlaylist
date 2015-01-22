package crm.gobelins.materialplaylist.about;

import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.support.v7.app.ActionBarActivity;
import android.view.MenuItem;

import crm.gobelins.materialplaylist.R;
import crm.gobelins.materialplaylist.appinfo.AppInfoFragment;

public class AboutActivity extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // The layout only contains a FrameLayout
        // with id: "app_info_container"
        setContentView(R.layout.activity_about);

        // Show the Up button in the action bar.
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        if (null == savedInstanceState) {

            // Dynamically add a new AppInfoFragment
            // into the view hierarchy
            getSupportFragmentManager()
                    .beginTransaction()
                    .add(R.id.app_info_container, new AppInfoFragment())
                    .commit();
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == android.R.id.home) {
            NavUtils.navigateUpFromSameTask(this);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
