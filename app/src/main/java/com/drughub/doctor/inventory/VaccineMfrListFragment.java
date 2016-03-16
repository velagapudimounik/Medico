package com.drughub.doctor.inventory;


import android.app.Dialog;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;

import com.drughub.doctor.R;
import com.drughub.doctor.utils.SimpleDividerItemDecoration;

import org.w3c.dom.Text;

import java.util.ArrayList;

class VaccineMfrItem
{
    public String manufacturerName;
    public VaccineMfrItem(String name)
    {
        manufacturerName = name;
    }
}

public class VaccineMfrListFragment extends Fragment {
    RecyclerView mRecyclerView;
    String mVaccineName;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        mVaccineName = getArguments().getString("name");
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_inventory_vaccine_mfr_list, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState)
    {
        mRecyclerView = (RecyclerView) view.findViewById(R.id.inventory_manufacturers_list);
        mRecyclerView.setHasFixedSize(true);

        mRecyclerView.addItemDecoration(new SimpleDividerItemDecoration(getActivity()));

        LinearLayoutManager mLayoutManager = new LinearLayoutManager(getActivity());
        mRecyclerView.setLayoutManager(mLayoutManager);

        ArrayList<VaccineMfrItem> mDataset = new ArrayList<>();

        for(int i=0; i<5; i++)
        {
            VaccineMfrItem item = new VaccineMfrItem("Manufacturer"+i);
            mDataset.add(item);
        }

        VaccineMfrListAdapter mAdapter = new VaccineMfrListAdapter(mDataset, getActivity(), mVaccineName);
        mRecyclerView.setAdapter(mAdapter);
    }

    public static class VaccineMfrListAdapter extends RecyclerView.Adapter<VaccineMfrListAdapter.ViewHolder>
    {
        private ArrayList<VaccineMfrItem> mDataSet;
        static FragmentActivity sContext;
        String mVaccineName;

        public static class ViewHolder extends RecyclerView.ViewHolder {
            TextView vaccineName;
            private final TextView textView;
            private View mItemView;
            private View mPurchaseBtn;

            public ViewHolder(View v)
            {
                super(v);

                mItemView = v;

                v.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v)
                    {

                    }
                });

                textView = (TextView) v.findViewById(R.id.textManufacturerName);
                vaccineName = (TextView) v.findViewById(R.id.textVaccineName);
                mPurchaseBtn = v.findViewById(R.id.itemPurchaseBtn);

                mPurchaseBtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v)
                    {
                        final Dialog dialog = new Dialog(sContext);
                        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                        dialog.setContentView(R.layout.dialog_add_to_cart);
                        Spinner dropdown = (Spinner)dialog.findViewById(R.id.add_to_cart_db_quantity_ropDown);
                        String[] items = new String[]{"0.5ML", "1ML", "2ML"};
                        ArrayAdapter<String> adapter = new ArrayAdapter<>(sContext, android.R.layout.simple_spinner_dropdown_item, items);
                        dropdown.setAdapter(adapter);
                        View closeBtn = dialog.findViewById(R.id.add_to_cart_db_close_btn);
                        closeBtn.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                dialog.dismiss();
                            }
                        });
                        View addBtn = dialog.findViewById(R.id.add_to_cart_db_add_btn);
                        addBtn.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                dialog.dismiss();
                            }
                        });
                        dialog.show();
                    }
                });
            }

            public void setItemSelected(boolean selected)
            {
                mItemView.setSelected(selected);
            }

            public void setItemDetails(VaccineMfrItem item, String vaccineNameText)
            {
                textView.setText(item.manufacturerName);
                vaccineName.setText(vaccineNameText);
            }
        }

        public VaccineMfrListAdapter(ArrayList<VaccineMfrItem> dataSet, FragmentActivity context, String vaccineName)
        {
            mDataSet = dataSet;
            sContext = context;
            mVaccineName = vaccineName;
        }

        @Override
        public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
            // Create a new view.
            View v = LayoutInflater.from(viewGroup.getContext())
                    .inflate(R.layout.inventory_vaccine_mfr_item, viewGroup, false);

            return new ViewHolder(v);
        }

        @Override
        public void onBindViewHolder(ViewHolder viewHolder, final int position)
        {
            // Get element from your dataset at this position and replace the contents of the view
            // with that element
            viewHolder.setItemDetails(mDataSet.get(position), mVaccineName);
        }

        @Override
        public int getItemCount()
        {
            return mDataSet.size();
        }
    }
}

