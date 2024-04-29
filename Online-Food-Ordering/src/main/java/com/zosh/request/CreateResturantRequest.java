package com.zosh.request;

import com.zosh.model.Address;
import com.zosh.model.ContactInformation;
import lombok.Data;

import java.util.List;

@Data
public class CreateResturantRequest {

    private Long id;
    private String name;
    private String description;
    private String cuisineType;
    private Address address;
    private ContactInformation contactInformation;
    private String openingHours;
    private List<String> images;

}
