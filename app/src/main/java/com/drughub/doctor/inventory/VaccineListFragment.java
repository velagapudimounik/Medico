package com.drughub.doctor.inventory;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.drughub.doctor.R;

import java.util.ArrayList;

class ItemVaccine
{
    public String name;
    public int consumed;
    public int available;
    public int reserve;
    public ItemVaccine(String name, int consumed, int available, int reserve)
    {
        this.name = name;
        this.consumed = consumed;
        this.available = available;
        this.reserve = reserve;
    }
}

public class VaccineListFragment extends Fragment
{
    RecyclerView mRecyclerView;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.inventory_vaccine_list, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState)
    {
        mRecyclerView = (RecyclerView) view.findViewById(R.id.inventory_vaccines_list);
        mRecyclerView.setHasFixedSize(true);

        LinearLayoutManager mLayoutManager = new LinearLayoutManager(getActivity(),LinearLayoutManager.HORIZONTAL, false);
        mRecyclerView.setLayoutManager(mLayoutManager);

        ArrayList<ItemVaccine> mDataset = new ArrayList<>();

        for(int i=0; i<10; i++)
        {
            ItemVaccine item = new ItemVaccine("Item"+i, (int)(10 * Math.random()), (int)(10 * Math.random()), (int)(10 * Math.random()));
            mDataset.add(item);
        }

        VaccineListAdapter mAdapter = new VaccineListAdapter(mDataset, getActivity());
        mRecyclerView.setAdapter(mAdapter);

        try {
            MyInventoryFragment inventoryFragment = (MyInventoryFragment) getActivity().getSupportFragmentManager().findFragmentById(R.id.container);
            inventoryFragment.setInventoryInfo(mDataset.get(0).consumed, mDataset.get(0).available, mDataset.get(0).reserve);

        }catch (Exception e)
        {
            e.printStackTrace();
        }

        Fragment fragment = new VaccineMfrListFragment();
        Bundle bundle = new Bundle();
        bundle.putString("name", "Item0");
        fragment.setArguments(bundle);
        getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.inventory_manufacturers, fragment).commit();
    }

    public static class VaccineListAdapter extends RecyclerView.Adapter<VaccineListAdapter.ViewHolder>
    {
        private ArrayList<ItemVaccine> mDataSet;
        static FragmentActivity sContext;
        private View mSelectedItem;
        private TextView mSelectedItemName;
        private int selectedItem = 0;

        public class ViewHolder extends RecyclerView.ViewHolder {
            private final TextView textView;
            private View mStatusConsumed;
            private View mStatusAvailable;
            private View mStatusReserve;
            private ItemVaccine itemData;

            public ViewHolder(View v)
            {
                super(v);

                v.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v)
                    {
                        if(selectedItem == getLayoutPosition())
                            return;

                        // Redraw the old selection and the new
                        notifyItemChanged(selectedItem);
                        selectedItem = getLayoutPosition();
                        notifyItemChanged(selectedItem);

                        MyInventoryFragment fragment = (MyInventoryFragment) sContext.getSupportFragmentManager().findFragmentById(R.id.container);
                        fragment.setInventoryInfo(itemData.consumed, itemData.available, itemData.reserve);

                        Fragment mfrList = new VaccineMfrListFragment();
                        Bundle bundle = new Bundle();
                        bundle.putString("name", textView.getText().toString());
                        mfrList.setArguments(bundle);

                        sContext.getSupportFragmentManager().beginTransaction().replace(R.id.inventory_manufacturers, mfrList).commit();
                    }
                });
                textView = (TextView) v.findViewById(R.id.inventory_vaccine_name);
                mStatusConsumed = v.findViewById(R.id.inventory_vaccine_item_status_consumed);
                mStatusAvailable = v.findViewById(R.id.inventory_vaccine_item_status_available);
                mStatusReserve = v.findViewById(R.id.inventory_vaccine_item_status_reserve);
            }

            public void setItemSelected(boolean selected)
            {
                itemView.setSelected(selected);
                textView.setTextColor(ContextCompat.getColor(sContext, selected?R.color.colorPrimary :R.color.colorWhite));
            }

            public void setItemDetails(ItemVaccine item)
            {
                itemData = item;
                textView.setText(item.name);

                mStatusConsumed.setLayoutParams(new LinearLayout.LayoutParams(mStatusConsumed.getLayoutParams().width, mStatusConsumed.getLayoutParams().height, item.consumed));
                mStatusAvailable.setLayoutParams(new LinearLayout.LayoutParams(mStatusAvailable.getLayoutParams().width, mStatusAvailable.getLayoutParams().height, item.available));
                mStatusReserve.setLayoutParams(new LinearLayout.LayoutParams(mStatusReserve.getLayoutParams().width, mStatusReserve.getLayoutParams().height, item.reserve));
            }
        }

        public VaccineListAdapter(ArrayList<ItemVaccine> dataSet, FragmentActivity context)
        {
            mDataSet = dataSet;
            sContext = context;
        }

        @Override
        public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
            // Create a new view.
            View v = LayoutInflater.from(viewGroup.getContext())
                    .inflate(R.layout.inventory_vaccine_item, viewGroup, false);

            return new ViewHolder(v);
        }

        @Override
        public void onBindViewHolder(ViewHolder viewHolder, final int position)
        {
            // Get element from your dataset at this position and replace the contents of the view
            // with that element
            viewHolder.setItemDetails(mDataSet.get(position));
            viewHolder.setItemSelected(selectedItem == position);
        }

        @Override
        public int getItemCount()
        {
            return mDataSet.size();
        }
    }
}
