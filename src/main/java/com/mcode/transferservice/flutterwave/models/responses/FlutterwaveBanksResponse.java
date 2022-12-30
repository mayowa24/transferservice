package com.mcode.transferservice.flutterwave.models.responses;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;


@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class FlutterwaveBanksResponse {
    private String status;
    private String message;
    private List<Data> data;


    @NoArgsConstructor
    @AllArgsConstructor
    @Getter
    @Setter
    public static class Data{
        private int id;
        private String code;
        private String name;


    }

}
