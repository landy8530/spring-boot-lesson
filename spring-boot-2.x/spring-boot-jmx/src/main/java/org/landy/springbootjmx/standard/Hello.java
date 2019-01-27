package org.landy.springbootjmx.standard;

/**
 * 标准Bean实现的接口必须以MBean结尾
 */
public class Hello implements HelloMBean {
    private String value;

    @Override
    public String greeting() {
        return "hello world";
    }

    @Override
    public void setValue(String value) {
        this.value = value;
    }

    @Override
    public String getValue() {
        return value;
    }
}
