package mandatory.two.controller;

import mandatory.two.helper.SessionHelper;
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
    public String login(Model model, HttpServletRequest request) {

        HttpSession session = request.getSession();
        if (SessionHelper.isSessionValid(request)) {
            User user = (User) session.getAttribute("user");

            //return SessionHelper.loginRedirect(user);
            return "";
        }
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
            user = companyRepo.findByEmail(email);
            if (user.getPassword().equals(password) && user.getEmail() != null) {
                session.setAttribute("company", user);
                System.out.println("FIRST NAME: " + user.getFirstName());
                System.out.println("PASSWORD: " + user.getPassword());
                return "company/index";
            }
        } else if (customerRepo.findByEmail(email) != null) {
            user = customerRepo.findByEmail(email);
            if (user.getPassword().equals(password) && user.getEmail() != null) {
                session.setAttribute("customer", user);
                return "customer/index";
            }
        } else if (adminRepo.findByEmail(email) != null) {
            user = adminRepo.findByEmail(email);
            if (user.getPassword().equals(password) && user.getEmail() != null) {
                session.setAttribute("admin", user);
                return "admin/index";
            }
        }
        //return SessionHelper.loginRedirect(user);
        //System.out.println("FIRST NAME: " + user.getFirstName());
        //System.out.println("PASSWORD: " + user.getPassword());

        error = "Email or password is invalid";

        return "redirect:/login";
    }
}
