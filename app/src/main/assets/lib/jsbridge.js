/* 这里封装和业务相关的功能 */

(function init() {
    showShareButton();
})();

function showShareButton() {
    Jockey.on('shareButtonClick', function () {
        console.log('After button click')
        Jockey.send("showShare", {
            shareTitle: 'share title',
            shareContent:'share content',
            shareImage:'https://ohpsctde4.qnssl.com/v5qr0.png',
            shareUrl: 'http://www.baidu.com'
        });
    });

    showRightButton(1, 'share', '', 'shareButtonClick')
}

function showRightButton(type, text, icon, clickListener) {
    Jockey.send('addRightButton', {
        type: type,
        text: text,
        icon: icon,
        clickListener: clickListener.toString()
    });
}