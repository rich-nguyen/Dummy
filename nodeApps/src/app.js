/*
* Run "npm install" on this directory to get a node_modules dir,
* then "node app.js" to run the app.
*/

var FiniteStateMachine = require("./modules/finite-state-machine");

/* State(S) x Event(E) -> Actions (A), State(S')
*
* These relations are interpreted as meaning:
* If we are in state S and the event E occurs, we should perform the actions A and make a transition to the state S'.
*/

var machine = new FiniteStateMachine({
	initial: "state A",

	transitions: [
		{from: "state A", event: "trigger A", to: "state B"},
		{from: "state B", event: "trigger B", to: "state A"},
	],

	actions: {
		'trigger A': function(){ console.log('trigger A fired'); },
		'trigger B': function(){ console.log('trigger B fired'); }
	}
});

// Should initially be state A.
console.log("Expect initial state to be state A : " + machine.getCurrentState());

// Should do nothing.
machine.sendEvent("trigger B");
console.log("Expect invalid event should be state A : " + machine.getCurrentState());

// Should do something.
machine.sendEvent("trigger A");
console.log("Expect valid event should be state B : " + machine.getCurrentState());

// Should do nothing.
machine.sendEvent("trigger A");
console.log("Expect invalid event should be state B : " + machine.getCurrentState());

// Should do something.
machine.sendEvent("trigger B");
console.log("Expect valid event should be state A : " + machine.getCurrentState());

// Null machine.
var nullMachine = new FiniteStateMachine({});
console.log("Expect empty machine to be empty : " + nullMachine.getCurrentState());

// Self-transition machine.
var selfieCount = 0;
var selfie = new FiniteStateMachine({
	initial: "state A",

	transitions: [
		{from: "state A", event: "selfie event", to: "state A"}
	],

	actions: {
		'selfie event': function(){selfieCount++;}
	},
});
selfie.sendEvent("selfie event");
selfie.sendEvent("selfie event");
selfie.sendEvent("selfie event");
selfie.sendEvent("selfie event");
console.log("Expect self-transition to work 4 times: " + selfieCount);

console.log("-- end --");
