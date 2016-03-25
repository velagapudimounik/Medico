package com.drughub.doctor;

import android.support.v4.app.FragmentManager;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.drughub.doctor.utils.DrughubIcon;

import java.util.ArrayList;
import java.util.List;

public class BaseActivity extends AppCompatActivity {

    private ActionBar mActionBar;
    private TextView mTitleText;
    private Toolbar mToolbar;
    private List<View> actionBtns = new ArrayList<>();

    @Override
    public void setContentView(int layoutResID) {
        LinearLayout fullView = (LinearLayout) getLayoutInflater().inflate(R.layout.activity_base, null);
        FrameLayout activityContainer = (FrameLayout) fullView.findViewById(R.id.activity_content);
        getLayoutInflater().inflate(layoutResID, activityContainer, true);
        super.setContentView(fullView);

        mToolbar = (Toolbar) findViewById(R.id.toolbar);

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
                super.onBackPressed();
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
        actionBtn.setTag(drughubIconRes);

        Toolbar.LayoutParams params = new Toolbar.LayoutParams(Toolbar.LayoutParams.WRAP_CONTENT, Toolbar.LayoutParams.WRAP_CONTENT);
        params.gravity = Gravity.RIGHT;
        actionBtn.setLayoutParams(params);

        int pixels = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 10, getResources().getDisplayMetrics());
        actionBtn.setPadding(0, 0, pixels, 0);

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

        if (getSupportFragmentManager().getBackStackEntryCount() == 1) {
            finish();
        } else {
            super.onBackPressed();
        }
    }

}

