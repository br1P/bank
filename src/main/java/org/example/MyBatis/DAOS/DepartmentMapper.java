package org.example.MyBatis.DAOS;

import org.apache.ibatis.annotations.*;
import org.example.model.Department;

import java.util.List;

@Mapper
public interface DepartmentMapper {

    @Select("SELECT * FROM Department WHERE DepartmentID = #{id}")
    Department get(int id);

    @Select("SELECT * FROM Department")
    List<Department> getAll();

    @Insert("INSERT INTO Department (DepartmentName) VALUES (#{departmentName})")
    @Options(useGeneratedKeys = true, keyProperty = "departmentID")
    void insert(Department department);

    @Update("UPDATE Department SET DepartmentName = #{departmentName} WHERE DepartmentID = #{departmentID}")
    void update(Department department);

    @Delete("DELETE FROM Department WHERE DepartmentID = #{id}")
    void delete(int id);
}
