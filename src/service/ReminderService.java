package service;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;

public class ReminderService {
    private final String FILE_PATH = "reminders.log";

    public void sendReminder(String message) {
        String reminderMessage = LocalDateTime.now() + " - " + message;

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(FILE_PATH, true))) {
            writer.write(reminderMessage);
            writer.newLine();
        } catch (IOException e) {
            System.out.println("Error writing reminder: " + e.getMessage());
        }

        // For console app, also print reminder
        System.out.println("⏰ Reminder: " + message);
    }
}
