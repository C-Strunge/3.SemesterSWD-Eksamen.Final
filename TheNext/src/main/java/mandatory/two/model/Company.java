package mandatory.two.model;

import javax.persistence.*;
import java.util.List;

/**
 * Created by Matthias Skou 30/11/2018
 */

@Entity
public class Company extends User {

    @ManyToMany(cascade = CascadeType.REMOVE)
    @JoinColumn(name="id")
    private List<Offer> offerList;
    private String companyName;
    private Integer isActive;
    private Integer cvr;
    private Long accountNumber;
    private Integer registrationNumber;
    @ManyToMany
    private List<Category> categoryList;

    public Company() {
    }

    public void addOffer(Offer offer){
        offerList.add(offer);
    }

    public List<Offer> getOfferList() {
        return offerList;
    }

    public Long getAccountNumber() {
        return accountNumber;
    }

    public void setAccountNumber(Long accountNumber) {
        this.accountNumber = accountNumber;
    }

    public Integer getRegistrationNumber() {
        return registrationNumber;
    }

    public void setRegistrationNumber(Integer registrationNumber) {
        this.registrationNumber = registrationNumber;
    }

    public void setOfferList(List<Offer> offerList) {
        this.offerList = offerList;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public Integer getCvr() {
        return cvr;
    }

    public void setCvr(Integer cvr) {
        this.cvr = cvr;
    }

    public List<Category> getCategoryList() {
        return categoryList;
    }

    public void setCategoryList(List<Category> categoryList) {
        this.categoryList = categoryList;
    }

    public Integer getIsActive() {
        return isActive;
    }

    public void setIsActive(Integer isActive) {
        this.isActive = isActive;
    }
}
