package org.example.MyBatis.Service;

import org.example.MyBatis.DAOS.CustomerMapper;
import org.example.model.Customer;

import java.util.List;

public class CustomerService {

    private final CustomerMapper customerMapper;

    public CustomerService(CustomerMapper customerMapper) {
        this.customerMapper = customerMapper;
    }

    public Customer getCustomer(int id) {
        return customerMapper.get(id);
    }

    public List<Customer> getAllCustomers() {
        return customerMapper.getAll();
    }

    public void createCustomer(Customer customer) {
        customerMapper.insert(customer);
    }

    public void updateCustomer(Customer customer) {
        customerMapper.update(customer);
    }

    public void deleteCustomer(int id) {
        customerMapper.delete(id);
    }
}
