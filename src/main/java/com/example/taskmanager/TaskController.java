package com.example.taskmanager;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/tasks")
public class TaskController {

    @Autowired
    private K8sTaskRunner k8sTaskRunner;

    @PostMapping("/run")
    public String runTask(@RequestParam String command) {
        return k8sTaskRunner.runCommandInPod(command);
    }

    @GetMapping
    public String hello() {
        return "Task Manager is running!";
        return "thankyou for the oppertunity";
        return "from nishad";
    }
}
