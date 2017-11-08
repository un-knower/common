package com.postss.common.base.page;

/**
 * 分页接收
 * @author jwSun
 * @date 2017年3月31日 下午4:20:38
 */
public class QueryPage {

    //private PageResolver basePage;
    //历史遗留问题
    private BasePage basePage;
    //优先page
    private BasePage page;

    public BasePage getBasePage() {
        if (page != null) {
            return page;
        }
        return basePage;
    }

    public void setBasePage(BasePage basePage) {
        this.basePage = basePage;
    }

    /**
     * 内部调用getBasePage();
     * @return
     */
    public BasePage getPage() {
        return getBasePage();
    }

    public void setPage(BasePage page) {
        this.page = page;
    }

    public PageResolver getPageResolver() {
        return new PageResolver(getPage());
    }

}
