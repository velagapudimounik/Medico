package com.drughub.doctor;

import android.support.v4.app.FragmentManager;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.drughub.doctor.model.Country;
import com.drughub.doctor.network.Globals;
import com.drughub.doctor.utils.DrughubIcon;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import io.realm.Realm;

public class BaseActivity extends AppCompatActivity {

    private ActionBar mActionBar;
    private TextView mTitleText;
    private Toolbar mToolbar;
    private List<View> actionBtns = new ArrayList<>();
    private Globals globals;
    private Realm realm;


    @Override
    public void setContentView(int layoutResID) {
        LinearLayout fullView = (LinearLayout) getLayoutInflater().inflate(R.layout.activity_base, null);
        FrameLayout activityContainer = (FrameLayout) fullView.findViewById(R.id.activity_content);
        getLayoutInflater().inflate(layoutResID, activityContainer, true);
        super.setContentView(fullView);
        globals = new Globals(getApplicationContext());

        Globals.getCountries(new Globals.VolleyCallback() {
            @Override
            public void onSuccess(String result) {
                try {
                    realm = Realm.getDefaultInstance();
                    realm.beginTransaction();
                    realm.allObjects(Country.class).clear();
                    realm.createAllFromJson(Country.class, (new JSONObject(result)).getJSONArray("response").toString());
                    realm.commitTransaction();
                    realm.close();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFail(String result) {

            }
        });
        Globals.getSpecialization(new Globals.VolleyCallback() {
            @Override
            public void onSuccess(String result) {
                Log.v("resultSP", result);
            }

            @Override
            public void onFail(String result) {
                Log.v("FailSp", result);

            }
        });
        Globals.getQualification(new Globals.VolleyCallback() {
            @Override
            public void onSuccess(String result) {
                Log.v("resultQL=", result);
            }

            @Override
            public void onFail(String result) {
                Log.v("FailQl=", result);

            }
        });
        mToolbar = (Toolbar) findViewById(R.id.toolbar);

        int pixels = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 3, getResources().getDisplayMetrics());
        mToolbar.setPadding(pixels, pixels, pixels, pixels);

        setSupportActionBar(mToolbar);
        mTitleText = (TextView) mToolbar.findViewById(R.id.toolbar_title);

        mActionBar = getSupportActionBar();
        if (mActionBar != null) {
            mActionBar.setDisplayShowTitleEnabled(false);
            mActionBar.setHomeAsUpIndicator(R.drawable.ic_arrow_back_white_24dp);
        }

        setTitle(getString(R.string.app_name));
        setBackButton(false);

        getSupportFragmentManager().addOnBackStackChangedListener(
                new FragmentManager.OnBackStackChangedListener() {
                    public void onBackStackChanged() {
                        int backStackCount = getSupportFragmentManager().getBackStackEntryCount();
                        setBackButton(!isTaskRoot() || (backStackCount != 0));
                    }
                });
    }

    @Override
    public void setTitle(CharSequence title) {
        mTitleText.setText(title);
    }

    public void setBackButton(boolean enable) {
        if (mActionBar != null)
            mActionBar.setDisplayHomeAsUpEnabled(enable);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home: {
                if (getSupportFragmentManager().getBackStackEntryCount() == 0) {
                    finish();
                } else {
                    super.onBackPressed();
                }
                return true;
            }
        }

        return super.onOptionsItemSelected(item);
    }

    public void onActionButtonClicked(int drughubIconRes) {

    }

    public View addActionButton(int drughubIconRes) {
        DrughubIcon actionBtn = new DrughubIcon(this);
        actionBtn.setText(getString(drughubIconRes));
        actionBtn.setTextColor(ContextCompat.getColor(this, R.color.colorWhite));
        actionBtn.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 25);
        actionBtn.setBackgroundResource(R.drawable.background_selector_action_button);
        actionBtn.setTag(drughubIconRes);

        int pixels = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 8, getResources().getDisplayMetrics());
        actionBtn.setPadding(pixels, pixels, pixels, pixels);

        Toolbar.LayoutParams params = new Toolbar.LayoutParams(Toolbar.LayoutParams.WRAP_CONTENT, Toolbar.LayoutParams.WRAP_CONTENT);
        params.gravity = Gravity.RIGHT;
        actionBtn.setLayoutParams(params);

        mToolbar.addView(actionBtn);
        actionBtns.add(actionBtn);

        actionBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onActionButtonClicked((int) v.getTag());
            }
        });

        return actionBtn;
    }

    public void removeActionButton(int drughubIconRes) {
        View v = mToolbar.findViewWithTag(drughubIconRes);
        if (v != null) {
            mToolbar.removeView(v);
            actionBtns.remove(v);
        }
    }

    public void clearActionButtons() {
        for (View v : actionBtns) {
            mToolbar.removeView(v);
        }

        actionBtns.clear();
    }

    public int getActionBarHeight() {
        return mToolbar.getHeight();
    }


    public void setActionBarVisibility(boolean visibility) {
        if (visibility) {
            mToolbar.setVisibility(View.VISIBLE);
        } else {
            mToolbar.setVisibility(View.GONE);
        }
    }

    @Override
    public void onBackPressed() {

        if (getSupportFragmentManager().getBackStackEntryCount() == 0) {
            finish();
        } else {
            super.onBackPressed();
        }
    }

}

