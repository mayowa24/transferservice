package com.mcode.transferservice.paystack.models.responses;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class PaystackBanksResponse {
    private boolean status;
    private String message;
    private List<Data> data;

    @NoArgsConstructor
    @AllArgsConstructor
    @Getter
    @Setter
    public static class Data{
        private float id;
        private String name;
        private String slug;
        private String code;
        private String longcode;
        private String gateway;
        private boolean pay_with_bank;
        private boolean active;
        private String country;
        private String currency;
        private String type;
        private boolean is_deleted;
        private String createdAt;
        private String updatedAt;
    }
}
