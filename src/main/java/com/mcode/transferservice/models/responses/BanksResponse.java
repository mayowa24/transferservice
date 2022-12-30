package com.mcode.transferservice.models.responses;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class BanksResponse {
    private String code;
    private String bankName;
    private String longCode;

}
