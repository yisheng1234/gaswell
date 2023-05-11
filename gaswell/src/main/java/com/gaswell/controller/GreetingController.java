package com.gaswell.controller;

import com.gaswell.pojo.Greeting;
import com.gaswell.pojo.HelloMessage;
import io.swagger.annotations.Api;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.util.HtmlUtils;

/**
 * @author Lei Wang
 * @Date: 2021/12/18/ 15:52
 * @Blog leiwang.xyz
 * @Email ileiwang@live.com
 */
@Controller
// @RequestMapping("/greeting")
@Api(tags = "WebSocket通信")
public class GreetingController {
    @MessageMapping("/hello")
    @SendTo("/topic/greetings")
    public Greeting greeting(HelloMessage message) throws Exception {
        Thread.sleep(1000); // simulated delay
        return new Greeting("Hello, " + HtmlUtils.htmlEscape(message.getName()) + "!");
    }
}
