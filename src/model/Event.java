package src.model;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Task implements Serializable {
    private String title;
    private String description;
    private LocalDateTime dueDate;
    private String priority;          // e.g., Low, Medium, High
    private String duration;          // e.g., 30 mins, 1 hour
    private String reminderType;      // e.g., "At time of task", "10 mins before"
    private boolean isDone;

    public Task(String title, String description, LocalDateTime dueDate,
                String priority, String duration, String reminderType) {
        this.title = title;
        this.description = description;
        this.dueDate = dueDate;
        this.priority = priority;
        this.duration = duration;
        this.reminderType = reminderType;
        this.isDone = false;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public LocalDateTime getDueDate() {
        return dueDate;
    }

    public String getFormattedDueDate() {
        if (dueDate == null) return "No Date";
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("d MMM yyyy HH:mm");
        return dueDate.format(formatter);
    }

    public String getPriority() {
        return priority;
    }

    public String getDuration() {
        return duration;
    }

    public String getReminderType() {
        return reminderType;
    }

    public boolean isDone() {
        return isDone;
    }

    public void markDone() {
        this.isDone = true;
    }

    public void markUndone() {
        this.isDone = false;
    }

    @Override
    public String toString() {
        return title + " (" + getFormattedDueDate() + ")";
    }
}
