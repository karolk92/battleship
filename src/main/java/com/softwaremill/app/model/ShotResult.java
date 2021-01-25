package com.softwaremill.app.model;

import com.softwaremill.app.model.board.ShipType;
import lombok.Getter;

@Getter
public class ShotResult {
    private final Shot result;
    private ShipType shipType;
    private boolean sunken;

    public ShotResult(Shot shotResult) {
        this.result = shotResult;
    }

    public ShotResult(Shot shot, ShipType shipType, boolean sunken) {
        this.result = shot;
        this.shipType = shipType;
        this.sunken = sunken;
    }
}
