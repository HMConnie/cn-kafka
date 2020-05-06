package cn.kafka.provider.controller;

import cn.kafka.common.mq.ConsumerMessageMQ;
import cn.kafka.provider.service.SendMQService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class ProviderController {


    @Autowired
    private SendMQService sendMQService;

    @RequestMapping(value = "/send", method = RequestMethod.POST)
    @ResponseBody
    public void sendMQ(@RequestParam("content") String content) {
        sendMQService.sendMQ(new ConsumerMessageMQ(content));
    }
}
