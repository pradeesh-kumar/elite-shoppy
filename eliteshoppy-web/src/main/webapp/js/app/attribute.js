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
			txtValue = td.getElementsByTagName("input")[0].value;
			console.log(txtValue);
			if (txtValue.toUpperCase().indexOf(filter) > -1) {
				tr[i].style.display = "";
			} else {
				tr[i].style.display = "none";
			}
		}
	}
}

function addAttribute() {
	var row = $('<tr id="">');
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
	row.append('<td><input type="button" value="Delete" class="btn btn-xs btn-danger" onclick="deleteAtr(this)" /></td>');
	$("#atrBody").prepend(row);
	ti.tagsinput();
}

function deleteAtr(control) {
	confirmationModal("Are you sure you want to delete this attribute?", function() {
		accessToken = localStorage.getItem("access_token");
		$.ajax({
			'url' : DELETE_ATTRIBUTE + control.parentElement.parentElement.id,
			'type' : 'DELETE',
			'headers' : {
				'Authorization' : 'bearer ' + accessToken
			},
			'success' : function(result) {
				$("#error").hide();
				$("#success").removeClass("hide").text("Attribute has been deleted.");
				control.parentElement.parentElement.remove();
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
							deleteAtr(control);
						},
						'error': function(response) {
							console.log("Error: " + response);
							$("#success").hide();
							$("#error").removeClass("hide").text("Something went wrong! Please try again later!");
						}
					});
				}
			}
		});
	});
}

function saveAtr(control) {
	var atrName = control.parentElement.parentElement.getElementsByTagName("input")[0].value;
	var atrValues = control.parentElement.parentElement.getElementsByTagName("input")[2].value;
	var id = control.parentElement.parentElement.id;
	
	if (atrName == '') {
		showErrorModal("Please enter Attribute name.");
	} else {
		requestPayload = {
			'name': atrName,
			'values': atrValues.split(","),
			'id': id == '' ? null : id
		}
		method = id == '' ? 'POST' : 'PUT';
		operation = id == '' ? 'created' : 'updated';
		accessToken = localStorage.getItem("access_token");
		$.ajax({
			'url' : CREATE_ATTRIBUTE,
			'type' : 'POST',
			'contentType' : 'application/json',
			'data' : JSON.stringify(requestPayload),
			'dataType' : 'json',
			'headers' : {
				'Authorization' : 'bearer ' + accessToken
			},
			'success' : function(response) {
				$("#error").hide();
				$("#success").removeClass("hide").text("Attribute has been " + operation);
				control.parentElement.parentElement.id = response.id;
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
							saveAtr(control);
						},
						'error': function(response) {
							console.log("Error: " + response);
							$("#success").hide();
							$("#error").removeClass("hide").text("Something went wrong! Please try again later!");
						}
					});
				} else {
					console.log("Error occured while saving!");
					console.log(response);
					$("#success").hide();
					$("#error").removeClass("hide").text("Something went wrong! Please try again later!");
				}
			}
		});
	}
}

function fetchAtrs() {
	$.ajax({
		'url' : GET_ALL_ATTRIBUTES,
		'type' : 'GET',
		'headers' : {
			'Authorization' : 'bearer ' + localStorage.getItem("access_token")
		},
		'success' : function(response) {
			$("#error").hide();
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
					},
					'error': function(response) {
						console.log("Error: " + response);
						$("#error").removeClass("hide").addClass("show").text("Something went wrong while loading the products! Please try again later.");
					}
				});
			} else {
				console.log("Error occured while Loading products! " + response);
				$("#error").removeClass("hide").addClass("show").text("Something went wrong while loading the products! Please try again later.");
			}
		}
	});
}

function loadAtrs(attributes) {
	if (attributes.length == 0) {
		$("#error").hide();
	} else {
		attributes.forEach(function(a) {
			addAttribute();
			$("#atrBody tr:first-child").attr("id", a.id);
			$("#atrBody tr:first-child input")[0].value = a.name;
			a.values.forEach(function(v) {
				$("#atrBody tr:first-child input").eq(2).tagsinput('add', v);
			});
		});
	}
}

$(document).ready(function() {
	fetchAtrs();
});