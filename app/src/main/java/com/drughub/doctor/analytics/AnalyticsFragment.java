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
import android.widget.TextView;

import com.drughub.doctor.R;
import com.drughub.doctor.utils.DrughubIcon;

public class AnalyticsFragment extends Fragment implements RadioGroup.OnCheckedChangeListener {
    RadioButton radioConsumption, radioFMP, radioProfitability, radioColdHub;
    RadioButton radioWeekly, radioMonthly, radioYearly;
    TextView title;
    LinearLayout coldHubLayout, restLayout;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.analytics_main, container, false);

        title = (TextView) view.findViewById(R.id.title);
        restLayout = (LinearLayout) view.findViewById(R.id.restLayout);
        coldHubLayout = (LinearLayout) view.findViewById(R.id.coldhubLayout);

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
                    break;
                case R.id.radioButton2: // Fast-Moving Products
                    radioFMP.setText(getText(R.string.icon_fmp_s));
                    title.setText(getString(R.string.fmp));
                    break;
                case R.id.radioButton3: // Profitability
                    radioProfitability.setText(getText(R.string.icon_profitability_s));
                    title.setText(getString(R.string.profitability));
                    break;
                case R.id.radioButton4: // ColdHub
                    radioColdHub.setText(getText(R.string.icon_coldhub_s));
                    title.setText(getString(R.string.coldhub));
                    restLayout.setVisibility(View.GONE);
                    coldHubLayout.setVisibility(View.VISIBLE);
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
                    break;
                case R.id.radioMonthly:
                    radioMonthly.setBackgroundResource(R.drawable.fill_rect_orange);
                    radioMonthly.setTextColor(Color.WHITE);
                    break;
                case R.id.radioYearly:
                    radioYearly.setBackgroundResource(R.drawable.fill_rect_orange);
                    radioYearly.setTextColor(Color.WHITE);
                    break;
                default:
                    break;
            }

        }

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
}
