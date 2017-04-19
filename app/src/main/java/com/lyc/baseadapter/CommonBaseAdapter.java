package com.lyc.baseadapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import java.util.List;

/**
 * @author Bby
 * @time 2017/04/17
 * 常用Adapter基类
 * @param <T>
 */
public abstract class CommonBaseAdapter<T> extends BaseAdapter {
    private Context mContext;
    private List<T> mDatas;
    private int layoutId;

    public CommonBaseAdapter(Context context, List<T> datas, int layoutId) {
        this.mContext = context;
        this.mDatas = datas;
        this.layoutId = layoutId;
    }

    @Override
    public int getCount() {
        return mDatas.size();
    }

    @Override
    public T getItem(int position) {
        return mDatas.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        CommonViewHolder holder = CommonViewHolder.getInstance(mContext,layoutId, position, convertView, parent);
        convert(holder,mDatas.get(position));
        return holder.getConvertView();
    }
    /**
     * 填充holder里面控件的数据
     * @param holder
     * @param bean 
     */
    public abstract void convert(CommonViewHolder holder,T bean);

    public void updateData(List<T> datas){
        this.mDatas = datas;
        notifyDataSetChanged();
    }

}