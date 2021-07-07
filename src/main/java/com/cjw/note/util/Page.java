package com.cjw.note.util;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class Page<T> {
    private Integer pageNum;//当前页
    private Integer pageSize;//每页显示的数量
    private long totalCount;//总数


    private Integer totalPages;//总页数(总记录数 / 每页显示的数量)
    private  Integer prePage;//上一页
    private  Integer nextPage;//下一页

    private  Integer startNavPage;//导航开始页
    private Integer endNavPage;//导航结束页

    private List<T> dataList;//当前页的数据集合

    /**
     * 带参构造
     * @param pageNum
     * @param pageSize
     * @param totalCount
     */
    public Page(Integer pageNum, Integer pageSize, long totalCount) {
        this.pageNum = pageNum;
        this.pageSize = pageSize;
        this.totalCount = totalCount;
        //总页数
        this.totalPages=(int)Math.ceil(totalCount/(pageSize * 1.0));

        ////上一页
        this.prePage=pageNum -1 < 1 ? 1 :pageNum-1;

        //下一页
        this.nextPage=pageNum + 1>totalPages ? totalPages :pageNum + 1;


        this.startNavPage =pageNum -5;//导航开始页

        this.endNavPage = pageNum +4;//导航结束页
        //导航页结束时为导航开始计数的+ 9;如果导航页开始数+ 9大于总页数，则导航页结束为总页数
        if (this.startNavPage<1){
            //如果当前页面- 5小于1，导航开始到第1页
            this.startNavPage=1;
            //导航页结束时为导航开始计数的+ 9;如果导航页开始数+ 9大于总页数，则导航页结束为总页数
            this.endNavPage =this.startNavPage + 9 >totalPages ? totalPages : this.startNavPage + 9;
        }
        if (this.endNavPage>totalPages){
            this.endNavPage =totalPages;

            this.startNavPage=this.endNavPage -9 <1 ? 1: this.endNavPage -9;
        }
    }
}
