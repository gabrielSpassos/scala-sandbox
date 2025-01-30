package com.gabrielspassos.contracts.v1;

import com.gabrielspassos.contracts.v1.request.BankRequest;
import com.gabrielspassos.contracts.v1.response.BankResponse;

public interface BankContract {

    BankResponse createBank(BankRequest bankRequest);

    BankResponse findByCode(String code);
    
}
