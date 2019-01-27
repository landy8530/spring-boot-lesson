package org.landy.springbootjmx.spring;

import org.springframework.jmx.export.annotation.ManagedAttribute;
import org.springframework.jmx.export.annotation.ManagedResource;
import org.springframework.stereotype.Component;

@ManagedResource
@Component
public class Person {

    private String name;
    private String description;

    @ManagedAttribute(defaultValue = "Landy", description = "This is a name field.")
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @ManagedAttribute(defaultValue = "This is Landy's description", description = "This is a description field.")
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
