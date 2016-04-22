package com.drughub.doctor.MyProfile;


import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.drughub.doctor.R;
import com.drughub.doctor.model.ServiceProvider;
import com.drughub.doctor.utils.DrughubIcon;
import com.soundcloud.android.crop.Crop;

import java.io.File;

import io.realm.Realm;

public class MyProfileFragment extends Fragment {
    private ImageView profileImage;
    private TextView profileIcon;
    View imageView;
    RadioButton myprofile;
    RadioGroup profile_radiogroup;
    DrughubIcon editicon,righticon;
    TextView doctorName,doctorDHCode,doctorEmail;
    boolean editMode = false;

    private static final int SELECT_PICTURE = 120;
    public static final int PICK_IMAGE = 110;
    public static final  int CROP_IMAGE=200;
    String fileName ;
    Uri outputUri;

    Realm realm;
    ServiceProvider serviceProvider;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, final ViewGroup container, Bundle savedInstanceState) {
        getActivity().setTitle(getString(R.string.myProfile));
        final View view = inflater.inflate(R.layout.myprofile_main, container, false);
        profile_radiogroup = (RadioGroup) view.findViewById(R.id.myProfileRadiogroup);
        doctorName=(TextView)view.findViewById(R.id.doctorName);
        doctorDHCode=(TextView)view.findViewById(R.id.doctorID);
        doctorEmail=(TextView)view.findViewById(R.id.doctorEmail);
        editicon = (DrughubIcon) view.findViewById(R.id.Editicon);
        righticon = (DrughubIcon) view.findViewById(R.id.rightmark);
        imageView = view.findViewById(R.id.image_view);
        profileIcon = (DrughubIcon)view.findViewById(R.id.profile_icon);
        profileImage = (ImageView)view.findViewById(R.id.profile_image);
//        imageView.setEnabled(false);
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectImage();
                profileIcon.setVisibility(View.INVISIBLE);
                profileImage.setVisibility(View.VISIBLE);
            }
        });

        myprofile = (RadioButton) view.findViewById(R.id.myProfileButton);
        final RadioButton changepassword = (RadioButton) view.findViewById(R.id.changepasswordbutton);
        final RadioButton myclinic = (RadioButton) view.findViewById(R.id.Myclinic_button);
        getFragmentManager().beginTransaction().replace(R.id.container2, new MyProfileDetailsFragment()).commit();
        myprofile.setChecked(true);
        righticon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                ((MyprofileEditFragment)getFragmentManager().findFragmentById(R.id.container2)).updateProfile();
                getFragmentManager().beginTransaction().replace(R.id.container2, new MyProfileDetailsFragment()).commit();
                myprofile.setPressed(true);
                editMode = false;

                onUpdateProfile();
            }
        });
        editicon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getFragmentManager().beginTransaction().replace(R.id.container2, new MyprofileEditFragment()).commit();
                if(!myprofile.isChecked())
                {
                    editMode = true;
                    myprofile.setChecked(true);
                    changepassword.setChecked(false);
                    myclinic.setChecked(false);
                }

                profileIcon.setText(getResources().getText(R.string.icon_foradd_image));
                righticon.setVisibility(View.VISIBLE);
                editicon.setVisibility(View.INVISIBLE);
            }
        });
        profile_radiogroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if (checkedId == R.id.myProfileButton) {
                    if (!editMode)
                        getFragmentManager().beginTransaction().replace(R.id.container2, new MyProfileDetailsFragment()).commit();
                    editMode = false;
                } else if (checkedId == R.id.Myclinic_button) {
                    myprofile.setChecked(false);
                    righticon.setVisibility(View.INVISIBLE);
                    editicon.setVisibility(View.VISIBLE);
                    profileIcon.setText(getResources().getText(R.string.icon_noimage));
                    getFragmentManager().beginTransaction().replace(R.id.container2, new MyClinicsFragment()).commit();
                } else if (checkedId == R.id.changepasswordbutton) {
                    myprofile.setChecked(false);
                    righticon.setVisibility(View.INVISIBLE);
                    editicon.setVisibility(View.VISIBLE);
                    profileIcon.setText(getResources().getText(R.string.icon_noimage));
                    getFragmentManager().beginTransaction().replace(R.id.container2, new MyProfileChangePasswordFragment()).commit();
                }
            }
        });
        return view;
    }

    private void selectImage() {
        fileName = "temp_"+System.currentTimeMillis();
        //SyncStateContract.Constants.iscamera=true;
        final CharSequence[] items = {"Take a photo", "Select from Gallery", "Cancel"};
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle("Add a Photo");
        builder.setItems(items, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int item) {
                if (items[item].equals("Take a photo")) {
                    Intent camera = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                    File file = new File(android.os.Environment.getExternalStorageDirectory(), fileName);
                    camera.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(file));
                    startActivityForResult(camera, PICK_IMAGE);
                } else if (items[item].equals("Select from Gallery")) {
                    Intent photoPickerIntent = new Intent(Intent.ACTION_PICK);
                    photoPickerIntent.setType("image/*");
                    startActivityForResult(photoPickerIntent, SELECT_PICTURE);
                } else if (items[item].equals("Cancel")) {
                    dialog.dismiss();
                }
            }
        });
        builder.show();
    }
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        System.out.println(requestCode + "====requestcode");
        System.out.println(resultCode + "====resultcode");
        if (resultCode == AppCompatActivity.RESULT_OK) {
            if (requestCode == SELECT_PICTURE) {
                Uri selectedImageUri = data.getData();
                outputUri=selectedImageUri;
                Crop.of(selectedImageUri,outputUri).asSquare().start(getActivity(),MyProfileFragment.this);
            } else if (requestCode == PICK_IMAGE) {
                File file=new File(Environment.getExternalStorageDirectory().toString());
                for (File temp:file.listFiles()){
                    if (temp.getName().equals(fileName)){
                        file=temp;
                        break;
                    }
                }
                try{
                    Uri uri=Uri.fromFile(file);
                    outputUri=uri;
                    Crop.of(uri, outputUri).asSquare().start(getActivity(),MyProfileFragment.this);
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
            else if (requestCode==Crop.REQUEST_CROP){
                System.out.println(requestCode+"=====requestcode2");
                Toast.makeText(getActivity(), "Crop", Toast.LENGTH_SHORT).show();
                profileImage.setImageURI(outputUri);
                System.out.println(profileImage +"====p=====");
            }
        }
    }

    void onUpdateProfile()
    {
        if (serviceProvider != null){
            doctorName.setText("Dr."+serviceProvider.getFirstName());
            doctorDHCode.setText(""+serviceProvider.getSpProfileId());
            doctorEmail.setText(serviceProvider.getEmailId());
        }

        righticon.setVisibility(View.INVISIBLE);
        editicon.setVisibility(View.VISIBLE);
        profileIcon.setText(getResources().getText(R.string.icon_noimage));
    }

    @Override
    public void onStart() {
        super.onStart();
        realm = Realm.getDefaultInstance();
        serviceProvider = realm.where(ServiceProvider.class).findFirst();
        onUpdateProfile();
    }
}