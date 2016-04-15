package com.drughub.doctor.MyProfile;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.widget.Button;

import com.drughub.doctor.BaseActivity;
import com.drughub.doctor.R;
import com.drughub.doctor.model.ServiceProvider;
import com.drughub.doctor.network.Globals;
import com.drughub.doctor.network.Urls;
import com.drughub.doctor.utils.PrefUtils;

import org.json.JSONObject;

import io.realm.Realm;

public class MyProfileActivity extends BaseActivity {
    Fragment fragment = null;
    Realm realm;
    ProgressDialog progress;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.myprofile_activity);
        realm = Realm.getDefaultInstance();
        final Button myClinicButton = (Button) findViewById(R.id.Myclinic_button);
        setBackButton(true);
        progress = ProgressDialog.show(MyProfileActivity.this, "", "Please wait...", true);
        Globals.GET(Urls.SERVICE_PROVIDER + PrefUtils.getUserName(getApplicationContext()), null, new Globals.VolleyCallback() {
            @Override
            public void onSuccess(String result) {
                try {
                    Log.v("SP GET result", result);
                    JSONObject object = new JSONObject(result);
                    if (object.getBoolean("result")) {
                        realm.beginTransaction();
                        realm.allObjects(ServiceProvider.class).clear();
                        realm.createOrUpdateObjectFromJson(ServiceProvider.class, object.getJSONObject("response"));
                        realm.commitTransaction();
                    }
                    if (progress != null)
                        progress.dismiss();

                    fragment = new MyProfileFragment();
                    FragmentManager manager = getSupportFragmentManager();
                    FragmentTransaction transaction = manager.beginTransaction();
                    transaction.replace(R.id.containeractivity, fragment).commit();

                } catch (Exception e) {
                    e.printStackTrace();
                    progress.dismiss();
                }
            }

            @Override
            public void onFail(String result) {
                if (progress != null)
                    progress.dismiss();
            }
        });

    }
}
