package com.opt.exception;


import static com.opt.exception.ResultCode.PARTY_NOT_FOUND;

public class NotFoundException extends CustomerException {

    public NotFoundException(Long partyId) {
        super(String.format("PartyId %s not found", partyId), PARTY_NOT_FOUND);
    }

    public NotFoundException(String message) {
        super(message, PARTY_NOT_FOUND);
    }
}
