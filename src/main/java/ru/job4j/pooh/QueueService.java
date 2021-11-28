package ru.job4j.pooh;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentLinkedQueue;

public class QueueService implements Service {
    private final ConcurrentHashMap<String, ConcurrentLinkedQueue<String>> queue = new ConcurrentHashMap<>();
    public static final String GET = "GET";
    public static final String POST = "POST";

    @Override
    public Resp process(Req req) {
        Resp resp = null;
        if (POST.equals(req.httpRequestType())) {
            queue.putIfAbsent(req.getSourceName(), new ConcurrentLinkedQueue<>());
            queue.get(req.getSourceName()).add(req.getParam());
        }
        if (GET.equals(req.httpRequestType())) {
            String status;
            status = queue.get(req.getSourceName()).isEmpty() ? "204" : "202";
            resp = new Resp(queue.get(req.getSourceName()).poll(), status);
        }
        return resp;
    }
}