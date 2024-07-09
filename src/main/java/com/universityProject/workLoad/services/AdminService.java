package com.universityProject.workLoad.services;

import com.universityProject.workLoad.model.Person;
import com.universityProject.workLoad.repositories.PersonRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;

@Service
public class AdminService {
    private final PersonRepository personRepository;

    @Autowired
    public AdminService(PersonRepository personRepository) {
        this.personRepository = personRepository;
    }

    public List<Person> getAllPersons() {

        String currentUser = SecurityContextHolder.getContext().getAuthentication().getName();

        List<Person> persons = personRepository.findAll(Sort.by("enabled"));
        persons.removeIf(person -> person.getUsername().equals(currentUser));

        return persons;
    }

    @Transactional
    public void activate(int id) {
        personRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("No users with this id were found"))
                .setEnabled(true);
    }

    @Transactional
    public void deactivate(int id) {
        personRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("No users with this id were found"))
                .setEnabled(false);
    }


}
