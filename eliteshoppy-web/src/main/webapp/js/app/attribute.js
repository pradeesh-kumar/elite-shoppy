/* Redirect On Role */
redirectByRole([ "Admin" ]);

function atrSearch() {
	var input, filter, table, tr, td, i, txtValue;
	
	input = document.getElementById("searchInput");
	filter = input.value.toUpperCase();
	table = document.getElementById("atrTable");
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

function addAttribute() {
	var row = $('<tr>');
	row.append('<td><input type="text" class="form-control unboardered" placeholder="Name" /></td>');
	var ti = $('<input />', {
	    class: 'form-control',
	    type: 'text',
	    'placeholder': 'Enter Attribute Value and then press <Enter>',
	    'data-role': 'tagsinput'
	});
	var td = $('<td>');
	td.append(ti);
	row.append(td);
	row.append('<td><input type="button" value="Save" class="btn btn-xs btn-success" onclick="saveAtr(this)" /> <input type="button" value="Delete" class="btn btn-xs btn-danger" onclick="deleteAtr(this)" /></td>');
	$("#atrBody").prepend(row);
	ti.tagsinput();
}

function saveAtr(control) {
	atrName = control.parentElement.parentElement.getElementsByTagName("input")[0].value;
	atrValues = control.parentElement.parentElement.getElementsByTagName("input")[2].value;
	
	if (atrName == '') {
		showErrorModal("Please enter Attribute name.");
	} else {
		requestPayload = {
			'name': atrName,
			'values': atrValues.split(",")
		}
		accessToken = localStorage.getItem("access_token");
		$.ajax({
			'url' : USER_UPDATE_URL,
			'type' : 'PUT',
			'contentType' : 'application/json',
			'data' : JSON.stringify(requestPayload),
			'dataType' : 'json',
			'headers' : {
				'Authorization' : 'bearer ' + accessToken
			},
			'success' : function(result) {
				$("#accountSettingsErr").hide();
				$("#accountSettingsSuccess").show().text("Account has been updated!");
				fetchPricipalUser();
				invertButton("updateBtn", "Update", false);
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
							saveAtr(control);
						},
						'error': function(response) {
							console.log("Error: " + response);
							$("#accountSettingsSuccess").hide();
							$("#accountSettingsErr").show().text("Something went wrong! Please try again later!");
						}
					});
				} else {
					console.log("Error occured while updating the account!");
					console.log(response);
					$("#accountSettingsSuccess").hide();
					$("#accountSettingsErr").show().text("Something went wrong! Please try again later!");
				}
			}
		});
	}
}

function fetchAtrs() {
	$.ajax({
		'url' : GET_ALL_ATTRIBUTES + authUser.id,
		'type' : 'GET',
		'headers' : {
			'Authorization' : 'bearer ' + localStorage.getItem("access_token")
		},
		'success' : function(response) {
			$("#atrLoadError").hide();
			loadAtrs(response);
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

function loadAtrs(products) {
	if (products.length == 0) {
		$("#atrLoadError").hide();
	} else {
		$("#atrTable").show();
	}
}

$(document).ready(function() {
	fetchAtrs();
});