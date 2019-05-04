function title(str) {
	return str.charAt(0).toUpperCase() + str.slice(1).toLowerCase();
}

function refreshUi() {
	if (localStorage.principalUser == undefined) {
		$(".anonymous").show();
		$(".authorized").hide();
	} else {
		var authUser = JSON.parse(localStorage.principalUser);
		
		$(".anonymous").hide();
		$(".authorized").hide();
		$(".authorized.all").show();
		$(".authorized." + authUser.userType.toLowerCase()).show();
		$("#authUserName").text(title(authUser.fullName));
	}
}

function showErrorModal(msg) {
	$("#errorModalText").text(msg);
	$('#errorModal').modal('show');
}

$(document).ready(function() {
	refreshUi();
});