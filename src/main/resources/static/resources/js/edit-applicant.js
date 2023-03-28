$(document).ready(function () {
	$("#vacancy").change(function (event){
		ajaxQuerypositionByVacancy();
	});
	
	$(".select-search").each(function(i, t) {
		dselect(t, {
			search: true
		});
	});

	var form = document.getElementById("applicantForm");
    form.addEventListener("submit", function(event) {
        validated(event);
    });

    $(".validated-input").on("input", function(event) {
        let labels = $(event.target.parentElement).find(".validated-label");
        labels.each((i, l) => {
            l.parentNode.removeChild(l);
        });
        if ($(this).attr("id") != "cvFileInput" && $(this).val()) {
            validatedEach(event, $(this).attr("id"));
        }
    });

    $(".validated-input").on("change", function(event) {
        let labels = $(event.target.parentElement).find(".validated-label");
        labels.each((i, l) => {
            l.parentNode.removeChild(l);
        });
        console.log($(this).attr("id"));
        if ($(this).val()) {
            validatedEach(event, $(this).attr("id"));
        }
    });
});

var constraints = {
    name: {
        presence: {message : "^Enter name"},
        length: {
            maximum: 50,
            message: "^Name must be maximum 50 characters"
        }
    },
    code: {
        presence: {message : "^Enter code"},
        format : {
            pattern : /^\S*$/,
            message : "^Code cannot contain space"
        },
        length: {
            minimum: 6,
            maximum: 30,
            message: "^Code must be between 6 and 30 characters"
        }
    },
    phone : {
        presence : {message : "^Enter phone number"},
        format : {
            pattern : /^\d+$/,
            message : "^Enter valid phone number"
        },
        length : {
            minimum : 6,
            maximum : 16,
            message : "^Phone number must be between 6 ant 16 digits"
        }
    },
    email : {
        presence : {message : "^Enter email"},
        email : {message : "^Enter valid email"}
    },
    recruitmentResource : {
        presence : {message : "^Select source"},
    },
    vacancy : {
        presence : {message : "^Select vacancy"},
    },
    requirePosition : {
        presence : {message : "^Select position"},
    },
    attachedFileName : {
        presence  : {message : "^Choose cv file"}
    },
    education : {
        length : {
            maximum : 225,
            message : "^Length cannot be exceed 225 characters"
        }
    },
    experience : {
        length : {
            maximum : 225,
            message : "^Length cannot be exceed 225 characters"
        }
    },
    skill : {
        length : {
            maximum : 225,
            message : "^Length cannot be exceed 225 characters"
        }
    },
    address : {
        length : {
            maximum : 225,
            message : "^Length cannot be exceed 225 characters"
        }
    }
    
};

const validated = (event) => {
    let form = document.getElementById("applicantForm");
    event.preventDefault();
        let values = validate.collectFormValues(form);
        let validation = validate(values, constraints);
        
        let codeError = document.createElement("label");
        codeError.id = "codeError";
        codeError.classList.add("text-danger", "validated-label");

        let nameError = document.createElement("label");
        nameError.id = "nameError";
        nameError.classList.add("text-danger", "validated-label");

        let phoneError = document.createElement("label");
        phoneError.id = "phoneError";
        phoneError.classList.add("text-danger", "validated-label");

        let emailError = document.createElement("label");
        emailError.id = "emailError";
        emailError.classList.add("text-danger", "validated-label");

        let recruitmentResourceError = document.createElement("label");
        recruitmentResourceError.id = "recruitmentResourceError";
        recruitmentResourceError.classList.add("text-danger", "validated-label");

        let vacancyError = document.createElement("label");
        vacancyError.id = "vacancyError";
        vacancyError.classList.add("text-danger", "validated-label");

        let requirePositionError = document.createElement("label");
        requirePositionError.id = "requirePositionError";
        requirePositionError.classList.add("text-danger", "validated-label");

        let attachedFileNameError = document.createElement("label");
        attachedFileNameError.id = "attachedFileNameError";
        attachedFileNameError.classList.add("text-danger", "validated-label");

        let educationError = document.createElement("label");
        educationError.id = "educationError";
        educationError.classList.add("text-danger", "validated-label");

        let experienceError = document.createElement("label");
        experienceError.id = "experienceError";
        experienceError.classList.add("text-danger", "validated-label");

        let skillError = document.createElement("label");
        skillError.id = "skillError";
        skillError.classList.add("text-danger", "validated-label");

        let addressError = document.createElement("label");
        addressError.id = "addressError";
        addressError.classList.add("text-danger", "validated-label");

        if (validation) {
            checkValidation(codeError, "codeError", "code", validation.code);
            checkValidation(nameError, "nameError", "name", validation.name);
            checkValidation(phoneError, "phoneError", "phone", validation.phone);
            checkValidation(emailError, "emailError", "email", validation.email);
            checkValidation(recruitmentResourceError, "recruitmentResourceError", "recruitmentResourceHelp", validation.recruitmentResource);
            checkValidation(vacancyError, "vacancyError", "vacancyHelp", validation.vacancy);
            checkValidation(requirePositionError, "requirePositionError", "position", validation.requirePosition);
            checkValidation(attachedFileNameError, "attachedFileNameError", "cv", validation.attachedFileName);
            checkValidation(educationError, "educationError", "education", validation.education);
            checkValidation(experienceError, "experienceError", "experience", validation.experience);
            checkValidation(skillError, "skillError", "skill", validation.skill);
            checkValidation(addressError, "addressError", "address", validation.address);
        } else {
            form.submit();
        }
}

const validatedEach = (event, inputId) => {
    let form = document.getElementById("applicantForm");
    event.preventDefault();
    let values = validate.collectFormValues(form);
    let validation = validate(values, constraints);
    
    let codeError = document.createElement("label");
    codeError.id = "codeError";
    codeError.classList.add("text-danger", "validated-label");

    let nameError = document.createElement("label");
    nameError.id = "nameError";
    nameError.classList.add("text-danger", "validated-label");

    let phoneError = document.createElement("label");
    phoneError.id = "phoneError";
    phoneError.classList.add("text-danger", "validated-label");

    let emailError = document.createElement("label");
    emailError.id = "emailError";
    emailError.classList.add("text-danger", "validated-label");

    let recruitmentResourceError = document.createElement("label");
    recruitmentResourceError.id = "recruitmentResourceError";
    recruitmentResourceError.classList.add("text-danger", "validated-label");

    let vacancyError = document.createElement("label");
    vacancyError.id = "vacancyError";
    vacancyError.classList.add("text-danger", "validated-label");

    let requirePositionError = document.createElement("label");
    requirePositionError.id = "requirePositionError";
    requirePositionError.classList.add("text-danger", "validated-label");

    let attachedFileNameError = document.createElement("label");
    attachedFileNameError.id = "attachedFileNameError";
    attachedFileNameError.classList.add("text-danger", "validated-label");

    let educationError = document.createElement("label");
    educationError.id = "educationError";
    educationError.classList.add("text-danger", "validated-label");

    let experienceError = document.createElement("label");
    experienceError.id = "experienceError";
    experienceError.classList.add("text-danger", "validated-label");

    let skillError = document.createElement("label");
    skillError.id = "skillError";
    skillError.classList.add("text-danger", "validated-label");

    let addressError = document.createElement("label");
    addressError.id = "addressError";
    addressError.classList.add("text-danger", "validated-label");

    if (validation) {
        if (inputId == "code") {
            checkValidation(codeError, "codeError", "code", validation.code);
        } else if (inputId == "name") {
            checkValidation(nameError, "nameError", "name", validation.name);
        } else if (inputId == "phone") {
            checkValidation(phoneError, "phoneError", "phone", validation.phone);
        } else if (inputId == "email") {
            checkValidation(emailError, "emailError", "email", validation.email);
        } else if (inputId == "recruitmentResource") {
            checkValidation(recruitmentResourceError, "recruitmentResourceError", "recruitmentResourceHelp", validation.recruitmentResource);
        } else if (inputId == "vacancy") {
            checkValidation(vacancyError, "vacancyError", "vacancyHelp", validation.vacancy);
        } else if (inputId == "position") {
            checkValidation(requirePositionError, "requirePositionError", "position", validation.requirePosition);
        } else if (inputId == "cvFileInput") {
            checkValidation(attachedFileNameError, "attachedFileNameError", "cv", validation.attachedFileName);
        } else if (inputId == "education") {
            checkValidation(educationError, "educationError", "education", validation.education);
        } else if (inputId == "experience") {
            checkValidation(experienceError, "experienceError", "experience", validation.experience);
        } else if (inputId == "skill") {
            checkValidation(skillError, "skillError", "skill", validation.skill);
        } else if (inputId == "address") {
            checkValidation(addressError, "addressError", "address", validation.address);
        }
        
    }
}

const onCvFileChange = () => {
    let fileInput = $("#cvFileInput");
    let fileNameInput = $("#cvFileNameInput");
    let attachedFileNameInput = $("#attachedFileNameInput");
    let file = fileInput[0].files[0];
    if (file) {
        fileNameInput.val(file.name)
    } else {
        fileNameInput.val(attachedFileNameInput.val());
    }
}

const ajaxQuerypositionByVacancy = () => {
	
	let vacancyId= $('#vacancy').find(":selected").val();
	let data={};
	
	data['vacancyId']=vacancyId;
	$.ajax({
		type:"POST",
		contentType: "application/json",
		url: "/api/positionByVacancy/search",
		data: JSON.stringify(data),
		dataType: 'json',
		cache: false,
		timeout: 600000,
		success: function (data) {
			let options = "";
			for(let i=0;i<data.length;i++){
				let position=data[i];
				options += `<option value=${position.id}>${position.position.name} -> ${position.team.name} -> ${position.team.department.name}</option>`;			
			}
					
			let positionSelectWrapper = $("#position");
			positionSelectWrapper.html(`
                <option value=''>-- select a position --</option>
                ${options}`);
  		},
        error: function (e) {

            console.log('error');
        }
    }
);

};