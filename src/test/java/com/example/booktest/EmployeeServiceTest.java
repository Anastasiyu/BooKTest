package com.example.booktest;

import com.example.booktest.exception.EmployeeAlredyAddedException;
import com.example.booktest.exception.EmployeeStorageIsFullException;
import com.example.booktest.exception.IncorrectFirstNameExeption;
import com.example.booktest.exception.IncorrectLastNameExeption;
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
import static org.assertj.core.api.InstanceOfAssertFactories.ARRAY;

public class EmployeeServiceTest {
    private final EmployeeService employeeService = new EmployeeService(new ValidatorService());


    @AfterEach
    public void afterEach(){
        employeeService.getALL().forEach(employee -> employeeService.remove(employee.getFirstName(), employee.getLastName(),employee.getDepartment(), employee.getSalary()));
    } // метод очищения данных для нового теста
    @Test
    public void addPositivTest() {
        addOneWithChek();
    }
    private Employee addOneWithChek(){
        return addOneWithChek("FirstName", "LastName");
    }

        private Employee addOneWithChek(String firstName, String lastName){
        Employee expected = new Employee(firstName, lastName,2,55000);
        int sizeBefore = employeeService.getALL().size();
        employeeService.add(expected.getFirstName(),expected.getLastName(),expected.getDepartment(),expected.getSalary());
        assertThat(employeeService.getALL())
                .isNotEmpty()
                .hasSize(sizeBefore +1)
                .contains(expected);

        assertThat(employeeService.find(expected.getFirstName(), expected.getLastName(), expected.getDepartment(), expected.getSalary())).isEqualTo(expected);
        return expected;
    }

    @ParameterizedTest
    @MethodSource("addNegativeParams")
    public void addNegativeTest(String firstName,
                                String lastName,
                                Class<Throwable> expectedExceptionType) {

        assertThatExceptionOfType(expectedExceptionType)
                .isThrownBy(() -> employeeService.add("FirstName", "LastName", 2, 55000));
    }



    public static Stream<Arguments> addNegativeParams() {
        return Stream.of(
                Arguments.of("Николай9", "Николаев", IncorrectFirstNameExeption.class),
                Arguments.of("Николай", "николаев", IncorrectLastNameExeption.class),
                Arguments.of("Николай", "Николаев9", IncorrectLastNameExeption.class),
                Arguments.of("Николай", "Николаев@", IncorrectLastNameExeption.class),
                Arguments.of("Николай", "Николаев-Сидоров", IncorrectLastNameExeption.class),
                Arguments.of("Николай", "Николаев-Сидоров", IncorrectLastNameExeption.class)

        );
    }

    @Test
    public void addNegative2Test(){
        Employee employee = addOneWithChek();
        assertThatExceptionOfType(EmployeeAlredyAddedException.class)
                .isThrownBy(()->employeeService.add(employee.getFirstName(),employee.getLastName(),employee.getDepartment(),employee.getSalary()));
    }

    @Test
    public  void  addNegstive3Test(){
        for (int i =0; i < 10; i++){
            addOneWithChek("FirstName" +(char)('a' +i), "LastName" + (char) ('a' +i));

        }
        assertThatExceptionOfType(EmployeeStorageIsFullException.class)
                .isThrownBy(()-> employeeService.add("FirstName", "LastName", 5, 53000));
    }
}

