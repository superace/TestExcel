package com.lyc.testexcel;

import android.app.Activity;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.lyc.baseadapter.CommonBaseAdapter;
import com.lyc.baseadapter.CommonViewHolder;
import com.lyc.bean.Test;
import com.lyc.utils.DBManager;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private int i;
    private SQLiteDatabase sqliteDB;
    private DBManager dbHelper;
    private ListView lv;
    private Activity mContext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mContext = MainActivity.this;
        lv = (ListView) findViewById(R.id.lv);

        List<List<String>> datas = new ArrayList<>() ;
        //首次执行导入.db文件
        dbHelper = new DBManager(this);
        dbHelper.openDatabase();
        SQLiteDatabase database = dbHelper.getDatabase();
        try{
            Cursor result =database.rawQuery("SELECT * from dept",null);
            result.moveToFirst();
            int cnt=0;
            while(!result.isAfterLast()&&cnt++<result.getCount()){
                String[] columnNames = result.getColumnNames();
                List<String> data = new ArrayList();
                for (int i=0;i<columnNames.length;i++){
                    data.add(result.getString(result.getColumnIndex(columnNames[i])));
                }
                datas.add(data);
                result.moveToNext();
            }
            result.close();
        }catch(Exception e){
            e.printStackTrace();
        }
        dbHelper.closeDatabase();


        System.out.println("----------------start-0--------------------");
        for (int j=0;j<datas.size();j++){
            System.out.println("Test-base:"+datas.get(j).toString());
        }
        System.out.println("----------------end-0--------------------");

        List<Test> newDatas = new ArrayList<>();
        System.out.println("----------------start--------------------");
        int flag = 0;
        for (int j=0;j<datas.size();j++){
            System.out.println(datas.get(j).toString());
            if(j == 0){
                Test test0 = new Test();
                newDatas.add(test0);

                newDatas.get(flag).setTitle(datas.get(j).get(0));
                List<List<String>> datas0 = new ArrayList<>();
                newDatas.get(flag).setDatas(datas0);
                List<String> datas11 = new ArrayList<>();
                datas11.add("部门名称");
                datas11.add("具体数据");
                newDatas.get(flag).getDatas().add(datas11);
                List<String> datas1 = new ArrayList<>();
                datas1.add(datas.get(j).get(1));
                datas1.add(datas.get(j).get(2));
                newDatas.get(flag).getDatas().add(datas1);
            }else{
                if(TextUtils.equals(datas.get(j).get(0),datas.get(j-1).get(0))){
                    List<String> datas1 = new ArrayList<>();
                    datas1.add(datas.get(j).get(1));
                    datas1.add(datas.get(j).get(2));
                    newDatas.get(flag).getDatas().add(datas1);
                }else{
                    Test test0 = new Test();
                    newDatas.add(test0);
                    flag++;


                    newDatas.get(flag).setTitle(datas.get(j).get(0));
                    List<List<String>> datas0 = new ArrayList<>();
                    newDatas.get(flag).setDatas(datas0);
                    List<String> datas11 = new ArrayList<>();
                    datas11.add("部门名称");
                    datas11.add("具体数据");
                    newDatas.get(flag).getDatas().add(datas11);
                    List<String> datas1 = new ArrayList<>();
                    datas1.add(datas.get(j).get(1));
                    datas1.add(datas.get(j).get(2));
                    newDatas.get(flag).getDatas().add(datas1);

                }
            }
        }
        System.out.println("----------------end--------------------");

        System.out.println("----------------start-0--------------------");
        for (int j=0;j<newDatas.size();j++){
            System.out.println("Test-base:"+newDatas.get(j).toString());
        }
        System.out.println("----------------end-0--------------------");

        final CommonBaseAdapter<Test> commonBaseAdapter = new CommonBaseAdapter<Test>(this,newDatas,R.layout.item_one) {
            @Override
            public void convert(CommonViewHolder holder, Test bean) {
                holder.setText(R.id.title1,bean.getTitle());
                CommonBaseAdapter<List<String>> commonBaseAdapter1 = new CommonBaseAdapter<List<String>>(MainActivity.this,bean.getDatas(),R.layout.item_two) {
                    @Override
                    public void convert(CommonViewHolder holder, List<String> bean) {
                        int position = holder.getPosition();
                        System.out.println("Test-position:"+position);
                        if (position == 0){
                            ((TextView)holder.getView(R.id.TestTitle1)).setTextColor(getResources().getColor(R.color.colorPrimary));
                            ((TextView)holder.getView(R.id.TestTitle1)).setTextSize(16);
                            ((TextView)holder.getView(R.id.TestTitle2)).setTextColor(getResources().getColor(R.color.colorPrimary));
                            ((TextView)holder.getView(R.id.TestTitle2)).setTextSize(16);
                        }
                        holder.setText(R.id.TestTitle1,bean.get(0));
                        holder.setText(R.id.TestTitle2,bean.get(1));
                    }
                };
                holder.setAdapter(R.id.lv1,commonBaseAdapter1);
            }
        };

//        CommonBaseAdapter<Test> commonBaseAdapter = new CommonBaseAdapter<Test>(this,newDatas,R.layout.item_two) {
//            @Override
//            public void convert(CommonViewHolder holder, Test bean) {
//                holder.setText(R.id.TestTitle1,bean.getTitle()+"-------------------------------------------");
//            }
//        };

        lv.setAdapter(commonBaseAdapter);
//        fixListViewHeight(lv);

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

}
