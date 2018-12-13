package mandatory.two.helper;

import mandatory.two.model.Company;
import mandatory.two.model.Customer;
import mandatory.two.model.User;
import mandatory.two.repository.CompanyRepository;
import mandatory.two.repository.CustomerRepository;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.Optional;

public class CreateHelper {

    public static boolean checkIfEmailNotExists(ArrayList<User> userArrayList) {
        if (userArrayList.size() >= 1) {
            return false;
        } else {
            return true;
        }
    }

    public static Company getCompanyFromSession(HttpServletRequest request, CompanyRepository companyRepo) {
        Company company = null;
        if (SessionHelper.isCompany(request)) {
            HttpSession session = request.getSession();
            Company companySession = (Company) session.getAttribute("company");

            Optional<Company> companyOptional = companyRepo.findById(companySession.getId());
            company = companyOptional.get();
            company.setId(companySession.getId());
        }
        return company;
    }

    public static Customer getCustomerFromSession(HttpServletRequest request, CustomerRepository customerRepo) {
        Customer customer = null;
        if (SessionHelper.isCustomer(request)) {

            HttpSession session = request.getSession();
            Customer customerSession = (Customer) session.getAttribute("customer");

            Optional<Customer> customerOptional = customerRepo.findById(customerSession.getId());
            customer = customerOptional.get();
            customer.setId(customerSession.getId());
        }
        return customer;
    }
}
