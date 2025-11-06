package com.gabrielspassos.contracts.v1;

import com.gabrielspassos.contracts.v1.response.ReportResponse;

public interface ReportContract {

    ReportResponse createReport(String externalId1);
    
}
