package com.example.message;

import org.springframework.stereotype.Component;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

@Component
public class Receiver {

    private static final String AUDIT_LOG_FILE = "audit.log"; 

    public void receiveMessage(String message) {
        System.out.println("Received <" + message + ">");
        logAudit(message); 
    }

    private void logAudit(String message) {
        try (PrintWriter writer = new PrintWriter(new FileWriter(AUDIT_LOG_FILE, true))) {
            writer.println("Mensagem recebida: " + message);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
