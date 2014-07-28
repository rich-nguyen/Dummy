exports.create = function(configuration)
{
	var currentState = configuration.initial,
		transitions = configuration.transitions,
		actions = configuration.actions;


    return {
    	currentState: function(){ return currentState; },
    	sendEvent: function(){}
    };
}
