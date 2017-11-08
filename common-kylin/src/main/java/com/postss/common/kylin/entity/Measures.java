package com.postss.common.kylin.entity;

/**
 * 度量
 * @className Measures
 * @author jwSun
 * @date 2017年8月1日 上午9:53:31
 * @version 1.0.0
 */
public class Measures {

    /**度量方法**/
    private MeasuresFunction function;
    /**度量名**/
    private String name;

    public MeasuresFunction getFunction() {
        return function;
    }

    public void setFunction(MeasuresFunction function) {
        this.function = function;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    /**
     * 度量方法
     * @className Function
     * @author jwSun
     * @date 2017年8月1日 上午9:59:21
     * @version 1.0.0
     */
    public class MeasuresFunction {
        /**计算方式(SUM...)**/
        private String expression;
        /**参数**/
        private MeasuresFunctionParameter parameter;
        /**返回类型(bigint...)**/
        private String returntype;

        public String getExpression() {
            return expression;
        }

        public void setExpression(String expression) {
            this.expression = expression;
        }

        public MeasuresFunctionParameter getParameter() {
            return parameter;
        }

        public void setParameter(MeasuresFunctionParameter parameter) {
            this.parameter = parameter;
        }

        public String getReturntype() {
            return returntype;
        }

        public void setReturntype(String returntype) {
            this.returntype = returntype;
        }

        /**
         * 度量参数
         * @className Parameter
         * @author jwSun
         * @date 2017年8月1日 上午9:59:32
         * @version 1.0.0
         */
        public class MeasuresFunctionParameter {
            /**参数类型(constant/column...)**/
            private String type;
            /**值**/
            private String value;

            public String getType() {
                return type;
            }

            public void setType(String type) {
                this.type = type;
            }

            public String getValue() {
                return value;
            }

            public void setValue(String value) {
                this.value = value;
            }

        }

    }

}
