/* 这里封装和业务相关的功能 */

(function init() {
    getEnvironment();
    getSession();

    showMyButton();
    showShareButton();
    showShareButton1();
})();

function getEnvironment() {
    Jockey.send('getEnvironment', function (env) {
        console.log(env);
    });
}

function getSession() {
    Jockey.send('getSession', function (session) {
        console.log(session);
    })
}

/**
 * 直接定义的展示一个分享按钮，同时把分享内容相关的参数传递过去
 */
function showShareButton1() {
    Jockey.send("shareButton", {
        text: '分享',
        icon: 'https://oss-cn-hangzhou.aliyuncs.com/jfzapp-static2/devenv/ad/cd796d11ec931f822453cf1bef101d65.png',
        share: {
            shareTitle: 'share title',
            shareContent: '分享的内容balabala',
            shareImage: 'https://ohpsctde4.qnssl.com/v5qr0.png',
            shareUrl: 'http://www.baidu.com'
        }
    });
}

/**
 * 展示分享按钮
 */
function showShareButton() {
    Jockey.on('shareButtonClick', function () {
        console.log('After button click');

        Jockey.send("showShare", {
            shareTitle: 'share title',
            shareContent: 'share content',
            shareImage: 'https://ohpsctde4.qnssl.com/v5qr0.png',
            shareUrl: 'http://www.baidu.com'
        });
    });

    showRightButton(1, 'share', '', 'shareButtonClick');
}

function showMyButton() {
    Jockey.on("setDiv", function () {
        document.getElementById('webDiv').style.visibility = 'hidden';
    })
    showRightButton(2, "hidden", '', 'setDiv')
}

/**
 * 展示Toolbar右上角的按钮,通用一个“addRightButton”协议，添加一个按钮后点击事件由clickListener触发，
 * 客户端需要同时实现这2个事件处理，好处是可以分离点击行为和参数
 * @param type
 * @param text
 * @param icon
 * @param clickListener
 */
function showRightButton(type, text, icon, clickListener) {
    Jockey.send('addRightButton', {
        type: type,
        text: text,
        icon: icon,
        clickListener: clickListener
    });
}