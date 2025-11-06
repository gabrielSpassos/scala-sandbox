package com.gabrielspassos.contracts.v2;

import com.gabrielspassos.contracts.v2.request.UserRequest;
import com.gabrielspassos.contracts.v2.response.UserResponse;

public interface UserContract {

    UserResponse createUser(UserRequest userRequest);
    
}
