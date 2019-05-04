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
			$('#chb').val("Changing...");
			$('#chb').attr("disabled", true);
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
					$("#changePassSuccess").show();
					$("#changePassSuccess").text("Password has been changed!");
					$('#chb').val("Change Password");
					$('#chb').attr("disabled", false);
					$('#changePasswordForm')[0].reset();
				},
				'error' : function(response) {
					$("#changePassErr").show();
					$("#changePassSuccess").hide();
					$("#changePassErr").text("Something went wrong! Please try again later!");
					console.log(response);
					$('#chb').val("Change Password");
					$('#chb').attr("disabled", false);
				}
			});
		}
	} else {
		$('#changePasswordForm')[0].reportValidity();
	}
}