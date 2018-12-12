package mandatory.two.controller;

import mandatory.two.model.Offer;
import mandatory.two.repository.OfferRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.ArrayList;

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
        offer.setActive(true);
        offerRepo.save(offer);

        return "";
    }

    @GetMapping("/offer")
    public String offerView(Model model){
        ArrayList<Offer> o = (ArrayList) offerRepo.findAll();
        System.out.println("ACTIVE: " + o.get(0).getActive());
        model.addAttribute("offer", offerRepo.findAll());
        return "Offer/viewOffer";
    }

}
