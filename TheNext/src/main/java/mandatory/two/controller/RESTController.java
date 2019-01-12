package mandatory.two.controller;

import mandatory.two.model.Category;
import mandatory.two.model.Customer;
import mandatory.two.repository.CategoryRepository;
import mandatory.two.repository.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

/* Created by Casper Frost
 */
@RestController
public class RESTController {
    @Autowired
    private CategoryRepository categoryRepository;
    @Autowired
    private CustomerRepository customerRepository;


    @GetMapping("customer/get/{id}")
    public ResponseEntity<Customer> getCustomer(@PathVariable Long id){


        return new ResponseEntity(customerRepository.findById(id), HttpStatus.ACCEPTED);
    }


    @PostMapping("offer/create/")

        public ResponseEntity<Category> createCategory(@RequestParam(name = "name") String name, @RequestParam(name="URL") String url){

          Category category= new Category();
          category.setName(name);
          category.setIcon_url(url);
          categoryRepository.save(category);
          return new ResponseEntity(category, HttpStatus.ACCEPTED);

    }


    @DeleteMapping("category/delete/{id}")
    public ResponseEntity<Category> deleteCategory(@PathVariable Long id){
                  Optional<Category> temp=categoryRepository.findById(id);
                  if(!temp.isPresent()) {
                      return new ResponseEntity(HttpStatus.NO_CONTENT);

                  }
                         categoryRepository.deleteById(id);
        return new ResponseEntity(temp, HttpStatus.ACCEPTED);
    }
    @PutMapping("category/update/{id}")
    public ResponseEntity<Category> update(@PathVariable Long id,@RequestParam(name = "name") String name, @RequestParam(name="URL") String url){
        Category category= new Category();
        category.setName(name);
        category.setIcon_url(url);
        category.setId(id);
        categoryRepository.save(category);
        return new ResponseEntity(category, HttpStatus.ACCEPTED);

    }

}
