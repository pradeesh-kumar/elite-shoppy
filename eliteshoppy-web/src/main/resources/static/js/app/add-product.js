/* Redirect other than Seller and Admin user*/
redirectByRole([ "Seller", "Admin" ]);
var attributes = null;

function addProduct() {
	
}

function addAttributeRow() {
	var row = $('<tr id="">');
	row.append('<td><input type="text" class="form-control unboardered" placeholder="Name" /></td>');
	var ti = $('<input />', {
	    class: 'form-control',
	    type: 'text',
	    'placeholder': 'Enter Attribute Value and then press <Enter>',
	    'data-role': 'tagsinput'
	});
	var td = $('<td>');
	td.append(ti);
	row.append(td);
	row.append('<td><input type="button" value="Save" class="btn btn-xs btn-success" onclick="saveAtr(this)" /> <input type="button" value="Delete" class="btn btn-xs btn-danger" onclick="deleteAtr(this)" /></td>');
	$("#atrBody").prepend(row);
	ti.tagsinput();
}

function loadAttributes() {
	$.get(GET_ALL_ATTRIBUTES, function(response) {
		attributes = response;
		console.log(response);
		if (response.length > 0) {
			response.forEach(function(a) {
				$("#attributes").append('<option value="' + a.name + '">' + a.name + '</option>');
			});
			$('#attributes').multiselect({
				onChange: function(option, checked, select) {
	                console.log('Changed option ' + $(option).val() + '.');
	            }
			});
		}
	});
}

$(document).ready(function() {
	loadAttributes();
});