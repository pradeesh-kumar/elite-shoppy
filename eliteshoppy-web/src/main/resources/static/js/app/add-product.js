/* Redirect other than Seller and Admin user*/
redirectByRole([ "Seller", "Admin" ]);
var attributes = null;

function addProduct() {
	if ($('#addProductForm')[0].checkValidity()) {
		var name = $("#name").val();
		var category = $("#category").val();
		var idealFor = $("#idealFor").val();
		var availableQuantity = $("#availableQuantity").val();
		var price = $("#price").val();
		var offerPrice = $("#offerPrice").val();
		var description = $("#description").val();
		
		var prodAttrs = [];
		$("#atrBody tr").each(function(index, tr) {
			atrName = $(tr).children().eq(0).text();
			atrValues = $(tr).children().eq(1).children().eq(1).val().split(",");
			if (atrValues == '') {
				showErrorModal("Please do not leave any attribute value empty.");
				return;
			}
			prodAttrs.push({'name': atrName, 'values': atrValues});
		});
		
		requestPayload = {
			'name': name,
			'category': category,
			'idealFor': idealFor,
			'availableQuantity': availableQuantity,
			'price': price,
			'offerPrice': offerPrice,
			'description': description,
			'active': false,
			'attributes': prodAttrs
		}
		invertButton("addBtn", "Adding...", true);
		$.ajax({
			'url' : CREATE_PRODUCT,
			'type' : 'POST',
			'contentType' : 'application/json',
			'data' : JSON.stringify(requestPayload),
			'dataType' : 'json',
			'headers' : {
				'Authorization' : 'bearer ' + localStorage.getItem("access_token")
			},
			'success' : function(result) {
				$("#error").hide();
				$("#success").removeClass("hide").text("Product has been added.");
				invertButton("addBtn", "Add", false);
				$("#toTop").click();
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
							$("#success").hide();
							$("#error").show().text("Something went wrong! Please try again later!");
						}
					});
				} else {
					console.log("Error occured while updating the account!");
					console.log(response);
					$("#success").hide();
					$("#error").show().text("Something went wrong! Please try again later!");
				}
				invertButton("addBtn", "Add", false);
				$("#toTop").click();
			}
		});
	} else {
		$('#addProductForm')[0].reportValidity();
	}
}

function removeAttributeRow(attrName) {
	$("#atrBody tr td:first-child:contains(" + attrName + ")").parent().remove();
	$('#attributes').multiselect('deselect', attrName);
}

function addAttributeRow(attrName) {
	var row = $('<tr>');
	row.append('<td>' + attrName + '</td>');
	var ti = $('<input />', {
	    class: 'form-control',
	    type: 'text',
	    'placeholder': 'Enter Attribute Value and then press <Enter>',
	    'data-role': 'tagsinput'
	});
	var td = $('<td>');
	td.append(ti);
	row.append(td);
	row.append('<td class="text-center"><a href="javascript:void(0);" onclick="removeAttributeRow(\'' + attrName + '\')"><b class="fa fa-minus-circle color-red"></b></a></td>');
	$("#atrBody").prepend(row);
	ti.tagsinput();
	
	var filteredAtr = attributes.find(function(e) { return e.name == attrName });
	var atrValues = filteredAtr.values;
	if (atrValues != undefined || atrValues != null || atrValues.length > 0) {
		atrValues.forEach(function(v) {
			ti.tagsinput('add', v);
		});
	}
}

function loadAttributes() {
	$.get(GET_ALL_ATTRIBUTES, function(response) {
		attributes = response;
		if (response.length > 0) {
			response.forEach(function(a) {
				$("#attributes").append('<option value="' + a.name + '">' + a.name + '</option>');
			});
			$('#attributes').multiselect({
				onChange: function(option, checked, select) {
	                if (checked) {
	                	addAttributeRow($(option).val());
	                } else {
	                	removeAttributeRow($(option).val());
	                }
	            }
			});
		}
	});
}

$(document).ready(function() {
	loadAttributes();
});