package com.lwm.common.vo;

import lombok.Data;

import java.io.Serializable;

/**
 * @author lwm1435@163.com
 * @date 2022-01-02 21:06
 * @description
 */
@Data
public class Page implements Serializable {
    /**
     * 默认的分页大小
     */
    public static final Integer PAGE_SIZE = 9;
    /**
     * 页码
     */
    private Integer pageNo;
    /**
     * 每页记录数
     */
    private Integer pageSize;
    /**
     * 总记录数
     */
    private Integer totalRecords;
    /**
     * 总页数
     */
    private Integer totalPages;


    public Page(){

    }

    public Page(Integer pageNo, Integer pageSize, Integer totalRecords) {
        this.pageNo = pageNo;
        this.pageSize = pageSize;
        this.totalRecords = totalRecords;
        if (pageNo == null || pageNo < 1){
            this.pageNo = 1;
        }
        if (pageSize == null || pageSize < 1){
            this.pageSize = PAGE_SIZE;
        }
        //设置总页数
        if (totalRecords % pageSize == 0){
            this.totalPages = totalRecords / this.pageSize;
        }else {
            this.totalPages = totalRecords / this.pageSize + 1;
        }

    }
}
