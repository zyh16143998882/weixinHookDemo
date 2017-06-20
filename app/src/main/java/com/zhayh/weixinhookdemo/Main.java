package com.zhayh.weixinhookdemo;

import android.util.Log;

import de.robv.android.xposed.IXposedHookLoadPackage;
import de.robv.android.xposed.XC_MethodHook;
import de.robv.android.xposed.XposedHelpers;
import de.robv.android.xposed.callbacks.XC_LoadPackage;

/**
 * Created by zhayh on 2017/6/20.
 */
public class Main implements IXposedHookLoadPackage {
    @Override
    public void handleLoadPackage(XC_LoadPackage.LoadPackageParam loadPackageParam) throws Throwable {

        if(loadPackageParam.packageName.equals("com.tencent.mm")){
            Log.d("XposedTag","进入微信");

            Class mSQLiteDatabase = XposedHelpers.findClass(
                    "com.tencent.wcdb.database.SQLiteDatabase",loadPackageParam.classLoader);
            Log.d("XposedTag","Class mSQLiteDatabase == " + mSQLiteDatabase);
            Class mSQLiteDatabaseConfiguration = XposedHelpers.findClass(
                    "com.tencent.wcdb.database.SQLiteDatabaseConfiguration",
                    loadPackageParam.classLoader
            );
            Log.d("XposedTag","Class mSQLiteDatabaseConfiguration == " + mSQLiteDatabaseConfiguration);
            Class mSQLiteCipherSpec = XposedHelpers.findClass(
                    "com.tencent.wcdb.database.SQLiteCipherSpec",
                    loadPackageParam.classLoader
            );
            Log.d("XposedTag","Class mSQLiteCipherSpec == " + mSQLiteCipherSpec);

            Log.d("XposedTag","byte[].class == " + byte[].class);

            XposedHelpers.findAndHookMethod("com.tencent.wcdb.database.SQLiteConnectionPool",
                    loadPackageParam.classLoader,
                    "open",
                    mSQLiteDatabase,
                    mSQLiteDatabaseConfiguration,
                    byte[].class,
                    mSQLiteCipherSpec,
                    int.class,
                    new XC_MethodHook() {
                        @Override
                        protected void beforeHookedMethod(MethodHookParam param) throws Throwable {
                            super.beforeHookedMethod(param);
                            byte[] array = (byte[]) param.args[2];
                            for(int i = 0; i < array.length;i++){
                                Log.d("XposedTag","array[" + i + "] == " + array[i]);
                            }
                            Log.d("XposedTag","array == " + (new String(array)));
                        }

                        @Override
                        protected void afterHookedMethod(MethodHookParam param) throws Throwable {
                            super.afterHookedMethod(param);

                        }
                    });
        }

    }
}

