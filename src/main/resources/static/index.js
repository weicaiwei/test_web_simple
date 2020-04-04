const clickGet = () => {
    server.get("serverTest", {"name": "caiwei"})
        .then((data) => {
            alert(data.name);
        })
        .catch((e) => {
            console.log("服务器异常", e);
        });
};

const clickPost = () => {
    let requestBody = {
        name: "caiwei",
        other: {
            age: 28,
            love: ["吃饭", "拉屎"]
        }
    };
    server.post("serverTest", requestBody)
        .then((data) => {
            alert(JSON.stringify(data));
        })
        .catch(() => {
            console.log("服务器异常", event);
        });


};

const createQRCode = () => {
    const imgElement = document.createElement("img");
    imgElement.src = "/qrCode/create";
    imgElement.setAttribute("alt", "");
    const bodyElement = document.getElementsByTagName("body")[0];
    bodyElement.appendChild(imgElement);
};