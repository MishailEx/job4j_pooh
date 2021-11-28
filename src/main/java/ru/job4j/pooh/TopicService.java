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
        String status = "";
        Resp resp = null;
        if (POST.equals(req.httpRequestType())) {
            if (topics.get(req.getSourceName()) != null) {
                for (ConcurrentLinkedQueue<String> queue: topics.get(req.getSourceName()).values()) {
                    queue.add(req.getParam());
                }
            }
        }
        if (GET.equals(req.httpRequestType())) {
            status = topics.putIfAbsent(req.getSourceName(),
                    new ConcurrentHashMap<>()) == null ? "204" : "202";
            if (status.equals("202")) {
                if (topics.get(req.getSourceName()).get(req.getParam()) != null) {
                    return new Resp(topics.get(req.getSourceName()).get(req.getParam()).poll(), status);
                }
            } else {
                topics.get(req.getSourceName()).put(req.getParam(), new ConcurrentLinkedQueue<>());
                status = "201";
            }
            resp = new Resp("", status);
        }
        return resp;
    }
}