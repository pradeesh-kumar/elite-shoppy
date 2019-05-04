var principalUser = null;

function signup() {
	document.getElementById("signupUsername").setCustomValidity("");
	document.getElementById("signuppassword").setCustomValidity("");
	
	var fullName = $("form[name='signUpForm'] input[name='fullName']").val();
	var email = $("form[name='signUpForm'] input[name='email']").val();
	var password = $("form[name='signUpForm'] input[name='password']").val();
	var password2 = $("form[name='signUpForm'] input[name='password1']").val();
	
	console.log(password);
	console.log(password2);
	if ($('#signUpForm')[0].checkValidity()) {
		if (password != password2) {
			document.getElementById("signuppassword").setCustomValidity("Passwords must match!");
			$('#signUpForm')[0].reportValidity();
		} else {
			$('#signupbtn').val("Registering...");
			$('#signupbtn').attr("disabled", true);
			
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
					requestAccessToken(email, password);
				},
				'error' : function(response) {
					if (response.status == 409) {
						document.getElementById("signupUsername").setCustomValidity("Email id already exists!");
						$('#signUpForm')[0].reportValidity();
					} else {
						showErrorModal(ERROR_MSG_SIGNUP);
					}
					console.log(response);
					$('#signupbtn').val("SIGN UP");
					$('#signupbtn').attr("disabled", false);
				}
			});
		}
	} else {
		$('#signUpForm')[0].reportValidity();
	}
}

function signin() {
	if ($('#signInForm')[0].checkValidity()) {
		$('#signinbtn').val("Signing in...");
		$('#signinbtn').attr("disabled", true);

		var email = $("form[name='signInForm'] input[name='email']").val();
		var password = $("form[name='signInForm'] input[name='password']").val();
		requestAccessToken(email, password);
	} else {
		$('#signInForm')[0].reportValidity();
	}
}

function requestAccessToken(username, password) {
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
		'success' : authSuccess,
		'error' : authError
	});
}

function authSuccess(result) {
	accessToken = result.access_token;
	refreshToken = result.refresh_token;
	expiresIn = result.expires_in;
	console.log("Login success!");
	console.log("Token: " + accessToken);
	console.log("Refresh Token: " + refreshToken);
	console.log("Expires In: " + expiresIn);
	localStorage.setItem("access_token", accessToken);
	localStorage.setItem("refresh_token", refreshToken);
	localStorage.setItem("expires_in", expiresIn);
	fetchPricipalUser();
}

function authError(XMLHttpRequest, textStatus, errorThrown) {
	$('#signinbtn').attr("disabled", false);
	$('#signinbtn').val("SIGN IN");
	console.log("Error on auth:" + errorThrown);
	console.log(XMLHttpRequest.status + ' ' + XMLHttpRequest.statusText);
	showErrorModal(ERROR_MSG_SIGNIN);
}

function fetchPricipalUser() {
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
			$('#signInModal').modal('hide');
			$('#signupModal').modal('hide');
			refreshUi();
		},
		'error' : function(XMLHttpRequest, textStatus, errorThrown) {
			console.error("Couldn't fetch principal user!");
			console.error(errorThrown);
			console.error(textStatus);
			showErrorModal(ERROR_MSG_SIGNIN);
		},
		'always': function() {
			$('#signinbtn').attr("disabled", false);
			$('#signinbtn').val("SIGN IN");
			$('#signupbtn').val("SIGN UP");
			$('#signupbtn').attr("disabled", false);
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
	refreshUi();
	return false;
}