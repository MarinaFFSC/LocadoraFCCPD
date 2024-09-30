package com.example.message;

import org.springframework.stereotype.Component;

@Component
public class AuditReceiver {

    public void auditMessage(String message) {
        System.out.println("AUDIT LOG: Received <" + message + ">");
    }
}