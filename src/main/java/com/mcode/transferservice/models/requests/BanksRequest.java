package com.mcode.transferservice.models.requests;

import com.mcode.transferservice.enums.TransferProvider;
import lombok.Getter;
import lombok.Setter;

@Getter
//@Setter
public class BanksRequest {
    private TransferProvider provider;

}
