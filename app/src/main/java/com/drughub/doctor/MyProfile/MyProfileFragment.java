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
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.drughub.doctor.R;
import com.drughub.doctor.utils.DrughubIcon;
import com.soundcloud.android.crop.Crop;

import java.io.File;

public class MyProfileFragment extends Fragment {
    private ImageView profileimage;
    RadioButton myprofile;
    RadioGroup profile_radiogroup;
    FrameLayout emptyimageview;
    FrameLayout addimageIcon;
    DrughubIcon editicon;
    DrughubIcon righticon;
    boolean clickCount = false;
    private static final int SELECT_PICTURE = 120;
    public static final int PICK_IMAGE = 110;
    public static final  int CROP_IMAGE=200;
    String fileName ;
    Uri outputUri;

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
        editicon = (DrughubIcon) view.findViewById(R.id.Editicon);
        //editicon.setOnClickListener(this);
        righticon = (DrughubIcon) view.findViewById(R.id.rightmark);
        emptyimageview=(FrameLayout)view.findViewById(R.id.emptyimage);
        //righticon.setOnClickListener(this);
        //final TextView changetext=(TextView)view.findViewById(R.id.profile_changetextview);
        addimageIcon = (FrameLayout) view.findViewById(R.id.add_image_view);
        profileimage=(ImageView)view.findViewById(R.id.profile_image);
        addimageIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectImage();
                addimageIcon.setVisibility(View.INVISIBLE);
                emptyimageview.setVisibility(View.GONE);
                profileimage.setEnabled(false);

            }
        });
        //addimageIcon.setOnClickListener(this);
        emptyimageview = (FrameLayout) view.findViewById(R.id.emptyimage);
        myprofile = (RadioButton) view.findViewById(R.id.myProfileButton);
        final RadioButton changepassword = (RadioButton) view.findViewById(R.id.changepasswordbutton);
        final RadioButton myclinic = (RadioButton) view.findViewById(R.id.Myclinic_button);
        getFragmentManager().beginTransaction().replace(R.id.container2, new MyProfileDetailsFragment()).commit();
        myprofile.setChecked(true);
        righticon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getFragmentManager().beginTransaction().replace(R.id.container2, new MyProfileDetailsFragment()).commit();
                righticon.setVisibility(View.INVISIBLE);
                editicon.setVisibility(View.VISIBLE);
                addimageIcon.setVisibility(View.INVISIBLE);
                //emptyimageview.setVisibility(View.VISIBLE);
                myprofile.setPressed(true);
            }
        });
        editicon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clickCount = true;
                getFragmentManager().beginTransaction().replace(R.id.container2, new MyprofileEditFragment()).commit();
                myprofile.setChecked(true);
                changepassword.setChecked(false);
                myclinic.setChecked(false);
                System.out.println(clickCount + "clickCount");
                Toast.makeText(getActivity(), "Editmain", Toast.LENGTH_SHORT).show();
                addimageIcon.setVisibility(View.VISIBLE);
                righticon.setVisibility(View.VISIBLE);
                editicon.setVisibility(View.INVISIBLE);
                emptyimageview.setVisibility(View.INVISIBLE);
                //clickCount=false;
            }
        });
        profile_radiogroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if (checkedId == R.id.myProfileButton) {
                    getFragmentManager().beginTransaction().replace(R.id.container2, new MyProfileDetailsFragment()).commit();
                    if (clickCount) {
                        Toast.makeText(getContext(), "Edit", Toast.LENGTH_SHORT).show();
                        getFragmentManager().beginTransaction().replace(R.id.container2, new MyprofileEditFragment()).commit();
                    }
                    clickCount = false;
                } else if (checkedId == R.id.Myclinic_button) {
                    myprofile.setChecked(false);
                    righticon.setVisibility(View.INVISIBLE);
                    addimageIcon.setVisibility(View.INVISIBLE);
                    emptyimageview.setVisibility(View.VISIBLE);
                    editicon.setVisibility(View.VISIBLE);
                    getFragmentManager().beginTransaction().replace(R.id.container2, new MyClinicsFragment()).commit();
                } else if (checkedId == R.id.changepasswordbutton) {
                    myprofile.setChecked(false);
                    righticon.setVisibility(View.INVISIBLE);
                    addimageIcon.setVisibility(View.INVISIBLE);
                    emptyimageview.setVisibility(View.VISIBLE);
                    editicon.setVisibility(View.VISIBLE);
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
                    File file=new File(android.os.Environment.getExternalStorageDirectory(),fileName);
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
        System.out.println(requestCode+"====requestcode");
        System.out.println(resultCode+"====resultcode");
        if (resultCode == getActivity().RESULT_OK) {
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
                profileimage.setImageURI(outputUri);
                System.out.println(profileimage+"====p=====");
            }
        }
    }


    public void editIcon() {
        righticon.setVisibility(View.INVISIBLE);
        editicon.setVisibility(View.VISIBLE);
        addimageIcon.setVisibility(View.INVISIBLE);
        emptyimageview.setVisibility(View.VISIBLE);
        myprofile.setChecked(true);

    }
}