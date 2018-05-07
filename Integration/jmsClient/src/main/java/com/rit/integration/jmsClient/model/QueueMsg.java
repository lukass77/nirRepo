package com.rit.integration.jmsClient.model;

/**
 * Created by nirbo on 11/24/2015.
 */
@Deprecated
public class QueueMsg {

    private String name;
    private int id;

    public QueueMsg() {
    }

    public QueueMsg(int s, String name) {
        this.id = s;
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
