package mandatory.two.controller;

import mandatory.two.helper.CreateHelper;
import mandatory.two.helper.SessionHelper;
import mandatory.two.model.Company;
import mandatory.two.model.User;
import mandatory.two.repository.CategoryRepository;
import mandatory.two.repository.CompanyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.Optional;

/**
 * Created by Matthias Skou 30/11/2018
 */


@Controller
public class CompanyController {

    @Autowired
    private CompanyRepository companyRepo;
    @Autowired
    private CategoryRepository categoryRepo;

    String error = "";

    @GetMapping("/company/create")
    public String createCompany(Model model) {
        model.addAttribute("company", new Company());
        model.addAttribute("category", categoryRepo.findAll());
        model.addAttribute("error", error);
        error = "";
        return "company/createCompany";
    }

    @PostMapping("/company/create")
    public String createCompany(@ModelAttribute Company company) {
        ArrayList<User> companyArrayList = (ArrayList) companyRepo.findAllByEmail(company.getEmail());
        if (CreateHelper.checkIfEmailNotExists(companyArrayList)) {
            company.setIsActive(0);
            companyRepo.save(company);
        } else {
            error = "Email '" + company.getEmail() +  "' already exists, please try again!";
            return "redirect:/company/create";
        }
        return "redirect:/login";
    }

    @GetMapping("/company/edit/{id}")
    public String editCompany(@PathVariable Long id, Model model, HttpServletRequest request) {
        Optional<Company> company = companyRepo.findById(id);
        Company c = company.get();
        c.setId(id);
        model.addAttribute("category", categoryRepo.findAll());
        model.addAttribute("company", c);
        return SessionHelper.redirectCompany(request, "company/editCompany");
    }

    @PostMapping("/company/edit")
    public String editCompany(@ModelAttribute Company company) {
        companyRepo.save(company);
        return "redirect:/company/frontpage";
    }

    @GetMapping("/company/statistics")
    public String offerView(Model model, HttpServletRequest request) {

        Company company = CreateHelper.getCompanyFromSession(request, companyRepo);
        model.addAttribute("company", company);

        return SessionHelper.redirectCompany(request, "Company/offerStatistics");
    }

    @GetMapping("/company/frontpage")
    public String companyFrontpage(Model model, HttpServletRequest request) {

        Company company = CreateHelper.getCompanyFromSession(request, companyRepo);
        model.addAttribute("company", company);
        return SessionHelper.redirectCompany(request, "Company/frontpageCompany");
    }

}
