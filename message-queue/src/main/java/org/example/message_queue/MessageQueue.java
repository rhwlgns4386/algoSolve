package org.example.message_queue;

import org.example.message_queue.dto.Message;

public interface MessageQueue {
    Message take();

    void add(Message dto);
}
