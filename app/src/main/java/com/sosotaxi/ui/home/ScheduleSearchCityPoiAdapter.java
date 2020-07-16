/**
 * @Author 岳兵
 * @CreateTime 2020/7/10
 * @UpdateTime 2020/7/16
 */
package com.sosotaxi.ui.home;


import java.util.List;



import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.SectionIndexer;
import android.widget.TextView;

import com.baidu.mapapi.search.core.PoiInfo;
import com.sosotaxi.R;

public class ScheduleSearchCityPoiAdapter extends BaseAdapter implements
        SectionIndexer {
    private List<PoiInfo> list = null;
    private Context mContext;

    public ScheduleSearchCityPoiAdapter(Context mContext,
                                     List<PoiInfo> list) {
        this.mContext = mContext;
        this.list = list;
    }

    /**
     * @param list
     */
    public void updateListView(List<PoiInfo> list) {
        this.list = list;
        notifyDataSetChanged();
    }

    public int getCount() {
        return this.list.size();
    }

    public Object getItem(int position) {
        return list.get(position);
    }

    public long getItemId(int position) {
        return position;
    }

    public View getView(final int position, View view, ViewGroup arg2) {
        ViewHolder viewHolder = null;
        if (view == null) {
            viewHolder = new ViewHolder();
            view = LayoutInflater.from(mContext).inflate(
                    R.layout.schedule_search_item, null);
            viewHolder.tvTitle = (TextView) view
                    .findViewById(R.id.city_list_name);
            viewHolder.tvLetter = (TextView) view
                    .findViewById(R.id.city_list_catalog);
            view.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) view.getTag();
        }
        PoiInfo poiInfo = list.get(position);
        viewHolder.tvTitle.setText(poiInfo.name);
        viewHolder.tvLetter.setText(poiInfo.address);


        return view;

    }

    final static class ViewHolder {
        TextView tvLetter;
        TextView tvTitle;
    }



    @Override
    public Object[] getSections() {
        return null;
    }

    @Override
    public int getPositionForSection(int sectionIndex) {
        return 0;
    }

    @Override
    public int getSectionForPosition(int position) {
        return 0;
    }

}

