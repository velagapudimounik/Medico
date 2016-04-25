package com.drughub.doctor.analytics;

import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.ScrollView;
import android.widget.TextView;

import com.drughub.doctor.R;
import com.drughub.doctor.utils.DrughubIcon;
import com.drughub.doctor.utils.GaugeView;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.charts.HorizontalBarChart;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.interfaces.datasets.IBarDataSet;

import java.util.ArrayList;
import java.util.List;

public class AnalyticsFragment extends Fragment implements RadioGroup.OnCheckedChangeListener {
    RadioButton radioConsumption, radioFMP, radioProfitability, radioColdHub;
    RadioButton radioWeekly, radioMonthly, radioYearly;
    TextView title;
    LinearLayout coldHubLayout, restLayout;
    BarChart chart;
    HorizontalBarChart horizontalBarChart;
    private ScrollView pieChartLayout;
    private GaugeView chartPie1;
    private GaugeView chartPie2;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.analytics_main, container, false);

        title = (TextView) view.findViewById(R.id.title);
        restLayout = (LinearLayout) view.findViewById(R.id.restLayout);
        coldHubLayout = (LinearLayout) view.findViewById(R.id.coldhubLayout);
        chart = (BarChart) view.findViewById(R.id.chart);
        horizontalBarChart = (HorizontalBarChart) view.findViewById(R.id.chart_horizontal);

        pieChartLayout = (ScrollView) view.findViewById(R.id.pie_chart_layout);
        chartPie1 = (GaugeView) view.findViewById(R.id.chart_pie1);
        chartPie2 = (GaugeView) view.findViewById(R.id.chart_pie2);

        chartInit(getXAxisValues("week"), getDataSet("week"));

        RadioGroup analyticsGroup = (RadioGroup) view.findViewById(R.id.analyticsGroup);
        RadioGroup forecastGroup = (RadioGroup) view.findViewById(R.id.forecastGroup);

        radioConsumption = (RadioButton) view.findViewById(R.id.radioButton1);
        radioFMP = (RadioButton) view.findViewById(R.id.radioButton2);
        radioProfitability = (RadioButton) view.findViewById(R.id.radioButton3);
        radioColdHub = (RadioButton) view.findViewById(R.id.radioButton4);

        radioWeekly = (RadioButton) view.findViewById(R.id.radioWeekly);
        radioMonthly = (RadioButton) view.findViewById(R.id.radioMonthly);
        radioYearly = (RadioButton) view.findViewById(R.id.radioYearly);

        Typeface ttf = DrughubIcon.getsDrughubIcons(getActivity());
        radioConsumption.setTypeface(ttf);
        radioFMP.setTypeface(ttf);
        radioProfitability.setTypeface(ttf);
        radioColdHub.setTypeface(ttf);

        analyticsGroup.setOnCheckedChangeListener(this);
        forecastGroup.setOnCheckedChangeListener(this);
//        radioConsumption.setChecked(true);

        return view;
    }

    private void chartInit(List<String> XAxisValues, IBarDataSet dataset) {
        BarData data = new BarData(XAxisValues, dataset);
        chart.setData(data);
        chart.setDescription(null);
        chart.animateXY(2000, 2000);

        chart.getData().setDrawValues(false);
        chart.setPinchZoom(true);
        chart.setScaleEnabled(true);
        chart.setDoubleTapToZoomEnabled(false);
        chart.setHighlightPerDragEnabled(false);
        chart.setHighlightPerTapEnabled(false);

        chart.getXAxis().setDrawGridLines(false);
        chart.getXAxis().setPosition(XAxis.XAxisPosition.BOTTOM);
        chart.getXAxis().setDrawAxisLine(false);
        chart.getXAxis().setTextColor(ContextCompat.getColor(getActivity(), R.color.colorPrimary));
        chart.getXAxis().setSpaceBetweenLabels(0);
        chart.getXAxis().setTextSize(13);
        chart.getAxisLeft().setEnabled(false);
        chart.getAxisRight().setEnabled(false);
        chart.getLegend().setEnabled(false);
        chart.fitScreen();
        chart.setVisibleXRangeMaximum(7);
        chart.invalidate();
    }

    private void horizontalChartInit(HorizontalBarChart horizontalBarChart, List<String> XAxisValues, IBarDataSet dataset) {
        BarData data = new BarData(XAxisValues, dataset);
        horizontalBarChart.setData(data);
        horizontalBarChart.setDescription(null);
        horizontalBarChart.animateXY(2000, 2000);

        horizontalBarChart.getData().setDrawValues(false);
        horizontalBarChart.setPinchZoom(true);
        horizontalBarChart.setScaleEnabled(true);
        horizontalBarChart.setDoubleTapToZoomEnabled(false);
        horizontalBarChart.setHighlightPerDragEnabled(false);
        horizontalBarChart.setHighlightPerTapEnabled(false);

        horizontalBarChart.getXAxis().setDrawGridLines(false);
        horizontalBarChart.getXAxis().setPosition(XAxis.XAxisPosition.BOTTOM);
        horizontalBarChart.getXAxis().setDrawAxisLine(false);
        horizontalBarChart.getXAxis().setTextColor(ContextCompat.getColor(getActivity(), R.color.colorPrimary));
        horizontalBarChart.getAxisLeft().setEnabled(false);
        horizontalBarChart.getAxisRight().setEnabled(false);

        horizontalBarChart.getLegend().setEnabled(false);

        horizontalBarChart.invalidate();
    }


    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        getActivity().setTitle(getString(R.string.analytics));
    }

    @Override
    public void onCheckedChanged(RadioGroup group, int checkedId) {
        if (group.getId() == R.id.analyticsGroup) {
            resetButtons();
            switch (checkedId) {
                case R.id.radioButton1: // Consumption
                    radioConsumption.setText(getText(R.string.icon_consumption_s));
                    title.setText(getString(R.string.consumption));
                    chart.setVisibility(View.VISIBLE);
                    horizontalBarChart.setVisibility(View.GONE);
                    pieChartLayout.setVisibility(View.GONE);
                    break;
                case R.id.radioButton2: // Fast-Moving Products
                    radioFMP.setText(getText(R.string.icon_fmp_s));
                    title.setText(getString(R.string.fmp));
                    chart.setVisibility(View.GONE);
                    horizontalBarChart.setVisibility(View.VISIBLE);
                    pieChartLayout.setVisibility(View.GONE);
                    horizontalChartInit(horizontalBarChart, getFMPXAxisValues("week"), getFMPDataSet("week"));
                    break;
                case R.id.radioButton3: // Profitability
                    radioProfitability.setText(getText(R.string.icon_profitability_s));
                    title.setText(getString(R.string.profitability));
                    chart.setVisibility(View.VISIBLE);
                    horizontalBarChart.setVisibility(View.GONE);
                    pieChartLayout.setVisibility(View.GONE);
                    break;
                case R.id.radioButton4: // ColdHub
                    radioColdHub.setText(getText(R.string.icon_coldhub_s));
                    title.setText(getString(R.string.coldhub));
                    restLayout.setVisibility(View.GONE);
                    coldHubLayout.setVisibility(View.VISIBLE);
                    chart.setVisibility(View.GONE);
                    horizontalBarChart.setVisibility(View.GONE);
                    pieChartLayout.setVisibility(View.VISIBLE);
                    chartPie1.setTargetValue(50);
                    chartPie2.setTargetValue(70);

//                    horizontalBarChart2.setMaxVisibleValueCount(100);
//                    horizontalChartInit(horizontalBarChart2, getXAxisValues(""), getDataSet(""));
                    break;
                default:
                    break;

            }
        } else if (group.getId() == R.id.forecastGroup) {
            radioWeekly.setBackgroundColor(Color.TRANSPARENT);
            radioWeekly.setTextColor(ContextCompat.getColor(getActivity(), R.color.colorDecoration));
            radioMonthly.setBackgroundColor(Color.TRANSPARENT);
            radioMonthly.setTextColor(ContextCompat.getColor(getActivity(), R.color.colorDecoration));
            radioYearly.setBackgroundColor(Color.TRANSPARENT);
            radioYearly.setTextColor(ContextCompat.getColor(getActivity(), R.color.colorDecoration));

            switch (checkedId) {
                case R.id.radioWeekly:
                    radioWeekly.setBackgroundResource(R.drawable.fill_rect_orange);
                    radioWeekly.setTextColor(Color.WHITE);
                    changeCharts("week");

                    break;
                case R.id.radioMonthly:
                    radioMonthly.setBackgroundResource(R.drawable.fill_rect_orange);
                    radioMonthly.setTextColor(Color.WHITE);
                    changeCharts("month");
                    break;
                case R.id.radioYearly:
                    radioYearly.setBackgroundResource(R.drawable.fill_rect_orange);
                    radioYearly.setTextColor(Color.WHITE);
                    changeCharts("year");
                    break;
                default:
                    break;
            }
        }
    }

    private void changeCharts(String type) {
        if (chart.getVisibility() == View.VISIBLE) {
            chartInit(getXAxisValues(type), getDataSet(type));
        } else if (horizontalBarChart.getVisibility() == View.VISIBLE) {
            horizontalChartInit(horizontalBarChart, getFMPXAxisValues(type), getFMPDataSet(type));
        }
    }

    private IBarDataSet getFMPDataSet(String type) {
        ArrayList<BarEntry> valueSet1 = new ArrayList<>();
//        if (type.equals("week"))
        {
            for (int i = 0; i < 5; i++) {
                valueSet1.add(new BarEntry(((i + 1) * 10), i));
            }
        }
//        else if (type.equals("month")) {
//            for (int i = 0; i < 12; i++) {
//                valueSet1.add(new BarEntry(((i + 1) * 10), i));
//            }
//        } else if (type.equals("year")) {
//            for (int i = 0; i < 4; i++) {
//                valueSet1.add(new BarEntry(((i + 1) * 10), i));
//            }
//        } else {
//            valueSet1.add(new BarEntry(78, 0));
//        }

        BarDataSet barDataSet1 = new BarDataSet(valueSet1, "Brand 1");
        barDataSet1.setColor(ContextCompat.getColor(getActivity(), R.color.colorPrimary));

        return barDataSet1;
    }

    private List<String> getFMPXAxisValues(String type) {
        ArrayList<String> xAxis = new ArrayList<>();
        xAxis.add("Vaccine-5");
        xAxis.add("Vaccine-4");
        xAxis.add("Vaccine-3");
        xAxis.add("Vaccine-2");
        xAxis.add("Vaccine-1");
        return xAxis;
    }

    private void resetButtons() {
        radioConsumption.setText(getText(R.string.icon_consumption_us));
        radioFMP.setText(getText(R.string.icon_fmp_us));
        radioProfitability.setText(getText(R.string.icon_profitability_us));
        radioColdHub.setText(getText(R.string.icon_coldhub_us));
        coldHubLayout.setVisibility(View.GONE);
        restLayout.setVisibility(View.VISIBLE);

        radioWeekly.setChecked(true);

    }

    private IBarDataSet getDataSet(String type) {

        ArrayList<BarEntry> valueSet1 = new ArrayList<>();
        if (type.equals("week")) {
            for (int i = 0; i < 7; i++) {
                valueSet1.add(new BarEntry(((i + 1) * 10), i));
            }
        } else if (type.equals("month")) {
            for (int i = 0; i < 12; i++) {
                valueSet1.add(new BarEntry(((i + 1) * 10), i));
            }
        } else if (type.equals("year")) {
            for (int i = 0; i < 4; i++) {
                valueSet1.add(new BarEntry(((i + 1) * 10), i));
            }
        } else {
            valueSet1.add(new BarEntry(78, 0));
        }

        BarDataSet barDataSet1 = new BarDataSet(valueSet1, "Brand 1");
        barDataSet1.setColor(ContextCompat.getColor(getActivity(), R.color.colorPrimary));

        return barDataSet1;
    }

    private List<String> getXAxisValues(String type) {
        ArrayList<String> xAxis = new ArrayList<>();
        if (type.equals("week")) {
            xAxis.add("Mon");
            xAxis.add("Tue");
            xAxis.add("Wed");
            xAxis.add("Thu");
            xAxis.add("Fri");
            xAxis.add("Sat");
            xAxis.add("Sun");
        } else if (type.equals("month")) {
            xAxis.add("Jan");
            xAxis.add("Feb");
            xAxis.add("Mar");
            xAxis.add("Apr");
            xAxis.add("May");
            xAxis.add("Jun");
            xAxis.add("Jul");
            xAxis.add("Aug");
            xAxis.add("Sep");
            xAxis.add("Oct");
            xAxis.add("Nov");
            xAxis.add("Dec");
        } else if (type.equals("year")) {
            xAxis.add("2013");
            xAxis.add("2014");
            xAxis.add("2015");
            xAxis.add("2016");
        } else {
            xAxis.add("");
        }
        return xAxis;
    }


}
