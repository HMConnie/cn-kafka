package cn.kafka.common.mq;

import com.sgcai.commons.lang.base.BasicTO;

public class CustomMQ extends BasicTO {

    private String content;

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }


    @Override
    public String toString() {
        return "CustomMQ{" +
                "content='" + content + '\'' +
                '}';
    }
}
