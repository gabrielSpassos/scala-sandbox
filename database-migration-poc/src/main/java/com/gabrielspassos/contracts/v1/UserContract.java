package com.gabrielspassos.contracts.v1;

import com.gabrielspassos.contracts.v1.request.UserRequest;
import com.gabrielspassos.contracts.v1.response.UserResponse;

public interface UserContract {
    
    UserResponse createUser(UserRequest userRequest);
    
}
