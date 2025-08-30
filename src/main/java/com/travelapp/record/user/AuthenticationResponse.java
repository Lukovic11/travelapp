package com.travelapp.record.user;

public record AuthenticationResponse(String token, UserResponseRecord user) {
}