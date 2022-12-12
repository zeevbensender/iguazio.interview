package com.interview.iguazio.services;

public class Message {
    private final String text;
    private final String url;
    private final boolean poisoned;
    private final String threadName;
    private final int status;

    public Message(String text, String url, String threadName, int status) {
        this.text = text;
        this.url = url;
        this.threadName = threadName;
        this.status = status;
        poisoned = false;
    }

    private Message() {
        this.poisoned = true;
        text = null;
        url = null;
        threadName = null;
        status = -1;
    }

    public String getText() {
        return text;
    }

    public String getThreadName() {
        return threadName;
    }

    public int getStatus() {
        return status;
    }

    public String getUrl() {
        return url;
    }

    public boolean isPoisoned() {
        return poisoned;
    }

    public static Message poisonedPill() {
        return new Message();
    }

    @Override
    public String toString() {
        return "Message{" +
                "wordsCount='" + text.split(" ").length + '\'' +
                ", url='" + url + '\'' +
                ", threadName='" + threadName + '\'' +
                '}';
    }
}
