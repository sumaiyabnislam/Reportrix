package model;

import java.time.LocalDateTime;

public class MoodEntry {
    private String mood;
    private int energyLevel;
    private LocalDateTime timestamp;

    public MoodEntry(String mood, int energyLevel, LocalDateTime timestamp) {
        this.mood = mood;
        this.energyLevel = energyLevel;
        this.timestamp = timestamp;
    }

    public String getMood() { return mood; }
    public int getEnergyLevel() { return energyLevel; }
    public LocalDateTime getTimestamp() { return timestamp; }

    @Override
    public String toString() {
        return String.format("[%s] Mood: %s, Energy Level: %d", timestamp, mood, energyLevel);
    }

    public String toFileString() {
        return mood + "|" + energyLevel + "|" + timestamp;
    }

    public static MoodEntry fromFileString(String line) {
        try {
            String[] parts = line.split("\\|");
            if (parts.length != 3) return null;
            return new MoodEntry(parts[0], Integer.parseInt(parts[1]), LocalDateTime.parse(parts[2]));
        } catch (Exception e) {
            return null;
        }
    }
}
