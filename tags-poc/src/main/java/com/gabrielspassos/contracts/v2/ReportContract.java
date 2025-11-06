package com.gabrielspassos.contracts.v2;

import com.gabrielspassos.contracts.v2.response.ReportResponse;

public interface ReportContract {

    ReportResponse createReport(String externalId1);
    
}
