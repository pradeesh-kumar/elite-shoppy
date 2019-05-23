var authUser = null;

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
		$("#userType").text(" (" + authUser.userType + ")");
	}
}

function showErrorModal(msg) {
	$("#errorModalText").text(msg);
	$('#errorModal').modal('show');
}

function confirmationModal(modalText, confirmFunction) {
	$("#modalConfirmText").text(modalText);
	$('#confirmModal').modal('show');
	$('#confirmModal').modal({
		backdrop : 'static',
		keyboard : false
	}).off('click', '#modalConfirmBtn').on('click', '#modalConfirmBtn', function(e) {
		confirmFunction();
	});
}

function redirectUnauthenticated() {
	if (localStorage.principalUser == undefined) {
		window.location.href = "/index.html";
	}
}

function redirectByRole(roles) {
	if (localStorage.principalUser == undefined || roles.indexOf(JSON.parse(localStorage.principalUser).userType) == -1) {
		window.location.href = "/index.html";
	}
}

function invertButton(btnId, value, disabled) {
	$("#" + btnId).val(value);
	$("#" + btnId).attr("disabled", disabled);
}

function formatDate(dt) {
	if (dt == undefined || dt == null) {
		return null;
	}
	var d = new Date(dt);
	return '' + d.getUTCDate() + '/' + d.getUTCMonth() + '/' + d.getUTCFullYear() + '';
}

$(document).ready(function() {
	refreshUi();
	if (localStorage.principalUser != undefined) {
		authUser = JSON.parse(localStorage.principalUser);
	}
});