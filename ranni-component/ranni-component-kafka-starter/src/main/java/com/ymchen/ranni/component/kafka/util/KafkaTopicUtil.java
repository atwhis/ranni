package com.ymchen.ranni.component.kafka.util;

import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.admin.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.kafka.KafkaProperties;

import java.util.*;
import java.util.stream.Collectors;


@Slf4j
public class KafkaTopicUtil {

    @Autowired
    private KafkaProperties kafkaProperties;

    private AdminClient getAdminClient() {
        Properties properties = new Properties();
        properties.setProperty(AdminClientConfig.BOOTSTRAP_SERVERS_CONFIG, kafkaProperties.getProducer().getBootstrapServers().stream().collect(Collectors.joining(",")));
        AdminClient adminClient = AdminClient.create(properties);
        return adminClient;
    }


    public Boolean hasTopic(String topic) {
        AdminClient adminClient = getAdminClient();
        ListTopicsOptions listTopicsOptions = new ListTopicsOptions();
        //是否列出内部主题
        listTopicsOptions.listInternal(true);
        ListTopicsResult listTopicsResult = adminClient.listTopics(listTopicsOptions);
        boolean result = false;
        try {
            result = listTopicsResult.names().get().contains(topic);
        } catch (Exception ex) {
            log.error("listTopic error:{}", ex.getMessage(), ex);
        }
        adminClient.close();
        return result;
    }

    public void createTopic(String topic, int numPartitions, short replicationFactor) {
        AdminClient adminClient = getAdminClient();
        if (!hasTopic(topic)) {
            NewTopic newTopic = new NewTopic(topic, numPartitions, replicationFactor);
            List<NewTopic> topicList = Collections.singletonList(newTopic);
            CreateTopicsResult createTopicsResult = adminClient.createTopics(topicList);
            adminClient.close();
        }
    }

    public void deleteTopic(String topic) {
        AdminClient adminClient = getAdminClient();
        if (hasTopic(topic)) {
            DeleteTopicsOptions deleteTopicsOptions = new DeleteTopicsOptions();
            deleteTopicsOptions.timeoutMs(3000);
            adminClient.deleteTopics(Arrays.asList(topic), deleteTopicsOptions);
        }
        adminClient.close();
    }


    public Object getAllTopic() {
        AdminClient adminClient = getAdminClient();
        ListTopicsOptions listTopicsOptions = new ListTopicsOptions();
        listTopicsOptions.listInternal(true);
        ListTopicsResult listTopicsResult = adminClient.listTopics(listTopicsOptions);
        Set<String> result = null;
        try {
            result = listTopicsResult.names().get();
        } catch (Exception ex) {
            log.error("listTopic error:{}", ex.getMessage(), ex);
        }
        adminClient.close();
        return result;
    }


}
