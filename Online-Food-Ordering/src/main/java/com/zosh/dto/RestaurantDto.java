package com.zosh.dto;


import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import jakarta.persistence.Entity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Embeddable
@Data
public class RestaurantDto {

    private String titles;


    @Column(length = 1000)
    private List<String> images;

    private String description;

    private Long id;


}
