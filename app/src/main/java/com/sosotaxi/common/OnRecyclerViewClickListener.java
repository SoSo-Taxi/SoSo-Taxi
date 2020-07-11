/**
 * @Author 范承祥
 * @CreateTime 2020/7/9
 * @UpdateTime 2020/7/11
 */
package com.sosotaxi.common;

import android.view.View;

/**
 * 列表点击事件监听器接口
 */
public interface OnRecyclerViewClickListener {

    /**
     * 列表项点击事件
     * @param view 视图
     */
    void onItemClickListener(View view);
}
