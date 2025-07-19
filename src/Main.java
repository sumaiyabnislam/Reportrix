import model.Task;
import service.*;

import java.time.DateTimeException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        TaskManager taskManager = new TaskManager();
        MoodTracker moodTracker = new MoodTracker();
        Scheduler scheduler = new Scheduler(taskManager, moodTracker);
        ReportGenerator reportGenerator = new ReportGenerator(taskManager, moodTracker);
        ReminderService reminderService = new ReminderService();

        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.println("\n===  Reportrix ===");
            System.out.println("1. Add New Task");
            System.out.println("2. View Tasks");
            System.out.println("3. Mark Task as Done");
            System.out.println("4. Log Mood & Energy");
            System.out.println("5. View Mood History");
            System.out.println("6. View Suggested Schedule");
            System.out.println("7. Generate Daily Report");
            System.out.println("8. Send Reminder");
            System.out.println("9. Exit");
            System.out.print("Choose an option: ");
            String option = scanner.nextLine().trim();

            switch (option) {
                case "1" -> {
                    try {
                        System.out.print("Task Title: ");
                        String title = scanner.nextLine().trim();

                        System.out.print("Description: ");
                        String desc = scanner.nextLine().trim();

                        System.out.print("Priority (High/Medium/Low): ");
                        String priority = scanner.nextLine().trim();
                        if (!(priority.equalsIgnoreCase("High") || priority.equalsIgnoreCase("Medium") || priority.equalsIgnoreCase("Low"))) {
                            System.out.println("Priority must be High, Medium, or Low.");
                            break;
                        }

                        System.out.print("Estimated Time (minutes): ");
                        int minutes = Integer.parseInt(scanner.nextLine());

                        System.out.print("Due DateTime (yyyy-MM-ddTHH:mm): ");
                        LocalDateTime dueDate = LocalDateTime.parse(scanner.nextLine());

                        Task newTask = new Task(title, desc, priority, minutes, dueDate);
                        taskManager.addTask(newTask);
                        System.out.println("✅ Task added!");

                    } catch (NumberFormatException e) {
                        System.out.println(" Invalid number format. Please enter an integer for minutes.");
                    } catch (DateTimeException e) {
                        System.out.println(" Invalid date/time format. Use yyyy-MM-ddTHH:mm");
                    }
                }
                case "2" -> {
                    List<Task> tasks = taskManager.getAllTasks();
                    if (tasks.isEmpty()) {
                        System.out.println("No tasks found.");
                    } else {
                        System.out.println("\n📋 Your Tasks:");
                        for (int i = 0; i < tasks.size(); i++) {
                            System.out.printf("%d. %s\n", i + 1, tasks.get(i));
                        }
                    }
                }
                case "3" -> {
                    List<Task> tasks = taskManager.getAllTasks();
                    if (tasks.isEmpty()) {
                        System.out.println("No tasks to mark as done.");
                        break;
                    }
                    System.out.print("Enter task number to mark as done: ");
                    try {
                        int taskNum = Integer.parseInt(scanner.nextLine());
                        if (taskNum < 1 || taskNum > tasks.size()) {
                            System.out.println("❌ Invalid task number.");
                        } else {
                            tasks.get(taskNum - 1).markDone();
                            taskManager.saveTasks();
                            System.out.println(" Task marked as done!");
                        }
                    } catch (NumberFormatException e) {
                        System.out.println(" Please enter a valid integer.");
                    }
                }
                case "4" -> {
                    System.out.print("Enter your mood (e.g., Happy, Sad, Anxious): ");
                    String mood = scanner.nextLine().trim();

                    System.out.print("Enter energy level (1-10): ");
                    try {
                        int energy = Integer.parseInt(scanner.nextLine());
                        if (energy < 1 || energy > 10) {
                            System.out.println(" Energy level must be between 1 and 10.");
                            break;
                        }
                        moodTracker.logMood(mood, energy);
                        System.out.println(" Mood logged!");
                    } catch (NumberFormatException e) {
                        System.out.println(" Please enter a valid integer for energy level.");
                    }
                }
                case "5" -> {
                    List<model.MoodEntry> history = moodTracker.loadMoodHistory();
                    if (history.isEmpty()) {
                        System.out.println("No mood entries found.");
                    } else {
                        System.out.println("\n Mood History:");
                        history.forEach(System.out::println);
                    }
                }
                case "6" -> {
                    List<Task> suggested = scheduler.suggestSchedule();
                    if (suggested.isEmpty()) {
                        System.out.println("No pending tasks to schedule.");
                    } else {
                        System.out.println("\n Suggested Schedule (Pending tasks sorted by due date & priority):");
                        suggested.forEach(System.out::println);
                    }
                }
                case "7" -> reportGenerator.generateDailyReport();
                case "8" -> {
                    System.out.print("Enter reminder message: ");
                    String msg = scanner.nextLine();
                    reminderService.sendReminder(msg);
                }
                case "9" -> {
                    System.out.print("Are you sure you want to exit? (y/n): ");
                    String confirm = scanner.nextLine().trim().toLowerCase();
                    if (confirm.equals("y") || confirm.equals("yes")) {
                        System.out.println("👋 Goodbye!");
                        scanner.close();
                        System.exit(0);
                    }
                }
                default -> System.out.println("❌ Invalid option. Try again.");
            }
        }
    }
}
