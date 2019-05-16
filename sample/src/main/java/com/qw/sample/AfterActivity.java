package com.qw.sample;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.widget.Toast;

import com.qw.sample.adapter.CheckPermissionAdapter;
import com.qw.sample.utils.Utils;
import com.qw.sample.utils.UtilsWithPermission;
import com.qw.soul.permission.SoulPermission;
import com.qw.soul.permission.bean.Permission;
import com.qw.soul.permission.callbcak.RequestPermissionListener;
import com.qw.soul.permission.callbcak.RequestPermissionWithRationaleListener;

import static android.os.Build.VERSION_CODES.M;

public class AfterActivity extends AppCompatActivity {
    private static final int REQUEST_CODE_CONTACT = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_example);
        findViewById(R.id.bt_call).setOnClickListener(view -> {
            UtilsWithPermission.makeCall(AfterActivity.this, "10086");
//                makeCall();
        });
        findViewById(R.id.bt_choose_contact).setOnClickListener(view -> chooseContact());
        findViewById(R.id.bt_read_storage).setOnClickListener(view -> requestReadExternalStoragePermission());
        findViewById(R.id.bt_get_imei).setOnClickListener(view -> getDeviceId(this, new Callback() {
            @Override
            public void callback(String deviceId) {
                Toast.makeText(AfterActivity.this, deviceId, Toast.LENGTH_LONG).show();
            }
        }));
        findViewById(R.id.bt_get_imsi).setOnClickListener(view -> getImsi(this, new Callback() {
            @Override
            public void callback(String imsi) {
                Toast.makeText(AfterActivity.this, imsi, Toast.LENGTH_LONG).show();
            }
        }));

        findViewById(R.id.bt_go_seeting).setOnClickListener(view -> {
            SoulPermission.goPermissionSettings();
        });
    }

    public interface Callback {
        void callback(String id);
    }

    /**
     * 获取设备ID
     *
     * @param context
     * @return
     */
    public static void getDeviceId(final Context context, final Callback callback) {
        int sdkVersion = android.os.Build.VERSION.SDK_INT, P = 28;
        if(sdkVersion > P) { // Android Q 无论如何都获取不到 IMEI，使用utdid
            callback.callback("Q");
        } else { // 申请权限获取IMEI：（低于6.0手机，SoulPermission会直接返回onGranted）
            SoulPermission.requestPermission(Manifest.permission.READ_PHONE_STATE, new RequestPermissionListener() {
                @SuppressLint("MissingPermission")
                @Override
                public void onGranted(Permission permission) {
                    callback.callback(getDeviceId(context));
                }

                @Override
                public void onDenied(Permission permission) {
                    callback.callback("deny");
                }
            });
        }
    }

    public static void getImsi(final Context context, final Callback callback) {
        int sdkVersion = android.os.Build.VERSION.SDK_INT, P = 28;
        if(sdkVersion > P) { // Android Q 无论如何都获取不到 IMSI，返回 ""
            callback.callback("");
        } else { // 申请权限获取IMEI：（低于6.0手机，SoulPermission会直接返回onGranted）
            SoulPermission.requestPermission(Manifest.permission.READ_PHONE_STATE, new RequestPermissionListener() {
                @SuppressLint("MissingPermission")
                @Override
                public void onGranted(Permission permission) {
                    callback.callback(getImsi(context));
                }

                @Override
                public void onDenied(Permission permission) {
                    callback.callback(getImsi(context));
                }
            });
        }
    }

    @SuppressLint("MissingPermission")
    public static String getDeviceId(Context context) {
        try {
            TelephonyManager telephonyManager = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
            return telephonyManager.getDeviceId();
        } catch (Exception ignore) {
            return "getDeviceId";
        }
    }

    @SuppressLint("MissingPermission")
    private static String getImsi(Context context) {
        try {
            TelephonyManager telephonyManager = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
            //获取IMSI号
            return telephonyManager.getSubscriberId();
        } catch (Exception e) {
            Log.e("AppUtils", e.getMessage());
            return "";
        }
    }
    public void requestReadExternalStoragePermission() {
        String rationaleMessage = "如果您拒绝了权限，将无法选择本地视频，请点击授予权限";
        SoulPermission.requestPermission(Manifest.permission.READ_EXTERNAL_STORAGE,
                new RequestPermissionWithRationaleListener(rationaleMessage) {
                    @Override
                    public void onGranted(Permission permission) {
                        toast();
                    }
                });
    }

    private void toast() {
        Toast.makeText(AfterActivity.this, "已授权", Toast.LENGTH_SHORT).show();
    }



//    public void makeCall() {
//        SoulPermission
//                .checkAndRequestPermission(Manifest.permission.CALL_PHONE, new RequestPermissionListener() {
//                    @Override
//                    public void onPermissionOk(Permission permission) {
//                        Utils.makeCall(AfterActivity.this, "10086");
//                    }
//
//                    @Override
//                    public void onPermissionDenied(Permission permission) {
//                        //绿色框中的流程
//                        //用户第一次拒绝了权限，没有勾选"不再提示。"这个值为true，此时告诉用户为什么需要这个权限。
//                        if (permission.shouldRationale) {
//                            new AlertDialog.Builder(AfterActivity.this)
//                                    .setTitle("提示")
//                                    .setMessage("如果你拒绝了权限，你将无法拨打电话，请点击授予权限")
//                                    .setPositiveButton("授予", new DialogInterface.OnClickListener() {
//                                        @Override
//                                        public void onClick(DialogInterface dialogInterface, int i) {
//                                            //用户确定以后，重新执行请求原始流程
//                                            makeCall();
//                                        }
//                                    }).create().show();
//                        } else {
//                            Toast.makeText(AfterActivity.this, "本次拨打电话授权失败,请手动去设置页打开权限，或者重试授权权限", Toast.LENGTH_SHORT).show();
//                        }
//                    }
//                });
//    }

    public void chooseContact() {
        UtilsWithPermission.chooseContact(AfterActivity.this, REQUEST_CODE_CONTACT);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable final Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                case REQUEST_CODE_CONTACT:
                    Utils.onGetChooseContactData(AfterActivity.this, data, new Utils.ReadContactListener() {
                        @Override
                        public void onSuccess(Utils.ContactInfo contactInfo) {
                            Toast.makeText(AfterActivity.this, contactInfo.toString(), Toast.LENGTH_SHORT).show();
                        }

                        @Override
                        public void onFailed() {

                        }
                    });
                    break;
                default:
                    break;
            }
        }
    }
}
