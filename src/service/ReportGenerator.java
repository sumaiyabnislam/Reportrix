package service;

import model.MoodEntry;
import model.Task;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDate;
import java.util.List;

public class ReportGenerator {
    private final TaskManager taskManager;
    private final MoodTracker moodTracker;

    public ReportGenerator(TaskManager taskManager, MoodTracker moodTracker) {
        this.taskManager = taskManager;
        this.moodTracker = moodTracker;
    }

    public void generateDailyReport() {
        LocalDate today = LocalDate.now();
        String fileName = "daily_report_" + today + ".txt";

        List<Task> allTasks = taskManager.getAllTasks();
        List<MoodEntry> moods = moodTracker.loadMoodHistory();

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(fileName))) {
            writer.write("📋 Daily Report - " + today + "\n");
            writer.write("============================================\n\n");

            // Tasks Summary
            writer.write("📌 Tasks Summary:\n");
            long completed = allTasks.stream().filter(t -> t.getStatus().equalsIgnoreCase("Done")).count();
            long pending = allTasks.stream().filter(t -> t.getStatus().equalsIgnoreCase("Pending")).count();
            writer.write("- Completed Tasks: " + completed + "\n");
            writer.write("- Pending Tasks: " + pending + "\n\n");

            // Mood Summary
            writer.write("😊 Mood Summary:\n");
            moods.stream()
                    .filter(m -> m.getTimestamp().toLocalDate().equals(today))
                    .forEach(m -> {
                        try {
                            writer.write("- " + m + "\n");
                        } catch (IOException e) {
                            throw new RuntimeException(e);
                        }
                    });
            if (moods.stream().noneMatch(m -> m.getTimestamp().toLocalDate().equals(today))) {
                writer.write("- No mood entries logged today.\n");
            }

            // Suggestions (Optional)
            writer.write("\n🔧 Suggested Improvements:\n");
            if (pending > 0) {
                writer.write("- Focus on clearing at least one pending task tomorrow.\n");
            }
            if (completed == 0) {
                writer.write("- Try breaking tasks into smaller chunks to finish them.\n");
            }
            writer.write("- Maintain a consistent mood log for better tracking.\n");

            writer.write("\n============================================\n");
            System.out.println("✅ Daily report saved to: " + fileName);

        } catch (IOException e) {
            System.out.println("Error generating report: " + e.getMessage());
        }
    }
}
