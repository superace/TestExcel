package com.lyc.baseadapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.lyc.view.AutoTextView;

/**
 * @author Bby
 * @time 2017/04/17
 * 常用ViewHolder类
 */
public class CommonViewHolder {
    private int mPosition;
    /*
     * 用于存储holder里面的各个view，此集合比map效率高,但key必须为Integer
     */
    private SparseArray<View> mViews;
    /**
     * 复用的view
     */
    private View convertView;

    private CommonViewHolder(Context context, int position, int layoutId, ViewGroup parent) {
        this.mPosition = position;
        mViews = new SparseArray<View>();
        convertView = LayoutInflater.from(context).inflate(layoutId, parent,false);
        convertView.setTag(this);
    }

    public static CommonViewHolder getInstance(Context context, int layoutId, int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            return new CommonViewHolder(context, position, layoutId, parent);
        } else {
            CommonViewHolder holder = (CommonViewHolder) convertView.getTag();
            holder.mPosition = position;
            return holder;
        }

    }

    /**
     * 通过resourceId获取item里面的view
     * @param resourceId 控件的id
     * @return
     */
    @SuppressWarnings("unchecked")
    public <T extends View> T getView(int resourceId) {
        View view = mViews.get(resourceId);
        if (view == null) {
            view = convertView.findViewById(resourceId);
            mViews.put(resourceId, view);
        }
        return (T) view;
    }

    /**
     * 为textview类型填充内容
     * @param resourceId
     * @param text
     * @return CommonViewHolder
     */
    public CommonViewHolder setText(int resourceId,CharSequence text ) {
        ((TextView) getView(resourceId)).setText(text);
        return this;
    }

    public CommonViewHolder setText1(int resourceId,String text) {
        ((AutoTextView) getView(resourceId)).setText(text);
        return this;
    }
    public CommonViewHolder setText(int resourceId,int resid ) {
        ((TextView) getView(resourceId)).setText(resid);
        return this;
    }

    public CommonViewHolder setAdapter(int resourceId,ListAdapter adapter ) {
        ((ListView) getView(resourceId)).setAdapter(adapter);
//        fixListViewHeight((ListView) getView(resourceId));
        setListViewHeight((ListView) getView(resourceId));
        return this;
    }

    public void fixListViewHeight(ListView listView) {
        // 如果没有设置数据适配器，则ListView没有子项，返回。
        ListAdapter listAdapter = listView.getAdapter();
        int totalHeight = 0;
        if (listAdapter == null) {
            return;
        }
        for (int index = 0, len = listAdapter.getCount(); index < len; index++) {
            View listViewItem = listAdapter.getView(index , null, listView);
            // 计算子项View 的宽高
            listViewItem.measure(0, 0);
            // 计算所有子项的高度和
            totalHeight += listViewItem.getMeasuredHeight();
        }
        ViewGroup.LayoutParams params = listView.getLayoutParams();
        params.height = totalHeight+ (listView.getDividerHeight() * (listAdapter.getCount() - 1));
        listView.setLayoutParams(params);
    }

    /*
    * scrollview与listview合用会出现listview只显示一行多点。此方法是为了定死listview的高度就不会出现以上状况
    * 算出listview的高度
    */
    public static void setListViewHeight(ListView listView) {
        ListAdapter listAdapter = listView.getAdapter();
        if (listAdapter == null) {
            return;
        }
        int totalHeight = 0;
        for (int i = 0; i < listAdapter.getCount(); i++) {
            View listItem = listAdapter.getView(i, null, listView);
            listItem.measure(0, 0);
            totalHeight = totalHeight + listItem.getMeasuredHeight() + listView.getPaddingTop() + listView.getPaddingBottom();
        }

        ViewGroup.LayoutParams params = listView.getLayoutParams();
        params.height = totalHeight + (listView.getDividerHeight() * (listAdapter.getCount() - 1)) + listView.getPaddingTop() + listView.getPaddingBottom();
        listView.setLayoutParams(params);
    }

    /**
     * 为ImageView设置Bitmap
     * @param resourceId
     * @param bm
     * @return
     */
    public CommonViewHolder setBitmap(int resourceId,Bitmap bm) {
        ((ImageView)getView(resourceId)).setImageBitmap(bm);
        return  this;
    }
    public CommonViewHolder setImageDrawable(int resourceId,Drawable drawable) {
        ((ImageView)getView(resourceId)).setImageDrawable(drawable);
        return  this;
    }
    public CommonViewHolder setImageResource(int resourceId,int resId) {
        ((ImageView)getView(resourceId)).setImageResource(resId);
        return  this;
    }


    public View getConvertView() {
        return convertView;
    }
    /**
     * 获取当前item的位置
     * @return
     */
    public int getPosition() {
        return mPosition;
    }
}