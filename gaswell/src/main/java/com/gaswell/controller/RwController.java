package com.gaswell.controller;

import io.swagger.annotations.Api;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.nio.charset.StandardCharsets;

@RestController
@RequestMapping("rw")
@Api(tags = "读写硬件变量")
public class RwController {
    @PostMapping("modify")
    public void modify() throws IOException {
        DatagramSocket socket = new DatagramSocket();
        byte[] msg = "findWork".getBytes(StandardCharsets.UTF_8);
        InetAddress inetAddress = InetAddress.getByName("192.168.35.1");
        System.out.println(inetAddress);
        int port=2000;
        DatagramPacket packet = new DatagramPacket(msg, msg.length,inetAddress,port);
        System.out.println("准备发送数据.....");
        socket.send(packet);
        System.out.println("发送到数据成功.....");
        socket.close();

    }
}
