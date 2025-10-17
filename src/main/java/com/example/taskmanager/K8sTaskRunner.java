package com.example.taskmanager;

import io.kubernetes.client.openapi.ApiClient;
import io.kubernetes.client.openapi.ApiException;
import io.kubernetes.client.openapi.apis.CoreV1Api;
import io.kubernetes.client.openapi.models.*;
import io.kubernetes.client.util.Config;
import org.springframework.stereotype.Component;

@Component
public class K8sTaskRunner {

    public String runCommandInPod(String command) {
        try {
            ApiClient client = Config.defaultClient();
            CoreV1Api api = new CoreV1Api(client);

            String podName = "task-" + System.currentTimeMillis();

            V1Pod pod = new V1Pod()
                    .metadata(new V1ObjectMeta().name(podName).namespace("default"))
                    .spec(new V1PodSpec()
                            .containers(java.util.Collections.singletonList(
                                    new V1Container()
                                            .name("busybox")
                                            .image("busybox")
                                            .command(java.util.Arrays.asList("sh","-c", command))
                            ))
                            .restartPolicy("Never")
                    );

            // FIX: Use "default" namespace explicitly
            api.createNamespacedPod("default", pod, null, null, null, null);

            return "Pod " + podName + " created to run command: " + command;

        } catch (ApiException | java.io.IOException e) {
            e.printStackTrace();
            return "Error: " + e.getMessage();
        }
    }
}
