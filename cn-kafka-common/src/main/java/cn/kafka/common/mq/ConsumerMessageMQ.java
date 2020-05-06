package cn.kafka.common.mq;

import com.sgcai.commons.lang.base.BasicTO;

public class ConsumerMessageMQ extends BasicTO {
    private String content;

    public ConsumerMessageMQ() {
    }

    public ConsumerMessageMQ(String content) {
        this.content = content;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    @Override
    public String toString() {
        return "ConsumerMessageMQ{" +
                "content='" + content + '\'' +
                '}';
    }
}
