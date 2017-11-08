package com.postss.common.base.page;

import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;

/**
 * hibernate分页
 * @author jwSun
 * @date 2017年3月23日 下午2:56:42
 */
public class PageResolver implements Pageable {

    private BasePage basePage;

    public BasePage getBasePage() {
        return basePage;
    }

    public void setBasePage(BasePage basePage) {
        this.basePage = basePage;
    }

    public PageResolver(int pageNumber, int pageSize, Sort sort) {
        this.basePage = new BasePage();
        this.basePage.pageNumber = pageNumber;
        this.basePage.pageSize = pageSize;
        String[] sortAndOrder = sort.toString().split(":");
        this.basePage.sort = sortAndOrder[0].trim();
        this.basePage.order = sortAndOrder[1].trim();
    }

    public PageResolver(BasePage basePage) {
        super();
        this.basePage = basePage;
    }

    public PageResolver() {
        this.basePage = new BasePage();
    }

    @Override
    public int getPageNumber() {
        return this.basePage.getPageNumber() - this.basePage.pageNumBeginGet;
    }

    @Override
    public int getPageSize() {
        return this.basePage.getPageSize();
    }

    public Sort getSort() {
        if (this.basePage.getOrder() == null || this.basePage.getSortStr() == null)
            return null;
        return new Sort(Direction.fromString(this.basePage.getOrder()), this.basePage.getSortStr());
    }

    @Override
    public Pageable next() {
        return new PageResolver(getPageNumber(), getPageSize(), getSort());
    }

    @Override
    public Pageable previousOrFirst() {
        return hasPrevious() ? previous() : first();
    }

    public Pageable previous() {
        return getPageNumber() == 0 ? this : new PageResolver(getPageNumber() - 1, getPageSize(), getSort());
    }

    @Override
    public Pageable first() {
        return new PageResolver(0, getPageSize(), getSort());
    }

    @Override
    public boolean hasPrevious() {
        return getPageNumber() > 0;
    }

    @Override
    public int getOffset() {
        return this.basePage.getBeginNumber();
    }

}
