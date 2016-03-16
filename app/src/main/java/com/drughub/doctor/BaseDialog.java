package com.drughub.doctor;

import android.app.Dialog;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.support.v4.app.FragmentManager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.widget.Toolbar;
import android.view.Window;
import android.widget.FrameLayout;
import android.widget.TextView;

public class BaseDialog extends Dialog
{
    public BaseDialog(Context context, int layoutResId, int themeResId) {
        super(context, themeResId);

        setContentView(layoutResId);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setBackgroundDrawable(new ColorDrawable(0x88000000));
    }


//    @Override
//    public void setContentView(int layoutResID)
//    {
//        DrawerLayout fullView = (DrawerLayout) getLayoutInflater().inflate(R.layout.activity_base, null);
//        FrameLayout activityContainer = (FrameLayout) fullView.findViewById(R.id.activity_content);
//        getLayoutInflater().inflate(layoutResID, activityContainer, true);
//        super.setContentView(fullView);
//
//        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
//
//        setSupportActionBar(toolbar);
//        mTitleText = (TextView) toolbar.findViewById(R.id.toolbar_title);
//
//        mActionBar = getSupportActionBar();
//        if(mActionBar != null) {
//            mActionBar.setDisplayShowTitleEnabled(false);
//            mActionBar.setHomeAsUpIndicator(R.drawable.ic_arrow_back_white_24dp);
//        }
//
//        setTitle(getString(R.string.app_name));
//        setBackButton(false);
//
//        getSupportFragmentManager().addOnBackStackChangedListener(
//                new FragmentManager.OnBackStackChangedListener() {
//                    public void onBackStackChanged() {
//                        int backStackCount = getSupportFragmentManager().getBackStackEntryCount();
//                        setBackButton(backStackCount != 0);
//                    }
//                });
//    }
}
