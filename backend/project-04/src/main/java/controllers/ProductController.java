package controllers;

import entities.Employee;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import services.EmployeeService;


@RestController
public class ProductController {

    private  EmployeeService employeeService;

    public ProductController(EmployeeService service) {
        this.employeeService =service;
    }

    @GetMapping()
    public Iterable<Employee> getList(){
        return this.employeeService.getAll();
    }
}
