package com.lamp.atom.service.sdk.domain;

import lombok.Data;

import java.util.Map;

@Data
public class ReasonResponse {
    private Map<Integer, Double> idToScoreMap;
}
