package com.drughub.doctor.network;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Point;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.support.v4.util.LruCache;
import android.util.Log;
import android.view.Display;
import android.view.WindowManager;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.ImageRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.drughub.doctor.MyApplication;
import com.drughub.doctor.model.User;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;


public class Globals {
    private static final String TAG = "GLOBALS";
    private static final String VOLLEY_TAG = "VOLLEY_GLOBALS";
    public static Drawable drawable;
    public static int MY_SOCKET_TIMEOUT_MS = 15000;
    public static Typeface typeFace, lightTypeFace;
    public static Double latitude = 0.0, longitude = 0.0;
    public static boolean sendLocation = false;
    public static User userProfile;
    static String stringResponse = null;
    static Bitmap responseBitmap;
    private static ImageLoader mImageLoader;
    private static Context mCtx;
    private static RequestQueue mRequestQueue;

    public Globals(Context context) {
        mCtx = context;
        mRequestQueue = getRequestQueue();
    }

    public static void startStringRequest(int method, final String url, final Map<String, String> headers, final Map<String, String> params, final String body, final VolleyCallback callback) {
        getRequestQueue();
        StringRequest stringRequest = new StringRequest(method, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.v(VOLLEY_TAG, "onResponse(): " + response);
                stringResponse = response;
                callback.onSuccess(stringResponse);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                try {
                    Log.v(VOLLEY_TAG, "URL: " + url);
                    Log.v(VOLLEY_TAG, "onErrorResponse(): " + volleyError);

                    Log.v(VOLLEY_TAG, "onErrorResponse(): StatusCode: " + volleyError.networkResponse.statusCode);

                    if (volleyError.networkResponse.statusCode == 400)
                        stringResponse = "Bad Request";
                    else if (volleyError.networkResponse.statusCode == 401)
                        stringResponse = "Session Timed Out";
                    else if (volleyError.networkResponse.statusCode == 403)
                        stringResponse = "Forbidden Request";
                    else if (volleyError.networkResponse.statusCode == 404)
                        stringResponse = "URL Not Found";
                    else if (volleyError.networkResponse.statusCode == 500)
                        stringResponse = "Internal Server Error";
                    else
                        stringResponse = "Error Occurred";

                } catch (Exception e) {
                    stringResponse = "Error Occurred";
                    Log.v(VOLLEY_TAG, "onErrorResponse(): Exception: " + e);
                    e.printStackTrace();
                }
                callback.onFail(stringResponse);
            }
        }) {

            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {

                Map<String, String> localHeaders = headers;

                if (localHeaders == null)
                    localHeaders = super.getHeaders();

                if (localHeaders == null
                        || localHeaders.equals(Collections.emptyMap())) {
                    localHeaders = new HashMap<String, String>();
                }
                MyApplication.get().addSessionCookie(localHeaders);

                localHeaders.put("Content-Type", "application/json");

                Log.v(VOLLEY_TAG, "URL: " + url);
                Log.v(VOLLEY_TAG, "getHeaders(): " + localHeaders.toString());

                return localHeaders;
            }

            @Override
            protected Map<String, String> getParams() throws AuthFailureError {

                Map<String, String> localParams = params;

                if (localParams == null)
                    localParams = super.getParams();

                Log.v(VOLLEY_TAG, "getParams(): " + localParams);

                return localParams;
            }

            @Override
            public byte[] getBody() throws AuthFailureError {

                String localBody = body;

                if (localBody == null || localBody.isEmpty())
                    return super.getBody();

                Log.v(VOLLEY_TAG, "getBody(): " + localBody);

                return localBody.getBytes();
            }

            @Override
            protected Response<String> parseNetworkResponse(NetworkResponse response) {

                MyApplication.get().checkSessionCookie(response.headers);

                Log.v(VOLLEY_TAG, "URL: " + url);
                Log.v(VOLLEY_TAG, "parseNetworkResponse(): StatusCode: " + response.statusCode);
                Log.v(VOLLEY_TAG, "parseNetworkResponse(): Headers: " + response.headers.toString());
                Log.v(VOLLEY_TAG, "parseNetworkResponse(): Data: " + response.data.toString());

                return super.parseNetworkResponse(response);
            }
        };

        stringRequest.setRetryPolicy(new DefaultRetryPolicy(MY_SOCKET_TIMEOUT_MS, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        addRequestQueue(stringRequest);
    }


    /*GET METHOD REQUEST FOR API*/
    public static void GET(String url, final Map<String, String> headers, final VolleyCallback callback) {
        startStringRequest(Request.Method.GET, url, headers, null, null, callback);
    }

    /*POST METHOD REQUEST FOR API*/
    public static void POST(String url, final Map<String, String> params, final String body, final VolleyCallback callback) {
        startStringRequest(Request.Method.POST, url, null, params, body, callback);
    }

    /*PUT METHOD REQUEST FOR API*/
    public static void PUT(String url, final Map<String, String> headers, final Map<String, String> params, final String body, final VolleyCallback callback) {
        startStringRequest(Request.Method.PUT, url, headers, params, body, callback);
    }

    /*PATCH METHOD REQUEST FOR API*/
    public static void PATCH(String url, final Map<String, String> headers, final Map<String, String> params, final String body, final VolleyCallback callback) {
        startStringRequest(Request.Method.PATCH, url, headers, params, body, callback);
    }

    /*IMAGE REQUEST FOR API*/
    public static Bitmap IMAGE(String url, final HashMap<String, String> headers, int width, int height, final VolleyImageCallback callback) {
        getRequestQueue();
        ImageRequest imageRequest = new ImageRequest(url, new Response.Listener<Bitmap>() {
            @Override
            public void onResponse(Bitmap bitmap) {
                responseBitmap = bitmap;
                callback.onSuccess(bitmap);

            }
        }, width, height, null, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                try {
                    if (volleyError.networkResponse.statusCode == 404) {
                        Log.v("statusCode", "" + volleyError.networkResponse.statusCode);
                        stringResponse = "URL Not Found";
                        Log.v("stringResponse", stringResponse);
                    } else if (volleyError.networkResponse.statusCode == 400) {
                        stringResponse = "Bad Request";
                    } else if (volleyError.networkResponse.statusCode == 500) {
                        stringResponse = "Internal Server Error";
                    } else {
                        stringResponse = "error occured";
                    }

                    callback.onFail(stringResponse);
                } catch (Exception e) {
                    stringResponse = "error occured";
                    e.printStackTrace();
                }
            }
        }) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                return headers;
            }
        };
        imageRequest.setRetryPolicy(new DefaultRetryPolicy(
                MY_SOCKET_TIMEOUT_MS,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        addRequestQueue(imageRequest);
        return responseBitmap;
    }

    public static RequestQueue getRequestQueue() {
        if (mRequestQueue == null)
            mRequestQueue = Volley.newRequestQueue(mCtx);

        return mRequestQueue;

    }

    public static void addRequestQueue(Request request) {
        getRequestQueue().add(request);
    }

    /*Method For ImageLoader*/
    public static ImageLoader getImageLoader() {
        mImageLoader = new ImageLoader(mRequestQueue,
                new ImageLoader.ImageCache() {
                    private final LruCache<String, Bitmap>
                            cache = new LruBitmapCache();

                    @Override
                    public Bitmap getBitmap(String url) {
                        return cache.get(url);
                    }

                    @Override
                    public void putBitmap(String url, Bitmap bitmap) {
                        cache.put(url, bitmap);
                    }
                });
        return mImageLoader;
    }

    public static Point getScreenSize(Context context) {
        WindowManager windowManager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        Display display = windowManager.getDefaultDisplay();
        Point point = new Point();
        display.getSize(point);
        return point;
    }

    public static boolean isOnline(Context contex) {
        ConnectivityManager connectivityManager = (ConnectivityManager) contex.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
        if (networkInfo != null && networkInfo.isConnectedOrConnecting()) {

            return true;
        }

        return false;
    }

    public static Typeface typefaceFor(Context context, String typeface) {
        return Typeface.createFromAsset(context.getAssets(), "fonts/" + typeface);
    }

    public static String encryptString(String value) {
        String md5 = null;
//        value = salt.concat(value);
        if (null == value) return null;
        try {
            //Create MessageDigest object for MD5
            MessageDigest digest = MessageDigest.getInstance("SHA-1");
            digest.update(value.getBytes(), 0, value.length());
            md5 = new BigInteger(1, digest.digest()).toString(16);

        } catch (Exception e) {
            e.printStackTrace();
        }
        return md5;
    }

    public static DateFormat getDateFormat() {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("MM/dd/yyyy");
        return simpleDateFormat;
    }

    public static String getDeviceName() {
        final String manufacturer = Build.MANUFACTURER;
        final String model = Build.MODEL;
        if (model.startsWith(manufacturer)) {
            return model;
        }
        if (manufacturer.equalsIgnoreCase("HTC")) {
            // make sure "HTC" is fully capitalized.
            return "HTC " + model;
        }
        return manufacturer + " " + model;
    }

    //    public static Bitmap customMarker(Context context, Bitmap bmp1) {
//        Bitmap bmp2 = BitmapFactory.decodeResource(context.getResources(), R.drawable.pin);
//        try {
//
//            Bitmap bmOverlay = Bitmap.createBitmap(bmp2.getWidth(), bmp2.getHeight(), bmp1.getConfig());
//            Canvas canvas = new Canvas(bmOverlay);
//            canvas.drawBitmap(bmp1, 12, 10, null);
//            canvas.drawBitmap(bmp2, 0, 0, null);
//            return bmOverlay;
//
//        } catch (Exception e) {
//            return bmp2;
//        }
//    }
    public final static boolean isValidEmail(String email) {
        if (email == null) {
            return false;
        } else {
            return android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches();
        }
    }

//    public static void addDevice(Context context){
//        HashMap<String, String> postParams = new HashMap<>();
//        HashMap<String, String> postHeaders = new HashMap<>();
//        postHeaders.put("token",Globals.userProfile.token);
//
//        postParams.put("push_token", ZeroPush.getInstance().getDeviceToken());
//        postParams.put("model", getDeviceName());
//        postParams.put("os_version", Build.VERSION.RELEASE);
//        postParams.put("os_name","android");
//        Log.v("device params"," "+postParams);
//
//        Globals.POST(Globals.urlFor(context, "device"), postHeaders, postParams, "", new VolleyCallback() {
//            @Override
//            public void onSuccess(String result) {
//                Log.v("result", " " + result);
//            }
//
//            @Override
//            public void onFail(String result) {
//                Log.v("result error"," "+result);
//            }
//        });
//    }

    public static void getCountries(final VolleyCallback callback) {
        GET(Urls.COUNTRY, null, new VolleyCallback() {
            @Override
            public void onSuccess(String result) {
                callback.onSuccess(result);
            }

            @Override
            public void onFail(String result) {
                callback.onFail(result);
            }
        });
    }

    public static void getState(int id, final VolleyCallback callback) {
        GET(Urls.STATE + id, null, new VolleyCallback() {
            @Override
            public void onSuccess(String result) {
                callback.onSuccess(result);
            }

            @Override
            public void onFail(String result) {
                callback.onFail(result);
            }
        });
    }

    public static void getCity(int id, final VolleyCallback callback) {
        GET(Urls.CITY + id, null, new VolleyCallback() {
            @Override
            public void onSuccess(String result) {
                callback.onSuccess(result);
            }

            @Override
            public void onFail(String result) {
                callback.onFail(result);
            }
        });
    }

    public interface VolleyCallback {
        void onSuccess(String result);

        void onFail(String result);
    }

    public interface VolleyImageCallback {
        void onSuccess(Bitmap bitmap);

        void onFail(String result);
    }
    public static void getSpecialization(final VolleyCallback callback){
        GET(Urls.GET_SPECIALLIZATION, null, new VolleyCallback() {
            @Override
            public void onSuccess(String result) {
                callback.onSuccess(result);
                Log.v("result===", result);
            }

            @Override
            public void onFail(String result) {
                callback.onFail(result);
                Log.v("FailSP===", result);
            }
        });
    }
    public static void getQualification(final VolleyCallback callback){
        GET(Urls.GET_QUALIFICATION, null, new VolleyCallback() {
            @Override
            public void onSuccess(String result) {
                callback.onSuccess(result);
                Log.v("resultQL===",result);
            }

            @Override
            public void onFail(String result) {
                callback.onFail(result);
                Log.v("FailQL===",result);
            }
        });
    }

}
