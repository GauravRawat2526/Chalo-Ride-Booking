package com.bookride.chalo.services;

public interface EmailSenderService {

    public void sendEmail(String toEmail , String subject , String body);
}
