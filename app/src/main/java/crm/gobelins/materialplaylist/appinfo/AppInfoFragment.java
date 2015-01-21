package crm.gobelins.materialplaylist.appinfo;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import crm.gobelins.materialplaylist.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class AppInfoFragment extends Fragment {


    public AppInfoFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_app_info, container, false);
        TextView versionTv = (TextView) view.findViewById(R.id.app_version);

        String version = VersionInfo.getVersionName(getActivity());
        int revision = VersionInfo.getRevisionNumber(getActivity());
        String versionInfo = getActivity().getString(R.string.app_version, version, revision);

        versionTv.setText(versionInfo);

        return view;
    }


}
