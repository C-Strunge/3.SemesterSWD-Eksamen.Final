package mandatory.two.controller;

import mandatory.two.helper.CreateHelper;
import mandatory.two.helper.SessionHelper;
import mandatory.two.model.Company;
import mandatory.two.model.Offer;
import mandatory.two.repository.CompanyRepository;
import mandatory.two.repository.OfferRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
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
}
