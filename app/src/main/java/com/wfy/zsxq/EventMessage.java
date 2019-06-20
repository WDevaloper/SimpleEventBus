package com.wfy.zsxq;

/**
 * @Describe:
 * @Author: wfy
 * @Version: Create time: (wfy) on 2019/6/20 16:25
 * company :
 */
public class EventMessage {
    private String data;

    public EventMessage(String data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return "EventMessage{" +
                "data='" + data + '\'' +
                '}';
    }
}
