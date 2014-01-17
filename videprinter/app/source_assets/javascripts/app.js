require([
    'qwery',
    'bonzo'
], function (qwery, bonzo) {

    function getRandomInt(min, max) {
      return Math.floor(Math.random() * (max - min + 1) + min);
    }

    function addLine(event) {
        var $pushedContent = bonzo.create('<span>' + event.data + '</span><br>');
        bonzo($pushedContent).addClass('pushed-content lazyloaded');
        bonzo(qwery(".cursor")).before($pushedContent);
    }

    function connect(config) {

        var chatSocket = new window.WebSocket("ws://localhost:9000/eventStream");

        var receiveEvent = function(message) {

            console.log(message)

            if (message && 'data' in message) {
                var events = (JSON.parse(message.data)).events;

                events.forEach(function (event, index) {
                    var startTime = (index * 4000) + 2000;
                    var endTime = (index * 4000) + 5000;
                    window.setTimeout( function(){addLine(event)},getRandomInt( startTime, endTime ))

                });

                // For now, just finish.
                chatSocket.close();

            } else {
                console.log('Invalid data returned from socket');
            }
        };

        var disconnectEvent = function(event) {
            chatSocket.close();
            //connect(config);
        };

        chatSocket.onmessage = receiveEvent;
        chatSocket.onerror = disconnectEvent;
        chatSocket.onclose = disconnectEvent;
    }

    connect();
});
