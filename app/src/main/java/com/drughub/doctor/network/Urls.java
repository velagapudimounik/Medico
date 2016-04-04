package com.drughub.doctor.network;

public class Urls {
    public static String BASE_URL = "";
    public static String SIGN_UP = BASE_URL+"/serviceprovider/1.0/signup";
    public static String SIGN_IN = BASE_URL+"/1.0/login/loginAuthentication";
    public static String ADD_CLINIC=BASE_URL+"/1.0/sp/clinics/{userId}";
    public static String UPDATE_CLINIC=BASE_URL+"/1.0/sp/clinic/{userId}";
    public static String CREATE_CALANDER = BASE_URL+"/1.0/sp/clinic";
    public static String SERVICE_PROVIDER = BASE_URL+"/1.0/sp/clinic";
}
