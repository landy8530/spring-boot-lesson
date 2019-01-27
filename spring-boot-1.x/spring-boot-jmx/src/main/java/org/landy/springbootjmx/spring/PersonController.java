package org.landy.springbootjmx.spring;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class PersonController {

    @Autowired
    private Person person;

    @GetMapping("/person")
    public Person person(@RequestParam(required = false) String name,
                         @RequestParam(required = false) String description
                         ) {

        person.setDescription(description);
        person.setName(name);

        return person;
    }

}
