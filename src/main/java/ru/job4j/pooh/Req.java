package ru.job4j.pooh;

import java.util.Arrays;

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
        String[] str = content.split("\\s\\n");
        if (str.length != 0) {
            if (str[0].contains("POST") || str[0].contains("GET")) {
                String[] pars = str[0].split("(\\s/)|/|\\s");
                httpRequestType = pars[0];
                poohMode = pars[1];
                sourceName = pars[2];
                if (pars.length == 6) {
                    param = pars[3];
                }
            }
            if (str[str.length - 1].contains("temperature")) {
                param = str[str.length - 1];
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

    public static void main(String[] args) {
        String ls = System.lineSeparator();
        String s =  "GET /topic/weather/client407 HTTP/1.1" + ls
                + "Host: localhost:9000" + ls
                + "User-Agent: curl/7.72.0" + ls
                + "Accept: */*" + ls + ls + ls;
        String[] str = s.split("\\s\\n");
        String[] str2 = str[0].split("(\\s/)|/|\\s");
        System.out.println(Arrays.toString(str));
        System.out.println(Arrays.toString(str2));
        System.out.println(str2.length);
    }
}