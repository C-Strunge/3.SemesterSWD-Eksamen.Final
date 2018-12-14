package mandatory.two.repository;

import mandatory.two.model.Offer;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

/**
 * Created by Matthias Skou 30/11/2018
 */

public interface OfferRepository extends CrudRepository<Offer, Long> {

    List<Offer> findAllByIsActive(Boolean b);

}
