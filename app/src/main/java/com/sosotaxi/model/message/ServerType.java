/**
 * @Author 范承祥
 * @CreateTime 2020/7/23
 * @UpdateTime 2020/7/23
 */
package com.sosotaxi.model.message;

/**
 * 服务类型
 */
public enum ServerType {
    /**
     * 经济型 0
     */
    ECONOMIC,

    /**
     * 舒适型 1
     */
    COMFORT;

    @Override
    public String toString(){
        return String.valueOf(this.ordinal());
    }
}
