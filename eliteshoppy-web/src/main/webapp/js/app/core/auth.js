function signin() {
	if ($('#signInForm')[0].checkValidity()) {
		email = $("form[name='signInForm'] input[name='email']").val(); 
		password = $("form[name='signInForm'] input[name='password']").val();
		$('#signinbtn').val("Signing in...");
		$('#signinbtn').attr("disabled", true);
	} else {
		$('#signInForm')[0].reportValidity();
	}
}

function requestToken(username, password) {
	
}