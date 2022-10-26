package com.example.booktest;

import com.example.booktest.exception.*;
import com.example.booktest.model.Employee;
import com.example.booktest.service.EmployeeService;
import com.example.booktest.service.ValidatorService;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;


import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;



public class EmployeeServiceTest {
    private final EmployeeService employeeService = new EmployeeService(new ValidatorService());

    @AfterEach
  public void afterEach(){
   employeeService.getALL().forEach(employee -> employeeService.remove(employee.getFirstName(), employee.getLastName()));
   } // метод очищения данных для нового теста

    @Test
    public void addPositivTest() {
        addOneWithChek();
    }


        private Employee addOneWithChek(String firstName, String lastName){
            Employee employee = new Employee(firstName,lastName, 2, 55000);
            int sizeBefore = employeeService.getALL().size();
            employeeService.add(employee.getFirstName(), employee.getLastName(), employee.getDepartment(), employee.getSalary());
            assertThat(employeeService.getALL())
                    .isNotEmpty()
                    .hasSize(sizeBefore +1)
                    .contains(employee);
            Employee actual = employeeService.find(employee.getFirstName(), employee.getLastName());
            assertThat(actual).isEqualTo(employee);
        return employee;
    }

   private Employee addOneWithChek() {
       return addOneWithChek("Firstname", "lastname");
    }


     @ParameterizedTest
    @MethodSource("addNegativeParams")
    public void addNegativeTest(String firstName,
                                String lastName,
                                Class<Throwable> employeeExceptionType) {
      assertThatExceptionOfType(employeeExceptionType)
               .isThrownBy(() -> employeeService.add(firstName, lastName, 1, 56_000));
    }


    @Test
    public void addNegative2Test(){                                        //  есть ли уже такой сотрудник
     Employee employee = addOneWithChek();
       assertThatExceptionOfType(EmployeeAlredyAddedException.class)
               .isThrownBy(()->employeeService.add(employee.getFirstName(), employee.getLastName(), employee.getDepartment(), employee.getSalary()));
    }

    @Test
   public  void  addNegstive3Test() {                        // проверяет что еще есть место в списке (10)
        for (int i = 0; i < 10; i++) {
            addOneWithChek("Firstname" + (char) ('a' + i), "Lastname" + (char) ('a' + i));

        }
        assertThatExceptionOfType(EmployeeStorageIsFullException.class)
                .isThrownBy(() -> employeeService.add("Firstname", "Lastname", 1, 53_000));
    }
    @Test
    public void findPositive () {
        Employee employee1 = addOneWithChek("Firstname", "Lastname");
        Employee employee2 = addOneWithChek("Имя" , "Фамилия");
        assertThat(employeeService.find(employee1.getFirstName(), employee1.getLastName()))
                .isEqualTo(employee1);
        assertThat(employeeService.find(employee2.getFirstName(), employee2.getLastName()))
                .isEqualTo(employee2);
    }
    @Test
    public void findNegative() {

        assertThatExceptionOfType(EmployeeNotFoundException.class)
                .isThrownBy(()->employeeService.find("Petr", "Petrov"));

         addOneWithChek("Firstname", "Lastname");
         addOneWithChek("Имя" , "Фамилия");
        assertThatExceptionOfType(EmployeeNotFoundException.class)
                .isThrownBy(()->employeeService.find("Petr", "Petrov"));
    }

    @Test
    public void removePositive () {
        Employee employee1 = addOneWithChek("Firstname", "Lastname");
        Employee employee2 = addOneWithChek("Имя" , "Фамилия");
        assertThat(employeeService.remove(employee1.getFirstName(), employee1.getLastName()));
        assertThat(employeeService.getALL())
                .isNotEmpty()
                .hasSize(1)
                .containsExactly(employee2);
        employeeService.remove(employee2.getFirstName(), employee2.getLastName());
        assertThat(employeeService.getALL()).isEmpty();
    }
    @Test
    public void removeNegative() {

        assertThatExceptionOfType(EmployeeNotFoundException.class)
                .isThrownBy(()->employeeService.remove("Petr", "Petrov"));

        addOneWithChek("Firstname", "Lastname");
        addOneWithChek("Имя" , "Фамилия");
        assertThatExceptionOfType(EmployeeNotFoundException.class)
                .isThrownBy(()->employeeService.remove("Petr", "Petrov"));
    }


    public static Stream<Arguments> addNegativeParams() {
        return Stream.of(
                Arguments.of("Николай9", "Николаев", IncorrectFirstNameExeption.class),
                Arguments.of("Николай&", "Hиколаев", IncorrectFirstNameExeption.class),
                Arguments.of("Николай", "Николаев9", IncorrectLastNameExeption.class),
                Arguments.of("Николай", "Николаев@", IncorrectLastNameExeption.class),
                Arguments.of("Николай", "Николаев-@Сидоров", IncorrectLastNameExeption.class),
                Arguments.of("Николай", "Николаев-Сидоров1", IncorrectLastNameExeption.class)
        );
    }
}



