package com.example.test;


import android.Manifest;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.romellfudi.permission.PermissionService;
import com.romellfudi.ussdlibrary.OverlayShowingService;
import com.romellfudi.ussdlibrary.SplashLoadingService;
import com.romellfudi.ussdlibrary.USSDApi;
import com.romellfudi.ussdlibrary.USSDController;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;


/**
 * A simple {@link Fragment} subclass.
 */
public class MainFragment extends Fragment {

    private TextView result;
    private EditText phone;
    private Button btn1, btn2, btn3, btn4;
    private HashMap<String, HashSet<String>> map;
    private USSDApi ussdApi;
    private MainActivity menuActivity;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        map = new HashMap<>();
        map.put("KEY_LOGIN", new HashSet<>(Arrays.asList("espere", "waiting", "loading", "esperando")));
        map.put("KEY_ERROR", new HashSet<>(Arrays.asList("problema", "problem", "error", "null")));
        ussdApi = USSDController.getInstance(getActivity());
        menuActivity = (MainActivity) getActivity();
        new PermissionService(getActivity()).request(
                new String[]{Manifest.permission.CALL_PHONE, Manifest.permission.READ_PHONE_STATE},
                callback);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.content_op1, container, false);
        result = (TextView) view.findViewById(R.id.result);
        phone = (EditText) view.findViewById(R.id.phone);
        btn1 = (Button) view.findViewById(R.id.btn1);
//        btn2 = (Button) view.findViewById(R.id.btn2);
        btn3 = (Button) view.findViewById(R.id.btn3);
//        btn4 = (Button) view.findViewById(R.id.btn4);
        setHasOptionsMenu(false);

        btn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String phoneNumber = phone.getText().toString().trim();
                ussdApi = USSDController.getInstance(getActivity());
                result.setText("");
                ussdApi.callUSSDInvoke(phoneNumber, map, new USSDController.CallbackInvoke() {
                    @Override
                    public void responseInvoke(String message) {
                        Log.d("APP", message);
                        result.append("\n-\n" + message);
                        // first option list - select option 1
                        ussdApi.send("1", new USSDController.CallbackMessage() {
                            @Override
                            public void responseMessage(String message) {
                                Log.d("APP", message);
                                result.append("\n-\n" + message);
                                // second option list - select option 1
                                ussdApi.send("1", new USSDController.CallbackMessage() {
                                    @Override
                                    public void responseMessage(String message) {
                                        Log.d("APP", message);
                                        result.append("\n-\n" + message);
                                    }
                                });
                            }
                        });
                    }

                    @Override
                    public void over(String message) {
                        Log.d("APP", message);
                        result.append("\n-\n" + message);
                    }
                });
            }
        });

//        btn2.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                if (USSDController.verifyOverLay(getActivity())) {
//                    final Intent svc = new Intent(getActivity(), OverlayShowingService.class);
//                    svc.putExtra(OverlayShowingService.EXTRA, "PROCESANDO");
//                    getActivity().startService(svc);
//                    Log.d("APP", "START OVERLAY DIALOG");
//                    String phoneNumber = phone.getText().toString().trim();
//                    ussdApi = USSDController.getInstance(getActivity());
//                    result.setText("");
//                    ussdApi.callUSSDOverlayInvoke(phoneNumber, map, new USSDController.CallbackInvoke() {
//                        @Override
//                        public void responseInvoke(String message) {
//                            Log.d("APP", message);
//                            result.append("\n-\n" + message);
//                            // first option list - select option 1
//                            ussdApi.send("1", new USSDController.CallbackMessage() {
//                                @Override
//                                public void responseMessage(String message) {
//                                    Log.d("APP", message);
//                                    result.append("\n-\n" + message);
//                                    // second option list - select option 1
//                                    ussdApi.send("1", new USSDController.CallbackMessage() {
//                                        @Override
//                                        public void responseMessage(String message) {
//                                            Log.d("APP", message);
//                                            result.append("\n-\n" + message);
//                                            getActivity().stopService(svc);
//                                            Log.d("APP", "STOP OVERLAY DIALOG");
//                                            Log.d("APP", "successful");
//                                        }
//                                    });
//                                }
//                            });
//                        }
//
//                        @Override
//                        public void over(String message) {
//                            Log.d("APP", message);
//                            result.append("\n-\n" + message);
//                            getActivity().stopService(svc);
//                            Log.d("APP", "STOP OVERLAY DIALOG");
//                        }
//                    });
//                }
//            }
//        });

//        btn4.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                if (USSDController.verifyOverLay(getActivity())) {
//                    final Intent svc = new Intent(getActivity(), SplashLoadingService.class);
//                    getActivity().startService(svc);
//                    Log.d("APP", "START SPLASH DIALOG");
//                    String phoneNumber = phone.getText().toString().trim();
//                    result.setText("");
//                    ussdApi.callUSSDOverlayInvoke(phoneNumber, map, new USSDController.CallbackInvoke() {
//                        @Override
//                        public void responseInvoke(String message) {
//                            Log.d("APP", message);
//                            result.append("\n-\n" + message);
//                            // first option list - select option 1
//                            ussdApi.send("1", new USSDController.CallbackMessage() {
//                                @Override
//                                public void responseMessage(String message) {
//                                    Log.d("APP", message);
//                                    result.append("\n-\n" + message);
//                                    // second option list - select option 1
//                                    ussdApi.send("1", new USSDController.CallbackMessage() {
//                                        @Override
//                                        public void responseMessage(String message) {
//                                            Log.d("APP", message);
//                                            result.append("\n-\n" + message);
//                                            getActivity().stopService(svc);
//                                            Log.d("APP", "STOP SPLASH DIALOG");
//                                            Log.d("APP", "successful");
//                                        }
//                                    });
//                                }
//                            });
//                        }
//
//                        @Override
//                        public void over(String message) {
//                            Log.d("APP", message);
//                            result.append("\n-\n" + message);
//                            getActivity().stopService(svc);
//                            Log.d("APP", "STOP OVERLAY DIALOG");
//                        }
//                    });
//                }
//            }
//        });

        btn3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                USSDController.verifyAccesibilityAccess(getActivity());
            }
        });

        return view;
    }

    private PermissionService.Callback callback = new PermissionService.Callback() {
        @Override
        public void onRefuse(ArrayList<String> RefusePermissions) {
            Toast.makeText(getContext(),
                    getString(R.string.refuse_permissions),
                    Toast.LENGTH_SHORT).show();
            getActivity().finish();
        }

        @Override
        public void onFinally() {
            // pass
        }
    };

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        callback.handler(permissions, grantResults);
    }
}

