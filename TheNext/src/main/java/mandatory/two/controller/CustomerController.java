package mandatory.two.controller;

import mandatory.two.helper.CreateHelper;
import mandatory.two.model.Customer;
import mandatory.two.model.User;
import mandatory.two.repository.CategoryRepository;
import mandatory.two.repository.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.ArrayList;
import java.util.Optional;

/**
 * Created by Matthias Skou 30/11/2018
 */

@Controller
public class CustomerController {


    
    @Autowired
    private CustomerRepository customerRepo;
    @Autowired
    private CategoryRepository categoryRepo;

    @GetMapping("/customer/create")
    public String createCustomer(Model model){
        model.addAttribute("customer", new Customer());
        model.addAttribute("category", categoryRepo.findAll());
        return "customer/createCustomer";
    }

    @PostMapping("/customer/create")
    public String createCustomer(@ModelAttribute Customer customer){
        ArrayList<User> customerArrayList = (ArrayList) customerRepo.findAllByEmail(customer.getEmail());
        if (CreateHelper.checkIfEmailExists(customerArrayList)){
            customerRepo.save(customer);
        }
        // Insert else statement that redirects to company/create
        return "redirect:/customer/create/payment";
    }

    @GetMapping("/customer/create/payment")
    public String createCustomerPayment(Model model){
        Customer c = customerRepo.findTopByOrderByIdDesc();
        System.out.println("C ID: " + c.getId());
        model.addAttribute("customer", c);
        System.out.println("GET ID: " + customerRepo.findTopByOrderByIdDesc().getId());
        return "customer/createCustomerPayment";
    }

    @PostMapping("/customer/create/payment")
    public String createCustomerPayment(@ModelAttribute Customer customer){
        System.out.println();
        customerRepo.save(customer);
        return "";
    }

    @GetMapping("/customer/edit/{id}")
    public String editCustomer(@PathVariable Long id, Model model){
        Optional<Customer> customerOptional = customerRepo.findById(id);
        Customer customer = customerOptional.get();
        customer.setId(id);
        model.addAttribute("category", categoryRepo.findAll());
        model.addAttribute("customer", customer);
        return "customer/editCustomer";
    }

    @PostMapping("/customer/edit")
    public String editCustomer(@ModelAttribute Customer customer){
        customerRepo.save(customer);
        return "";
    }

    @GetMapping("/customer/edit/payment/{id}")
    public String editCustomerPayment(@PathVariable Long id, Model model){
        Optional<Customer> customerOptional = customerRepo.findById(id);
        Customer customer = customerOptional.get();
        customer.setId(id);
        model.addAttribute("customer", customer);
        return "customer/editCustomerPayment";
    }

    @PostMapping("/customer/edit/payment")
    public String editCustomerPayment(@ModelAttribute Customer customer){
        customerRepo.save(customer);
        return "";
    }
}
