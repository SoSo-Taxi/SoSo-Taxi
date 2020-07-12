package com.sosotaxi.ui.userInformation.order;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.sosotaxi.R;

public class OrderItemListAdapter extends BaseAdapter {

    private Context mContext;
    private LayoutInflater mLayoutInflater;

    public OrderItemListAdapter(Context context){
        this.mContext = context;
        this.mLayoutInflater = LayoutInflater.from(context);
    }
    @Override
    public int getCount() {
        return 10;
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    static class ViewHolder{
        public TextView textView1;
        public TextView textView2;
        public TextView textView3;
        public TextView textView4;
    }
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder = null;
        if(convertView == null){
            convertView = mLayoutInflater.inflate(R.layout.listview_adapter_orderitem,null);
            viewHolder = new ViewHolder();
            viewHolder.textView1 = convertView.findViewById(R.id.carType_textView);
            viewHolder.textView2 = convertView.findViewById(R.id.time_textView);
            viewHolder.textView3 = convertView.findViewById(R.id.getCarTime_textView);
            viewHolder.textView4 = convertView.findViewById(R.id.leaveCarTime_textView);
            convertView.setTag(viewHolder);
        }else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        return convertView;
    }
}
