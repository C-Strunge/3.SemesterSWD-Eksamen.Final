package mandatory.two.controller;

/* Created by Casper Frost
 */


import mandatory.two.model.Category;
import mandatory.two.model.Company;
import mandatory.two.model.Customer;
import mandatory.two.repository.CategoryRepository;
import mandatory.two.repository.CompanyRepository;
import mandatory.two.repository.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Optional;

@Controller
public class AdminController {

    @Autowired
    private CompanyRepository companyRepository;
    @Autowired
    private CustomerRepository customerRepo;
    @Autowired
    private CategoryRepository categoryRepo;

    @GetMapping("/admin/verify")
    public String verifyCompany(Model model) {
        ArrayList<Company> verifyCompanyList = (ArrayList) companyRepository.findAllByIsActive(0);
        model.addAttribute("companyToView", verifyCompanyList);
        return "Admin/verifyCompany";
    }

    @GetMapping("/admin/verify/accepted/{id}")
    public String acceptCompany(@PathVariable Long id){
        Optional<Company> optionalCompany= companyRepository.findById(id);
        Company c = optionalCompany.get();
        c.setIsActive(1);
        companyRepository.save(c);
        return "redirect:/admin/verify";
    }

    @GetMapping("/admin/verify/declined/{id}")
    public String declineCompany(@PathVariable Long id) {
        companyRepository.deleteById(id);
        return "redirect:/admin/verify";
    }

    @GetMapping("/admin/edit/customer/{id}")
    public String editCustomerAdmin(@PathVariable Long id, Model model){
        Optional<Customer> customerOptional = customerRepo.findById(id);
        Customer customer = customerOptional.get();
        customer.setId(id);
        model.addAttribute("category", categoryRepo.findAll());
        model.addAttribute("customer", customer);
        return "Admin/editCustomerAdmin";
    }

    @PostMapping("/admin/edit/customer")
    public String editCustomer(@ModelAttribute Customer customer){
        customerRepo.save(customer);
        return "redirect:/admin/verify";
    }
    @GetMapping("/admin/edit/company/{id}")
    public String editCompany(@PathVariable Long id, Model model) {
        Optional<Company> company = companyRepository.findById(id);
        Company c = company.get();
        c.setId(id);
        model.addAttribute("category", categoryRepo.findAll());
        model.addAttribute("company", c);
        return "admin/editCompanyAdmin";
    }

    @PostMapping("/admin/edit/company/")
    public String editCompany(@ModelAttribute Company company) {
        companyRepository.save(company);
        return "redirect:/admin/verify";
    }

    @GetMapping("/admin/companies/view")
    public String viewCompanys(Model model) {
        ArrayList<Company> companyArrayList = (ArrayList) companyRepository.findAllByIsActive(1);
        model.addAttribute("companies", companyArrayList);
        return "Admin/companyView";

    }

}
