package crm.gobelins.materialplaylist.about;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;

import crm.gobelins.materialplaylist.R;
import crm.gobelins.materialplaylist.appinfo.AppInfoFragment;

public class AboutActivity extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // The layout only contains a FrameLayout
        // with id: "app_info_container"
        setContentView(R.layout.activity_about);

        // Dynamically add a new AppInfoFragment
        // into the view hierarchy
        getSupportFragmentManager()
                .beginTransaction()
                .add(R.id.app_info_container, new AppInfoFragment())
                .commit();
    }
}
