package com.lamp.atom.service.sdk.sdk;

import com.lamp.atom.service.sdk.domain.ReasonRequest;
import com.lamp.atom.service.sdk.domain.ReasonResponse;
import com.lamp.light.annotation.POST;

public interface AtomReasonService {

    @POST("/reasonService")
    public ReasonResponse reasonService(ReasonRequest request);
}
