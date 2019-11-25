var ws;

function now() {
    return new Date();
}

var heartCheck = {
    timeout: 2000,  // 60000 60ms
    timeoutObj: null,
    serverTimeoutObj: null,
    reset: function () {
        this.stop();
        this.start();
    },
    start: function () {
        this.timeoutObj = setTimeout(function () {
            ws.send("HeartBeat");
            //console.log(now(), "send hearbeat");
        }, this.timeout);
        this.serverTimeoutObj = setTimeout(function () {
            ws.close();
            // console.log(now(), "ws close !")
        }, this.timeout * 2);

    },
    stop: function () {
        if (this.timeoutObj != null)
            clearTimeout(this.timeoutObj);
        //console.log(now(), "serverTimeoutObj ->", this.serverTimeoutObj);
        if (this.serverTimeoutObj != null)
            clearTimeout(this.serverTimeoutObj);
    }
}

function setConnected(connected) {
    $("#connect").prop("disabled", connected);
    $("#disconnect").prop("disabled", !connected);
    $("#send").prop("disabled", !connected);
}

function connect() {
    try {
        ws = new WebSocket('ws://localhost:8080/springboot/myWebSocketHandler');

        ws.onmessage = function (data) {
            heartCheck.reset();
            if (data.data != "HeartBeat")
                helloWorld(data.data);

        }

        ws.onopen = function (ev) {
            setConnected(true);
            // ws.send() 登陆成功发送业务信息
            console.log("connect successfully !");
            heartCheck.start();
        }

        ws.onclose = function (ev) {
            console.log("disconnect !");
            setConnected(false);
            setTimeout(function () {
                connect();
            }, 2000);
            heartCheck.stop();
        }

        ws.onerror = function (err) {
            console.error('Socket encountered error: ', err, 'Closing socket');
            ws.close();
        }

    } catch (e) {
        console.error(e);
    }
}

function disconnect() {
    if (ws != null) {
        ws.close();
    }

}

function sendData() {
    var data = JSON.stringify({
        'user': $("#user").val()
    })
    ws.send(data);
}

function helloWorld(message) {
    $("#helloworldmessage").append("<tr><td> " + message + "</td></tr>");
}

$(function () {
    console.log(" init ");
    $("form").on('submit', function (e) {
        e.preventDefault();
    });
    $("#connect").click(function () {
        connect();
    });
    $("#disconnect").click(function () {
        disconnect();
    });
    $("#send").click(function () {
        sendData();
    });
});
