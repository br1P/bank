package org.example.MyBatis.Service;

import org.example.MyBatis.DAOS.PersonMapper;
import org.example.model.Person;

import java.util.List;

public class PersonService {

    private final PersonMapper personMapper;


    public PersonService(PersonMapper personMapper) {
        this.personMapper = personMapper;
    }

    public Person getPersonById(int id) {
        return personMapper.get(id);
    }

    public List<Person> getAllPersons() {
        return personMapper.getAll();
    }

    public void createPerson(Person person) {
        personMapper.insert(person);
    }

    public void updatePerson(Person person) {
        personMapper.update(person);
    }

    public void deletePerson(int personID) {
        personMapper.delete(personID);
    }
}
