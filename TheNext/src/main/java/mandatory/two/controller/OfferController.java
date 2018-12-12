package mandatory.two.controller;

import mandatory.two.model.Offer;
import mandatory.two.repository.OfferRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

/**
 * Created by Matthias Skou 12/12/2018
 */

@Controller
public class OfferController {
    @Autowired
    OfferRepository offerRepo;


    @GetMapping("/offer/create")
    public String createOffer(Model model) {

        model.addAttribute("offer", new Offer());

        return "Offer/createOffer";
    }

    @PostMapping("/offer/create")
    public String createOffer(@ModelAttribute Offer offer) {

        offerRepo.save(offer);

        return "";
    }

}
