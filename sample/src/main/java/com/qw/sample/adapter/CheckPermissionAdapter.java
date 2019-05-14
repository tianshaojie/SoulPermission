package com.qw.sample.adapter;

import android.app.Activity;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import com.qw.soul.permission.SoulPermission;
import com.qw.soul.permission.bean.Permission;
import com.qw.soul.permission.callbcak.RequestPermissionListener;

/**
 * @author cd5160866
 */
public abstract class CheckPermissionAdapter implements RequestPermissionListener {

    @Override
    public void onDenied(Permission permission) {
        //SoulPermission提供栈顶Activity
        Activity activity = SoulPermission.getTopActivity();
        if (null == activity) {
            return;
        }
        String permissionDesc = permission.getPermissionNameDesc();
        new AlertDialog.Builder(activity)
                .setTitle("提示")
                .setMessage(permissionDesc + "异常，请前往设置－>权限管理，打开" + permissionDesc + "。")
                .setPositiveButton("去设置", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        //去设置页
                        SoulPermission.goPermissionSettings();
                    }
                }).create().show();
    }
}
