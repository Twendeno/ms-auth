package com.twendeno.msauth.ticket.dto;

public record UpdateTicketDto(String name, String description, float price, int duration) {
    @Override
    public String name() {
        return name.trim().toUpperCase();
    }
}
