var loadedProduct = null;

function addReviewToUi(review) {
	$("#reviewContainer").append('<li style="display:block;float: none !important;"><a href="javascript:void(0)">' + review.reviewerName + '</a><p>' + review.reviewMsg + '</p></li>');
}

function addReview() {
	if ($('#reviewForm')[0].checkValidity()) {
		if (localStorage.getItem("access_token") == undefined || localStorage.getItem("access_token") == '') {
			showErrorModal("You must signin to add review!");
		} else {
			var reviewMessage = $("#reviewMessage").val();
			requestPayload = {
				'productId': loadedProduct.id,
				'reviewMsg': reviewMessage
			}
			invertButton("addReviewButton", "Sending...", true);
			$.ajax({
				'url' : CREATE_REVIEW,
				'type' : 'POST',
				'contentType' : 'application/json',
				'data' : JSON.stringify(requestPayload),
				'dataType' : 'json',
				'headers' : {
					'Authorization' : 'bearer ' + localStorage.getItem("access_token")
				},
				'success' : function(result) {
					invertButton("addReviewButton", "Send", false);
					addReviewToUi(result);
				},
				'error' : function(response) {
					if (response.status == 401) {
						console.log("Access token has been expired! Trying to login with refresh token...");
						signinWithRefreshToken(localStorage.refresh_token, {
							'invalidToken': function(response) {
								console.log("Refresh token has been expired!");
								console.log("Logging out the user.");
								signout();
							},
							'success': function(response) {
								console.log("Access token successfully obtained from Refresh token: " + response);
								storeToken(response);
								fetchPricipalUser();
							},
							'error': function(response) {
								console.log("Error: " + response);
								$("#success").hide();
								$("#error").show().text("Something went wrong! Please try again later!");
							}
						});
					} else {
						showErrorModal("Something went wrong! Please try later");
					}
					invertButton("addReviewButton", "Send", false);
				}
			});
		}
	} else {
		$('#reviewForm')[0].reportValidity();
	}
}

function attachFlexImage() {
	$('.flexslider').flexslider({
		animation : "slide",
		controlNav : "thumbnails"
	});
}

function loadProduct() {
	var productId = $("#productId").val();
	$.get(GET_PRODUCT + productId, function(response) {
		loadedProduct = response;
		$("#productName").text(loadedProduct.name);
		$("#offerPrice").html('&#8377;' + loadedProduct.offerPrice);
		$("#price").html('- &#8377;' + loadedProduct.price);
		$("#description").text(loadedProduct.description);
		
		if (response.attributes.length > 0) {
			response.attributes.forEach(function(a) {
				$("#attributes").append('<li>' + a.name + ' - ' + a.values +'</li>');
			});
		}
		loadProductImages();
		loadReviews();
	});
}

function loadProductImages() {
	var productId = $("#productId").val();
	$.get(GET_IMAGES + productId, function(response) {
		if (response.length > 0) {
			response.forEach(function(i) {
				var imgUrl = DOWNLOAD_IMAGE + i.path;
				var img = '<li data-thumb="' + imgUrl + '">' 
				img += '<div class="thumb-image">';
				img += '<img src="' + imgUrl + '" data-imagezoom="true" class="img-responsive"></div>';
				$("#productImages").append(img);
			});
			attachFlexImage();
		}
	});
}

function loadReviews() {
	var productId = $("#productId").val();
	$.get(GET_REVIEWS_BY_PRODUCT_ID + productId, function(response) {
		if (response.length > 0) {
			response.forEach(function(r) {
				addReviewToUi(r);
			});
		}
	});
}

$(document).ready(function() {
	loadProduct();
});