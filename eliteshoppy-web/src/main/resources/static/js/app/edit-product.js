/* Redirect other than Seller and Admin user*/
redirectByRole([ "Seller", "Admin" ]);
var attributes = null;
var loadedProduct = null;

function uploadImages() {
	if ($("#fileImages").val() == '') {
		showErrorModal("Please select an image.");
	} else {
		var formData = new FormData();
		invertButton("uploadBtn", "Uploading...", true);
		imageFiles = document.querySelector('#fileImages').files;
	    for(var index = 0; index < imageFiles.length; index++) {
	        formData.append("productImages", imageFiles[index]);
	    }
		formData.append("productId", loadedProduct.id);
	    $.ajax({
	    	'url' : UPLOAD_IMAGE,
			'type' : 'POST',
			'enctype' : 'multipart/form-data',
			'data' : formData,
			'processData': false,
			'contentType': false,
			'cache': false,
			'multipart': true,
			'timeout': 0,
			'success': function(response) {
				$("#imgUploadError").hide();
				$("#imgUploadSuccess").removeClass("hide");
				invertButton("uploadBtn", "Upload", false);
			},
			'error': function(response) {
				$("#imgUploadError").removeClass("hide");
				$("#imgUploadSuccess").hide();
				invertButton("uploadBtn", "Upload", false);
			}
	    });
	}
}

function updateProduct() {
	if ($('#addProductForm')[0].checkValidity()) {
		var id = $("#productId").val();
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
		
		loadedProduct.name = name;
		loadedProduct.category = category;
		loadedProduct.idealFor = idealFor;
		loadedProduct.availableQuantity = availableQuantity;
		loadedProduct.price = price;
		loadedProduct.offerPrice = offerPrice;
		loadedProduct.description = description;
		loadedProduct.attributes = prodAttrs;
		
		requestPayload = loadedProduct;
		accessToken = localStorage.getItem("access_token");
		invertButton("updBtn", "Updating...", true);
		$.ajax({
			'url' : UPDATE_PRODUCT,
			'type' : 'PUT',
			'contentType' : 'application/json',
			'data' : JSON.stringify(requestPayload),
			'dataType' : 'json',
			'headers' : {
				'Authorization' : 'bearer ' + accessToken
			},
			'success' : function(result) {
				$("#error").hide();
				$("#success").removeClass("hide").text("Product has been updated.");
				invertButton("updBtn", "Update", false);
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
							updateProduct();
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
				invertButton("updBtn", "Update", false);
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

function addAttributeRow(attrName, values) {
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
	var atrValues = values == null ? filteredAtr.values : values;
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
		loadProduct();
	});
}

function loadProduct() {
	var productId = $("#productId").val();
	$.get(GET_PRODUCT + productId, function(response) {
		loadedProduct = response;
		
		$("#name").val(response.name);
		$("#category").val(response.category);
		$("#idealFor").val(response.idealFor);
		$("#availableQuantity").val(response.availableQuantity);
		$("#price").val(response.price);
		$("#offerPrice").val(response.offerPrice);
		$("#description").val(response.description);
		
		if (response.attributes.length > 0) {
			response.attributes.forEach(function(a) {
				addAttributeRow(a.name, a.values);
				$('#attributes').multiselect('select', a.name);
			});
		}
	});
}

$(document).ready(function() {
	loadAttributes();
});