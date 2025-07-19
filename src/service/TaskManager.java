package service;

import model.Task;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class TaskManager {
    private final String FILE_PATH = "data/tasks_data.txt";
    private final List<Task> tasks = new ArrayList<>();

    public TaskManager() {
        loadTasks();
    }

    public void addTask(Task task) {
        tasks.add(task);
        saveTasks();
    }

    public List<Task> getAllTasks() {
        return tasks;
    }

    public void saveTasks() {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(FILE_PATH))) {
            for (Task t : tasks) {
                writer.write(t.toFileString());
                writer.newLine();
            }
        } catch (IOException e) {
            System.out.println("Error saving tasks: " + e.getMessage());
        }
    }

    private void loadTasks() {
        tasks.clear();
        File file = new File(FILE_PATH);
        if (!file.exists()) return;
        try (BufferedReader reader = new BufferedReader(new FileReader(FILE_PATH))) {
            String line;
            while ((line = reader.readLine()) != null) {
                Task task = Task.fromFileString(line);
                if (task != null) tasks.add(task);
            }
        } catch (IOException e) {
            System.out.println("Error loading tasks: " + e.getMessage());
        }
    }
}
