package ru.job4j.pooh;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentLinkedQueue;

public class QueueService implements Service {
    private final ConcurrentHashMap<String, ConcurrentLinkedQueue<String>> queue = new ConcurrentHashMap<>();
    public static final String GET = "GET";
    public static final String POST = "POST";

    @Override
    public Resp process(Req req) {
        Resp resp = new Resp("", "204");
        queue.putIfAbsent(req.getSourceName(), new ConcurrentLinkedQueue<>());
        if (POST.equals(req.httpRequestType())) {
            queue.get(req.getSourceName()).add(req.getParam());
        } else if (GET.equals(req.httpRequestType())) {
            if (!queue.get(req.getSourceName()).isEmpty()) {
                resp = new Resp(queue.get(req.getSourceName()).poll(), "202");
            }
        }
        return resp;
    }
}