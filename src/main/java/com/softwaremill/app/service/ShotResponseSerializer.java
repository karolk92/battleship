package com.softwaremill.app.service;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;
import com.softwaremill.app.api.model.ShotResponse;
import com.softwaremill.app.model.Shot;

import java.io.IOException;

public class ShotResponseSerializer extends StdSerializer<ShotResponse> {

    public ShotResponseSerializer() {
        this(null);
    }

    public ShotResponseSerializer(Class<ShotResponse> t) {
        super(t);
    }

    @Override
    public void serialize(
        ShotResponse value, JsonGenerator jsonGenerator, SerializerProvider provider)
        throws IOException {

        jsonGenerator.writeStartObject();
        jsonGenerator.writeStringField("result", value.getResult().toString());

        if(Shot.HIT.equals(value.getResult()) && value.getShipType() != null ) {
            jsonGenerator.writeStringField("shipType", value.getShipType().toString());
            jsonGenerator.writeBooleanField("sunken", value.isSunken());
        }

        jsonGenerator.writeEndObject();
    }
}
