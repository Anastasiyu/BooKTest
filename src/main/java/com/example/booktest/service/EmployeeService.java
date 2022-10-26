package com.example.booktest.service;

import com.example.booktest.exception.EmployeeAlredyAddedException;
import com.example.booktest.exception.EmployeeNotFoundException;
import com.example.booktest.exception.EmployeeStorageIsFullException;
import com.example.booktest.model.Employee;
import org.springframework.stereotype.Service;

import java.util.*;


@Service
public class EmployeeService {


    private static final int LIMIT = 10;

    private final Map<String, Employee> employees = new HashMap<>();
    private final ValidatorService validatorService;

    private String getKey(String firstName, String lastName) {
        return firstName + " " + lastName;
    }


    public EmployeeService(ValidatorService validatorService) {
        this.validatorService = validatorService;
    }


    public Employee add(String firstName,
                        String lastName,
                        int department,
                        double salary) {
        Employee employee = new Employee(
                validatorService.validateFirstName(firstName),
                validatorService.validateLastName(lastName),
                department, salary);

        String key = getKey(firstName, lastName);

        if (employees.containsKey(key)) {
            throw new EmployeeAlredyAddedException();
        }

        if (employees.size() < LIMIT) {

            employees.put(key, employee);
            return employee;
        }
        throw new EmployeeStorageIsFullException();
    }

    public Employee remove(String firstName,
                           String lastName) {
        Employee employee = new Employee(firstName, lastName);
        String key = getKey(firstName, lastName);
        if (employees.containsKey(key)) {
            return employees.remove(key);
        }
        throw new EmployeeNotFoundException();
    }

    public Employee find(String firstName,
                         String lastName) {
        Employee employee = new Employee(firstName, lastName);
        String key = getKey(firstName, lastName);
        if (employees.containsKey(key)) {
            return employees.get(key);
        }
        throw new EmployeeNotFoundException();
    }

    public List<Employee> getALL() {
        return new ArrayList<>(employees.values());
    }

}


