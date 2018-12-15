package mandatory.two.controller;

import mandatory.two.helper.CreateHelper;
import mandatory.two.helper.SessionHelper;
import mandatory.two.model.Customer;
import mandatory.two.model.Offer;
import mandatory.two.model.User;
import mandatory.two.repository.CategoryRepository;
import mandatory.two.repository.CustomerRepository;
import mandatory.two.repository.OfferRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import javax.servlet.http.HttpServletRequest;
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
    @Autowired
    private OfferRepository offerRepo;

    String error = "";

    @GetMapping("/customer/create")
    public String createCustomer(Model model) {
        model.addAttribute("customer", new Customer());
        model.addAttribute("category", categoryRepo.findAll());
        model.addAttribute("error", error);
        error = "";
        return "customer/createCustomer";
    }

    @PostMapping("/customer/create")
    public String createCustomer(@ModelAttribute Customer customer) {
        ArrayList<User> customerArrayList = (ArrayList) customerRepo.findAllByEmail(customer.getEmail());
        if (CreateHelper.checkIfEmailNotExists(customerArrayList)) {
            customerRepo.save(customer);
        } else {
            error = "Email '" + customer.getEmail() +  "' already exists, please try again!";
            return "redirect:/customer/create";
        }
        return "redirect:/customer/create/payment";
    }

    @GetMapping("/customer/create/payment")
    public String createCustomerPayment(Model model) {
        Customer c = customerRepo.findTopByOrderByIdDesc();
        System.out.println("C ID: " + c.getId());
        model.addAttribute("customer", c);
        System.out.println("GET ID: " + customerRepo.findTopByOrderByIdDesc().getId());
        return "customer/createCustomerPayment";
    }

    @PostMapping("/customer/create/payment")
    public String createCustomerPayment(@ModelAttribute Customer customer) {
        System.out.println();
        customerRepo.save(customer);
        return "redirect:/login";
    }

    @GetMapping("/customer/edit/{id}")
    public String editCustomer(@PathVariable Long id, Model model, HttpServletRequest request) {
        Optional<Customer> customerOptional = customerRepo.findById(id);
        Customer customer = customerOptional.get();
        customer.setId(id);
        model.addAttribute("category", categoryRepo.findAll());
        model.addAttribute("customer", customer);
        return SessionHelper.redirectCustomer(request, "customer/editCustomer");
    }

    @PostMapping("/customer/edit")
    public String editCustomer(@ModelAttribute Customer customer) {
        System.out.println("CUSTOMER ID: " + customer.getId());
        customerRepo.save(customer);
        return "redirect:/customer/offer/view";
    }

    @GetMapping("/customer/edit/payment/{id}")
    public String editCustomerPayment(@PathVariable Long id, Model model, HttpServletRequest request) {
        Optional<Customer> customerOptional = customerRepo.findById(id);
        Customer customer = customerOptional.get();
        customer.setId(id);
        model.addAttribute("customer", customer);
        return SessionHelper.redirectCustomer(request, "customer/editCustomerPayment");
    }

    @PostMapping("/customer/edit/payment")
    public String editCustomerPayment(@ModelAttribute Customer customer) {
        customerRepo.save(customer);
        return "redirect:/customer/offer/view";
    }

    @GetMapping("/customer/offer/view")
    public String customerOfferView(Model model, HttpServletRequest request) {

        Customer customer = CreateHelper.getCustomerFromSession(request, customerRepo);
        ArrayList<Offer> offerArrayList = (ArrayList) offerRepo.findAllByIsActive(true);

        model.addAttribute("customer", customer);
        model.addAttribute("offer", offerArrayList);
        return SessionHelper.redirectCustomer(request, "customer/frontpageCustomer");
    }

}
