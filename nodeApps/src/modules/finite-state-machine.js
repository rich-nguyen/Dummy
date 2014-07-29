
function FiniteStateMachine(configuration) {
    this.currentState = configuration.initial || "";
    this.transitions = configuration.transitions || [];
    this.actions = configuration.actions || {};
};

FiniteStateMachine.prototype.findTransition = function(event) {
    for (var i = 0; i < this.transitions.length; i++) {
        var transition = this.transitions[i];
        if (transition.from === this.currentState &&
            transition.event === event) {
            return transition;
        }
    }
    return null;    
}

FiniteStateMachine.prototype.getCurrentState = function() {
    return this.currentState;
}

FiniteStateMachine.prototype.sendEvent = function(event) {
    var transition = this.findTransition(event);
    if (transition) {
        this.currentState = transition.to;
        if (transition.event in this.actions) {
            this.actions[transition.event]();
        }
    }
}

module.exports = FiniteStateMachine;