function loadNewArrival(category) {
	$.get(NEW_ARRIVALS + category, function(response) {
		console.log("New arrivals found");
		console.log(response);
	})
}

function loadNewArrivals() {
	loadNewArrival('Men');
	loadNewArrival('Women');
	loadNewArrival('Bag');
	loadNewArrival('Footwear');
}

$(document).ready(function() {
	loadNewArrivals();
});