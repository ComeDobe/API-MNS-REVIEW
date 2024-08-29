package com.dobe.locmns.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;


@Getter
@Setter
@AllArgsConstructor

public class ExtendLoanRequest {
    private LocalDate nouvelleDateFin;
    private String pretDescription;

}
