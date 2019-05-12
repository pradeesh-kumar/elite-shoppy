/* Redirect other than Seller and Admin user*/
redirectByRole([ "Seller", "Admin" ]);

function productSearch() {
	var input, filter, table, tr, td, i, txtValue;
	
	input = document.getElementById("searchInput");
	filter = input.value.toUpperCase();
	table = document.getElementById("productTable");
	tr = table.getElementsByTagName("tr");
	for (i = 0; i < tr.length; i++) {
		td = tr[i].getElementsByTagName("td")[0];
		if (td) {
			txtValue = td.textContent || td.innerText;
			if (txtValue.toUpperCase().indexOf(filter) > -1) {
				tr[i].style.display = "";
			} else {
				tr[i].style.display = "none";
			}
		}
	}
}

function fetchProducts() {
	$.ajax({
		'url' : PRODUCT_LOADBYOWNER + authUser.id,
		'type' : 'GET',
		'headers' : {
			'Authorization' : 'bearer ' + localStorage.getItem("access_token")
		},
		'success' : function(response) {
			$("#noproductlbl").hide();
			$("#productLoadError").hide();
			loadProducts(response);
		},
		'error' : function(response) {
			/* Unauthenticated */
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
						fetchProducts();
					},
					'error': function(response) {
						console.log("Error: " + response);
						$("#noproductlbl").hide();
						$("#productLoadError").removeClass("hide").addClass("show").text("Something went wrong while loading the products! Please try again later.");
					}
				});
			} else if(response.status == 404) {
				$("#productTable").hide();
				$("#productLoadError").hide();
				$("#noproductlbl").removeClass("hide").addClass("show").text("No Products found!");
			} else {
				console.log("Error occured while Loading products! " + response);
				$("#noproductlbl").hide();
				$("#productLoadError").removeClass("hide").addClass("show").text("Something went wrong while loading the products! Please try again later.");
			}
		}
	});
}

function addProductRow(p) {
	var row = $("<tr>", {
		'id': p.id
	});
	row.append('<td>' + p.name + '</td>');
	row.append('<td>' + p.category + '</td>');
	row.append('<td>' + p.availableQuantity + '</td>');
	row.append('<td>' + p.price + '</td>');
	row.append('<td>' + formatDate(p.createdDate) + '</td>');
	row.append('<td>' + formatDate(p.updatedDate) + '</td>');
	
	var btns = '<td><button class="btn btn-xs btn-danger" onclick="deleteProduct(this)" title="Remove this product"><b class="glyphicon glyphicon-remove-circle"></b></button>';
	if (p.status) {
		btns += ' <button class="btn btn-xs btn-warning" onclick="disableProduct(this)" title="Disable this product"><b class="fa fa-times"></b></button>';
	} else {
		btns += ' <button class="btn btn-xs btn-success" onclick="enableProduct(this)" title="Enable this product"><b class="fa fa-check-circle"></b></button>';
	}
	btns += ' <button class="btn btn-xs btn-default" onclick="editProduct(this)" title="Edit this product"><b class="fa fa-edit"></b></button></td>';
	row.append(btns);
	$("#prodBody").append(row);
}

function loadProducts(products) {
	if (products.length == 0) {
		$("#productTable").hide();
		$("#productLoadError").hide();
		$("#noproductlbl").removeClass("hide").addClass("show").text("No Products found!");
	} else {
		$("#productTable").show();
		products.forEach(function(p) {
			addProductRow(p);
		});
	}
}

function enableProduct(ctr) {
	productId = ctr.parentElement.parentElement.id;
	updateStatus(productId, true);
}

function disableProduct(ctr) {
	productId = ctr.parentElement.parentElement.id;
	updateStatus(productId, false);
}

function updateStatus(productId, status) {
	$.ajax({
		'url' : UPDATE_PRODUCT_STATUS + productId + "/" + status,
		'type' : 'PUT',
		'headers' : {
			'Authorization' : 'bearer ' + localStorage.getItem("access_token")
		},
		'success' : function(response) {
			$("#productLoadError").hide();
			$("#success").removeClass("hide").text("Product has been updated.");
		},
		'error' : function(response) {
			/* Unauthenticated */
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
						updateStatus(productId, status);
					},
					'error': function(response) {
						console.log("Error: " + response);
						$("#success").hide();
						$("#productLoadError").removeClass("hide").addClass("show").text("Something went wrong! Please try again later.");
					}
				});
			} else if(response.status == 404) {
				$("#success").hide();
				$("#productLoadError").removeClass("hide").addClass("show").text("Something went wrong! Please try again later.");
			} else {
				console.log("Error occured while Loading products! " + response);
				$("#success").hide();
				$("#productLoadError").removeClass("hide").addClass("show").text("Something went wrong! Please try again later.");
			}
		}
	});
}

$(document).ready(function() {
	fetchProducts();
});