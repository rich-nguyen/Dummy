exports.create = function(configuration)
{
    var currentState = configuration.initial || "",
        transitions = configuration.transitions || [],
        actions = configuration.actions || {};

    function findTransition(event) {
        for (var i = 0; i < transitions.length; i++) {
            var transition = transitions[i];
            if (transition.from === currentState &&
               transition.event === event) {
                return transition;
            }
        }
        return null;
    }

    return {
    	currentState: function() { return currentState; },
    	sendEvent: function(event) {
    		var transition = findTransition(event);
    		if (transition) {
    			currentState = transition.to;
    			if (transition.event in actions) {
    				actions[transition.event]();
    			}
    		}			
    	}
    };
}
