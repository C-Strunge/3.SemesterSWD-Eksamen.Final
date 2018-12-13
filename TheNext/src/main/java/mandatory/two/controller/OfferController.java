package mandatory.two.controller;

import mandatory.two.helper.CreateHelper;
import mandatory.two.helper.SessionHelper;
import mandatory.two.model.Company;
import mandatory.two.model.Customer;
import mandatory.two.model.Offer;
import mandatory.two.repository.CompanyRepository;
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
import javax.servlet.http.HttpSession;
import javax.swing.text.html.Option;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * Created by Matthias Skou 12/12/2018
 */

@Controller
public class OfferController {
    @Autowired
    OfferRepository offerRepo;
    @Autowired
    CompanyRepository companyRepo;
    @Autowired
    CustomerRepository customerRepo;


    @GetMapping("/offer/create")
    public String createOffer(Model model) {

        model.addAttribute("offer", new Offer());

        return "Offer/createOffer";
    }

    @PostMapping("/offer/create")
    public String createOffer(@ModelAttribute Offer offer, HttpServletRequest request) {
        offer.setActive(true);
        offerRepo.save(offer);

        Company company = CreateHelper.getCompanyFromSession(request, companyRepo);

        company.addOffer(offer);
        companyRepo.save(company);

        return "";
    }

    @GetMapping("/offer")
    public String offerView(Model model, HttpServletRequest request) {

        Company company = CreateHelper.getCompanyFromSession(request, companyRepo);
        model.addAttribute("company", company);

        return "Offer/viewOffer";
    }

    @GetMapping("/offer/edit/{id}")
    public String editOffer(@PathVariable Long id, Model model) {
        Optional<Offer> offer = offerRepo.findById(id);
        Offer o = offer.get();
        o.setId(id);
        model.addAttribute("offer", o);
        return "offer/editOffer";
    }

    @PostMapping("/offer/edit")
    public String editOffer(@ModelAttribute Offer offer) {
        offerRepo.save(offer);
        return "";
    }
    @GetMapping("/offer/delete/{id}")
    public String deleteOffer(@PathVariable Long id) {
            offerRepo.deleteById(id);
        return "redirect:/offer";
    }

    @GetMapping("/offer/buy/{id}")
    public String buyOffer(@PathVariable Long id, Model model){
        Optional<Offer> offerOptional = offerRepo.findById(id);
        Offer offer = offerOptional.get();
        model.addAttribute("offer", offer);
        return "Offer/buyOffer";
    }
    @PostMapping("/offer/buy")
    public String buyOffer(@ModelAttribute Offer offer, HttpServletRequest request) {
        offerRepo.save(offer);
        System.out.println("before if");
        if (SessionHelper.isCustomer(request)) {
            System.out.println("after if");
            HttpSession session = request.getSession();
            Customer customerSession = (Customer) session.getAttribute("customer");

            Optional<Customer> customerOptional = customerRepo.findById(customerSession.getId());
            Customer customer = customerOptional.get();
            customer.setId(customerSession.getId());

            System.out.println("Customer id: " + customer.getId());
            System.out.println("Offer id: " + offer.getId());
            customer.addOffer(offer);
            customerRepo.save(customer);
        }
        return "";
    }


}
