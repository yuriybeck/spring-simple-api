package de.solovyov.demo.dao;

import de.solovyov.demo.model.Person;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository("postgres")
public class PersonDataAccessService implements PersonDao {

    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public PersonDataAccessService(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public boolean insertPerson(UUID id, Person person) {
        final String sql = "INSERT INTO " + PersonDao.TABLE_NAME + " (id, name) VALUES (uuid_generate_v4(), ?)";

        return this.jdbcTemplate.update(sql, new Object[]{person.getName()}) == 1;
    }

    @Override
    public List<Person> selectAllPeople() {
        final String sql = "SELECT id, name FROM " + PersonDao.TABLE_NAME;
        return this.jdbcTemplate.query(sql, (rs, rowNum) -> {
            UUID id = UUID.fromString(rs.getString("id"));
            String name = rs.getString("name");
            return new Person(id, name);
        });
    }

    @Override
    public Optional<Person> selectPersonById(UUID id) {
        final String sql = "SELECT id, name FROM " + PersonDao.TABLE_NAME + " WHERE id = ?";
        Person person = this.jdbcTemplate.queryForObject(sql, new Object[]{id}, (rs, rowNum) -> {
            UUID personId = UUID.fromString(rs.getString("id"));
            String name = rs.getString("name");
            return new Person(personId, name);
        });

        return Optional.ofNullable(person);
    }

    @Override
    public boolean deletePersonById(UUID id) {
        final String sql = "DELETE FROM " + PersonDao.TABLE_NAME + " WHERE id = ?";
        return this.jdbcTemplate.update(sql, new Object[]{id}) == 1;
    }

    @Override
    public boolean updatePersonById(UUID id, Person person) {
        final String sql = "UPDATE " + PersonDao.TABLE_NAME + " SET name = ? WHERE id = ?";
        return this.jdbcTemplate.update(sql, new Object[]{person.getName(), id}) == 1;
    }
}
