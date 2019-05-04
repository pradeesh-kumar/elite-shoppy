function refreshUi() {
	if (localStorage.principalUser == undefined) {
		$(".anonymous").show();
		$(".authorized").hide();
	} else {
		var authUser = localStorage.principalUser;
		
		$(".anonymous").hide();
		$(".authorized").show();
		
		$("#authUserName").text(authUser.user.firstName + " " + authUser.user.lastName);
	}
}

function showErrorModal(msg) {
	$("#errorModalText").text(msg);
	$('#errorModal').modal('show');
}

$(document).ready(function() {
	refreshUi();
});