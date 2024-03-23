package com.twendeno.msauth.shared;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.security.SecureRandom;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;

public class Utils {

    public static String getMachineInfo(){
        StringBuilder sb = new StringBuilder();
        try {
            InetAddress ip = InetAddress.getLocalHost();
            sb.append("Host Name: ").append(ip.getHostName()).append("\n");
            sb.append("IP Address: ").append(ip.getHostAddress()).append("\n");
            sb.append("Os: ").append(System.getProperty("os.name")).append("\n");

        }catch (UnknownHostException e){
            throw new RuntimeException(e);
        }

        return sb.toString();
    }

    public static String getIpAddress(){
        StringBuilder sb = new StringBuilder();
        try {
            InetAddress ip = InetAddress.getLocalHost();
            sb.append(ip.getHostAddress());

        }catch (UnknownHostException e){
            throw new RuntimeException(e);
        }

        return sb.toString();
    }

    public static String generateTicketReference() {
        SecureRandom random = new SecureRandom();
        int length = 12;
        String characters = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz#$&*!@";
        StringBuilder reference = new StringBuilder(length);

        for (int i = 0; i < length; i++) {
            reference.append(characters.charAt(random.nextInt(characters.length())));
        }

        return reference.toString();
    }
    public static String generateSubscriptionReference() {
        SecureRandom random = new SecureRandom();
        int length = 10;
        String characters = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz";
        StringBuilder reference = new StringBuilder(length);

        for (int i = 0; i < length; i++) {
            reference.append(characters.charAt(random.nextInt(characters.length())));
        }

        return reference.toString();
    }

    public static Instant convertDateToInstant(String date) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDate localDate = LocalDate.parse(date, formatter);
        return localDate.atStartOfDay(ZoneId.systemDefault()).toInstant();
    }
}
