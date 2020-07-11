/**
 * @Author 范承祥
 * @CreateTime 2020/7/9
 * @UpdateTime 2020/7/11
 */
package com.sosotaxi.adapter;

import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import com.sosotaxi.R;
import com.sosotaxi.common.OnRecyclerViewClickListener;

import java.util.ArrayList;
import java.util.List;

/**
 * 区号列表适配器
 */
public class AreaCodeRecyclerViewAdapter extends RecyclerView.Adapter<AreaCodeRecyclerViewAdapter.ViewHolder> implements Filterable {

    private final List<String> mValues;
    private List<String> mFilterValues;
    private OnRecyclerViewClickListener mListener;

    public AreaCodeRecyclerViewAdapter(List<String> items) {
        mValues = items;
        mFilterValues=items;
    }

    public void setListener(OnRecyclerViewClickListener listener) {
        this.mListener = listener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_area_code, parent, false);
        if(mListener!=null){
            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    mListener.onItemClickListener(view);
                }
            });
        }
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        holder.mContentView.setText(mFilterValues.get(position));
    }

    @Override
    public int getItemCount() {
        return mFilterValues.size();
    }

    @Override
    public Filter getFilter(){
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence constraint) {
                String filterString=constraint.toString();
                if(filterString.isEmpty()){
                    // 没有过滤的内容则使用原数据
                    mFilterValues=mValues;
                }else{
                    List<String> filterList=new ArrayList<>();

                    //寻找符合条件的区号
                    for(String string : mValues){
                        if(string.contains(filterString)){
                            filterList.add(string);
                        }
                    }

                    mFilterValues=filterList;
                }

                FilterResults filterResults=new FilterResults();
                filterResults.values=mFilterValues;
                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence constraint, FilterResults results) {
                mFilterValues=(List<String>) results.values;
                notifyDataSetChanged();
            }
        };
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public final View mView;
        public final TextView mContentView;

        public ViewHolder(View view) {
            super(view);
            mView = view;
            mContentView = (TextView) view.findViewById(R.id.textViewAreaCodeItem);
        }

        @Override
        public String toString() {
            return mContentView.getText().toString();
        }
    }
}