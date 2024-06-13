package com.universityProject.workLoad.util;

import com.universityProject.workLoad.model.Person;
import com.universityProject.workLoad.services.PersonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

@Component
public class PersonValidator implements Validator {
    private final PersonService personService;

    @Autowired
    public PersonValidator(PersonService personService) {
        this.personService = personService;
    }

    @Override
    public boolean supports(Class<?> clazz) {
        return Person.class.isAssignableFrom(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        Person person = (Person) target;

        if(personService.findByName(person.getUsername()).isPresent()) {
            errors.rejectValue("username",
                    null,
                    "Аккаунт с таким логином уже существует");
        }
    }
}
