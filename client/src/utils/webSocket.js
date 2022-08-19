import ElementUI from 'element-ui';
import axios from 'axios';

function initWebSocket(e) {
    const WS_API = "ws://localhost:8080/"
    const wsUri = WS_API + "noticeWebSocket/" + e;
    this.socket = new WebSocket(wsUri) //这里面的this都指向vue
    this.socket.onopen = webSocketOnOpen;
    this.socket.onerror = webSocketOnError;
    this.socket.onmessage = webSocketOnMessage;
    this.socket.onclose = closeWebsocket;
}

function webSocketOnOpen(e) {
    console.log("connect open")
        // axios({
        //     method:'get',
        //     url:''
        // })
}

function webSocketOnError(e) {
    console.log('websocket连接发生错误')
}

function webSocketOnMessage(e) {
    const data = JSON.parse(e.data);
    console.log(data.msgType === "INFO", data.msgType === "INFO")
    if (data.msgType === "INFO") {
        ElementUI.Notification({
            title: '',
            message: data.msg,
            type: 'success',
            duration: 3000,
        });

    } else if (data.msgType === "ERROR") {
        ElementUI.Notification({
            title: '',
            message: data.msg,
            type: 'error',
            duration: 0,
        });
    }
}
// 关闭websiocket
function closeWebsocket() {
    console.log('连接已关闭...')
}

function close() {
    this.socket.close() // 关闭 websocket
    this.socket.onclose = function(e) {
        console.log(e) //监听关闭事件
        console.log('关闭')
    }
}

// function webSocketSend(agentData) {
//     this.socket.send(agentData);
// }
export default {
    initWebSocket,
    close
}