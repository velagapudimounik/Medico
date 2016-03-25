package com.drughub.doctor.consultation;

import android.app.Dialog;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.drughub.doctor.BaseActivity;
import com.drughub.doctor.R;
import com.drughub.doctor.utils.CustomDialog;

import java.util.ArrayList;

class AvailableInventoryItem
{
    public String vaccineName;
    public String skuID;
    public String mfrName;
    public AvailableInventoryItem(String vaccine, String sku, String mfr)
    {
        this.vaccineName = vaccine;
        this.skuID = sku;
        this.mfrName = mfr;
    }
}

public class AvailableInventoryActivity extends BaseActivity {

    RecyclerView mRecyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.consultation_available_inventory);

        setTitle(getString(R.string.availableInventorytitle));
        setBackButton(true);

        mRecyclerView = (RecyclerView) findViewById(R.id.available_inventory_list);
        mRecyclerView.setHasFixedSize(true);

        //mRecyclerView.addItemDecoration(new SimpleDividerItemDecoration(getActivity()));

        LinearLayoutManager mLayoutManager = new LinearLayoutManager(this ,LinearLayoutManager.VERTICAL, false);
        mRecyclerView.setLayoutManager(mLayoutManager);

        ArrayList<AvailableInventoryItem> mDataSet = new ArrayList<>();

        for(int i=0; i<10; i++)
        {
            AvailableInventoryItem item = new AvailableInventoryItem("Vaccine Name"+i, "1234567890", "MFR Name");
            mDataSet.add(item);
        }

        AvailableInventoryListAdapter mAdapter = new AvailableInventoryListAdapter(mDataSet, this);
        mRecyclerView.setAdapter(mAdapter);


    }

    public static class AvailableInventoryListAdapter extends RecyclerView.Adapter<AvailableInventoryListAdapter.ViewHolder>
    {
        private ArrayList<AvailableInventoryItem> mDataSet;
        static FragmentActivity sContext;

        public static class ViewHolder extends RecyclerView.ViewHolder {
            private final TextView vaccineName;
            private View mItemView;

            public ViewHolder(View v)
            {
                super(v);

                mItemView = v;

                v.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v)
                    {
                    final Dialog dialog = CustomDialog.showMessageDialog((BaseActivity)sContext,
                            "Administering Vaccine at Temp 3\u00B0C", "Done");
                    Button okBtn = (Button)dialog.findViewById(R.id.dialogOkBtn);
                    okBtn.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            dialog.dismiss();
                        }
                    });
                    }
                });

                vaccineName = (TextView) v.findViewById(R.id.vaccineName);
            }

            public void setItemSelected(boolean selected)
            {
                mItemView.setSelected(selected);
            }

            public void setItemDetails(AvailableInventoryItem item)
            {
                vaccineName.setText(item.vaccineName);
            }
        }

        public AvailableInventoryListAdapter(ArrayList<AvailableInventoryItem> dataSet, FragmentActivity context)
        {
            mDataSet = dataSet;
            sContext = context;
        }

        @Override
        public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
            // Create a new view.
            View v = LayoutInflater.from(viewGroup.getContext())
                    .inflate(R.layout.consultation_available_inventory_list_item, viewGroup, false);

            return new ViewHolder(v);
        }

        @Override
        public void onBindViewHolder(ViewHolder viewHolder, final int position)
        {
            // Get element from your dataset at this position and replace the contents of the view
            // with that element
            viewHolder.setItemDetails(mDataSet.get(position));
        }

        @Override
        public int getItemCount()
        {
            return mDataSet.size();
        }
    }

}
