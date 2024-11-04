package com.adil.member_service.exception;

public class MemberNotFoundException extends RuntimeException {

    public MemberNotFoundException(Long id) {
        super("Membre non trouvé avec l'ID: " + id);
    }
}
