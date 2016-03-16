package com.drughub.doctor;

import com.drughub.doctor.Cart.CartActivity;
import com.drughub.doctor.consultation.ConsultationFragment;
import com.drughub.doctor.inventory.MyInventoryFragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;

public class MainActivity extends BaseActivity
{
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initTabControl();

        //inventory action buttons
        addActionButton(R.string.icon_cart);
        addActionButton(R.string.icon_notification);
        getSupportFragmentManager().beginTransaction().replace(R.id.container, new MyInventoryFragment()).commit();
    }

    @Override
    public void onActionButtonClicked(int drughubIconRes)
    {
        super.onActionButtonClicked(drughubIconRes);

        switch (drughubIconRes)
        {
            case R.string.icon_cart:
                //show cart
                Intent intent = new Intent(getBaseContext(), CartActivity.class);
                startActivity(intent);
                break;

            case R.string.icon_notification:
                //show notifications
                break;
        }
    }

    private void initTabControl() {
        TabLayout tabLayout = (TabLayout) findViewById(R.id.tabs);

        tabLayout.addTab(tabLayout.newTab().setText(getString(R.string.my_inventory)));//setIcon(R.drawable.ic_action_pickup);
        tabLayout.addTab(tabLayout.newTab().setText(getString(R.string.consultations)));
        tabLayout.addTab(tabLayout.newTab().setText(getString(R.string.analytics)));
        tabLayout.addTab(tabLayout.newTab().setText(getString(R.string.more)));
        tabLayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {

                clearActionButtons();

                Fragment fragment = null;
                switch (tab.getPosition()) {
                    case 0:
                        fragment = new MyInventoryFragment();
                        addActionButton(R.string.icon_cart);
                        addActionButton(R.string.icon_notification);
                        break;
                    case 1:
                        fragment = new ConsultationFragment();
                        addActionButton(R.string.icon_notification);
                        break;
                    case 2:
                        fragment = new ConsultationFragment();
                        break;
                    case 3:
                        fragment = new ConsultationFragment();
                        break;
                }
                if(fragment != null)
                    getSupportFragmentManager().beginTransaction().replace(R.id.container, fragment).commit();
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
    }
}
