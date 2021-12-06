package ru.job4j.pooh;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Req {

    private final String httpRequestType;
    private final String poohMode;
    private final String sourceName;
    private final String param;

    public Req(String httpRequestType, String poohMode, String sourceName, String param) {
        this.httpRequestType = httpRequestType;
        this.poohMode = poohMode;
        this.sourceName = sourceName;
        this.param = param;
    }

    public static Req of(String content) {
        String httpRequestType = "";
        String poohMode = "";
        String sourceName = "";
        String param = "";
        Scanner scan = new Scanner(content);
        List<String> array = new ArrayList<>();
        while (scan.hasNextLine()) {
            String str = scan.nextLine();
            if (!str.isEmpty()) {
                array.add(str);
            }
        }
        if (array.size() > 0) {
            if (array.get(0).contains("POST") || array.get(0).contains("GET")) {
                String[] pars = array.get(0).split("(\\s/)|/|\\s");
                httpRequestType = pars[0];
                poohMode = pars[1];
                sourceName = pars[2];
                if (array.get(0).contains("GET")) {
                    if (pars.length == 6) {
                        param = pars[3];
                    }
                } else {
                    param = array.get(array.size() - 1);
                }
            }
        }
        return new Req(httpRequestType, poohMode, sourceName, param);
    }

    public String httpRequestType() {
        return httpRequestType;
    }

    public String getPoohMode() {
        return poohMode;
    }

    public String getSourceName() {
        return sourceName;
    }

    public String getParam() {
        return param;
    }
}