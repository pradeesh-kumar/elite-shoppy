function addItem(item, containerIndex) {
	/* Load item image */
	$.get(GET_ANY_IMAGE + item.id, function(response) {
		imgUrl = DOWNLOAD_IMAGE + response.path;
		
		var itemData = '<div class="col-md-3 product-men">';
		itemData += '<div class="men-pro-item simpleCart_shelfItem">';
		itemData += '<div class="men-thumb-item">';
		
		/* Image */
		itemData += '<img src="' + imgUrl + '" alt="" class="pro-image-front"> <img src="' + imgUrl + '" alt="" class="pro-image-back">';
		
		itemData += '<div class="men-cart-pro">';
		itemData += '<div class="inner-men-cart-pro">';
		itemData += '<a href="/single.html?productId=' + item.id + '" class="link-product-add-cart">Quick View</a></div></div>';
		itemData += '<span class="product-new-top">New</span></div>';
		itemData += '<div class="item-info-product ">';
		
		/* Product Name */
		itemData += '<h4><a th:href="@{single.html}">' + item.name.substr(0, 17) + '</a></h4>';
		/* Offer and Actual Price */
		itemData += '<div class="info-product-price"><span class="item_price">&#8377;' + item.offerPrice + '</span><del>&#8377;' + item.price +'</del></div>';
		
		itemData += '<div class="snipcart-details top_brand_home_details item_add single-item hvr-outline-out button2">';
		itemData += '<form action="#" method="post">';
		itemData += '<fieldset>';
		itemData += '<input type="hidden" name="cmd" value="_cart" />';
		itemData += '<input type="hidden" name="add" value="1" />';
		itemData += '<input type="hidden" name="item_name" value="' + item.name.substr(0, 17) + '" />';
		itemData += '<input type="hidden" name="amount" value="' + item.price + '" />';
		itemData += '<input type="hidden" name="discount_amount" value="' + item.offerPrice + '" />';
		itemData += '<input type="hidden" name="currency_code" value="INR" />';
		itemData += '<input type="hidden" name="return" value=" " />';
		itemData += '<input type="hidden" name="cancel_return" value=" " />';
		itemData += '<input type="submit" name="submit" value="Add to cart" class="button" />';
		itemData += '</fieldset></form></div></div></div></div>';

		$("#newArrival" + containerIndex).append(itemData);
	})
}

function loadNewArrival(category) {
	$.get(NEW_ARRIVALS + category, function(response) {
		console.log("New arrivals found for %s", category);
		console.log(response);
		$("#newArrival" + category).empty();
		var counter = 0;
		response.forEach(function(item) {
			if (counter > 7) {
				return;
			}
			addItem(item, category);
			counter++;
		})
	})
}

function loadNewArrivals() {
	loadNewArrival('Men');
	loadNewArrival('Women');
	loadNewArrival('Bags');
	loadNewArrival('Footwear');
}

$(document).ready(function() {
	loadNewArrivals();
});