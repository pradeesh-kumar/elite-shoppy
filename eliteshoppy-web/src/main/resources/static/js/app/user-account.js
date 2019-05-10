// Change Password
function changePassword() {
	document.getElementById("password").setCustomValidity("");
	var password = $("#changePasswordForm input[name='password']").val();
	var password2 = $("#changePasswordForm input[name='password1']").val();
	
	if ($('#changePasswordForm')[0].checkValidity()) {
		console.log("hi");
		if (password != password2) {
			document.getElementById("password").setCustomValidity("Passwords must match!");
			$('#changePasswordForm')[0].reportValidity();
		} else {
			invertButton("chb", "Changing...", true);
			requestPayload = JSON.parse(localStorage.principalUser);
			requestPayload.password = password;
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
					$("#changePassErr").hide();
					$("#changePassSuccess").show().text("Password has been changed!");
					invertButton("chb", "Change Password", false);
					$('#changePasswordForm')[0].reset();
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
								$("#changePassSuccess").hide();
								$("#changePassErr").show().text("Something went wrong! Please try again later!");
							}
						});
					} else {
						console.log("Error occured while updating the account!");
						console.log(response);
						$("#changePassSuccess").hide();
						$("#changePassErr").show().text("Something went wrong! Please try again later!");
					}
					invertButton("chb", "Change Password", false);
				}
			});
		}
	} else {
		$('#changePasswordForm')[0].reportValidity();
	}
}

// Update User Account
function updateAccount() {
	var fullName = $("#accountSettingsForm input[name='fullName']").val();
	
	if ($('#accountSettingsForm')[0].checkValidity()) {
		invertButton("updateBtn", "Updating...", true);
		
		requestPayload = JSON.parse(localStorage.principalUser);
		requestPayload.fullName = fullName;
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
							fetchPricipalUser();
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
				invertButton("updateBtn", "Update", false);
			}
		});
	} else {
		$('#accountSettingsForm')[0].reportValidity();
	}
}

// Covert customer account to seller account
function convertToSeller() {
	confirmationModal("Are you sure you want to convert your account to seller account?", function() {
		invertButton("sellerBtn", "Converting...", true);
		requestPayload = JSON.parse(localStorage.principalUser);
		requestPayload.userType = "Seller"
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
				$("#sellerAccError").hide();
				$("#sellerAccSuccess").show().text("Account has been coverted!");
				invertButton("sellerBtn", "Covert my account to seller account", false);
				localStorage.setItem("principalUser", JSON.stringify(requestPayload));
				refreshUi();
			},
			'error' : function(response) {
				$("#sellerAccSuccess").hide();
				$("#sellerAccError").show().text("Something went wrong! Please try again later!");
				console.log(response);
				invertButton("sellerBtn", "Covert my account to seller account", false);
			}
		});
	});
}

$(document).ready(function() {
	$("#fullName").val(JSON.parse(localStorage.principalUser).fullName);
});

redirectUnauthenticated();