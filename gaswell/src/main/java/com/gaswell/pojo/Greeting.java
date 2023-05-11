package com.gaswell.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author Lei Wang
 * @Date: 2021/12/18/ 15:50
 * @Blog leiwang.xyz
 * @Email ileiwang@live.com
 */
// 服务器发送消息体，用于WebSocket
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Greeting {
    private String content;
}
