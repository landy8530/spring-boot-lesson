package org.landy.springbootjmx.standard;

public interface HelloMBean {

    String greeting();

    void setValue(String value);

    String getValue();
}
