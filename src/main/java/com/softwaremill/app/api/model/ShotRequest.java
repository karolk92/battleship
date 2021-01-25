package com.softwaremill.app.api.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import lombok.Getter;

@Getter
public class ShotRequest {
    private final String position;

    @JsonCreator
    public ShotRequest(String position) {
        this.position = position;
    }
}
