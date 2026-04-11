package com.universityProject.workLoad.services;

import com.universityProject.workLoad.model.Person;
import com.universityProject.workLoad.model.Role;
import com.universityProject.workLoad.repositories.PersonRepository;
import com.universityProject.workLoad.security.UserPrincipal;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class PersonDetailsService implements UserDetailsService {
    private final PersonRepository personRepository;

    @Autowired
    public PersonDetailsService(PersonRepository personRepository) {
        this.personRepository = personRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Person person = personRepository.findByUsername(username).
                orElseThrow(() -> new UsernameNotFoundException("User not found with username: " + username));

        return UserPrincipal.builder()
                .userId((long) person.getId())
                .userName(person.getUsername())
                .email(person.getEmail())
                .roles(person.getRoles().stream().map(Role::getName).toList())
                .build();
    }
}
