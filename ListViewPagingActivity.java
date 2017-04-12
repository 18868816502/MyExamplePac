package com.jonny.myexamplepac;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.sql.SQLOutput;
import java.util.Vector;

/**
 * ListView的分页
 */

public class ListViewPagingActivity extends AppCompatActivity implements AbsListView.OnScrollListener{
    private ListView listView;
    private int index = 1;//list编号
    private Vector<News> news = new Vector<News>();//装list数据的容器
    private Myadapter myAdapter;//自定义适配器
    private int visibleLastIndex;//用来可显示的最后一条索引
    private static final int DATA_UPDATE = 0x1;//数据更新完后的标记

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_listview);
        listView = (ListView) findViewById(R.id.listView);
        listView.setOnScrollListener(this);
        View footerView=getLayoutInflater().inflate(R.layout.loading,null);
        listView.addFooterView(footerView);
        initData();
        myAdapter = new Myadapter();
        listView.setAdapter(myAdapter);
    }

    /**
     * 初始化数据
     */
    private void initData() {
        for (int i = 0; i < 10; i++) {
            News n = new News();
            n.title = "title--" + index;
            n.content = "content--" + index;
            index++;
            news.add(n);
        }
    }

    @Override
    public void onScrollStateChanged(AbsListView view, int scrollState) {
         if (myAdapter.getCount()==visibleLastIndex && scrollState == AbsListView.OnScrollListener.SCROLL_STATE_IDLE){
             new LoadDataThread().start();
         }
    }

    @Override
    public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
       visibleLastIndex = firstVisibleItem+visibleItemCount-1;
    }

    //线程之间的通讯机制
    private Handler handler=new Handler(){
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what){
                case DATA_UPDATE:
                    myAdapter.notifyDataSetChanged();
                    break;
            }
        }
    };

    /**
     * 新开一个计时线程
     */
    class LoadDataThread extends Thread{
        @Override
        public void run() {
            initData();
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            handler.sendEmptyMessage(DATA_UPDATE);
        }
    }

    class Myadapter extends BaseAdapter {

        @Override
        public int getCount() {
            return news.size();
        }

        @Override
        public Object getItem(int position) {
            return news.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            ViewHolder vh;
            if (convertView == null) {
                convertView = getLayoutInflater().inflate(R.layout.list_item_1, null);
                vh=new ViewHolder();
                vh.tv_title= (TextView) convertView.findViewById(R.id.textView_title);
                vh.tv_content= (TextView) convertView.findViewById(R.id.textView_content);
                convertView.setTag(vh);
            }else {
                vh= (ViewHolder) convertView.getTag();
            }
            News n =news.get(position);
            vh.tv_title.setText(n.title);
            vh.tv_content.setText(n.content);
            return convertView;
        }

        class ViewHolder {
            TextView tv_title;
            TextView tv_content;
        }
    }
}
