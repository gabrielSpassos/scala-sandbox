package com.gabrielspassos.contracts.v1;

import com.gabrielspassos.contracts.v1.request.BankRequest;
import com.gabrielspassos.contracts.v1.response.BankResponse;

import java.util.List;

public interface BankContract {

    BankResponse createBank(BankRequest bankRequest);

    List<BankResponse> findAll();
    
    BankResponse findByCode(String code);

    BankResponse deleteByCode(String code);
    
}
