package com.twendeno.msauth.ticket.dto;

public record CreateTicketDto(String name, String description, float price, int duration) {
    @Override
    public String name() {
        return name.trim().toUpperCase();
    }
}
