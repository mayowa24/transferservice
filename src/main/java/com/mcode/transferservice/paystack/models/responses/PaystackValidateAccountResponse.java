package com.mcode.transferservice.paystack.models.responses;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class PaystackValidateAccountResponse {
    private boolean status;
    private String message;
    private Data data;

    @NoArgsConstructor
    @AllArgsConstructor
    @Setter
    @Getter
    public static class Data{
        private String account_number;
        private String account_name;
        private int bank_id;
    }
}
