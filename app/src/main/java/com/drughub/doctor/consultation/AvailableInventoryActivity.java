package com.drughub.doctor.consultation;

import android.app.Dialog;
import android.graphics.Color;
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
import com.drughub.doctor.utils.StringUtils;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;
import com.github.mikephil.charting.utils.ColorTemplate;

import java.util.ArrayList;
import java.util.Random;

class AvailableInventoryItem {
    public String vaccineName;
    public String skuID;
    public String mfrName;

    public AvailableInventoryItem(String vaccine, String sku, String mfr) {
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

        setTitle("Disease Name");
        setBackButton(true);

        mRecyclerView = (RecyclerView) findViewById(R.id.available_inventory_list);
        mRecyclerView.setHasFixedSize(true);

        //mRecyclerView.addItemDecoration(new SimpleDividerItemDecoration(getActivity()));

        LinearLayoutManager mLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        mRecyclerView.setLayoutManager(mLayoutManager);

        ArrayList<AvailableInventoryItem> mDataSet = new ArrayList<>();

        for (int i = 0; i < 10; i++) {
            AvailableInventoryItem item = new AvailableInventoryItem("Vaccine Name" + i, "1234567890", "MFR Name");
            mDataSet.add(item);
        }

        AvailableInventoryListAdapter mAdapter = new AvailableInventoryListAdapter(mDataSet, this);
        mRecyclerView.setAdapter(mAdapter);
    }

    public static class AvailableInventoryListAdapter extends RecyclerView.Adapter<AvailableInventoryListAdapter.ViewHolder> {
        private ArrayList<AvailableInventoryItem> mDataSet;
        static FragmentActivity sContext;

        public static class ViewHolder extends RecyclerView.ViewHolder {
            private final TextView vaccineName;
            private View mItemView;
            View expandBtn;
            LineChart lineChart;

            public ViewHolder(View v) {
                super(v);

                mItemView = v;

                View vaccinateBtn = v.findViewById(R.id.vaccinateBtn);
                vaccinateBtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        String value = "<font color=\"#ff5722\">4°C</font>";

                        final Dialog dialog = CustomDialog.showMessageDialog((BaseActivity) sContext,
                                StringUtils.findAndReplace(sContext.getResources().getString(R.string.temperature), "{temp}", value), "Done");
                        Button okBtn = (Button) dialog.findViewById(R.id.dialogOkBtn);
                        okBtn.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                dialog.dismiss();
                            }
                        });
                    }
                });

                vaccineName = (TextView) v.findViewById(R.id.vaccineName);
                expandBtn = v.findViewById(R.id.expandBtn);
                expandBtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (lineChart.getVisibility() != View.VISIBLE) {
                            lineChart.setVisibility(View.VISIBLE);
                            setData(lineChart, 6, 5);
                            expandBtn.setRotation(180);
                        } else {
                            lineChart.setVisibility(View.GONE);
                            expandBtn.setRotation(0);
                        }
                    }
                });

                lineChart = (LineChart) v.findViewById(R.id.moreInfo);

            }

            public void setItemSelected(boolean selected) {
                mItemView.setSelected(selected);
            }

            public void setItemDetails(AvailableInventoryItem item) {
                vaccineName.setText(item.vaccineName);

                lineChart.setVisibility(View.GONE);
                expandBtn.setRotation(0);
            }
        }

        public AvailableInventoryListAdapter(ArrayList<AvailableInventoryItem> dataSet, FragmentActivity context) {
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
        public void onBindViewHolder(ViewHolder viewHolder, final int position) {
            // Get element from your dataset at this position and replace the contents of the view
            // with that element
            viewHolder.setItemDetails(mDataSet.get(position));
        }

        @Override
        public int getItemCount() {
            return mDataSet.size();
        }
    }

    public static void setData(LineChart lineChart, int count, float range) {

        ArrayList<String> xVals = new ArrayList<String>();
        xVals.add("12 Oct");
        xVals.add("13 Oct");
        xVals.add("14 Oct");
        xVals.add("15 Oct");
        xVals.add("16 Oct");
        xVals.add("17 Oct");
        lineChart.getXAxis().setPosition(XAxis.XAxisPosition.BOTTOM);
//        lineChart.getXAxis().addLimitLine(new LimitLine(2f, "2"));
//        lineChart.getXAxis().addLimitLine(new LimitLine(2f, "8"));
        lineChart.setContentDescription("");

        {
            XAxis xAxis = lineChart.getXAxis();
            xAxis.setTextSize(9f);
            xAxis.setTextColor(Color.BLACK);
            xAxis.setAxisLineColor(Color.TRANSPARENT);
            xAxis.setSpaceBetweenLabels(1);
            xAxis.setDrawLabels(false); // no axis labels
//            xAxis.setDrawAxisLine(false); // no axis line
            xAxis.setDrawGridLines(false); //

            YAxis leftAxis = lineChart.getAxisLeft();
            leftAxis.setTextColor(ColorTemplate.getHoloBlue());
            leftAxis.setAxisLineColor(Color.TRANSPARENT);
            leftAxis.setAxisMaxValue(10f);
            leftAxis.setAxisMinValue(0f);
//            leftAxis.setDrawLabels(false); // no axis labels
//            leftAxis.setDrawAxisLine(false); // no axis line
            leftAxis.setDrawGridLines(false); //

            YAxis rightAxis = lineChart.getAxisRight();
            rightAxis.setAxisLineColor(Color.TRANSPARENT);
            rightAxis.setTextColor(Color.RED);
            rightAxis.setDrawZeroLine(false);
            rightAxis.setDrawLabels(false); // no axis labels
            rightAxis.setDrawAxisLine(false); // no axis line
            rightAxis.setDrawGridLines(false); //
        }

//        lineChart.getXAxis().addLimitLine(new LimitLine(8));


        ArrayList<Entry> yVals1 = new ArrayList<Entry>();
        ArrayList<Entry> yVals2 = new ArrayList<Entry>();

        for (int i = 0; i < count; i++) {
            yVals1.add(new Entry(new Random().nextInt(10), i));
        }
        for (int i = 0; i < count; i++) {
            yVals2.add(new Entry(3, i));
        }

        // create a dataset and give it a type
        LineDataSet set1 = new LineDataSet(yVals1, "Manufacturer");
        LineDataSet set2 = new LineDataSet(yVals2, "Transit");
        LineDataSet set3 = new LineDataSet(yVals1, "Warehouse");
        LineDataSet set4 = new LineDataSet(yVals2, "Transit");
        LineDataSet set5 = new LineDataSet(yVals1, "Doctor");


        set1.setColor(ColorTemplate.getHoloBlue());
        set1.setCircleColor(Color.RED);
        set1.setLineWidth(2f);
        set1.setCircleRadius(3f);
        set1.setFillAlpha(65);
        set1.setFillColor(ColorTemplate.getHoloBlue());
        set1.setHighLightColor(Color.rgb(244, 117, 117));
        set1.setDrawCircleHole(false);
        //set1.setFillFormatter(new MyFillFormatter(0f));
        set1.setDrawHorizontalHighlightIndicator(false);
//        set1.setVisible(false);
//        set1.setCircleHoleColor(Color.WHITE);

        // create a dataset and give it a type
        set2.setAxisDependency(YAxis.AxisDependency.RIGHT);
        set2.setColor(Color.RED);
        set2.setCircleColor(Color.WHITE);
        set2.setLineWidth(2f);
        set2.setCircleRadius(3f);
        set2.setFillAlpha(65);
        set2.setFillColor(Color.RED);
        set2.setDrawCircleHole(false);
        set2.setHighLightColor(Color.rgb(244, 117, 117));
        //set2.setFillFormatter(new MyFillFormatter(900f));

        ArrayList<ILineDataSet> dataSets = new ArrayList<ILineDataSet>();
//        dataSets.add(set5);
//        dataSets.add(set4);
//        dataSets.add(set3);
//        dataSets.add(set2);
        dataSets.add(set1);
        // add the datasets

        // create a data object with the datasets
        LineData data = new LineData(xVals, dataSets);
        data.setValueTextColor(Color.TRANSPARENT);
        data.setValueTextSize(9f);

        // set data
        //        lineChart.setDrawGridBackground(false);
        lineChart.setData(data);
        lineChart.invalidate();
    }
}
