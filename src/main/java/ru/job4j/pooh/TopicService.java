package ru.job4j.pooh;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentLinkedQueue;

public class TopicService implements Service {
    ConcurrentHashMap<String,
            ConcurrentHashMap<String, ConcurrentLinkedQueue<String>>> topics = new ConcurrentHashMap<>();
    public static final String GET = "GET";
    public static final String POST = "POST";

    @Override
    public Resp process(Req req) {
        Resp resp = new Resp("", "204");
        ConcurrentHashMap<String, ConcurrentLinkedQueue<String>> hashMap = topics.get(req.getSourceName());
        if (POST.equals(req.httpRequestType())) {
            if (hashMap != null) {
                for (ConcurrentLinkedQueue<String> queue : hashMap.values()) {
                    queue.add(req.getParam());
                }
            }
        } else if (GET.equals(req.httpRequestType())) {
            topics.putIfAbsent(req.getSourceName(), new ConcurrentHashMap<>());
            topics.get(req.getSourceName()).putIfAbsent(req.getParam(), new ConcurrentLinkedQueue<>());
            String answer = topics.get(req.getSourceName()).get(req.getParam()).poll();
            if (answer != null) {
                return new Resp(answer, "200");
            }
        }
        return resp;
    }
}