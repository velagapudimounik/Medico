package com.drughub.doctor.Login;


import android.support.v4.app.FragmentManager;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.support.v4.app.Fragment;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.drughub.doctor.R;
public class SignUpFragment extends Fragment {
    TextView titleSignup=null;
    Fragment fragment=null;

    public SignUpFragment() {

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        final View view = inflater.inflate(R.layout.signup, container, false);
        getActivity().setTitle("SignUP");
       // getActivity().setTitle("  SignUp");
        EditText name = (EditText) view.findViewById(R.id.nameSignup);
        EditText email = (EditText) view.findViewById(R.id.emailsignup);
        EditText mobile = (EditText) view.findViewById(R.id.mobilesignup);
        EditText password = (EditText) view.findViewById(R.id.passwordsignup);
        Button register = (Button) view.findViewById(R.id.registerbutton);
        TextView otp = (TextView) view.findViewById(R.id.otptextview);
        EditText otpinput = (EditText) view.findViewById(R.id.OTPEdittext);
        //ImageView symbol=(ImageView)findViewById(R.id.imagesignup);
        TextView resend = (TextView) view.findViewById(R.id.resend);

        View.OnClickListener listener = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (view == view.findViewById(R.id.registerbutton)) {
                    fragment = new LoginPage();
                }

                FragmentManager manager=getActivity().getSupportFragmentManager();
                FragmentTransaction transaction=manager.beginTransaction();
                transaction.replace(R.id.container1,fragment).commit();

                getActivity().getSupportFragmentManager().popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);
            }

        };


        Button btn1 = (Button)view.findViewById(R.id.registerbutton);
        btn1.setOnClickListener(listener);

        return view;
    }
}
