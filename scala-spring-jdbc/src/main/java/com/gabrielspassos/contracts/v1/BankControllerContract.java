package com.gabrielspassos.contracts.v1;

import com.gabrielspassos.contracts.v1.response.BankResponse;
import com.gabrielspassos.contracts.v1.response.BaseApiResponse;

public interface BankControllerContract {

    BaseApiResponse<BankResponse> findByCode(String code);
    
}
