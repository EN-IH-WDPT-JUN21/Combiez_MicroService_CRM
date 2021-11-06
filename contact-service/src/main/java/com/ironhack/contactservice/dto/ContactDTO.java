package com.ironhack.contactservice.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ContactDTO {

    @NotBlank(message = "No name input. Please try again.")
    @Size(max = 43, message = "Exceeds maximum value of 43 characters. Please try again.")
    @Pattern(regexp = "^[a-zA-Z\s]+$", message = "Name cannot contain numbers. Please try again.")
    private String name;

    @NotBlank(message = "No Phone Number input. Please try again.")
    @Size(max = 20, message = "Exceeds maximum value of 20 characters. Please try again.")
    //@Pattern(regexp = "[0-9]+", message = "The phone number must only contain numbers. Please try again.") // pattern doesn't allow () + spaces commonly found in numbers
    private String phoneNumber;

    @NotBlank(message = "No email input. Please, try again.")
    @Size(max = 40, message = "Exceeds maximum value of 40 characters. Please, try again.")
    @Pattern(regexp = "^(.+)@(.+)\\.(.+)$", message = "The email should follow the format \"***@***.**\". Please, try again.")
    private String email;

    @NotBlank(message = "No company name input. Please try again.")
    @Size(max = 43, message = "Exceeds maximum value of 43 characters. Please try again.")
    private String companyName;

    private Long salesRepId;


}
