package com.example.booktest;

import com.example.booktest.exception.EmployeeNotFoundException;
import com.example.booktest.model.Employee;
import com.example.booktest.service.DepartmentService;
import com.example.booktest.service.EmployeeService;

import org.assertj.core.api.AbstractBigDecimalAssert;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.*;
import java.util.stream.Stream;


import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class DepartmentServiceTest {

    @Mock
    private EmployeeService employeeService;

    @InjectMocks
    private DepartmentService departmentService;

    @BeforeEach
    public void beforeEach(){
        when(employeeService.getALL()).thenReturn(
                Arrays.asList(
                        new Employee("Владимир","Смирнов", 3, 41600.00),
                        new Employee("Виктор","Ветров", 2, 48900.00),
                        new Employee("Надежда","Петрова", 3, 46600.80),
                        new Employee("Сергей","Кротов", 2, 38600.00),
                        new Employee("Роман", "Золотов", 3, 48300.00),
                        new Employee("Нина", "Николаева", 2, 49600.00)
                )
        );
    }
    @ParameterizedTest
    @MethodSource("findEmployeeWhithMaxSalaryFromDepartmentTestParams")
    public void findEmployeeWhithMaxSalaryFromDepartmentTest(int department,
                                                             Employee expected){
       assertThat(departmentService.findEmployeeWhithMaxSalaryFromDepartment(department)).isEqualTo(expected);

    }

    @Test
    public void findEmployeeWhithMaxSalaryFromDepartmentNegativeTest(){
        Assertions.assertThatExceptionOfType(EmployeeNotFoundException.class)
                .isThrownBy(()->departmentService.findEmployeeWhithMaxSalaryFromDepartment(1));

    }

    @ParameterizedTest
    @MethodSource("findEmployeeWhithMinSalaryFromDepartmentTestParams")
    public void findEmployeeWhithMinSalaryFromDepartmentTest(int department,
                                                             Employee expected){
        assertThat(departmentService.findEmployeeWhithMinSalaryFromDepartment(department)).isEqualTo(expected);

    }
    @Test
    public void findEmployeeWhithMinSalaryFromDepartmentNegativeTest() {
        Assertions.assertThatExceptionOfType(EmployeeNotFoundException.class)
                .isThrownBy(() -> departmentService.findEmployeeWhithMinSalaryFromDepartment(5));
    }

    @ParameterizedTest
    @MethodSource("findEmployeesFromDepartmentTestParams")
    public void findEmployeesFromDepartmentTest(int department,
                                                Collection<Employee>expected){
        assertThat(departmentService.findEmployeesFromDepartment(department)).containsExactlyInAnyOrderElementsOf(expected);
    }

    @Test
    public void findEmployeesTest(){
        assertThat(departmentService.findEmployees()).containsExactlyInAnyOrderEntriesOf(
                Map.of(2,
                        List.of(
                                new Employee("Виктор","Ветров", 2, 48900.00),
                                new Employee(   "Сергей","Кротов", 2, 38600.00),
                                new Employee("Нина", "Николаева", 2, 49600.00)
                        ),
                        3,
                        List.of(
                                new Employee("Владимир","Смирнов", 3, 41600.00),
                                new Employee("Надежда","Петрова", 3, 46600.80),
                                new Employee("Роман", "Золотов", 3, 48300.00))
                )
        );
    }


    public static Stream<Arguments> findEmployeeWhithMaxSalaryFromDepartmentTestParams(){
        return Stream.of(
                Arguments.of( 2 ,   new Employee("Нина", "Николаева", 2, 49600.00)),
                Arguments.of(3,  new Employee("Роман", "Золотов", 3, 48300.00))
        );
    }
    public static Stream<Arguments> findEmployeeWhithMinSalaryFromDepartmentTestParams(){
        return Stream.of(
                Arguments.of( 2 , new Employee(   "Сергей","Кротов", 2, 38600.00)),
                Arguments.of(3,  new Employee("Владимир","Смирнов", 3, 41600.00))
        );
    }
    public static Stream<Arguments> findEmployeesFromDepartmentTestParams(){
        return Stream.of(
                Arguments.of(1, Collections.emptyList()),
                Arguments.of( 2 ,
                        List.of(
                                new Employee(   "Сергей","Кротов", 2, 38600.00),
                                new Employee("Виктор","Ветров", 2, 48900.00),
                                new Employee("Нина", "Николаева", 2, 49600.00))
                        ),
                Arguments.of(3,
                        List.of(
                                new Employee("Владимир","Смирнов", 3, 41600.00),
                                new Employee("Роман", "Золотов", 3, 48300.00),
                                new Employee("Надежда","Петрова", 3, 46600.80))
                        )
                );
}}
