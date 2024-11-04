package com.adil.member_service.exception;

public class MemberAlreadyExistsException extends RuntimeException {

    public MemberAlreadyExistsException(String email) {
        super("Un membre avec cet email existe déjà: " + email);
    }
}