package com.juliusmeinl.backend.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RecenzijaRequestDTO {
    private int value;
    private String komentar;
    private String email;
}
