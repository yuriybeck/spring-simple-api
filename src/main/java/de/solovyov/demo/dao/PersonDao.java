package de.solovyov.demo.dao;

import de.solovyov.demo.model.Person;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface PersonDao {

    static final String TABLE_NAME = "person";

    boolean insertPerson(UUID id, Person person);

    default boolean insertPerson(Person person) {
        UUID id = UUID.randomUUID();
        return insertPerson(id, person);
    }

    List<Person> selectAllPeople();

    Optional<Person> selectPersonById(UUID id);

    boolean deletePersonById(UUID id);

    boolean updatePersonById(UUID id, Person person);

}
