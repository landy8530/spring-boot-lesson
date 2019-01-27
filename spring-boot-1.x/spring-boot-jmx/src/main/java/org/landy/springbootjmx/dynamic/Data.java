package org.landy.springbootjmx.dynamic;

public interface Data {
    long getId();

    void setId(long id);

    String getName();

    void setName(String name);

    int getAge();

    void setAge(int age);

    @Override
    String toString();
}
