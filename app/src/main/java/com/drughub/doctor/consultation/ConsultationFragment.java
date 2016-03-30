package com.drughub.doctor.consultation;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;

import com.daimajia.swipe.SwipeLayout;
import com.daimajia.swipe.adapters.RecyclerSwipeAdapter;
import com.drughub.doctor.BaseActivity;
import com.drughub.doctor.R;
import com.drughub.doctor.Vaccschedule.VaccActivity;
import com.drughub.doctor.utils.CustomDialog;

import java.util.ArrayList;

class ItemConsultation
{
    public String name;
    public String vaccine;
    public String clinic;
    public String date;
    public ItemConsultation(String name, String consumed, String clinic, String date)
    {
        this.name = name;
        this.vaccine = consumed;
        this.clinic = clinic;
        this.date = date;
    }
}

public class ConsultationFragment extends Fragment {
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.consultation_main, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState)
    {
        getActivity().setTitle(getString(R.string.consultations));

        final TextView addBtn = (TextView) view.findViewById(R.id.addConsultationBtn);
        addBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //((BaseActivity)getActivity()).setBackButton(true);
                final Dialog dialog = CustomDialog.showCustomDialog((BaseActivity)getActivity(), R.layout.consultation_add_new,
                        Gravity.BOTTOM, true, false, true);
                Spinner time_dropdown = (Spinner)dialog.findViewById(R.id.time_extension);
                String[] time_items = new String[]{getString(R.string.am), getString(R.string.pm)};
                ArrayAdapter<String> time_adapter = new ArrayAdapter<>(getActivity(), android.R.layout.simple_list_item_1, time_items);
                time_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                time_dropdown.setAdapter(time_adapter);




                View addBtn = dialog.findViewById(R.id.addConsultationBtn);
                addBtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        startActivity(new Intent(getActivity(), VaccActivity.class));
                        dialog.dismiss();
                    }
                });
            }
        });

        final RecyclerView mRecyclerView = (RecyclerView) view.findViewById(R.id.consultation_list);
        mRecyclerView.setHasFixedSize(true);

        //mRecyclerView.addItemDecoration(new SimpleDividerItemDecoration(getActivity()));

        final LinearLayoutManager mLayoutManager = new LinearLayoutManager(getActivity(),LinearLayoutManager.VERTICAL, false);

        mRecyclerView.setLayoutManager(mLayoutManager);

        final ArrayList<ItemConsultation> mDataSet = new ArrayList<>();

        for(int i=0; i<5; i++)
        {
            ItemConsultation item = new ItemConsultation("Name"+i, "Vaccine Name", "Clinic", "Time");
            mDataSet.add(item);
        }

        final ArrayList<ItemConsultation> mDataSet1 = new ArrayList<>();

        for(int i=0; i<5; i++)
        {
            ItemConsultation item = new ItemConsultation("Name"+i, "Vaccine Name", "Clinic", "Date");
            mDataSet1.add(item);
        }

        final ConsultationListAdapter mAdapter = new ConsultationListAdapter(mDataSet, getActivity());
        mRecyclerView.setAdapter(mAdapter);

        final View noAppointments = view.findViewById(R.id.noAppointments);
        final EditText searchView = (EditText) view.findViewById(R.id.consultationSearch);
        final View editLayout = view.findViewById(R.id.editLayout);

        RadioGroup radioGroup = (RadioGroup) view.findViewById(R.id.radioGroupConsultation);

        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {

                ArrayList<ItemConsultation> dataSet = null;
                if (checkedId == R.id.todaysConsultations) {
                    dataSet = mDataSet;
                    searchView.setHint(getResources().getString(R.string.hintPatientName));
                }
                else if (checkedId == R.id.upcomingConsultations) {
                    dataSet = mDataSet1;
                    searchView.setHint(getResources().getString(R.string.hintDateOrPatient));
                }

                mLayoutManager.scrollToPosition(0);
                mAdapter.swap(dataSet);

                if(dataSet == null || dataSet.size() == 0) {
                    noAppointments.setVisibility(View.VISIBLE);
                    editLayout.setVisibility(View.GONE);
                    mRecyclerView.setVisibility(View.GONE);
                }
                else {
                    noAppointments.setVisibility(View.GONE);
                    editLayout.setVisibility(View.VISIBLE);
                    mRecyclerView.setVisibility(View.VISIBLE);
                }
            }
        });
    }

    public static class ConsultationListAdapter extends RecyclerSwipeAdapter<ConsultationListAdapter.ViewHolder>
    {
        private ArrayList<ItemConsultation> mDataSet;
        static FragmentActivity sContext;

        public static class ViewHolder extends RecyclerView.ViewHolder {
            private final TextView textView;
            TextView txtClinic;
            private View mItemView;
            SwipeLayout swipeLayout;
            View deleteBtn;

            public ViewHolder(View v)
            {
                super(v);
                mItemView = v;

                v.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        sContext.startActivity(new Intent(sContext, PatientVaccineScheduleActivity.class));
                    }
                });

                textView = (TextView) v.findViewById(R.id.consultationPatientDetails);
                txtClinic = (TextView) v.findViewById(R.id.consultationClinicDetails);


                View view = v.findViewById(R.id.consultationItem);
                view.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        sContext.startActivity(new Intent(sContext, PatientVaccineScheduleActivity.class));
                    }
                });

                swipeLayout = (SwipeLayout) v.findViewById(R.id.swipe);

                deleteBtn = v.findViewById(R.id.deleteConsultation);
            }

            public void setItemSelected(boolean selected)
            {
                mItemView.setSelected(selected);
            }

            public void setItemDetails(ItemConsultation item)
            {
                textView.setText(item.name + " | " + item.vaccine);
                txtClinic.setText(item.clinic + " | " + item.date);
            }
        }

        public ConsultationListAdapter(ArrayList<ItemConsultation> dataSet, FragmentActivity context)
        {
            mDataSet = dataSet;
            sContext = context;
        }

        @Override
        public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
            // Create a new view.
            View v = LayoutInflater.from(viewGroup.getContext())
                    .inflate(R.layout.consultation_item, viewGroup, false);

            return new ViewHolder(v);
        }

        @Override
        public void onBindViewHolder(final ViewHolder viewHolder, final int position)
        {
            // Get element from your dataset at this position and replace the contents of the view
            // with that element
            viewHolder.setItemDetails(mDataSet.get(position));

            viewHolder.deleteBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    final Dialog dialog = CustomDialog.showQuestionDialog((BaseActivity) sContext, sContext.getResources().getString(R.string.dialogDeleteConsultation));

                    View noBtn = dialog.findViewById(R.id.dialogNoBtn);
                    noBtn.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            dialog.dismiss();
                        }
                    });

                    View yesBtn = dialog.findViewById(R.id.dialogYesBtn);
                    yesBtn.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            dialog.dismiss();
                            mItemManger.removeShownLayouts(viewHolder.swipeLayout);
                            mDataSet.remove(position);
                            notifyItemRemoved(position);
                            notifyItemRangeChanged(position, mDataSet.size());
                            mItemManger.closeAllItems();
                        }
                    });
                }
            });

            mItemManger.bindView(viewHolder.itemView, position);
        }

        @Override
        public int getItemCount()
        {
            return mDataSet.size();
        }

        @Override
        public int getSwipeLayoutResourceId(int position) {
            return R.id.swipe;
        }

        public void swap(ArrayList<ItemConsultation> dataSet){
            mDataSet = dataSet;
            notifyDataSetChanged();
            mItemManger.closeAllItems();
        }
    }
}
