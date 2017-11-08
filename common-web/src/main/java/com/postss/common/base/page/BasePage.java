package com.postss.common.base.page;

import java.util.Date;

import com.postss.common.system.code.SystemCode;
import com.postss.common.system.exception.SystemCodeException;
import com.postss.common.util.ComparatorUtil;

/**
 * 分页基础类
 * @author jwSun
 * @date 2016年7月24日下午1:55:17
 */
public class BasePage {

    protected Integer total;// 条数

    //时间段查询
    protected Date startTime;// 开始时间
    protected Date endTime;// 结束时间

    //bootstarp表格
    protected String order;//asc或desc
    protected String sort;//排序字段
    protected Integer beginNumber;//开始查询条数 从0开始
    protected Integer pageNumber;//查询页数从0开始
    protected Integer pageSize;//查询数量
    protected Integer endNumber;//结束查询条数beginNumber+pageSize
    protected boolean allResult = true;//查询全部列表(结合baseJpaService findList使用,表较大时慎用)
    protected final int pageNumBeginSet = 1;//存入页数第一页为1
    protected final int pageNumBeginGet = 1;//取出页数第一页为1
    //private boolean calculate;//是否计算

    public BasePage() {
        super();
    }

    public BasePage(Integer pageNumber, Integer pageSize) {
        super();
        this.pageNumber = pageNumber;
        this.pageSize = pageSize;
        calculate();
    }

    public Integer getTotal() {
        return total;
    }

    public BasePage setTotal(Integer total) {
        this.total = total;
        return this;
    }

    public Date getStartTime() {
        return startTime;
    }

    public BasePage setStartTime(Date startTime) {
        this.startTime = startTime;
        return this;
    }

    public Date getEndTime() {
        return endTime;
    }

    public BasePage setEndTime(Date endTime) {
        this.endTime = endTime;
        return this;
    }

    public String getOrder() {
        return order;
    }

    public BasePage setOrder(String order) {
        this.order = order;
        return this;
    }

    public String getSortStr() {
        return sort;
    }

    public BasePage setSort(String sort) {
        this.sort = sort;
        return this;
    }

    public Integer getBeginNumber() {
        return beginNumber;
    }

    public BasePage setBeginNumber(Integer beginNumber) {
        this.beginNumber = beginNumber;
        calculate();
        return this;
    }

    public int getPageNumber() {
        if (pageNumber == null) {
            return -1;
        }
        return pageNumber + (pageNumBeginGet - pageNumBeginSet);
    }

    public BasePage setPageNumber(Integer pageNumber) {
        this.pageNumber = pageNumber;
        calculate();
        return this;
    }

    public int getPageSize() {
        if (pageSize == null)
            return -1;
        return pageSize;
    }

    public BasePage setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
        calculate();
        return this;
    }

    public Integer getEndNumber() {
        return endNumber;
    }

    public BasePage setEndNumber(Integer endNumber) {
        this.endNumber = endNumber;
        calculate();
        return this;
    }

    public boolean isAllResult() {
        return allResult;
    }

    public void setAllResult(boolean allResult) {
        this.allResult = allResult;
    }

    public void setAllResultPage(int total) {
        setPageNumber(this.pageNumBeginSet);
        setPageSize(total);
        calculate();
    }

    /**
     * 计算过程
     */
    private void calculate() {
        try {
            if (ComparatorUtil.isNull(this.pageNumber, this.pageSize, this.beginNumber, this.endNumber)) {
                this.allResult = true;
                return;
            } else {
                this.allResult = false;
            }
            if (ComparatorUtil.notNull(this.pageNumber, this.pageSize)) {
                if (this.pageSize == 0)
                    throw new SystemCodeException(SystemCode.PAGE_RESOLVER_ERROR, "pageSize 不能为0");
                int realPageNumber = this.pageNumber - this.pageNumBeginSet;
                this.beginNumber = realPageNumber * pageSize;
                this.endNumber = (realPageNumber + 1) * pageSize;
            } else if (ComparatorUtil.notNull(this.beginNumber, this.pageSize)) {
                if (this.pageSize == 0)
                    throw new SystemCodeException(SystemCode.PAGE_RESOLVER_ERROR, "pageSize 不能为0");
                this.pageNumber = this.beginNumber / this.pageSize + this.pageNumBeginSet;
                int realPageNumber = this.pageNumber - this.pageNumBeginSet;
                this.endNumber = (realPageNumber + 1) * pageSize;
            } else if (ComparatorUtil.notNull(this.beginNumber, this.pageNumber)) {
                int realPageNumber = this.pageNumber - this.pageNumBeginSet;
                this.pageSize = this.beginNumber / realPageNumber;
                this.endNumber = (realPageNumber + 1) * pageSize;
            } else if (ComparatorUtil.notNull(this.beginNumber, this.pageNumber)) {
                if (this.beginNumber == 0 || this.pageNumber - pageNumBeginSet == 0)
                    throw new SystemCodeException(SystemCode.PAGE_RESOLVER_ERROR,
                            "beginNumber 为0 或 页数为第一页,无法根据beginNumber与pageNumber计算出其他参数!");
                this.pageSize = this.beginNumber / (this.pageNumber - this.pageNumBeginSet);
                this.endNumber = this.pageSize + this.beginNumber;
            } else if (ComparatorUtil.notNull(this.pageNumber, this.endNumber)) {
                int realPageNumber = this.pageNumber - this.pageNumBeginSet;
                if (realPageNumber < 0)
                    throw new SystemCodeException(SystemCode.PAGE_RESOLVER_ERROR,
                            "pageNumber 不能小于" + this.pageNumBeginSet);
                this.pageSize = this.endNumber / (realPageNumber + 1);
                this.beginNumber = this.endNumber - this.pageSize;
            } else if (ComparatorUtil.notNull(this.pageSize, this.endNumber)) {
                if (this.pageSize == 0)
                    throw new SystemCodeException(SystemCode.PAGE_RESOLVER_ERROR, "pageSize 不能为0");
                int realPageNumber = this.endNumber / this.pageSize - 1;
                this.pageNumber = realPageNumber + this.pageNumBeginSet;
                this.beginNumber = this.endNumber - this.pageSize;
            } else if (ComparatorUtil.notNull(this.beginNumber, this.endNumber)) {
                this.pageSize = this.endNumber - this.beginNumber;
                if (this.pageSize <= 0)
                    throw new SystemCodeException(SystemCode.PAGE_RESOLVER_ERROR,
                            "endNumber 与  beginNumber 不能相同且 beginNumber 必须小于 endNumber ");
                this.pageNumber = this.beginNumber / this.pageSize + this.pageNumBeginSet;
            }
            if (ComparatorUtil.notNull(beginNumber, pageNumber, pageSize, endNumber)) {
                //this.calculate = true;
                return;
            }
            throw new SystemCodeException(SystemCode.PAGE_RESOLVER_ERROR, "计算参数不足");
        } catch (Exception e) {
            //逻辑稍后
        }

    }

}
