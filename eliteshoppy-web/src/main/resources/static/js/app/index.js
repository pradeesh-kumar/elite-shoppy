function loadNewArrivals(category) {
	$.get(NEW_ARRIVALS, function(response) {
		console.log("New arrivals found");
		console.log(response);
	})
}

function loadNewArrivals() {
	loadNewArrivals('Men');
	loadNewArrivals('Women');
	loadNewArrivals('Bag');
	loadNewArrivals('Footwear');
}

$(document).ready(function() {
	loadNewArrivals();
});