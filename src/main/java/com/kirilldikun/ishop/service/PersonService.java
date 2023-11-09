package com.kirilldikun.ishop.service;

import com.kirilldikun.ishop.dto.PersonDTO;
import com.kirilldikun.ishop.entity.Person;
import com.kirilldikun.ishop.exception.PersonNotFoundException;
import com.kirilldikun.ishop.repository.PersonRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PersonService {

    private final PersonRepository personRepository;

    public Person save(PersonDTO personDTO) {
        Person person = mapToPerson(personDTO);
        return personRepository.save(person);
    }

    public Person update(Long id, PersonDTO personDTO) {
        if (!personRepository.existsById(id)) {
            throw new PersonNotFoundException();
        }
        Person person = mapToPerson(personDTO);
        person.setId(id);
        return personRepository.save(person);
    }

    public void delete(Person person) {
        personRepository.delete(person);
    }

    public PersonDTO mapToPersonDTO(Person person) {
        return PersonDTO.builder()
                .id(person.getId())
                .firstName(person.getFirstName())
                .lastName(person.getLastName())
                .build();
    }

    public Person mapToPerson(PersonDTO personDTO) {
        return Person.builder()
                .id(personDTO.getId())
                .firstName(personDTO.getFirstName())
                .lastName(personDTO.getLastName())
                .build();
    }
}
