package com.qw.sample.utils;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.provider.ContactsContract;
import android.widget.Toast;

import com.qw.sample.adapter.CheckPermissionAdapter;
import com.qw.soul.permission.bean.Permissions;
import com.qw.soul.permission.callbcak.RequestPermissionListener;
import com.qw.soul.permission.callbcak.RequestPermissionWithRationaleListener;
import com.qw.soul.permission.SoulPermission;
import com.qw.soul.permission.bean.Permission;
import com.qw.soul.permission.callbcak.RequestPermissionsListener;

/**
 * @author cd5160866
 */
public class UtilsWithPermission {

    /**
     * 拨打指定电话
     */
    public static void makeCall(final Context context, final String phoneNumber) {
        SoulPermission.requestPermission(Manifest.permission.CALL_PHONE,
                new RequestPermissionWithRationaleListener("如果你拒绝了权限，你将无法拨打电话，请点击授予权限") {
                    @SuppressLint("MissingPermission")
                    @Override
                    public void onGranted(Permission permission) {
                        Intent intent = new Intent(Intent.ACTION_CALL);
                        Uri data = Uri.parse("tel:" + phoneNumber);
                        intent.setData(data);
                        if (!(context instanceof Activity)) {
                            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        }
                        context.startActivity(intent);
                    }
                });
    }

    /**
     * 选择联系人
     */
    public static void chooseContact(final Activity activity, final int requestCode) {
        SoulPermission.requestPermission(Manifest.permission.READ_CONTACTS,
                new CheckPermissionAdapter() {
                    @Override
                    public void onGranted(Permission permission) {
                        activity.startActivityForResult(new Intent(Intent.ACTION_PICK, ContactsContract.Contacts.CONTENT_URI), requestCode);
                    }
                });
    }


    public static void readStorage(final Context context, final String phoneNumber) {
        SoulPermission.requestPermission(Manifest.permission.READ_EXTERNAL_STORAGE,
                        // 没有授权，阐述说明，自动引导用户授权
                        new RequestPermissionWithRationaleListener("请授权才能使用功能") {
                            @Override
                            public void onGranted(Permission permission) {
                                // 正常授权
                                Toast.makeText(context, "授权成功", Toast.LENGTH_SHORT).show();
                            }
                        });


        SoulPermission.requestPermission(Manifest.permission.READ_EXTERNAL_STORAGE,
                        new RequestPermissionListener() {
                            @Override
                            public void onGranted(Permission permission) {
                                Toast.makeText(context, "授权成功", Toast.LENGTH_SHORT).show();
                            }

                            @Override
                            public void onDenied(Permission permission) {
                                Toast.makeText(context, "授权失败", Toast.LENGTH_SHORT).show();
                            }
                        });

        SoulPermission.goPermissionSettings();

        SoulPermission.requestPermissions(
                Permissions.build(Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE),
                //if you want do noting or no need all the callbacks you may use SimplePermissionsAdapter instead
                new RequestPermissionsListener() {
                    @Override
                    public void onGranted(Permission[] allPermissions) {
                        Toast.makeText(context, allPermissions.length + "permissions is ok" +
                                " \n  you can do your operations", Toast.LENGTH_LONG).show();
                    }

                    @Override
                    public void onDenied(Permission[] deniedPermissions) {
                        Toast.makeText(context, deniedPermissions[0].toString() +
                                " \n is refused you can not do next things", Toast.LENGTH_LONG).show();
                    }
                });
    }



}
