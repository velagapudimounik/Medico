package com.drughub.doctor.inventory;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.drughub.doctor.R;

public class MyInventoryFragment extends Fragment
{
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.inventory_main, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState)
    {
        getActivity().setTitle(getString(R.string.my_inventory));
        initInventoryTabs(view);
        initInventoryInfo(view, 0, 0, 0);
    }

    private TabLayout.Tab addTab(TabLayout tabLayout, String text)
    {
        TabLayout.Tab tab = tabLayout.newTab();
        tab.setCustomView(R.layout.inventory_disease_tab);

        View view = tab.getCustomView();
        TextView tabName = (TextView)view.findViewById(R.id.tabName);
        tabName.setText(text);

        tabLayout.addTab(tab);

        return tab;
    }

    private void selectTab(TabLayout.Tab tab)
    {
        View view = tab.getCustomView();
        LinearLayout.LayoutParams lp = (LinearLayout.LayoutParams)view.getLayoutParams();
        lp.setMargins(0, 0, 1, 0);
        view.setLayoutParams(lp);

        TextView tabName = (TextView)view.findViewById(R.id.tabName);
        tabName.setTextColor(ContextCompat.getColor(getActivity(), R.color.colorPrimary));

        getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.inventory_vaccines, new VaccineListFragment()).commit();
    }

    private void initInventoryTabs(View view) {
        TabLayout tabLayout = (TabLayout) view.findViewById(R.id.inventory_disease_tabs);

        String diseases[] = {"HEP-B", "DPT", "POLIO", "BCG", "HEP-B", "DPT", "POLIO", "BCG"};
        for(int i=0; i<diseases.length; i++)
            addTab(tabLayout, diseases[i]);

        selectTab(tabLayout.getTabAt(0));

        tabLayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                selectTab(tab);
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
                View view = tab.getCustomView();
                LinearLayout.LayoutParams lp = (LinearLayout.LayoutParams)view.getLayoutParams();
                int pixels = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 5, getResources().getDisplayMetrics());
                lp.setMargins(0, 0, 1, pixels);
                view.setLayoutParams(lp);

                TextView tabName = (TextView)view.findViewById(R.id.tabName);
                tabName.setTextColor(ContextCompat.getColor(getActivity(), R.color.colorText));
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
