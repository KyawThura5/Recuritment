$(document).ready(function () {
	
	$(".select-search").each(function(i, t) {
		dselect(t, {
			search: true
		});
	});

	setMinInterviewDate();
	
	
var constraints={
	code:{
		presence: {message:"^Enter code"},
		format:{
			 pattern : /^\S*$/,
             message : "^Code cannot contain space"
		},
		length: {
                maximum: 30,
                message: "^Name must be maximum 30 characters"
            }
	},
	interviewName:{
		presence: {message : "^Select interview name"},
	},
	applicant:{
		presence: {message : "^Select interview name"},

	},
	localDate:{
		presence : {message : "^Select date"}
	},
	localTime:{
		presence : {message : "^Select time"}
	}
}
var form=document.getElementById("interviewForm");
console.log(form);
form.addEventListener("submit",function(event){
	event.preventDefault();
	let values=validate.collectFormValues(form);
	let validation=validate(values,constraints);
	
	let codeError=document.createElement("label");
	codeError.id="codeError";
	codeError.classList.add("text-danger");
	
	let interviewNameError=document.createElement("label");
	interviewNameError.id="interviewNameError";
	interviewNameError.classList.add("text-danger");
	
	let applicantError=document.createElement("label");
	applicantError.id="applicantError";
	applicantError.classList.add("text-danger");
	
	let dateError=document.createElement("label");
	dateError.id="dateError";
	dateError.classList.add("text-danger");
	
	let timeError=document.createElement("label");
	timeError.id="timeError";
	timeError.classList.add("text-danger");
	if(validation){
		console.log(validation);
	 checkValidation(codeError, "codeError", "code", validation.code);
	checkValidation(interviewNameError, "interviewNameError","interviewNameHelp", validation.interviewName);
	checkValidation(applicantError, "applicantError","applicantHelp", validation.applicant);
	checkValidation(dateError, "dateError", "date", validation.localDate);
	checkValidation(timeError, "timeError", "time", validation.localTime);
	}
	else{
		form.submit();
	}
});
});

const setMinInterviewDate = () => {
    let createdDateTime = document.getElementById("createdDateTime").value;
    let minDate = new Date().toISOString().split("T")[0];
	let minTime = new Date().toISOString().split("T")[1];
    if (createdDateTime) {
        minDate = createdDateTime.split("T")[0];
		minTime = createdDateTime.split("T")[1];
    }
	console.log(minTime);
    let interviewDateInput = document.getElementById("date");
    interviewDateInput.min = minDate;
	let interviewTimeInput = document.getElementById("time");
    interviewTimeInput.min = minTime;

	$("#time").on("change", function(e) {
		if ($(this).val() < $(this).attr("min")) {
		  $(this).val($(this).attr("min"));
		} else if ($(this).val() > $(this).attr("max")) {
		  $(this).val($(this).attr("min"));
		}
	  });
}