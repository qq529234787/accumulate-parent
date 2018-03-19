package com.wme.base.utils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ming on 2016/3/27.
 */
public class PageQuery {

    /**
     * 第几页
     */
    private int pageNo = 1;
    /**
     * 每页大小
     */
    private int pageSize = 20;
    /**
     * 最大数据量
     */
    private int totalSize = 0;

    /**
     * 最多显示多少页码
     */
    private int maxShowPageNum=10;

    /**
     * 数据分页索引
     * @return
     */
    public int getStartRow() {
        int pageIndex=0;
        if(pageNo >0){
            pageIndex=(pageNo -1)*pageSize;
        }
        return pageIndex;
    }

    /**
     * 最大页数
     * @return
     */
    public int getMaxPageNum() {
        int maxPageNum=1;
        if(totalSize>0&&pageSize>0){
            int datamore =totalSize%pageSize;
            maxPageNum = totalSize/pageSize;
            if(datamore>0){
                maxPageNum+=1;
            }
        }
        return maxPageNum;
    }

    /**
     * 显示的页码
     * @return
     */
    public List<Integer> getShowPageNumList() {
        List<Integer> showPageNumList =  new ArrayList<Integer>();
        if(totalSize>0){
            int  maxPageNum= this.getMaxPageNum();
            int startPageNum=1;
            int endPageNum=maxPageNum;
            int preSize = maxShowPageNum / 2;
            int sufSize = maxShowPageNum - preSize-1;
            int preChange=0;
            int sufChange=0;
            if(pageNo +sufSize>maxPageNum){
                preChange= pageNo +sufSize-maxPageNum;
            }
            if(pageNo -preSize<=0){
                sufChange = preSize - pageNo +1;
            }


            if(pageNo +sufSize<maxPageNum){
                endPageNum= pageNo +sufSize;
                if(endPageNum+sufChange<=maxPageNum){
                    endPageNum=endPageNum+sufChange;
                }
            }
            if(pageNo >preSize){
                startPageNum = pageNo - preSize;
                if(startPageNum-preChange>0){
                    startPageNum=startPageNum-preChange;
                }
            }

            for(int i=startPageNum;i<=endPageNum;i++){
                showPageNumList.add(i);
            }
        }
        return showPageNumList;
    }


    public void setMaxShowPageNum(int maxShowPageNum) {
        this.maxShowPageNum = maxShowPageNum;
    }

    public int getMaxShowPageNum() {
        return maxShowPageNum;
    }

    public int getPageNo() {
        return pageNo;
    }

    public void setPageNo(int pageNo) {
        this.pageNo = pageNo;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public int getTotalSize() {
        return totalSize;
    }

    public void setTotalSize(int totalSize) {
        this.totalSize = totalSize;
    }


    /*public static void main(String[] args){
        PageQuery q = new PageQuery();
        q.setPageNo(7);
        q.setTotalSize(1000);
        System.out.println(q.getMaxPageNum());
        System.out.println(q.getShowPageNumList());
    }
    */

}
