package com.gabrielspassos.contracts;

import com.gabrielspassos.contracts.response.BankResponse;
import org.springframework.http.ResponseEntity;

public interface BankControllerContract {
    
    ResponseEntity<BankResponse> findByCode(String code);
    
}
