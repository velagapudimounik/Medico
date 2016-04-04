package com.drughub.doctor.Login;


import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
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

public class SignUpFragment extends Fragment {
    TextView titleSignup = null;
    Fragment fragment = null;

    public SignUpFragment() {

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        final View view = inflater.inflate(R.layout.login_signup, container, false);
        getActivity().setTitle(getResources().getString(R.string.signup));

        final EditText editName = (EditText) view.findViewById(R.id.nameSignup);
        final EditText editEmail = (EditText) view.findViewById(R.id.emailsignup);
        final EditText editMobile = (EditText) view.findViewById(R.id.mobilesignup);
        final EditText editPassword = (EditText) view.findViewById(R.id.passwordsignup);
        editPassword.setTypeface(Typeface.DEFAULT);
        Button register = (Button) view.findViewById(R.id.registerbutton);
        TextView otp = (TextView) view.findViewById(R.id.otptextview);
        EditText otpinput = (EditText) view.findViewById(R.id.OTPEdittext);
        //ImageView symbol=(ImageView)findViewById(R.id.imagesignup);
        TextView resend = (TextView) view.findViewById(R.id.resend);

        View.OnClickListener listener = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (view.getId() == R.id.registerbutton) {
//                    fragment = new LoginPage();
                    String name = editName.getText().toString();
                    String email = editEmail.getText().toString();
                    String mobile = editMobile.getText().toString();
                    String password = editPassword.getText().toString();

                    if (name.length() > 0) {
                        if (Globals.isValidEmail(email)) {
                            if (mobile.length() >= 10) {
                                if (password.length() >= 8) {
                                    User user = new User();
                                    user.setEmail(email);
                                    user.setMobile(mobile);
                                    user.setName(name);
                                    user.setPassword(password);
                                    user.SignUp(getActivity());

                                } else {
                                    Toast.makeText(getActivity(), "Password should be minimum 8 characters", Toast.LENGTH_SHORT).show();
                                }

                            } else {
                                Toast.makeText(getActivity(), "Enter valid mobile number", Toast.LENGTH_SHORT).show();
                            }
                        } else {
                            Toast.makeText(getActivity(), "Enter valid email", Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        Toast.makeText(getActivity(), "Enter valid name", Toast.LENGTH_SHORT).show();
                    }


                }

//                FragmentManager manager=getActivity().getSupportFragmentManager();
//                FragmentTransaction transaction=manager.beginTransaction();
//                transaction.replace(R.id.container1,fragment).commit();
//
//                getActivity().getSupportFragmentManager().popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);
            }

        };

        register.setOnClickListener(listener);


        return view;
    }
}
