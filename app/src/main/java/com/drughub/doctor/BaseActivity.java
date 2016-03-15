package com.drughub.doctor;

import android.app.Activity;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffColorFilter;
import android.support.v4.app.FragmentManager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.view.menu.ActionMenuItemView;
import android.support.v7.widget.ActionMenuView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.TextView;

public class BaseActivity extends AppCompatActivity {

    private ActionBar mActionBar;
    private TextView mTitleText;

    @Override
    public void setContentView(int layoutResID)
    {
        DrawerLayout fullView = (DrawerLayout) getLayoutInflater().inflate(R.layout.activity_base, null);
        FrameLayout activityContainer = (FrameLayout) fullView.findViewById(R.id.activity_content);
        getLayoutInflater().inflate(layoutResID, activityContainer, true);
        super.setContentView(fullView);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        mTitleText = (TextView) toolbar.findViewById(R.id.toolbar_title);

        mActionBar = getSupportActionBar();
        if(mActionBar != null) {
            mActionBar.setDisplayShowTitleEnabled(false);
            mActionBar.setHomeAsUpIndicator(R.drawable.ic_arrow_back_white_24dp);
        }

        setTitle(getString(R.string.app_name));
        setBackButton(false);

        getSupportFragmentManager().addOnBackStackChangedListener(
                new FragmentManager.OnBackStackChangedListener() {
                    public void onBackStackChanged() {
                        int backStackCount = getSupportFragmentManager().getBackStackEntryCount();
                        setBackButton(backStackCount != 0);
                    }
                });
    }

    @Override
    public void setTitle(CharSequence title)
    {
        mTitleText.setText(title);
    }

    public void setBackButton(boolean enable)
    {
        if(mActionBar != null)
            mActionBar.setDisplayHomeAsUpEnabled(enable);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        switch (item.getItemId()) {
            case android.R.id.home: {
                super.onBackPressed();
                return true;
            }
        }

        return super.onOptionsItemSelected(item);
    }
}

