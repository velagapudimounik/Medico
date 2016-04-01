package com.drughub.doctor.MyProfile;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.daimajia.swipe.SwipeLayout;
import com.daimajia.swipe.adapters.RecyclerSwipeAdapter;
import com.drughub.doctor.BaseActivity;
import com.drughub.doctor.R;
import com.drughub.doctor.utils.CustomDialog;
import com.drughub.doctor.utils.SimpleDividerItemDecoration;

import android.widget.Button;
import android.widget.TextView;

public class MyClinicsFragment extends android.support.v4.app.Fragment {
    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter adapter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        final View view = inflater.inflate(R.layout.myprofile_myclinic_fragment, container, false);

        mRecyclerView=(RecyclerView)view.findViewById(R.id.myclinic_recyclerview);
        MyClinicsListAdapter adapter = new MyClinicsListAdapter(this.getActivity());
        mRecyclerView.setAdapter(adapter);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this.getActivity()));
        mRecyclerView.addItemDecoration(new SimpleDividerItemDecoration(getActivity()));

        View.OnClickListener listener=new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(v==view.findViewById(R.id.addclinic_button)){
                    getFragmentManager().beginTransaction().add(R.id.containeractivity,new MyProfileAddClinicFragment()).addToBackStack(null).commit();
                }
            }
        };

        Button addClinic = (Button)view.findViewById(R.id.addclinic_button);
        addClinic.setOnClickListener(listener);

        return view;
    }


    public class MyClinicsListAdapter extends RecyclerSwipeAdapter<MyClinicsListAdapter.RecyclerViewHolder> {

        FragmentActivity context=null;

        public MyClinicsListAdapter(FragmentActivity context) {
            this.context = context;
        }

        @Override
        public RecyclerViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View v = LayoutInflater.from(context).inflate(R.layout.myprofile_clinic_item, parent, false);
            return new RecyclerViewHolder(v);
        }

        @Override
        public void onBindViewHolder(RecyclerViewHolder viewHolder, int position) {

            viewHolder.deleteBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    final Dialog dialog = CustomDialog.showQuestionDialog((BaseActivity) context, context.getResources().getString(R.string.deleteClinicMessage));

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
//                            mItemManger.removeShownLayouts(viewHolder.swipeLayout);
//                            mDataSet.remove(position);
//                            notifyItemRemoved(position);
//                            notifyItemRangeChanged(position, mDataSet.size());
//                            mItemManger.closeAllItems();
                        }
                    });
                }
            });

            mItemManger.bindView(viewHolder.itemView, position);
        }

        @Override
        public int getItemCount() {
            return 5;
        }

        @Override
        public int getSwipeLayoutResourceId(int position) {
            return R.id.swipe;
        }

        public class RecyclerViewHolder extends RecyclerView.ViewHolder {

            SwipeLayout swipeLayout;
            View deleteBtn;

            public RecyclerViewHolder(View itemView) {
                super(itemView);

                View myClinicItem = itemView.findViewById(R.id.myClinicItem);

                myClinicItem.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        context.getSupportFragmentManager().beginTransaction().add(R.id.containeractivity, new MyProfileClinicDetailsFragment()).addToBackStack(null).commit();
                    }
                });

                deleteBtn = itemView.findViewById(R.id.deleteClinic);

                swipeLayout = (SwipeLayout) itemView.findViewById(R.id.swipe);
            }
        }
    }
}
