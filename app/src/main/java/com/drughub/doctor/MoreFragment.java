package com.drughub.doctor;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.drughub.doctor.MyOrders.MyOrderContainer;
import com.drughub.doctor.MyProfile.MyProfile_Activity;
import com.drughub.doctor.OrangeWallet.OrangeWalletActivity;
import com.drughub.doctor.mycalendar.MyCalendarActivity;
import com.drughub.doctor.orangeconnect.OrangeConnectActivity;
import com.drughub.doctor.patientrecords.PatientRecordActivity;
import com.drughub.doctor.utils.CustomDialog;

public class MoreFragment extends Fragment {


    Fragment fragment = null;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ((BaseActivity) getActivity()).setActionBarVisibility(false);

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.more, container, false);

        LinearLayout my_orders = (LinearLayout) view.findViewById(R.id.my_orders);
        LinearLayout patient_records = (LinearLayout) view.findViewById(R.id.patient_records);
        LinearLayout orangeConnect = (LinearLayout) view.findViewById(R.id.orange_connect);
        LinearLayout about = (LinearLayout) view.findViewById(R.id.about);
        LinearLayout my_home = (LinearLayout) view.findViewById(R.id.my_home);
        my_home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CustomDialog.showRatingDialog((BaseActivity) getActivity()).show();
            }
        });

        TextView shareApp = (TextView) view.findViewById(R.id.shareapp);
        shareApp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent sendIntent = new Intent(Intent.ACTION_SEND);
                sendIntent.putExtra(Intent.EXTRA_TEXT,
                        "Doctor App: https://play.google.com/store/apps/details?id=" + getActivity().getPackageName());
                sendIntent.setType("text/plain");
                startActivity(Intent.createChooser(sendIntent, getResources().getString(R.string.send_to)));
            }
        });

        my_orders.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), MyOrderContainer.class);
                startActivity(intent);
            }
        });

        patient_records.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getContext(), PatientRecordActivity.class));
            }
        });
        orangeConnect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getContext(), OrangeConnectActivity.class));
            }
        });
        about.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getContext(), AboutActivity.class));
            }
        });

        View orangewallet = view.findViewById(R.id.orange_wallet);
        orangewallet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v){
                Intent intent = new Intent(getContext(), OrangeWalletActivity.class);
                startActivity(intent);

            }
        });


        View my_calendar = view.findViewById(R.id.my_calender);
        my_calendar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), MyCalendarActivity.class);
                startActivity(intent);
            }
        });
        View myprofile = view.findViewById(R.id.my_profile);
        myprofile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent1 = new Intent(getActivity(), MyProfile_Activity.class);
                startActivity(intent1);
            }
        });

        return view;


    }

}
