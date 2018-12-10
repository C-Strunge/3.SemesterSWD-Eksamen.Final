package mandatory.two.repository;

import mandatory.two.model.Category;
import org.springframework.data.repository.CrudRepository;

/**
 * Created by Matthias Skou 04/12/2018
 */

public interface CategoryRepository extends CrudRepository<Category, Long> {
}
