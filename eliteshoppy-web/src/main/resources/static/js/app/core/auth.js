var principalUser = null;

function signup() {
	document.getElementById("signupUsername").setCustomValidity("");
	document.getElementById("signuppassword").setCustomValidity("");
	
	var fullName = $("form[name='signUpForm'] input[name='fullName']").val();
	var email = $("form[name='signUpForm'] input[name='email']").val();
	var password = $("form[name='signUpForm'] input[name='password']").val();
	var password2 = $("form[name='signUpForm'] input[name='password1']").val();
	
	if ($('#signUpForm')[0].checkValidity()) {
		if (password != password2) {
			document.getElementById("signuppassword").setCustomValidity("Passwords must match!");
			$('#signUpForm')[0].reportValidity();
		} else {
			invertButton("signupbtn", "Registering...", true);
			
			requestPayload = {
				"username": email,
				"password": password,
				"userType": 2,
				"status": true,
				"fullName": fullName
			}
			
			$.ajax({
				'url' : USER_REGISTER_URL,
				'type' : 'POST',
				'contentType' : 'application/json',
				'data' : JSON.stringify(requestPayload),
				'dataType' : 'json',
				'success' : function(result) {
					requestAccessToken(email, password, {
						'success': function(response) {
							storeToken(response);
							fetchPricipalUser({
								'success': function() {
									$('#signUpForm')[0].reset();
									$('#signupModal').modal('hide');
								},
								'error': function() {
									showErrorModal(ERROR_MSG_SIGNUP);
								},
								'complete': function() {
									invertButton("signupbtn", "SIGN UP", false);
								}
							});
						}
					});
				},
				'error' : function(response) {
					if (response.status == 409) {
						document.getElementById("signupUsername").setCustomValidity("Email id already exists!");
						$('#signUpForm')[0].reportValidity();
					} else {
						showErrorModal(ERROR_MSG_SIGNUP);
					}
					console.log(response);
					invertButton("signupbtn", "SIGN UP", false);
				}
			});
		}
	} else {
		$('#signUpForm')[0].reportValidity();
	}
}

function signin() {
	if ($('#signInForm')[0].checkValidity()) {
		invertButton("signinbtn", "Signing in...", true);
		var email = $("form[name='signInForm'] input[name='email']").val();
		var password = $("form[name='signInForm'] input[name='password']").val();
		requestAccessToken(email, password, {
			'success': function(response) {
				storeToken(response);
				fetchPricipalUser({
					'success': function() {
						$('#signInModal').modal('hide');
					},
					'error': function() {
						showErrorModal(ERROR_MSG_SIGNIN);
					},
					'complete': function() {
						$("#loginError").hide();
						invertButton("signinbtn", "SIGN IN", false);
					}
				});
			},
			'invalidCredential': function(response) {
				$("#loginError").css('display', 'block').text(ERROR_MSG_INVALID_CRED);
			},
			'error': function(response) {
				showErrorModal(ERROR_MSG_SIGNIN);
			},
			'complete': function() {
				invertButton("signinbtn", "SIGN IN", false);
			}
		});
	} else {
		$('#signInForm')[0].reportValidity();
	}
}

function signinWithRefreshToken(refreshToken, callbacks) {
	var authToken = btoa(CLIENT_ID + ":" + CLIENT_SECRET);
	var requestPayload = {
		'refresh_token' : refreshToken,
		'grant_type' : "refresh_token"
	}
	$.ajax({
		'url' : REQUEST_TOKEN_URL,
		'type' : 'POST',
		'content-Type' : 'x-www-form-urlencoded',
		'dataType' : 'json',
		'headers' : {
			'Authorization' : 'basic ' + authToken
		},
		'data' : requestPayload,
		'success' : function(response) {
			callbacks.success(response);
		},
		'error' : function(response) {
			if (response.responseText.indexOf("Invalid refresh token") != -1) {
				callbacks.invalidToken(response);
			} else {
				callbacks.error(response);
			}
		}
	});
}

function requestAccessToken(username, password, callbacks) {
	var authToken = btoa(CLIENT_ID + ":" + CLIENT_SECRET);

	var requestPayload = {
		'username' : username,
		'password' : password,
		'grant_type' : GRANT_TYPE
	}

	$.ajax({
		'url' : REQUEST_TOKEN_URL,
		'type' : 'POST',
		'content-Type' : 'x-www-form-urlencoded',
		'dataType' : 'json',
		'headers' : {
			'Authorization' : 'basic ' + authToken
		},
		'data' : requestPayload,
		'success' : function(response) {
			console.log(response);
			if (callbacks != null && callbacks.success != undefined) {
				callbacks.success(response);
			}
		},
		'error' : function(response) {
			console.error(response);
			if (callbacks != null && callbacks != undefined) {
				if (response.responseText.indexOf("Bad credentials" != -1)) {
					if (callbacks.invalidCredential != undefined) {
						callbacks.invalidCredential(response);
					}
				} else {
					if (callbacks.error != undefined) {
						callbacks.error(response);
					}
				}
			}
		}, 
		'complete': function() {
			if (callbacks != undefined && callbacks != null && callbacks.complete != undefined) {
				callbacks.complete();
			}
		}
	});
}

function storeToken(result) {
	accessToken = result.access_token;
	refreshToken = result.refresh_token;
	expiresIn = result.expires_in;
	localStorage.setItem("access_token", accessToken);
	localStorage.setItem("refresh_token", refreshToken);
	localStorage.setItem("expires_in", expiresIn);
	console.log("Login success!");
	console.log("Token: " + accessToken);
	console.log("Refresh Token: " + refreshToken);
	console.log("Expires In: " + expiresIn);
}

function authError(XMLHttpRequest, textStatus, errorThrown) {
	invertButton("signinbtn", "SIGN IN", false);
	console.log("Error on auth:" + errorThrown);
	console.log(XMLHttpRequest.status + ' ' + XMLHttpRequest.statusText);
	showErrorModal(ERROR_MSG_SIGNIN);
}

function fetchPricipalUser(callbacks) {
	accessToken = localStorage.getItem("access_token");
	$.ajax({
		'url' : FETCH_PRINCIPAL_USER_URL,
		'type' : 'GET',
		'headers' : {
			'Authorization' : 'bearer ' + accessToken
		},
		'success' : function(result) {
			principalUser = result;
			console.info("Principal User:" + principalUser);
			localStorage.setItem("principalUser", JSON.stringify(principalUser));
			refreshUi();
			if (callbacks != undefined && callbacks.success != undefined) {
				callbacks.success(result);
			}
		},
		'error' : function(response) {
			console.error("Couldn't fetch principal user!");
			console.error(response);
			if (callbacks != undefined && callbacks.error != undefined) {
				callbacks.error(response);
			}
		}
	});
}

function destroyLocalTokens() {
	localStorage.removeItem("access_token");
	localStorage.removeItem("refresh_token");
	localStorage.removeItem("expires_in");
	localStorage.removeItem("principalUser");
}

function signout() {
	destroyLocalTokens();
	window.location.href = "/index.html";
	return false;
}