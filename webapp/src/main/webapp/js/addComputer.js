//On load
$(function() {
	$("#introduced").datepicker({
		format : 'mm/dd/yyyy'
	}).on('changeDate', function(e) {
		$('addComputerForm').computerValidation('validateFields', 'computer');
	});

	$('addComputerForm').computerValidation({
		fields : {
			name: {
				validators: {
					notEmpty: {
						message: 'The name cannot be empty'
					}
				}
			},
			introduced:{
				validators: {
					date: {
						format: 'DD/MM/YYYY',
						message: 'The date is not valid'
					}
				}
			},
			discontinued:{
				validators: {
					date: {
						format: 'DD/MM/YYYY',
						message: 'The date is not valid'
					}
				}
			}
		}
	})
	.on('success.validator.fv',  function(e,data){
		
	});
});
