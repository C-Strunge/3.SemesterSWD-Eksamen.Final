package mandatory.two.controller;

import mandatory.two.model.Company;
import mandatory.two.repository.CategoryRepository;
import mandatory.two.repository.CompanyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.ArrayList;

/**
 * Created by Matthias Skou 04/12/2018
 */

@Controller
public class CategoryController {
    /*

    @Autowired
    private CategoryRepository categoryRepo;
    @Autowired
    private CompanyRepository companyRepo;

    @GetMapping("/company/create/category")
    public String createCompanyCategory(Model model){

        Company company = companyRepo.findTopByOrderByIdDesc();
        System.out.println("Get 0: " + company.getId());

        model.addAttribute("category", categoryRepo.findAll());
        model.addAttribute("companyCategory", company);
        return "createCompanyCategory";
    }

    @PostMapping("/company/create/category")
    public String createCompanyCategory(@ModelAttribute Company company){
        System.out.println(company.getCompanyName() + " " + company.getId());

        companyRepo.save(company);
        return "";
    }
*/

    //testest
}
