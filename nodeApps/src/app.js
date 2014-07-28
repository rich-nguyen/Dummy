/*
* Module dependencies.
* Run "npm install" on this directory to get a node_modules dir,
* then "node app.js" to run the app.
*/

var moduleA = require("./modules/moduleA");
moduleA.demoFunc();

var finiteStateMachine = require("./modules/finite-state-machine");

/* State(S) x Event(E) -> Actions (A), State(S')
*
* These relations are interpreted as meaning:
* If we are in state S and the event E occurs, we should perform the actions A and make a transition to the state S'.
*/

var machine = finiteStateMachine.create({
	initial: "state A",

	transitions: [
		{from: "state A", event: "trigger A", to: "state B"},
		{from: "state B", event: "trigger B", to: "state A"},
	],

	actions: {
		'trigger A': function(){ console.log('trigger A'); },
		'trigger B': function(){ console.log('trigger B'); }
	}
});

// Should initially be state A.
console.log("Expect initial state to be state A : " + machine.currentState());

// Should do nothing.
machine.sendEvent("trigger B");
console.log("Expect invalid event should be state A : " + machine.currentState());

// Should do something.
machine.sendEvent("trigger A");
console.log("Expect valid event should be state B : " + machine.currentState());

// Should do nothing.
machine.sendEvent("trigger A");
console.log("Expect invalid event should be state B : " + machine.currentState());

// Should do something.
machine.sendEvent("trigger B");
console.log("Expect valid event should be state A : " + machine.currentState());

console.log("-- end --");
