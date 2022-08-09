package org.lr.controller.home;

import com.auth0.jwt.interfaces.Claim;
import lombok.extern.slf4j.Slf4j;
import org.lr.utils.JWTUtil;
import org.springframework.stereotype.Controller;

import javax.websocket.*;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Controller
@ServerEndpoint(value = "/noticeWebSocket/{token}")
@Slf4j
public class WebSocketController {

    private String workNumb;
    private Session session;

    private static ConcurrentHashMap<String, WebSocketController> map = new ConcurrentHashMap<>();  //key:workNumb

    @OnOpen
    public void onOpen(Session session, @PathParam("token") String token) throws Exception {
        Map<String, Claim> tokenMap = JWTUtil.verifyToken(token);
        this.session = session;
        this.workNumb = tokenMap.get("workNumb").asString();
        map.put(workNumb, this);
        log.info("用户\""+workNumb+"\"已连接websocket服务器,当前连接数:"+map.size());
    }

    @OnClose
    public void onClose() {
        map.remove(this.workNumb);
        log.info("用户\""+workNumb+"\"已从websocket服务器退出,当前连接数:"+map.size());
    }

//    @OnMessage
//    public void onMessage(String message, Session session) {}

    @OnError
    public void onError(Session session, Throwable error) {
        log.error("用户\""+workNumb+"\"的websocket连接发生故障");
    }

    private void sendMessage(String message) throws IOException {
        this.session.getBasicRemote().sendText(message);
    }

    public void sendMessageTo(String message, String... workNumb){
        sendMessageTo(message, Arrays.asList(workNumb));
    }

    public void sendMessageTo(String message, Collection<String> allReceiverId){

    }
}
