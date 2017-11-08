package com.postss.common.base.page;

import java.util.Date;

public class PageBuilder {

    public static BodyBuilder begin() {
        return new DefaultBodyBuilder();
    }

    public interface DefaultBuilder<B extends DefaultBuilder<B>> {
        public B setTotal(Integer total);

        public B setStartTime(Date startTime);

        public B setEndTime(Date endTime);

        public B setOrder(String order);

        public B setSort(String sort);

        public B setBeginNumber(Integer beginNumber);

        public B setPageNumber(Integer pageNumber);

        public B setPageSize(Integer pageSize);

        public B setEndNumber(Integer endNumber);

        public BasePage build();
    }

    public interface BodyBuilder extends DefaultBuilder<BodyBuilder> {

    }

    private static class DefaultBodyBuilder implements BodyBuilder {

        private BasePage page = new BasePage();

        public DefaultBodyBuilder setTotal(Integer total) {
            this.page.setTotal(total);
            return this;
        }

        public DefaultBodyBuilder setStartTime(Date startTime) {
            this.page.setStartTime(startTime);
            return this;
        }

        public DefaultBodyBuilder setEndTime(Date endTime) {
            this.page.setEndTime(endTime);
            return this;
        }

        public DefaultBodyBuilder setOrder(String order) {
            this.page.setOrder(order);
            return this;
        }

        public DefaultBodyBuilder setSort(String sort) {
            this.page.setSort(sort);
            return this;
        }

        public DefaultBodyBuilder setBeginNumber(Integer beginNumber) {
            this.page.setBeginNumber(beginNumber);
            return this;
        }

        public DefaultBodyBuilder setPageNumber(Integer pageNumber) {
            this.page.setPageNumber(pageNumber);
            return this;
        }

        public DefaultBodyBuilder setPageSize(Integer pageSize) {
            this.page.setPageSize(pageSize);
            return this;
        }

        public DefaultBodyBuilder setEndNumber(Integer endNumber) {
            this.page.setEndNumber(endNumber);
            return this;
        }

        @Override
        public BasePage build() {
            return this.page;
        }
    }

}
