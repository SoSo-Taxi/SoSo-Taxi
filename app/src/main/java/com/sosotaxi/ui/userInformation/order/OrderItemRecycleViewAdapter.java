/**
 * @Author 屠天宇
 * @CreateTime 2020/7/8
 * @UpdateTime 2020/7/22
 */


package com.sosotaxi.ui.userInformation.order;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.sosotaxi.R;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class OrderItemRecycleViewAdapter extends RecyclerView.Adapter<OrderItemRecycleViewAdapter.LinearViewHolder> {

    private Context mContext;
    private List<String> carTypes = new ArrayList<String>(Arrays.asList("经济型","舒适型","舒适型","舒适型"));
    private List<String> orderTime = new ArrayList<String>(Arrays.asList("2018年08月22日 05:30","2019年02月14日 15:11","2019年02月22日 17:31","2019年03月14日 20:11"));
    private List<String> startPoints = new ArrayList<String>(Arrays.asList("staringPoint1","staringPoint2","staringPoint3","staringPoint4"));
    private List<String> destination = new ArrayList<String>(Arrays.asList("destination1","destination2","destination3","destination4"));

    public OrderItemRecycleViewAdapter(Context mContext) {
        this.mContext = mContext;
    }

    @NonNull
    @Override
    public LinearViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new LinearViewHolder(LayoutInflater.from(mContext).inflate(R.layout.adapter_orderitem_recycleview,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull LinearViewHolder holder, int position) {

        holder.mOrderItemConstraintLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(mContext,"跳转到已完成导航的界面",Toast.LENGTH_SHORT).show();
            }
        });

        holder.mCarTypeTextView.setText(carTypes.get(position));
        holder.mOrderTimeTextView.setText(orderTime.get(position));
        holder.mStartingPointTextView.setText(startPoints.get(position));
        holder.mDestinationTextView.setText(destination.get(position));

    }

    @Override
    public int getItemCount() {
        return carTypes.size();
    }

    class LinearViewHolder extends RecyclerView.ViewHolder{

        private ConstraintLayout mOrderItemConstraintLayout;

        private TextView mCarTypeTextView;
        private TextView mOrderTimeTextView;
        private TextView mStartingPointTextView;
        private TextView mDestinationTextView;
        private TextView mIsFinishTextView;
        public LinearViewHolder(@NonNull View itemView) {
            super(itemView);
            mOrderItemConstraintLayout  = itemView.findViewById(R.id.orderItem_constraintLayout);

            mCarTypeTextView = itemView.findViewById(R.id.car_type_textView);
            mOrderTimeTextView = itemView.findViewById(R.id.order_time_textView);
            mStartingPointTextView = itemView.findViewById(R.id.order_startingPoint_textView);
            mDestinationTextView = itemView.findViewById(R.id.order_destination_textView);
            mIsFinishTextView = itemView.findViewById(R.id.order_is_finish_textView);
        }
    }
}
