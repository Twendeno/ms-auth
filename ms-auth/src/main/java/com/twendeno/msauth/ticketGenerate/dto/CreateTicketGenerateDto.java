package com.twendeno.msauth.ticketGenerate.dto;

public record CreateTicketGenerateDto(String email, String ticketName,int count) {
    @Override
    public String ticketName() {
        return ticketName.toUpperCase();
    }

    @Override
    public int count() {
        return Math.max(count, 1);
    }
}
