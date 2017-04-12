package com.jonny.myexamplepac;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

/**
 * 可扩展的ListView
 */
public class ExpandableListViewActivity extends AppCompatActivity {
    private android.widget.ExpandableListView listView;
    private String[] initial = {"A","B","C","D"};
    private String[][] fullTitle = {{"abnormal","absurd","action","acute","athlete"},{"bagge","batter","blink","boost"},{"cater","chant","class"},{"detach","disable","dog"}};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_expandable_list_view);
        listView= (android.widget.ExpandableListView) findViewById(R.id.listView);
        listView.setAdapter(new MyExpandableAdapter());
        listView.setOnChildClickListener(new android.widget.ExpandableListView.OnChildClickListener() {
            @Override
            public boolean onChildClick(android.widget.ExpandableListView parent, View v, int groupPosition, int childPosition, long id) {
                Toast.makeText(ExpandableListViewActivity.this,fullTitle[groupPosition][childPosition],Toast.LENGTH_SHORT).show();
                return true;
            }
        });
    }

    /**
     * 自定义适配器
     */
    class MyExpandableAdapter extends BaseExpandableListAdapter{

       @Override
        public int getGroupCount() {
            return initial.length;
        }

        @Override
        public int getChildrenCount(int groupPosition) {
            return fullTitle[groupPosition].length;
        }

        @Override
        public Object getGroup(int groupPosition) {
            return initial[groupPosition];
        }

        @Override
        public Object getChild(int groupPosition, int childPosition) {
            return fullTitle[groupPosition][childPosition];
        }

        @Override
        public long getGroupId(int groupPosition) {
            return groupPosition;
        }

        @Override
        public long getChildId(int groupPosition, int childPosition) {
            return childPosition;
        }

        @Override
        public boolean hasStableIds() {
            return false;
        }

        @Override
        public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
            if (convertView==null){
                convertView=getLayoutInflater().inflate(R.layout.group_layout,null);
            }
            ImageView icon= (ImageView) convertView.findViewById(R.id.icon);
            TextView title = (TextView) convertView.findViewById(R.id.titlt);
            title.setText(initial[groupPosition]);
            return convertView;
        }

        @Override
        public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
            if (convertView==null){
                convertView=getLayoutInflater().inflate(R.layout.child_layout,null);
            }
            ImageView icon= (ImageView) convertView.findViewById(R.id.icon);
            TextView title = (TextView) convertView.findViewById(R.id.titlt);
            title.setText(fullTitle[groupPosition][childPosition]);
            return convertView;
        }

        @Override
        public boolean isChildSelectable(int groupPosition, int childPosition) {
            return true;
        }
    }
}
