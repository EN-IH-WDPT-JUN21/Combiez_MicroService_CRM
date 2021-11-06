package com.ironhack.salesrepservice.dao;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor

@Entity
@Table(name = "sales_rep")
public class SalesRep {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull(message = "A name must be provided")
    @NotBlank(message = "A name must be provided")
    @Pattern(regexp = "[a-zA-Z\\u00C0-\\u00FF\\s]+", message = "Name can not contain numbers. Please try again.")
    @Size(max = 43, message = "Exceeds maximum value of 43 characters. Please try again.")
    private String name;

    public SalesRep(String name) {
        setName(name);
    }

}
