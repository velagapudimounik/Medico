package com.drughub.doctor.MyProfile.unused;

import android.app.Dialog;
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
import android.widget.LinearLayout;
import android.widget.TextView;

import com.daimajia.swipe.SwipeLayout;
import com.daimajia.swipe.adapters.RecyclerSwipeAdapter;
import com.drughub.doctor.BaseActivity;
import com.drughub.doctor.MyProfile.MyProfileAddClinicFragment;
import com.drughub.doctor.MyProfile.MyProfileClinicDetailsFragment;
import com.drughub.doctor.R;
import com.drughub.doctor.model.Address;
import com.drughub.doctor.model.City;
import com.drughub.doctor.model.Country;
import com.drughub.doctor.model.DoctorClinic;
import com.drughub.doctor.model.State;
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
    View itemView;
    private RecyclerView.Adapter adapter;
    private Realm realm;
    RealmResults<DoctorClinic> doctorClinics;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        final View view = inflater.inflate(R.layout.myprofile_myclinic_fragment, container, false);

        mRecyclerView = (RecyclerView) view.findViewById(R.id.myclinic_recyclerview);
        itemView = view.findViewById(R.id.no_items);

        View.OnClickListener listener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (v == view.findViewById(R.id.addClinicButton)) {
                    MyProfileAddClinicFragment fragment = new MyProfileAddClinicFragment();
                    Bundle bundle = new Bundle();
                    bundle.putString("doctorClinic", "addClinic");
                    fragment.setArguments(bundle);
                    getFragmentManager().beginTransaction().add(R.id.containeractivity, fragment).addToBackStack(null).commit();
                }
            }
        };


        Button addClinic = (Button) view.findViewById(R.id.addClinicButton);
        addClinic.setOnClickListener(listener);


        return view;
    }


    public class MyClinicsListAdapter extends RecyclerSwipeAdapter<MyClinicsListAdapter.RecyclerViewHolder> {

        FragmentActivity context = null;
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
            viewHolder.hospitalAddress.setText(doctorClinic.getAddress().getStreetName() + "," + doctorClinic.getAddress().getBuildingName());

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
                            Log.i("clinicID", doctorClinics.get(position).getAddress().getCountry().getId() + "");
                            doctorClinic.DeleteClinic(doctorClinics.get(position).getClinicId(), new Globals.VolleyCallback() {
                                @Override
                                public void onSuccess(String result) {
                                    realm.beginTransaction();
                                    doctorClinic.removeFromRealm();
                                    realm.commitTransaction();
                                    notifyDataSetChanged();
                                    notifyItemRemoved(position);
                                    if(doctorClinics.size() > 0)
                                        itemView.setVisibility(View.GONE);
                                    else
                                        itemView.setVisibility(View.VISIBLE);
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
                    bundle.putString("doctorClinic", "fromEdit");
                    notifyDataSetChanged();
                    Log.i("clinicID", Globals.selectedDoctorClinic.getAddress().getState().getValue() + "");
                    fragment.setArguments(bundle);
                    context.getSupportFragmentManager().beginTransaction().replace(R.id.containeractivity, fragment).addToBackStack(null).commit();
                }
            });
            viewHolder.myClinicItem.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Globals.selectedDoctorClinic = doctorClinic;
                    context.getSupportFragmentManager().beginTransaction().add(R.id.containeractivity, new MyProfileClinicDetailsFragment()).addToBackStack(null).commit();
                }
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
            TextView hospitalName, hospitalAddress;
            View myClinicItem;

            public RecyclerViewHolder(View itemView) {
                super(itemView);


                myClinicItem = itemView.findViewById(R.id.myClinicItem);


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

        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this.getActivity()));
        mRecyclerView.addItemDecoration(new SimpleDividerItemDecoration(getActivity()));

        loadClinics();

        super.onStart();
    }

    public void loadClinics() {
        Globals.GET(Urls.CLINIC, new Globals.VolleyCallback() {
            @Override
            public void onSuccess(String result) {
                try {
                    JSONObject object = new JSONObject(result);
                    if (object.getBoolean("result")) {
                        realm.beginTransaction();
                        realm.allObjects(DoctorClinic.class).clear();
                        realm.createOrUpdateAllFromJson(DoctorClinic.class, object.getJSONArray("response"));
                        Log.i("Clinic_Response", object.getJSONArray("response").toString());
                        realm.commitTransaction();
                        doctorClinics = realm.allObjects(DoctorClinic.class);
                        for (DoctorClinic doctorClinic : doctorClinics) {
                            int id = doctorClinic.getClinicId();
                            if (doctorClinic.getAddress() == null) {
                                doctorClinic.setAddress(realm.createObject(Address.class));
                                doctorClinic.getAddress().setState(realm.createObject(State.class));
                                doctorClinic.getAddress().setCity(realm.createObject(City.class));
                                doctorClinic.getAddress().setCountry(realm.createObject(Country.class));

                            }

                        }
                        addValuesToRecyclerView(doctorClinics);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFail(String result) {
            }
        }, "");
    }

    private void addValuesToRecyclerView(RealmResults<DoctorClinic> doctorClinics) {
        MyClinicsListAdapter adapter = new MyClinicsListAdapter(this.getActivity(), doctorClinics);
        if (doctorClinics.size() > 0)
            itemView.setVisibility(View.GONE);
        else
            itemView.setVisibility(View.VISIBLE);
        mRecyclerView.setAdapter(adapter);
    }
}
