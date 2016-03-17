package com.drughub.doctor.Login;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.drughub.doctor.R;


public class ForgotPasswordFragment extends Fragment {

    Fragment fragment=null;
    public ForgotPasswordFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        getActivity().setTitle("   Forgot Password  ");
        final View view=inflater.inflate(R.layout.forgotpassword, container, false);

        TextView text=(TextView)view.findViewById(R.id.reset);
        EditText email=(EditText)view.findViewById(R.id.emailForForgot);
        Button btn=(Button)view.findViewById(R.id.submitButton);
        // Inflate the layout for this fragment
        View.OnClickListener listener=new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (view==view.findViewById(R.id.submitButton)){
                    fragment=new ThanksRegards();
                }

                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.container1,fragment).commit();
            }
        };


        Button btn1 = (Button)view.findViewById(R.id.submitButton);
        btn1.setOnClickListener(listener);
        return view;
    }
}
