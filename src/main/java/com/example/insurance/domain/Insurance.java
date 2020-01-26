package com.example.insurance.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
@Builder
public class Insurance {
    private Integer id;
    private String startDate;
    private String endDate;


    public static Insurance createInstanceWithId(Integer id){
        return Insurance.builder()
                .id(id)
                .startDate(LocalDateTime.parse("2020-01-01T12:30:59.999").toString())
                .endDate(LocalDateTime.parse("2019-05-01T12:30:59.999").toString())
                .build();
    }
}
