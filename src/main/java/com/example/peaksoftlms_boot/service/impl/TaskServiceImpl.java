package com.example.peaksoftlms_boot.service.impl;

import com.example.peaksoftlms_boot.model.Lesson;
import com.example.peaksoftlms_boot.model.Task;
import com.example.peaksoftlms_boot.repository.LessonRepository;
import com.example.peaksoftlms_boot.repository.TaskRepository;
import com.example.peaksoftlms_boot.service.TaskService;
import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TaskServiceImpl implements TaskService {
    private final TaskRepository taskRepository;

    private final LessonRepository lessonRepository;

    @Override
    public List<Task> getAllTasks(Long id) {
        return taskRepository.findAllTaskByLessonId(id);
    }

    @Override
    public void addTask(Long id, Task task) {
        Lesson lesson = lessonRepository.findById(id).get();
        lesson.addTask(task);
        task.setLesson(lesson);
        taskRepository.save(task);
    }

    @Override
    public Task getTaskById(Long id) {
        return taskRepository.getById(id);
    }

    @Override
    public void updateTask(Task task, Long id) {
        Task task1 = taskRepository.findById(id).get();
        task1.setTaskName(task.getTaskName());
        task1.setTaskText(task.getTaskText());
        task1.setDeadline(task.getDeadline());
        taskRepository.save(task1);
    }

    @Override
    public void deleteTask(Long id) {
        Task task = taskRepository.findById(id).get();
        taskRepository.delete(task);
    }
}