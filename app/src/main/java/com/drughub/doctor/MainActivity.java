package com.drughub.doctor;

import com.drughub.doctor.Cart.CartActivity;
import com.drughub.doctor.inventory.MyInventoryFragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private TextView mTitleText = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        mTitleText = (TextView) toolbar.findViewById(R.id.toolbar_title);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        if(actionBar != null) actionBar.setDisplayShowTitleEnabled(false);

        initTabControl();

        getSupportFragmentManager().beginTransaction().replace(R.id.container, new MyInventoryFragment()).commit();
    }

    //Method for setting title for action bar
    public void setTitle(String title)
    {
        mTitleText.setText(title);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
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
                Fragment fragment = null;
                switch (tab.getPosition()) {
                    case 0:
                        fragment = new MyInventoryFragment();
                        break;
                    case 1:
                        fragment = new MyInventoryFragment();
                        break;
                    case 2:
                        fragment = new MyInventoryFragment();
                        break;
                    case 3:
                        //fragment =
                        Intent intent = new Intent(getBaseContext(), CartActivity.class);
                        startActivity(intent);
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
