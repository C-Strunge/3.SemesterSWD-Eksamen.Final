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

    String error = "";

    @GetMapping("/offer/create")
    public String createOffer(Model model, HttpServletRequest request) {

        Company company = CreateHelper.getCompanyFromSession(request, companyRepo);
        model.addAttribute("company", company);
        model.addAttribute("offer", new Offer());

        return "Offer/createOffer";
    }

    @PostMapping("/offer/create")
    public String createOffer(@ModelAttribute Offer offer, HttpServletRequest request) {
        offer.setActive(true);
        offer.setQuantityBought(0);
        offerRepo.save(offer);

        Company company = CreateHelper.getCompanyFromSession(request, companyRepo);

        company.addOffer(offer);
        companyRepo.save(company);

        return "";
    }

    @GetMapping("/offer")
    public String offerView(Model model, HttpServletRequest request) {

        Company company = CreateHelper.getCompanyFromSession(request, companyRepo);
        System.out.println("COMPANY ID: "  + company.getId());
        model.addAttribute("company", company);

        return "Offer/viewOffer";
    }

    @GetMapping("/offer/edit/{id}")
    public String editOffer(@PathVariable Long id, Model model, HttpServletRequest request) {
        Optional<Offer> offer = offerRepo.findById(id);
        Offer o = offer.get();
        o.setId(id);
        Company company = CreateHelper.getCompanyFromSession(request, companyRepo);
        model.addAttribute("company", company);
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
    public String buyOffer(@PathVariable Long id, Model model, HttpServletRequest request) {
        Optional<Offer> offerOptional = offerRepo.findById(id);
        Offer offer = offerOptional.get();

        Customer customer = CreateHelper.getCustomerFromSession(request, customerRepo);
        model.addAttribute("customer", customer);
        model.addAttribute("offer", offer);
        model.addAttribute("error", error);
        error = "";
        return "Offer/buyOffer";
    }

    @PostMapping("/offer/buy")
    public String buyOffer(@ModelAttribute Offer offer, HttpServletRequest request) {
        // Makes sure that quantity bought is incremented and not replaced in DB
        Optional<Offer> offerOptional = offerRepo.findById(offer.getId());
        Offer offerTemp = offerOptional.get();
        Integer quantityBought = offerTemp.getQuantityBought();
        offer.setQuantityBought(offer.getQuantityBought() + quantityBought);

        if (offer.getQuantity() - offer.getQuantityBought() < 0) {
            error = "Your desired quantity exceeded the available stock - please try again.";
            String redirect = "redirect:/offer/buy/" + offer.getId() + "";
            return redirect;
        }
        if(offer.getQuantity() - offer.getQuantityBought() == 0){
            offer.setActive(false);
        }

        offerRepo.save(offer);
        Customer customer = CreateHelper.getCustomerFromSession(request, customerRepo);

            customer.addOffer(offer);
            customerRepo.save(customer);

        return "Offer/receipt";
    }

    @GetMapping("/offer/bought")
    public String boughtOffer(Model model, HttpServletRequest request){

        Customer customer = CreateHelper.getCustomerFromSession(request, customerRepo);
        model.addAttribute("customer", customer);
        return "Offer/viewBoughtOffer";
    }

}
