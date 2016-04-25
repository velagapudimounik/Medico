package com.drughub.doctor.Login;

import android.content.Intent;
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

import com.drughub.doctor.MainActivity;
import com.drughub.doctor.R;
import com.drughub.doctor.model.User;
import com.drughub.doctor.network.Globals;
import com.drughub.doctor.utils.PrefUtils;

import org.json.JSONException;
import org.json.JSONObject;

public class SignUpFragment extends Fragment {
    TextView titleSignup = null;
    Fragment fragment = null;
    public SignUpFragment() {}

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
                    String name = editName.getText().toString().trim();
                    final String email = editEmail.getText().toString().trim();
                    String mobile = editMobile.getText().toString().trim();
                    final String password = editPassword.getText().toString();
                    if (name.isEmpty())
                        Toast.makeText(getActivity(), getString(R.string.EnterName), Toast.LENGTH_SHORT).show();
                    else if (mobile.isEmpty())
                        Toast.makeText(getActivity(),getString(R.string.EnterMobile) , Toast.LENGTH_SHORT).show();
                    else if (!Globals.isValidPhoneNumber(mobile))
                        Toast.makeText(getActivity(),getString(R.string.EnterValidMobile),Toast.LENGTH_SHORT).show();
                    else if ( mobile.length()<10)
                        Toast.makeText(getActivity(),getString(R.string.EnterValidMobile), Toast.LENGTH_SHORT).show();
                    else if (email.isEmpty())
                        Toast.makeText(getActivity(),getString(R.string.EnterEmail), Toast.LENGTH_SHORT).show();
                    else if (!Globals.isValidEmail(email))
                        Toast.makeText(getActivity(),getString(R.string.EnterValidEmail), Toast.LENGTH_SHORT).show();
                    else if (password.isEmpty())
                        Toast.makeText(getActivity(),getString(R.string.EnterPassword), Toast.LENGTH_SHORT).show();
                    else if (password.length() < 8)
                        Toast.makeText(getActivity(),getString(R.string.PasswordValidation), Toast.LENGTH_SHORT).show();
                    else {
                        User user = new User();
                        user.setEmail(email);
                        user.setMobile(mobile);
                        user.setName(name);
                        user.setPassword(Globals.encryptString(password));
                        user.SignUp(getActivity(), new Globals.VolleyCallback() {
                            @Override
                            public void onSuccess(String result) {
                                try {
                                    JSONObject object = new JSONObject(result);
                                    if (object.getBoolean("result")) {
                                        PrefUtils.saveToLoginPrefs(getActivity(), email, password);
                                        startActivity(new Intent(getActivity(), MainActivity.class));
                                        getActivity().finish();
                                    } else {
                                        Toast.makeText(getActivity(), object.getString("errorMessage"), Toast.LENGTH_SHORT).show();
                                    }
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }

                            }

                            @Override
                            public void onFail(String result) {
                            }
                        });
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
