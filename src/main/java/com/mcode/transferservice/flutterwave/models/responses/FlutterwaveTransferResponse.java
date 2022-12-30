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
public class FlutterwaveTransferResponse {
    private String status;
    private String message;
    private Data data;

    @NoArgsConstructor
    @AllArgsConstructor
    @Getter
    @Setter
    public static class Data{
        private int id;
        private String account_number;
        private String bank_code;
        private String full_name;
        private String created_at;
        private String currency;
        private String debit_currency;
        private double amount;
        private float fee;
        private String status;
        private String reference;
        private String meta = null;
        private String narration;
        private String approver;
        private String complete_message;
        private int requires_approval;
        private int is_approved;
        private String bank_name;
    }

}
