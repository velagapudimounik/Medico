package com.drughub.doctor.MyProfile;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.widget.Button;

import com.drughub.doctor.BaseActivity;
import com.drughub.doctor.R;
import com.drughub.doctor.model.Country;
import com.drughub.doctor.model.ServiceProvider;
import com.drughub.doctor.network.Globals;
import com.drughub.doctor.network.Urls;
import com.drughub.doctor.utils.PrefUtils;

import org.json.JSONException;
import org.json.JSONObject;

import io.realm.Realm;

public class MyProfileActivity extends BaseActivity {
    Fragment fragment = null;
    Realm realm;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.myprofile_activity);
        realm = Realm.getDefaultInstance();
        setBackButton(true);

        Globals.getCountries(new Globals.VolleyCallback() {
            @Override
            public void onSuccess(String result) {
                try {
                    realm.beginTransaction();
                    realm.allObjects(Country.class).clear();
                    realm.createAllFromJson(Country.class, (new JSONObject(result)).getJSONArray("response").toString());
                    realm.commitTransaction();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFail(String result) {

            }
        });

        Globals.GET(Urls.SERVICE_PROVIDER + PrefUtils.getUserName(getApplicationContext()), new Globals.VolleyCallback() {
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

                    getSupportFragmentManager().beginTransaction().replace(R.id.containeractivity, new MyProfileFragment()).commit();

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFail(String result) {
            }
        }, "");
    }
}
