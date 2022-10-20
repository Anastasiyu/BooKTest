package com.example.booktest.controller;

import com.example.booktest.model.Employee;
import com.example.booktest.service.DepartmentService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collection;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/departments")
public class DepartmentController {


    private  final DepartmentService departmentService;

    public  DepartmentController(DepartmentService departmentService) {
        this.departmentService = departmentService;
    }

    @GetMapping("/max-salary")
    public Employee findWhithMaxSalaryFromDepartment(int department){
        return  departmentService.findEmployeeWhithMaxSalaryFromDepartment(department);
    }
   // @GetMapping("/max-salary")
  //  public  findWhithMaxSalaryFromDepartment(int department){
   //     return  departmentService.findWhithMaxSalaryFromDepartment(department);
   // }
    @GetMapping("/min-salary")
    public Employee findEmployeeWhithMinSalaryFromDepartment(int department){
        return  departmentService.findEmployeeWhithMinSalaryFromDepartment(department);
    }
    @GetMapping(value = "/all", params =  "department")
    public Collection<Employee>findEmployeesFromDepartment(int department){
        return  departmentService.findEmployeesFromDepartment(department);
    }
    @GetMapping("/all")
    public Map<Integer, List<Employee>> findEmployees(){

        return  departmentService.findEmployees();
    }
}
