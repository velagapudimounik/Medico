package com.drughub.doctor;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.drughub.doctor.Cart.CartActivity;
import com.drughub.doctor.Notification.NotificationActivity;
import com.drughub.doctor.analytics.AnalyticsFragment;
import com.drughub.doctor.consultation.ConsultationFragment;
import com.drughub.doctor.inventory.MyInventoryFragment;
import com.drughub.doctor.inventory.VaccineListFragment;

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
                startActivity( new Intent(getBaseContext(), NotificationActivity.class));
                break;
        }
    }

    private TabLayout.Tab addTab(TabLayout tabLayout, int textRes, int imgRes)
    {
        TabLayout.Tab tab = tabLayout.newTab();
        tab.setCustomView(R.layout.main_tab_view);

        View view = tab.getCustomView();
        TextView tabName = (TextView)view.findViewById(R.id.tabName);
        tabName.setText(getString(textRes));

        ImageView tabIcon = (ImageView)view.findViewById(R.id.tabIcon);
        tabIcon.setBackgroundResource(imgRes);

        tabLayout.addTab(tab);

        return tab;
    }

    private void initTabControl() {
        TabLayout tabLayout = (TabLayout) findViewById(R.id.tabs);

        addTab(tabLayout, R.string.my_inventory, R.drawable.ic_vaccine);
        addTab(tabLayout, R.string.consultations, R.drawable.ic_consultations);
        addTab(tabLayout, R.string.analytics, R.drawable.ic_analytics);
        addTab(tabLayout, R.string.more, R.drawable.ic_more);

        tabLayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {

                clearActionButtons();
                setActionBarVisibility(true);

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
                        fragment = new AnalyticsFragment();
                        break;
                    case 3:
                        fragment = new MoreFragment();
                        setActionBarVisibility(false);
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
