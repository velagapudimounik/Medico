package com.drughub.doctor.Login;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.drughub.doctor.R;
import com.drughub.doctor.model.User;
import com.drughub.doctor.network.Globals;

import org.json.JSONObject;


public class ForgotPasswordFragment extends Fragment {

    Fragment fragment = null;

    public ForgotPasswordFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        getActivity().setTitle(getString(R.string.forgotPassword));
        final View view = inflater.inflate(R.layout.login_forgotpassword, container, false);

        TextView text = (TextView) view.findViewById(R.id.reset);
        final EditText editEmail = (EditText) view.findViewById(R.id.emailForForgot);
        Button btn = (Button) view.findViewById(R.id.submitButton);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = editEmail.getText().toString();
                if (Globals.isValidEmail(email)) {
                    User user = new User();
                    user.setEmail(email);
                    user.ForgetPassword(getActivity(), new Globals.VolleyCallback() {
                        @Override
                        public void onSuccess(String result) {
                            Log.v("forget response", result);
                            JSONObject object = null;
                            try {
                                object = new JSONObject(result);
                                if (object.getBoolean("result")) {
                                    fragment = new ThanksRegards();
                                    getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.container1, fragment).commit();
                                } else {
                                    Toast.makeText(getActivity(), object.getString("errorMessage"), Toast.LENGTH_SHORT).show();
                                }
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }

                        @Override
                        public void onFail(String result) {
                            Toast.makeText(getActivity(), "Unable to process your request, please try again.", Toast.LENGTH_SHORT).show();
                        }
                    });
                }


            }
        });
        return view;
    }
}
