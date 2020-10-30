package com.liuhq.o2o.utils;

public class PageCalculator {
	
	 /**
            * 比如 页码 1 大小 5 则 第1页的第一个条数据为 0
            *   页码 2 大小 5 则 第2页的第一条数据类 5
     * @param pageIndex
     * @param pageSize
     * @return
     */
    public static int calculateRowIndex(int pageIndex, int pageSize) {
        return (pageIndex > 0) ? (pageIndex - 1) * pageSize : 0;
    }

}
