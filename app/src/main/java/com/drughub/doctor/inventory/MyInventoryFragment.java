package com.drughub.doctor.inventory;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.drughub.doctor.BaseActivity;
import com.drughub.doctor.R;

public class MyInventoryFragment extends Fragment
{
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_my_inventory, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState)
    {
        getActivity().setTitle(getString(R.string.my_inventory));

        initInventoryTabs(view);

        getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.inventory_vaccines, new VaccineListFragment()).commit();

        initInventoryInfo(view, 0, 0, 0);
    }

    private void initInventoryTabs(View view) {
        TabLayout tabLayout = (TabLayout) view.findViewById(R.id.inventory_tabs);

        tabLayout.addTab(tabLayout.newTab().setText("HEP-B"));
        tabLayout.addTab(tabLayout.newTab().setText("DPT"));
        tabLayout.addTab(tabLayout.newTab().setText("POLIO"));
        tabLayout.addTab(tabLayout.newTab().setText("HEP-B"));
        tabLayout.addTab(tabLayout.newTab().setText("BCG"));
        tabLayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                Fragment fragment = new VaccineListFragment();
                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.inventory_vaccines, fragment).commit();
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {
            }
        });
    }

    public void initInventoryInfo(View view, int consumed, int available, int reserve)
    {
        View inventoryInfo = view.findViewById(R.id.inventory_consumed);
        initInventoryInfo(inventoryInfo, getString(R.string.inventory_consumed), consumed, R.color.colorPrimaryDull);

        inventoryInfo = view.findViewById(R.id.inventory_available);
        initInventoryInfo(inventoryInfo, getString(R.string.inventory_available), available, R.color.colorPrimaryLight);

        inventoryInfo = view.findViewById(R.id.inventory_reserve);
        initInventoryInfo(inventoryInfo, getString(R.string.inventory_reserve), reserve, R.color.colorPrimary);
    }

    public void initInventoryInfo(View view, String title, int count, int colorId)
    {
        TextView inventoryStatusTitle = (TextView) view.findViewById(R.id.inventory_status_title);
        inventoryStatusTitle.setText(title);

        TextView inventoryStatusCount = (TextView) view.findViewById(R.id.inventory_status_count);
        inventoryStatusCount.setText(String.format("%02d", count));

        View inventoryStatusColor = view.findViewById(R.id.inventory_status_color);

        inventoryStatusColor.setBackgroundColor(ContextCompat.getColor(getActivity(), colorId));
    }

    public void setInventoryInfo(int consumed, int available, int reserve)
    {
        if(getView() == null)
            return;

        View inventoryInfo = getView().findViewById(R.id.inventory_consumed);
        setInventoryInfo(inventoryInfo, consumed);

        inventoryInfo = getView().findViewById(R.id.inventory_available);
        setInventoryInfo(inventoryInfo, available);

        inventoryInfo = getView().findViewById(R.id.inventory_reserve);
        setInventoryInfo(inventoryInfo, reserve);
    }

    public void setInventoryInfo(View view, int count)
    {
        TextView inventoryStatusCount = (TextView) view.findViewById(R.id.inventory_status_count);
        inventoryStatusCount.setText(String.format("%02d", count));
    }
}
