/**
 * @Author 岳兵
 * @CreateTime 2020/7/10
 * @UpdateTime 2020/7/16
 */
package com.sosotaxi.ui.home;

import java.util.Comparator;

public class PinyinComparator implements Comparator<ScheduleCityGpsStruct>
{

    public int compare(ScheduleCityGpsStruct o1, ScheduleCityGpsStruct o2)
    {
        if (o1.getSortLetters().equals("@") || o2.getSortLetters().equals("#"))
        {
            return -1;
        } else if (o1.getSortLetters().equals("#") || o2.getSortLetters().equals("@"))
        {
            return 1;
        } else
        {
            return o1.getSortLetters().compareTo(o2.getSortLetters());
        }
    }

}
