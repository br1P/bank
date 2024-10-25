package org.example.MyBatis.Service;

import org.example.MyBatis.DAOS.DepartmentMapper;
import org.example.model.Department;

import java.util.List;

public class DepartmentService {

    private final DepartmentMapper departmentMapper;

    public DepartmentService(DepartmentMapper departmentMapper) {
        this.departmentMapper = departmentMapper;
    }

    public Department getDepartment(int id) {
        return departmentMapper.get(id);
    }

    public List<Department> getAllDepartments() {
        return departmentMapper.getAll();
    }

    public void createDepartment(Department department) {
        departmentMapper.insert(department);
    }

    public void updateDepartment(Department department) {
        departmentMapper.update(department);
    }

    public void deleteDepartment(int id) {
        departmentMapper.delete(id);
    }
}
