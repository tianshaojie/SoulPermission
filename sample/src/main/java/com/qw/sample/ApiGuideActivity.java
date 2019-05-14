package com.qw.sample;


import android.Manifest;
import android.app.Activity;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Toast;
import com.qw.soul.permission.SoulPermission;
import com.qw.soul.permission.bean.Permission;
import com.qw.soul.permission.bean.Permissions;
import com.qw.soul.permission.bean.Special;
import com.qw.soul.permission.callbcak.RequestPermissionListener;
import com.qw.soul.permission.callbcak.RequestPermissionsListener;

public class ApiGuideActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_api_guide);
    }

    public void checkSinglePermission(View view) {
        //you can also use checkPermissions() for a series of permissions
        Permission checkResult = SoulPermission.checkPermission(Manifest.permission.ACCESS_FINE_LOCATION);
        Toast.makeText(this, checkResult.toString(), Toast.LENGTH_LONG).show();
    }

    public void requestSinglePermission(View view) {
        SoulPermission.requestPermission(Manifest.permission.ACCESS_FINE_LOCATION,
                //if you want do noting or no need all the callbacks you may use SimplePermissionAdapter instead
                new RequestPermissionListener() {
                    @Override
                    public void onGranted(Permission permission) {
                        Toast.makeText(ApiGuideActivity.this, permission.toString() +
                                "\n is ok , you can do your operations", Toast.LENGTH_LONG).show();
                    }

                    @Override
                    public void onDenied(Permission permission) {
                        Toast.makeText(ApiGuideActivity.this, permission.toString() +
                                " \n is refused you can not do next things", Toast.LENGTH_LONG).show();
                    }
                });
    }

    public void requestPermissions(View view) {
        SoulPermission.requestPermissions(
                Permissions.build(Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE),
                //if you want do noting or no need all the callbacks you may use SimplePermissionsAdapter instead
                new RequestPermissionsListener() {
                    @Override
                    public void onGranted(Permission[] allPermissions) {
                        Toast.makeText(ApiGuideActivity.this, allPermissions.length + "permissions is ok" +
                                " \n  you can do your operations", Toast.LENGTH_LONG).show();
                    }

                    @Override
                    public void onDenied(Permission[] deniedPermissions) {
                        Toast.makeText(ApiGuideActivity.this, deniedPermissions[0].toString() +
                                " \n is refused you can not do next things", Toast.LENGTH_LONG).show();
                    }
                });
    }

    public void requestSinglePermissionWithRationale(View view) {
        SoulPermission.requestPermission(Manifest.permission.READ_CONTACTS,
                new RequestPermissionListener() {
                    @Override
                    public void onGranted(Permission permission) {
                        Toast.makeText(ApiGuideActivity.this, permission.toString() +
                                "\n is ok , you can do your operations", Toast.LENGTH_LONG).show();
                    }

                    @Override
                    public void onDenied(Permission permission) {
                        if (permission.shouldRationale()) {
                            Toast.makeText(ApiGuideActivity.this, permission.toString() +
                                    " \n you should show a explain for user then retry ", Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(ApiGuideActivity.this, permission.toString() +
                                    " \n is refused you can not do next things", Toast.LENGTH_LONG).show();
                        }
                    }
                });
    }

    public void goSystemSettings(View view) {
        SoulPermission.goPermissionSettings();
    }

    public void checkNotification(View view) {
        boolean checkResult = SoulPermission.checkSpecialPermission(Special.NOTIFICATION);
        if (checkResult) {
            Toast.makeText(view.getContext(), "Notification is enable", Toast.LENGTH_LONG).show();
        } else {
            new AlertDialog.Builder(view.getContext())
                    .setMessage("Notification is disable \n you may invoke goPermissionSettings and enable notification")
                    .setPositiveButton("Set", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            SoulPermission.goPermissionSettings();
                            dialog.dismiss();
                        }
                    })
                    .create()
                    .show();
        }
    }

    public void getTopActivity(View view) {
        Activity activity = SoulPermission.getTopActivity();
        if (null != activity) {
            Toast.makeText(activity, activity.getClass().getSimpleName() + " " + activity.hashCode(), Toast.LENGTH_LONG).show();
        }
    }

}
