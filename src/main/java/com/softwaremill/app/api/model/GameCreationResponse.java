package com.softwaremill.app.api.model;

import com.softwaremill.app.model.Invitation;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class GameCreationResponse {
    private final String invitationUrl;

    public static GameCreationResponse from(Invitation invitation) {
        return new GameCreationResponse(invitation.getInvitationUrl());
    }
}
