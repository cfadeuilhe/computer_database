//On load
$(function() {
	// Default: hide edit mode
	$(".editMode").hide();

	// Click on "selectall" box
	$("#selectall").click(function() {
		$('.cb').prop('checked', this.checked);
	});

	// Click on a checkbox
	$(".cb").click(function() {
		if ($(".cb").length == $(".cb:checked").length) {
			$("#selectall").prop("checked", true);
		} else {
			$("#selectall").prop("checked", false);
		}
		if ($(".cb:checked").length != 0) {
			$("#deleteSelected").enable();
		} else {
			$("#deleteSelected").disable();
		}
	});

});

// Function setCheckboxValues
(function($) {

	$.fn.setCheckboxValues = function(formFieldName, checkboxFieldName) {

		var str = $('.' + checkboxFieldName + ':checked').map(function() {
			return this.value;
		}).get().join();

		$(this).attr('value', str);

		return this;
	};

}(jQuery));

function getSystemLocale() {
    var systemLocale = window.navigator.language;
    return systemLocale;
}

function getResource(textToTrad){
    jQuery.i18n.properties({
        name:'messages', 
        path: 'resources/', 
        mode:'both', 
        language:getSystemLocale(),
        callback: function() {
        	$("#editComputer").text(jQuery.i18n.prop(textToTrad));
        }
    });
}

function getContextPath() {
	   return window.location.pathname.substring(0, window.location.pathname.indexOf("/",2));
	}
// Function toggleEditMode
(function($) {

	$.fn.toggleEditMode = function() {
		if ($(".editMode").is(":visible")) {
			$(".editMode").hide();
			$("#editComputer").text(del);
		} else {
			$(".editMode").show();
			$("#editComputer").text(view);
		}
		return this;
	};

}(jQuery));

// Function to sort by column
(function($) {
	$.fn.orderBy = function(column, order) {
		
		$('#orderForm input[name=order]').val(column + "." + order);
		$('#orderForm').submit();

	};
}(jQuery));

// Function delete selected: Asks for confirmation to delete selected computers,
// then submits it to the deleteForm
(function($) {
	$.fn.deleteSelected = function() {
		if (confirm("Are you sure you want to delete the selected computers?")) {
			$('#deleteForm input[name=selection]').setCheckboxValues(
					'selection', 'cb');
			$('#deleteForm').submit();
		}
	};
}(jQuery));

// Event handling
// Onkeydown
$(document).keydown(function(e) {

	switch (e.keyCode) {
	// DEL key
	case 46:
		if ($(".editMode").is(":visible") && $(".cb:checked").length != 0) {
			$.fn.deleteSelected();
		}
		break;
	// E key (CTRL+E will switch to edit mode)
	case 69:
		if (e.ctrlKey) {
			$.fn.toggleEditMode();
		}
		break;
	}
});
