package service;

import model.Task;

import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class Scheduler {
    private final TaskManager taskManager;
    private final MoodTracker moodTracker;

    public Scheduler(TaskManager taskManager, MoodTracker moodTracker) {
        this.taskManager = taskManager;
        this.moodTracker = moodTracker;
    }

    public List<Task> suggestSchedule() {
        LocalDateTime now = LocalDateTime.now();
        List<Task> pendingTasks = taskManager.getAllTasks().stream()
                .filter(task -> task.getStatus().equalsIgnoreCase("Pending"))
                .filter(task -> task.getDueDate().isAfter(now) || task.getDueDate().isEqual(now))
                .sorted(Comparator.comparing(Task::getDueDate).thenComparing(Task::getPriority))
                .collect(Collectors.toList());

        return pendingTasks;
    }
}
