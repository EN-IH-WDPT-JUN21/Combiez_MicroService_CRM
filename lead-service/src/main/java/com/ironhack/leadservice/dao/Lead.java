package com.ironhack.leadservice.dao;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "leads")
public class Lead {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long leadId;

    @Column(name="contact_name")
    private String name; // Is private better than protected for encapsulation

    @Column(name = "phone_number")
    private String phoneNumber;

    private String email;

    @Column(name = "company_name")
    private String companyName;

    @Column(name = "sales_rep")
    private Long salesRepId;


    public Lead(String name, String phoneNumber, String email, String companyName, Long salesRepId) {
        setName(name);
        setPhoneNumber(phoneNumber);
        setEmail(email);
        setCompanyName(companyName);
        setSalesRepId(salesRepId);
    }
}
