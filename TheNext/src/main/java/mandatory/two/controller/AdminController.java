package mandatory.two.controller;

/* Created by Casper Frost
 */


import mandatory.two.model.Company;
import mandatory.two.repository.CompanyRepository;
import mandatory.two.repository.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;

import java.util.ArrayList;
import java.util.Optional;

@Controller
public class AdminController {

    @Autowired
    private CompanyRepository companyRepository;
    @Autowired
    private CustomerRepository customerRepository;

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
}
