package com.example.audit;

import org.springframework.stereotype.Component;
import java.io.FileWriter;
import java.io.IOException;

@Component
public class AuditReceiver {

    public void auditMessage(String message) {
        System.out.println("Audit Received: <" + message + ">");
        try (FileWriter writer = new FileWriter("audit.log", true)) {
            writer.write("Audit Log: " + message + "\n");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
