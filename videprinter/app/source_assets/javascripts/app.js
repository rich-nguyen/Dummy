require([
    'qwery',
    'bonzo'
], function (qwery, bonzo) {

    function connect(config) {

        var chatSocket = new window.WebSocket("ws://localhost:9000/eventStream");

        var receiveEvent = function(event) {

            if (event && 'data' in event) {
                var data = JSON.parse(event.data);

                if (data.error) {
                    chatSocket.close();
                } else {
                    var $pushedContent = bonzo.create('<div>' + data.headline + ' ' + data.url + '</div>');
                    bonzo($pushedContent).addClass('pushed-content lazyloaded');
                    common.$g(".monocolumn-wrapper").after($pushedContent);
                }
            } else {
                console.log('Invalid data returned from socket');
            }
        };

        var disconnectEvent = function(event) {
            chatSocket.close();
            connect(config);
        };

        chatSocket.onmessage = receiveEvent;
        chatSocket.onerror = disconnectEvent;
        chatSocket.onclose = disconnectEvent;
    }

    return {
        connect: connect
    };
});
