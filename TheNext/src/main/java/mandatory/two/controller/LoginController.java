package mandatory.two.controller;

import mandatory.two.helper.SessionHelper;
import mandatory.two.model.Admin;
import mandatory.two.model.Company;
import mandatory.two.model.Customer;
import mandatory.two.model.User;
import mandatory.two.repository.AdminRepository;
import mandatory.two.repository.CompanyRepository;
import mandatory.two.repository.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;


@Controller
public class LoginController {

    @Autowired
    private CompanyRepository companyRepo;
    @Autowired
    private CustomerRepository customerRepo;
    @Autowired
    private AdminRepository adminRepo;

    String error = "";

    @GetMapping("/")
    public String index() {
        return "redirect:/login";
    }

    @GetMapping("/login")
    public String login(Model model) {

        model.addAttribute("error", error);
        error = "";
        return "login";
    }

    @PostMapping("/login")
    public String login(@RequestParam(defaultValue = "") String email,
                        @RequestParam(defaultValue = "") String password,
                        HttpServletRequest request) {

        User user;
        HttpSession session = request.getSession();
        if (companyRepo.findByEmail(email) != null) {
            Company c = companyRepo.findByEmail(email);
            System.out.println("IS ACTIVE: " + c.getIsActive());
            if(c.getIsActive() == 0){
                error = "Your company has not been verified yet. Please check again later.";
                return "redirect:/login";
            }
            if (c.getPassword().equals(password) && c.getEmail() != null) {
                session.setAttribute("company", c);
                return "redirect:/company/frontpage";
            }
        } else if (customerRepo.findByEmail(email) != null) {
            Customer c = customerRepo.findByEmail(email);
            if (c.getPassword().equals(password) && c.getEmail() != null) {
                session.setAttribute("customer", c);
                return "redirect:/customer/offer/view";
            }
        } else if (adminRepo.findByEmail(email) != null) {
            Admin a = adminRepo.findByEmail(email);
            if (a.getPassword().equals(password) && a.getEmail() != null) {
                session.setAttribute("admin", a);
                return "redirect:/admin/verify";
            }
        }
        //return SessionHelper.loginRedirect(user);
        //System.out.println("FIRST NAME: " + user.getFirstName());
        //System.out.println("PASSWORD: " + user.getPassword());

        error = "Email or password is invalid";

        return "redirect:/login";
    }

    @GetMapping("/logout")
    public String logout(HttpServletRequest request){
        SessionHelper.logout(request);

        return "redirect:/login";
    }

}
