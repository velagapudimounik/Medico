package com.drughub.doctor.MyProfile;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.daimajia.swipe.SwipeLayout;
import com.daimajia.swipe.adapters.RecyclerSwipeAdapter;
import com.drughub.doctor.BaseActivity;
import com.drughub.doctor.R;
import com.drughub.doctor.model.DoctorClinic;
import com.drughub.doctor.network.Globals;
import com.drughub.doctor.network.Urls;
import com.drughub.doctor.utils.CustomDialog;
import com.drughub.doctor.utils.SimpleDividerItemDecoration;

import org.json.JSONException;
import org.json.JSONObject;

import io.realm.Realm;
import io.realm.RealmResults;

public class MyClinicsFragment extends android.support.v4.app.Fragment {
    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter adapter;
    private Realm realm;
    ProgressDialog progress;
    RealmResults<DoctorClinic> doctorClinics;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        final View view = inflater.inflate(R.layout.myprofile_myclinic_fragment, container, false);
        mRecyclerView=(RecyclerView)view.findViewById(R.id.myclinic_recyclerview);

        View.OnClickListener listener=new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(v==view.findViewById(R.id.addclinic_button)){
                    MyProfileAddClinicFragment fragment = new MyProfileAddClinicFragment();
                    Bundle bundle = new Bundle();
                    bundle.putString("doctorClinic","addClinic");
                    fragment.setArguments(bundle);
                    getFragmentManager().beginTransaction().add(R.id.containeractivity,fragment).addToBackStack(null).commit();
                }
            }
        };

        Button addClinic = (Button)view.findViewById(R.id.addclinic_button);
        addClinic.setOnClickListener(listener);

        return view;
    }


    public class MyClinicsListAdapter extends RecyclerSwipeAdapter<MyClinicsListAdapter.RecyclerViewHolder> {

        FragmentActivity context=null;
        RealmResults<DoctorClinic> doctorClinics;

        public MyClinicsListAdapter(FragmentActivity context, RealmResults<DoctorClinic> doctorClinics) {
            this.context = context;
            this.doctorClinics = doctorClinics;
        }

        @Override
        public RecyclerViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View v = LayoutInflater.from(context).inflate(R.layout.myprofile_clinic_item, parent, false);
            return new RecyclerViewHolder(v);
        }

        @Override
        public void onBindViewHolder(RecyclerViewHolder viewHolder, final int position) {
            final DoctorClinic doctorClinic = doctorClinics.get(position);

            viewHolder.hospitalName.setText(doctorClinic.getClinicName());
            viewHolder.hospitalAddress.setText(doctorClinic.getAddress().getStreetName()+","+ doctorClinic.getAddress().getBuildingName());

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
                            Log.i("clinicID", doctorClinics.get(position).getClinicId()+"");
                            doctorClinic.DeleteClinic(doctorClinics.get(position).getClinicId(), new Globals.VolleyCallback() {
                                @Override
                                public void onSuccess(String result) {

                                    notifyDataSetChanged();
                                    notifyItemRemoved(position);
                                }

                                @Override
                                public void onFail(String result) {

                                }
                            });
                            dialog.dismiss();
                        }
                    });
                }
            });

            viewHolder.updatebtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Globals.selectedDoctorClinic = doctorClinic;
                    MyProfileAddClinicFragment fragment = new MyProfileAddClinicFragment();
                    Bundle bundle = new Bundle();
                    bundle.putString("doctorClinic","fromEdit");
                    fragment.setArguments(bundle);
                    context.getSupportFragmentManager().beginTransaction().add(R.id.containeractivity, fragment).addToBackStack(null).commit();                }
            });

            mItemManger.bindView(viewHolder.itemView, position);
        }

        @Override
        public int getItemCount() {
            return doctorClinics.size();
        }

        @Override
        public int getSwipeLayoutResourceId(int position) {
            return R.id.swipe;
        }

        public class RecyclerViewHolder extends RecyclerView.ViewHolder {

            SwipeLayout swipeLayout;
            View deleteBtn, updatebtn;
            TextView hospitalName , hospitalAddress;

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
                updatebtn = itemView.findViewById(R.id.editClinic);
                hospitalName = (TextView) itemView.findViewById(R.id.hospitalName);
                hospitalAddress = (TextView) itemView.findViewById(R.id.hospitalAddress);
                swipeLayout = (SwipeLayout) itemView.findViewById(R.id.swipe);
            }
        }
    }

    @Override
    public void onStart() {
        realm = Realm.getDefaultInstance();
        progress = ProgressDialog.show(getContext(), "", "Please wait...", true);
        Globals.GET(Urls.CLINIC, null, new Globals.VolleyCallback() {
            @Override
            public void onSuccess(String result) {
                try {
                    progress.dismiss();
                    JSONObject object = new JSONObject(result);
                    if(object.getBoolean("result"))
                    {
                        realm.beginTransaction();
                        realm.allObjects(DoctorClinic.class).clear();
                        realm.createOrUpdateAllFromJson(DoctorClinic.class, object.getJSONArray("response"));
                        Log.i("Clinic_Response", object.getJSONArray("response").toString());
                        realm.commitTransaction();
                        doctorClinics = realm.allObjects(DoctorClinic.class);
                        for (DoctorClinic doctorClinic : doctorClinics){
                            int id = doctorClinic.getClinicId();

                        }
                        addValuesToRecyclerView(doctorClinics);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFail(String result) {
                progress.dismiss();
            }
        });
        super.onStart();
    }

    private void addValuesToRecyclerView(RealmResults<DoctorClinic> doctorClinics) {


        MyClinicsListAdapter adapter = new MyClinicsListAdapter(this.getActivity(), doctorClinics);
        mRecyclerView.setAdapter(adapter);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this.getActivity()));
        mRecyclerView.addItemDecoration(new SimpleDividerItemDecoration(getActivity()));

    }
}
