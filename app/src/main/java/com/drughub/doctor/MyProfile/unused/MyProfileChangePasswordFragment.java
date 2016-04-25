package com.drughub.doctor.MyProfile.unused;

import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.drughub.doctor.R;
import com.drughub.doctor.network.Globals;
import com.drughub.doctor.network.Urls;

import org.json.JSONException;
import org.json.JSONObject;

public class MyProfileChangePasswordFragment extends Fragment {
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.myprofile_changepassword_fragment, container, false);

        final EditText currentPassword = (EditText) view.findViewById(R.id.currentPassword);
        currentPassword.setTypeface(Typeface.DEFAULT);
        final EditText newPassword = (EditText) view.findViewById(R.id.newPassword);
        newPassword.setTypeface(Typeface.DEFAULT);
        final EditText confirmPassword = (EditText) view.findViewById(R.id.confirmPassword);
        confirmPassword.setTypeface(Typeface.DEFAULT);
        Button submitbutton = (Button) view.findViewById(R.id.buttonSubmit);
        submitbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String currentpwd = currentPassword.getText().toString();
                String newPwd = newPassword.getText().toString();
                String confirmpwd = confirmPassword.getText().toString();
                if (currentpwd.isEmpty())
                    Toast.makeText(getActivity(), "Please enter current password", Toast.LENGTH_SHORT).show();
                else if (newPwd.isEmpty())
                    Toast.makeText(getActivity(), "Please enter your new password", Toast.LENGTH_SHORT).show();
                else if (confirmpwd.isEmpty())
                    Toast.makeText(getActivity(), "Please confirm your new password", Toast.LENGTH_SHORT).show();
                else {
                    if (newPwd.equals(confirmpwd)) {
                        JSONObject object = new JSONObject();
                        try {
                            String newPass = Globals.encryptString(newPwd);
                            object.put("oldpassword", Globals.encryptString(currentPassword.getText().toString().trim()));
                            object.put("newpassword", newPass);
                            object.put("confirmpassword", newPass);
                            //object.put("email", PrefUtils.getUserName(getActivity()));
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        Globals.POST(Urls.CHANGE_PASSWORD, object.toString(), new Globals.VolleyCallback() {
                            @Override
                            public void onSuccess(String result) {
                                    try {
                                        JSONObject object = new JSONObject(result);
                                        if (object.getBoolean("result"))
                                        {
                                            currentPassword.setText("");
                                            newPassword.setText("");
                                            confirmPassword.setText("");
                                            Toast.makeText(getActivity(), "Your password changed successfully", Toast.LENGTH_SHORT).show();
                                            Log.v("result==change", result);
                                        }
                                        else {
                                            Toast.makeText(getActivity(), ""+object.getString("errorMessage"), Toast.LENGTH_SHORT).show();
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
                }
            }
        });

        return view;
    }


}
