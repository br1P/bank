package org.example.MyBatis.DAOS;

import org.apache.ibatis.annotations.*;
import org.example.model.Person;
import java.util.List;

@Mapper
public interface PersonMapper {

    @Select("SELECT * FROM Person WHERE PersonID = #{id}")
    Person get(int id);

    @Select("SELECT * FROM Person")
    List<Person> getAll();

    @Insert("INSERT INTO Person (Name, LastName) VALUES (#{name}, #{lastName})")
    @Options(useGeneratedKeys = true, keyProperty = "personID")
    void insert(Person person);

    @Update("UPDATE Person SET Name = #{name}, LastName = #{lastName} WHERE PersonID = #{personID}")
    void update(Person person);

    @Delete("DELETE FROM Person WHERE PersonID = #{personID}")
    void delete(int personID);
}
