package com.softwaremill.app.api.model;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.softwaremill.app.model.Shot;
import com.softwaremill.app.model.ShotResult;
import com.softwaremill.app.model.board.ShipType;
import com.softwaremill.app.service.ShotResponseSerializer;
import lombok.Getter;

@Getter
@JsonSerialize(using = ShotResponseSerializer.class)
public class ShotResponse {
    private final Shot result;
    private ShipType shipType;
    private boolean sunken;

    public ShotResponse(Shot shotResult) {
        this.result = shotResult;
    }

    public ShotResponse(Shot shot, ShipType shipType, boolean sunken) {
        this.result = shot;
        this.shipType = shipType;
        this.sunken = sunken;
    }

    public static ShotResponse from(ShotResult result) {
        return new ShotResponse(result.getResult(), result.getShipType(), result.isSunken());
    }
}
