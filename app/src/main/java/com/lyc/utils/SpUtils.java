package com.lyc.utils;

import android.app.Activity;
import android.content.SharedPreferences;

import java.util.Set;

import static android.content.Context.MODE_PRIVATE;

/**
 * Created by Administrator on 2017/4/17.
 */

public class SpUtils {

    public SpUtils() {
    }

    public void putString(Activity activity, String key, String value){
        SharedPreferences sp = activity.getPreferences(MODE_PRIVATE);
        SharedPreferences.Editor edit = sp.edit();
        edit.putString(key, value);
        edit.commit();
    }

    public String getString(Activity activity,String key,String defValue){
        SharedPreferences sp = activity.getPreferences(MODE_PRIVATE);
        return sp.getString(key,defValue);
    }

    public void putBoolean(Activity activity,String key,boolean value){
        SharedPreferences sp = activity.getPreferences(MODE_PRIVATE);
        SharedPreferences.Editor edit = sp.edit();
        edit.putBoolean(key, value);
        edit.commit();
    }

    public boolean getBoolean(Activity activity,String key,boolean defValue){
        SharedPreferences sp = activity.getPreferences(MODE_PRIVATE);
        return sp.getBoolean(key,defValue);
    }

    public void putInt(Activity activity,String key,int value){
        SharedPreferences sp = activity.getPreferences(MODE_PRIVATE);
        SharedPreferences.Editor edit = sp.edit();
        edit.putInt(key, value);
        edit.commit();
    }

    public int getInt(Activity activity,String key,int defValue){
        SharedPreferences sp = activity.getPreferences(MODE_PRIVATE);
        return sp.getInt(key,defValue);
    }

    public void putLong(Activity activity,String key,long value){
        SharedPreferences sp = activity.getPreferences(MODE_PRIVATE);
        SharedPreferences.Editor edit = sp.edit();
        edit.putLong(key, value);
        edit.commit();
    }

    public long getLong(Activity activity,String key,long defValue){
        SharedPreferences sp = activity.getPreferences(MODE_PRIVATE);
        return sp.getLong(key,defValue);
    }

    public void putFloat(Activity activity,String key,float value){
        SharedPreferences sp = activity.getPreferences(MODE_PRIVATE);
        SharedPreferences.Editor edit = sp.edit();
        edit.putFloat(key, value);
        edit.commit();
    }

    public float getFloat(Activity activity,String key,float defValue){
        SharedPreferences sp = activity.getPreferences(MODE_PRIVATE);
        return sp.getFloat(key,defValue);
    }

    public void putDouble(Activity activity,String key,Set<String> value){
        SharedPreferences sp = activity.getPreferences(MODE_PRIVATE);
        SharedPreferences.Editor edit = sp.edit();
        edit.putStringSet(key, value);
        edit.commit();
    }

    public Set<String> getFloat(Activity activity,String key,Set<String> defValue){
        SharedPreferences sp = activity.getPreferences(MODE_PRIVATE);
        return sp.getStringSet(key,defValue);
    }

    /**
     * 检查是否包含key
     * @param activity
     * @param key
     * @return
     */
    public boolean hasKey(Activity activity,String key){
        SharedPreferences sp = activity.getPreferences(MODE_PRIVATE);
        return sp.contains(key);
    }

}
