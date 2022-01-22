package de.solovyov.demo.dao;

import de.solovyov.demo.model.Person;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository("fakeDao")
public class FakePersonDataAccessService implements PersonDao {
    private static List<Person> DB = new ArrayList<>();

    @Override
    public boolean insertPerson(UUID id, Person person) {
        DB.add(new Person(id, person.getName()));
        return true;
    }

    @Override
    public List<Person> selectAllPeople() {
        return DB;
    }

    @Override
    public Optional<Person> selectPersonById(UUID id) {
        return DB.stream().filter(person -> person.getId().equals(id)).findFirst();
    }

    @Override
    public boolean deletePersonById(UUID id) {
        Optional<Person> person = this.selectPersonById(id);
        if (person.isEmpty()) {
            return false;
        }
        DB.remove(person.get());
        return true;
    }

    @Override
    public boolean updatePersonById(UUID id, Person person) {
        return selectPersonById(id).map(p -> {
            int indexOfPersonToUpdate = DB.indexOf(p);
            if (indexOfPersonToUpdate >= 0) {
                DB.set(indexOfPersonToUpdate, new Person(id, person.getName()));
                return true;
            }
            return false;
        }).orElse(false);
    }
}

