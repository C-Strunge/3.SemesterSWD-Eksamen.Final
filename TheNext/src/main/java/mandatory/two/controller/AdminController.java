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

import java.util.ArrayList;

@Controller
public class AdminController {

    @Autowired
    private CompanyRepository companyRepository;
    @Autowired
    private CustomerRepository customerRepository;

    @GetMapping("/admin/verify")
    public String verifyCompany(Model model) {
        //ArrayList<Company> verifyCompanyList = (ArrayList) companyRepository.findAllByActive(false);
        //model.addAttribute("company", verifyCompanyList);
        return "verifyCompany";
    }
}
