package mandatory.two.controller;

/* Created by Casper Frost
 */


import mandatory.two.helper.CreateHelper;
import mandatory.two.helper.SessionHelper;
import mandatory.two.model.Admin;
import mandatory.two.model.Category;
import mandatory.two.model.Company;
import mandatory.two.model.Customer;
import mandatory.two.repository.AdminRepository;
import mandatory.two.repository.CategoryRepository;
import mandatory.two.repository.CompanyRepository;
import mandatory.two.repository.CustomerRepository;
import org.hibernate.service.spi.InjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
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
    @Autowired
    private AdminRepository adminRepo;

    @GetMapping("/admin/verify")
    public String verifyCompany(Model model, HttpServletRequest request) {
        ArrayList<Company> verifyCompanyList = (ArrayList) companyRepository.findAllByIsActive(0);

        Admin admin = CreateHelper.getAdminFromSession(request, adminRepo);
        model.addAttribute("admin", admin);
        model.addAttribute("companyToView", verifyCompanyList);
        return SessionHelper.redirectAdministrator(request, "Admin/verifyCompany");
    }

    @GetMapping("/admin/verify/accepted/{id}")
    public String acceptCompany(@PathVariable Long id) {
        Optional<Company> optionalCompany = companyRepository.findById(id);
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

    @GetMapping("/admin/customer/edit/{id}")
    public String editCustomerAdmin(@PathVariable Long id, Model model, HttpServletRequest request) {
        Optional<Customer> customerOptional = customerRepo.findById(id);
        Customer customer = customerOptional.get();
        customer.setId(id);
        Admin admin = CreateHelper.getAdminFromSession(request, adminRepo);
        model.addAttribute("admin", admin);
        model.addAttribute("category", categoryRepo.findAll());
        model.addAttribute("customer", customer);
        return SessionHelper.redirectAdministrator(request,"Admin/editCustomerAdmin");
    }

    @PostMapping("/admin/edit/customer/")
    public String editCustomer(@ModelAttribute Customer customer) {
        customerRepo.save(customer);
        return "redirect:/admin/customers/view/";
    }

    @GetMapping("/admin/customer/delete/{id}")
    public String deleteCustomer(@PathVariable Long id) {
        customerRepo.deleteById(id);
        return "redirect:/admin/customers/view/";
    }

    @GetMapping("/admin/edit/company/{id}")
    public String editCompany(@PathVariable Long id, Model model, HttpServletRequest request) {
        Optional<Company> company = companyRepository.findById(id);
        Company c = company.get();
        c.setId(id);
        Admin admin = CreateHelper.getAdminFromSession(request, adminRepo);
        model.addAttribute("admin", admin);
        model.addAttribute("category", categoryRepo.findAll());
        model.addAttribute("company", c);
        return SessionHelper.redirectAdministrator(request,"admin/editCompanyAdmin");
    }

    @PostMapping("/admin/edit/company/")
    public String editCompany(@ModelAttribute Company company) {
        companyRepository.save(company);
        return "redirect:/admin/verify";
    }

    @GetMapping("/admin/companies/view")
    public String viewCompanys(Model model, HttpServletRequest request) {
        ArrayList<Company> companyArrayList = (ArrayList) companyRepository.findAllByIsActive(1);
        Admin admin = CreateHelper.getAdminFromSession(request, adminRepo);
        model.addAttribute("admin", admin);
        model.addAttribute("companies", companyArrayList);
        return SessionHelper.redirectAdministrator(request,"Admin/companyView");

    }

    @GetMapping("/admin/customers/view")
    public String viewCustomers(Model model, HttpServletRequest request) {
        ArrayList<Customer> customerArrayList = (ArrayList) customerRepo.findAll();
        Admin admin = CreateHelper.getAdminFromSession(request, adminRepo);
        model.addAttribute("admin", admin);
        model.addAttribute("customers", customerArrayList);
        return SessionHelper.redirectAdministrator(request,"Admin/customerView");

    }

}
