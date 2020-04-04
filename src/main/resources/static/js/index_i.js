

const TYPE_COMMAND_ROOM_ENTER = "enterRoom";
const TYPE_COMMAND_ROOM_LIST = "roomList";
const TYPE_COMMAND_DIALOGUE = "dialogue";
const TYPE_COMMAND_READY = "ready";
const TYPE_COMMAND_OFFER = "offer";
const TYPE_COMMAND_ANSWER = "answer";
const TYPE_COMMAND_CANDIDATE = "candidate";




let iceServers={
    "iceServers":[
        {"url":"stun:stun.services.mozilla.com"},
        {"url":"stun:stun.l.google.com:19302"}
    ]
};
const mediaDeviceIdArray = [];
const mediaConstraints = {
    video: {
        width: {exact: 1280},
        height: {exact: 720},
        /*facingMode: "environment"//*/
        //facingMode: "left"//doc中描述：打开面向左肩的前置摄像头，实际：会打开手机前置摄像头
        //facingMode: "right"//doc中描述：打开面向右肩的前置摄像头，实际：会打开手机前置摄像头
    },
    audio: false // true,//由于没有麦克风，所有如果请求音频，会报错，不过不会影响视频流播放
};

const mediaConstraintsForChange = {
    video: {
        deviceId:"",
        width: {exact: 1280},
        height: {exact: 720},
        /*facingMode: "environment"//*/
        //facingMode: "left"//doc中描述：打开面向左肩的前置摄像头，实际：会打开手机前置摄像头
        //facingMode: "right"//doc中描述：打开面向右肩的前置摄像头，实际：会打开手机前置摄像头
    },
    audio: false // true,//由于没有麦克风，所有如果请求音频，会报错，不过不会影响视频流播放
};

alert("mediaConstraints" + JSON.stringify(mediaConstraints));
const offerOptions = {
    iceRestart: true,
    offerToReceiveAudio: false, //true,由于没有麦克风，所有如果请求音频，会报错，不过不会影响视频流播放
    offerToReceiveVideo: true
};

let localMediaStream;

let rtcPeerConnection;


//写在这里不行，得写到promise的then里面才起作用，暂不知为何
//const localVideo = document.getElementById("localVideo");
//const remoteVideo = document.getElementById("remoteVideo");

let websocket;
let userId = randomName();
let roomId;
let caller = false;

let log;

window.onload = ()=>{
    //初始化log
    log = (message) => {
        let log = document.getElementById("logContent");
        let oneLog = document.createElement("span");
        oneLog.innerText = message;
        let br  = document.createElement("br");
        log.append(oneLog,br);
    };

    //设定随机名称
    document.getElementById("showUserId").innerText = userId;

    //遍历媒体设备
    navigator.mediaDevices.enumerateDevices()
        .then((deviceInfos) => {
            gotDevices(deviceInfos);
        });

    //初始化websocket连接
    get("/getWebSocketUrl")
        .then((data) => {
            if (!websocket) {
/*
                alert("data.url:" + data.url);
*/
                websocket = new WebSocket(data.url);
                log("websocket连接成功")
            }
            websocket.onopen = () => {
                websocket.send(JSON.stringify({command:TYPE_COMMAND_ROOM_LIST}))
            };
            websocket.onclose = () => {
                log("Connection closed.");
            };
            websocket.onerror = (event) => {
                log("WebSocket error observed:" + JSON.stringify(event));
            };
            websocket.onmessage = handleMessage;

        })
        .catch((error) => {
            alert("error:" + error);
            log("catch:" + error);
        });

    //初始化各种按钮
    document.getElementById("enterRoom").onclick = () =>{
        openLocalMedia()
            .then(() => {
                alert("成功");
            })
            .catch(() => {

                alert("失败")
            });
    };

    document.getElementById("sendMessage").onclick = () =>{
        let textMessage = document.getElementById("textMessage").value;
        websocket.send(JSON.stringify({command:TYPE_COMMAND_DIALOGUE,userId:userId,roomId : roomId, message: textMessage}));
    };

    document.getElementById("clearLog").onclick = () =>{
        let logContentDiv = document.getElementById("logContent");
        while (logContentDiv.hasChildNodes()) {
            logContentDiv.removeChild(logContentDiv.firstChild);
        }
    };

    document.getElementById("change").onclick = () =>{
        changeLocalMedia();

    };




};

const handleMessage = (event) => {
    console.log(event);
    log(event.data);
    let message = JSON.parse(event.data);
    switch (message.command) {
        case TYPE_COMMAND_ROOM_ENTER:
            if (message.message === "joined") {
                log("加入房间：" + message.roomId + "成功");
                roomId = message.roomId;
                openLocalMedia()
                    .then(() => {
                        log("打开本地音视频设备成功");
                        websocket.send(JSON.stringify({command: TYPE_COMMAND_READY, userId: userId, roomId: roomId}));
                    })
                    .catch(() => {
                        log("打开本地音视频设备失败");
                    })
            } else {
                log("创建房间：" + message.roomId + "成功");
                caller = true;
                openLocalMedia()
                    .then(() => log("打开本地音视频设备成功"))
                    .catch(() => log("打开本地音视频设备失败"));
            }

            break;
        case TYPE_COMMAND_ROOM_LIST:
            let roomList = document.getElementById("roomList");
            //这个方法会少删子节点，不知为何，用另一个方法
            /*roomList.childNodes.forEach((node) =>{
                roomList.removeChild(node);
            });*/
            //当div下还存在子节点时 循环继续
            while (roomList.hasChildNodes()) {
                roomList.removeChild(roomList.firstChild);
            }
            JSON.parse(message.message).forEach((roomId) => {
                //大厅默认已经加入，不把大厅展示在房间列表
                if( roomId !== "lobby"){
                    let item = document.createElement("div");
                    let label = document.createElement("label");
                    label.setAttribute("for", roomId);
                    label.innerText = "房间号：";
                    let span = document.createElement("span");
                    span.innerText = roomId;
                    let button = document.createElement("button");
                    button.innerText = "加入房间";
                    button.onclick = () => websocket.send(JSON.stringify({
                        command: TYPE_COMMAND_ROOM_ENTER,
                        userId: userId,
                        roomId: roomId
                    }));
                    item.append(label, span, button);
                    roomList.append(item);
                }
            });
            break;
        case TYPE_COMMAND_DIALOGUE:
            let dialogue = document.createElement("p").innerText = message.userId + ":" + message.message;
            let br = document.createElement("br");
            document.getElementById("dialogueList").append(dialogue, br);
            break;
        case TYPE_COMMAND_READY:
            if (caller) {
                //初始化一个webrtc端点
                rtcPeerConnection = new RTCPeerConnection(iceServers);
                //添加事件监听函数
                rtcPeerConnection.onicecandidate = onIceCandidate;
                rtcPeerConnection.ontrack = onTrack;

                //addStream相关的方法，已过时
                //rtcPeerConnection.addTrack(localMediaStream);
                for( const track of localMediaStream.getTracks()){
                    rtcPeerConnection.addTrack(track, localMediaStream);
                }
                rtcPeerConnection.createOffer(offerOptions)
                    .then(
                        setLocalAndOffer
                    )
                    .catch(() => {
                            log("setLocalAndOffer error:")
                        }
                    );
            }
            break;
        case TYPE_COMMAND_OFFER:
            if (!caller) {
                //初始化一个webrtc端点
                rtcPeerConnection = new RTCPeerConnection(iceServers);
                //添加事件监听函数
                rtcPeerConnection.onicecandidate = onIceCandidate;

                rtcPeerConnection.ontrack = onTrack;

                //rtcPeerConnection.addTrack(localMediaStream);
                for (const track of localMediaStream.getTracks()) {
                    rtcPeerConnection.addTrack(track, localMediaStream);
                }
                //原因：后端接口返回的数据换行采用了\r\n方式，使得json文本无法解析带换行符的内容
                //解决方法：将Json字符串中所有的\r\n转成\\r\\n
                let sdpMessage = message.message;
                sdpMessage.replace(/[\r]/g, "\\r").replace(/[\n]/g, "\\n");
                console.log(sdpMessage);
                let sdp = JSON.parse(sdpMessage).sdp;
                rtcPeerConnection.setRemoteDescription(new RTCSessionDescription(sdp))
                    .then(
                        log("setRemoteDescription 完毕")
                    );
                rtcPeerConnection.createAnswer(offerOptions)
                    .then(
                        setLocalAndAnswer
                    )
                    .catch(() => {
                            log("setLocalAndAnswer error");
                        }
                    );
            }
            break;
        case TYPE_COMMAND_ANSWER:
            //原因：后端接口返回的数据换行采用了\r\n方式，使得json文本无法解析带换行符的内容
            //解决方法：将Json字符串中所有的\r\n转成\\r\\n
            let sdpMessage = message.message;
            sdpMessage.replace(/[\r]/g, "\\r").replace(/[\n]/g, "\\n");
            console.log(sdpMessage);
            let sdp = JSON.parse(sdpMessage).sdp;
            rtcPeerConnection.setRemoteDescription(new RTCSessionDescription(sdp))
                .then(
                    log("setRemoteDescription 完毕")
                );
            break;
        case TYPE_COMMAND_CANDIDATE:
            let candidateMessage = message.message;
            console.log(candidateMessage);
            let candidate = JSON.parse(candidateMessage).candidate;
            let rtcIceCandidate = new RTCIceCandidate({
                sdpMid: candidate.sdpMid,
                sdpMLineIndex: candidate.label,
                candidate: candidate.candidate
            });
            rtcPeerConnection.addIceCandidate(rtcIceCandidate)
                .then(
                    log("addIceCandidate 完毕")
                    );
            break;
    }


};

//打开本地音视频,用promise这样在打开视频成功后，再进行下一步操作
const openLocalMedia = () => {
    return new Promise((resolve, reject) => {
        /*alert("SupportedConstraints:"+JSON.stringify(navigator.mediaDevices.getSupportedConstraints()));*/
        mediaConstraintsForChange.video.deviceId = mediaDeviceIdArray[indexForTest];
        navigator.mediaDevices.getUserMedia(mediaConstraints)
            .then((stream) => {
                //make stream available to browser console(设置不设置都没问题)
                //window.stream = mediaStream;
                //localVideo.srcObject = mediaStream;
                localMediaStream = stream;
                let localVideo = document.getElementById("localVideo");
                localVideo.srcObject = localMediaStream;
                localVideo.play();
                indexForTest++;
            })
            .then(() => resolve())
            .catch(() => reject());
    });

};

//切换本地音视频

let indexForTest = 0;
const changeLocalMedia = () => {
    if (mediaDeviceIdArray.length === indexForTest ) {
        alert("没啦");
        return;
    }
    return new Promise((resolve, reject) => {
        /*alert("SupportedConstraints:"+JSON.stringify(navigator.mediaDevices.getSupportedConstraints()));*/
        if (localMediaStream){
            localMediaStream.getTracks().forEach(track => {
                track.stop();
            });
        }
        mediaConstraintsForChange.video.deviceId = mediaDeviceIdArray[indexForTest];
        navigator.mediaDevices.getUserMedia(mediaConstraintsForChange)
            .then((stream) => {
                //make stream available to browser console(设置不设置都没问题)
                //window.stream = mediaStream;
                //localVideo.srcObject = mediaStream;
                localMediaStream = stream;
                let localVideo = document.getElementById("localVideo");
                localVideo.srcObject = localMediaStream;
                localVideo.play();
                indexForTest++;

            })
            .then(() => resolve())
            .catch(() => reject());
    });

};

const onTrack = (event) =>{
    let remoteMediaStream = event.streams[0];
    let remoteVideo = document.getElementById("remoteVideo");
    remoteVideo.srcObject = remoteMediaStream;
    remoteVideo.play();

};

const onIceCandidate = (event) =>{
    if (event.candidate) {
        log("sending ice candidate");
        websocket.send(JSON.stringify({
            command: TYPE_COMMAND_CANDIDATE,
            userId: userId,
            roomId: roomId,
            message: {
                candidate:{
                    sdpMid: event.candidate.sdpMid,
                    sdpMLineIndex: event.candidate.sdpMLineIndex,
                    candidate: event.candidate.candidate
                }
            }
        }));

    }
};

const setLocalAndOffer = (sessionDescription) => {
    rtcPeerConnection.setLocalDescription(sessionDescription)
        .then(
            log("setLocalDescription 完毕")
        );
    websocket.send(
        JSON.stringify({
            command: TYPE_COMMAND_OFFER,
            userId: userId,
            roomId: roomId,
            message: {
                sdp: sessionDescription,
            }
        })
    );
};



const setLocalAndAnswer = (sessionDescription) => {
    rtcPeerConnection.setLocalDescription(sessionDescription)
        .then(
            log("setLocalDescription 完毕")
        );
    websocket.send(
        JSON.stringify({
            command: TYPE_COMMAND_ANSWER,
            userId: userId,
            roomId: roomId,
            message: {
                sdp: sessionDescription,
            }
        })
    );
};

function gotDevices(deviceInfos) {

    //当我们拿到deviceInfo这个数组之后，就遍历这个数组中的每一项

    //而在每一项中我们又可以注册一个匿名函数去处理每一项的内容，参数就是数组中的每一项

    let deviceArray = [];
    deviceInfos.forEach(function (deviceInfo) {
        if(deviceInfo.kind === "videoinput"){
            mediaDeviceIdArray.push(deviceInfo.deviceId);
        }
        //打印设备信息
        let device =
            "kind = " + deviceInfo.kind + "; " +
            "id = " + deviceInfo.deviceId + ";"  +
            "label = " + deviceInfo.label+"; " +
            "groupId =" + deviceInfo.groupId;
        deviceArray.push(device);
    });
    alert("deviceids:" + mediaDeviceIdArray.length);
    alert(deviceArray.join("\n"));


}


