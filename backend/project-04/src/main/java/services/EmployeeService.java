package services;

import entities.Employee;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import repositories.EmployeeRepositories;


@Service
public class EmployeeService {
    private  EmployeeRepositories employeeRepositories;
    @Autowired
    public EmployeeService( EmployeeRepositories employeeRepositories) {
        this.employeeRepositories = employeeRepositories;
    }

    public EmployeeService() {
    }

    public Iterable<Employee> getAll(){
        return this.employeeRepositories.findAll();
    }
}
