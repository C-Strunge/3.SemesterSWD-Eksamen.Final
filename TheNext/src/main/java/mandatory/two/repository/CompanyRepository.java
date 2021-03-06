package mandatory.two.repository;

import mandatory.two.model.Company;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;

/**
 * Created by Matthias Skou 30/11/2018
 */

public interface CompanyRepository extends CrudRepository<Company, Long> {
    Company findByEmail(String email);
    List<Company> findAllByEmail(String s);
    List<Company> findAllByIsActive(Integer a);
    Optional<Company> findById(Long id);
}
