package mandatory.two.repository;

import mandatory.two.model.Company;
import mandatory.two.model.Customer;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

/**
 * Created by Matthias Skou 30/11/2018
 */

public interface CustomerRepository extends CrudRepository<Customer, Long> {

    Customer findByEmail(String email);
    List<Company> findAllByEmail(String s);
    Customer findTopByOrderByIdDesc();

}
