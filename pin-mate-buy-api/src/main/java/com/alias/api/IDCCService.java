package com.alias.api;

import com.alias.api.response.Response;

public interface IDCCService {

    Response<Boolean> updateConfig(String key, String value);
    
}
