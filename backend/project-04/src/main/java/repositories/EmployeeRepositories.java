package repositories;

import entities.Employee;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EmployeeRepositories extends CrudRepository< Employee, Long> {
}
