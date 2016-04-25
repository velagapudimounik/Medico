package com.drughub.doctor.model;

import android.content.Context;
import android.support.v4.app.FragmentActivity;

import com.drughub.doctor.network.Globals;
import com.drughub.doctor.network.Urls;

import org.json.JSONObject;

import io.realm.RealmObject;

public class User extends RealmObject {

    private String name;
    private String email;
    private String mobile;
    private String password;

    private String address;
    private String role;
    private String createdDate;
    private String phone;
    private String userId;
    private String dhcode;
    private String firstName;
    private String lastName;

    public String getName() {
        return name;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public String getMobile() {
        return mobile;
    }

    public String getPassword() {
        return password;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getAddress() {
        return address;
    }

    public void setCreatedDate(String createdDate) {
        this.createdDate = createdDate;
    }

    public String getCreatedDate() {
        return createdDate;
    }

    public void setDhcode(String dhcode) {
        this.dhcode = dhcode;
    }

    public String getDhcode() {
        return dhcode;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getPhone() {
        return phone;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getRole() {
        return role;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUserId() {
        return userId;
    }

    public String toSignUp() {
        JSONObject params = new JSONObject();
        try {
            params.put("name", getName());
            params.put("email", getEmail());
            params.put("mobile", getMobile());
            params.put("password", getPassword());
//            object.put("category", 2);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return params.toString();
    }

    public String toSignIn() {
        JSONObject object = new JSONObject();
        try {
            object.put("email", getEmail());
            object.put("passwordDigest", getPassword());
            object.put("typeOfLogin", "Android");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return object.toString();
    }

    public String toForgetPassword() {
        JSONObject object = new JSONObject();
        try {
            object.put("email", getEmail());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return object.toString();
    }


    public void SignUp(Context context, final Globals.VolleyCallback callback) {

        Globals.POST(Urls.SIGN_UP, toSignUp(), new Globals.VolleyCallback() {
            @Override
            public void onSuccess(String result) {
                try {
//                    Log.v("response"," "+result );
                    callback.onSuccess(result);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFail(String result) {
                callback.onFail(result);
            }
        }, "Signing Up");
    }

    public void SignIn(Context context, final Globals.VolleyCallback callback) {

        Globals.POST(Urls.SIGN_IN, toSignIn(), new Globals.VolleyCallback() {
            @Override
            public void onSuccess(String result) {
                callback.onSuccess(result);
            }

            @Override
            public void onFail(String result) {
                callback.onFail(result);
            }
        }, "Signing In");
    }

    public void ForgetPassword(FragmentActivity activity, final Globals.VolleyCallback callback) {

        Globals.POST(Urls.FORGET_PASSWORD, toForgetPassword(), new Globals.VolleyCallback() {
            @Override
            public void onSuccess(String result) {
                callback.onSuccess(result);
            }

            @Override
            public void onFail(String result) {
                callback.onFail(result);
            }
        }, "");

    }
}
