package service;

import model.MoodEntry;

import java.io.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class MoodTracker {
    private final String FILE_PATH = "data/mood_energy_log.txt";

    public void logMood(String mood, int energyLevel) {
        MoodEntry entry = new MoodEntry(mood, energyLevel, LocalDateTime.now());

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(FILE_PATH, true))) {
            writer.write(entry.toFileString());
            writer.newLine();
        } catch (IOException e) {
            System.out.println("Error logging mood: " + e.getMessage());
        }
    }

    public List<MoodEntry> loadMoodHistory() {
        List<MoodEntry> history = new ArrayList<>();

        File file = new File(FILE_PATH);
        if (!file.exists()) return history;

        try (BufferedReader reader = new BufferedReader(new FileReader(FILE_PATH))) {
            String line;
            while ((line = reader.readLine()) != null) {
                MoodEntry entry = MoodEntry.fromFileString(line);
                if (entry != null) history.add(entry);
            }
        } catch (IOException e) {
            System.out.println("Error reading mood history: " + e.getMessage());
        }

        return history;
    }
}
