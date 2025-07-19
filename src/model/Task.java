package model;

import java.time.LocalDateTime;

public class Task {
    private String title;
    private String description;
    private String priority; // High, Medium, Low
    private int estimatedMinutes;
    private LocalDateTime dueDate;
    private String status; // Pending or Done

    public Task(String title, String description, String priority, int estimatedMinutes, LocalDateTime dueDate) {
        this.title = title;
        this.description = description;
        this.priority = priority;
        this.estimatedMinutes = estimatedMinutes;
        this.dueDate = dueDate;
        this.status = "Pending";
    }

    public String getTitle() { return title; }
    public String getDescription() { return description; }
    public String getPriority() { return priority; }
    public int getEstimatedMinutes() { return estimatedMinutes; }
    public LocalDateTime getDueDate() { return dueDate; }
    public String getStatus() { return status; }

    public void markDone() {
        this.status = "Done";
    }

    @Override
    public String toString() {
        return String.format("%s [Priority: %s, Estimated Time: %d mins, Due: %s, Status: %s]",
                title, priority, estimatedMinutes, dueDate, status);
    }

    // Serialization to file (simple CSV-like line)
    public String toFileString() {
        return title + "|" + description + "|" + priority + "|" + estimatedMinutes + "|" + dueDate + "|" + status;
    }

    // Deserialize from file line
    public static Task fromFileString(String line) {
        try {
            String[] parts = line.split("\\|");
            if (parts.length != 6) return null;
            Task t = new Task(parts[0], parts[1], parts[2], Integer.parseInt(parts[3]), LocalDateTime.parse(parts[4]));
            if (parts[5].equalsIgnoreCase("Done")) t.markDone();
            return t;
        } catch (Exception e) {
            return null;
        }
    }
}
