$(document).ready(function () {
	$("#departmentSelect").on('change', function (event) {
        ajaxQueryTeamByDepartment();
    });


	initTeamSelector();

    setMinDueDate();

    $("#btnAddNewPosition").click(addNewPositionEntry);
    listenRemovePositionEntry();

    addSelectSearch();

	var form = document.getElementById("vacancyForm");
    form.addEventListener("submit", function(event) {
        validated(event);
        $(this).attr("id")
    });

    $(".validated-input").on("change", function(event) {
        let labels = $(event.target.parentElement).find(".validated-label");
        labels.each((i, l) => {
            l.parentNode.removeChild(l);
        });
        if ($(this).val()) {
            validatedEach(event, $(this).attr("id"));
        }
    });

    $(".validated-input").on("input", function(event) {
        let labels = $(event.target.parentElement).find(".validated-label");
        labels.each((i, l) => {
            l.parentNode.removeChild(l);
        });
        if ($(this).val()) {
            validatedEach(event, $(this).attr("id"));
        }
    });
    
});

var constraints = {
    code: {
        presence: {message : "^Enter code"},
        format : {
            pattern : /^\S*$/,
            message : "^Code cannot contain space"
        },
        length: {
            maximum: 30,
            message: "^Name must be maximum 30 characters"
        }
    },
    department : {
        presence : {message : "^Select department"}
    },
    dueDate : {
        presence : {message : "^Select due date"}
    },
    status : {
        presence : {message : "^Select status"}
    },
    comment : {
        length : {
            maximum : 255,
            message : "^Maximum length is 255"
        }
    }
    
};

const validated = (event) => {
    let form = document.getElementById("vacancyForm");
    event.preventDefault();
    let values = validate.collectFormValues(form);
    let validation = validate(values, constraints);
    
    let codeError = document.createElement("label");
    codeError.id = "codeError";
    codeError.classList.add("text-danger", "validated-label");

    let departmentError = document.createElement("label");
    departmentError.id = "departmentError";
    departmentError.classList.add("text-danger", "validated-label");

    let dueDateError = document.createElement("label");
    dueDateError.id = "dueDateError";
    dueDateError.classList.add("text-danger", "validated-label");

    let statusError = document.createElement("label");
    statusError.id = "statusError";
    statusError.classList.add("text-danger", "validated-label");

    let commentError = document.createElement("label");
    commentError.id = "commentError";
    commentError.classList.add("text-danger", "validated-label");

    if (validation) {
        checkValidation(codeError, "codeError", "code", validation.code);
        checkValidation(departmentError, "departmentError", "departmentHelp", validation.department);
        checkValidation(dueDateError, "dueDateError", "dueDate", validation.dueDate);
        checkValidation(statusError, "statusError", "status", validation.status);
        checkValidation(commentError, "commentError", "comment", validation.comment);
    } else {
        form.submit();
    }
}

const validatedEach = (event, inputId) => {
    let form = document.getElementById("vacancyForm");
    event.preventDefault();
    let values = validate.collectFormValues(form);
    let validation = validate(values, constraints);
    
    let codeError = document.createElement("label");
    codeError.id = "codeError";
    codeError.classList.add("text-danger", "validated-label");

    let departmentError = document.createElement("label");
    departmentError.id = "departmentError";
    departmentError.classList.add("text-danger", "validated-label");

    let dueDateError = document.createElement("label");
    dueDateError.id = "dueDateError";
    dueDateError.classList.add("text-danger", "validated-label");

    let statusError = document.createElement("label");
    statusError.id = "statusError";
    statusError.classList.add("text-danger", "validated-label");

    let commentError = document.createElement("label");
    commentError.id = "commentError";
    commentError.classList.add("text-danger", "validated-label");

    if (validation) {
        if (inputId == "code") {
            checkValidation(codeError, "codeError", "code", validation.code);
        } else if (inputId == "departmentSelect") {
            checkValidation(departmentError, "departmentError", "departmentHelp", validation.department);
        } else if (inputId == "dueDate") {
            checkValidation(dueDateError, "dueDateError", "dueDate", validation.dueDate);
        } else if (inputId == "status") {
            checkValidation(statusError, "statusError", "status", validation.status);
        } else if (inputId == "comment") {
            checkValidation(commentError, "commentError", "comment", validation.comment);
        }
    }
}

const addSelectSearch = () => {
    $('.select-search').each(function(i, t) {
        dselect(t, {
            search: true
        });
    });
}

const setMinDueDate = () => {
    let createdDateTime = document.getElementById("createdDateTime").value;
    let minDate = new Date().toISOString().split("T")[0];
    if (createdDateTime) {
        minDate = createdDateTime.split(" ")[0];
    }
    let dueDateInput = document.getElementById("dueDate");
    dueDateInput.min = minDate;
}

const addNewPositionEntry = () => {
    let requirePositionsErrorMessage = $("#requirePositionsErrorMessage");
    requirePositionsErrorMessage.remove();
    let positionWrapper = $("#positionWrapper");
    let index = positionWrapper.children().length;
    let positionOptions = $("#positionSelectorWrapper select").html();
	let teamOptions = $("#teamSelectorWrapper select").html();
    let positoinSelect = document.createElement("select");
    positoinSelect.setAttribute("name", `requirePositions[${index}].position`);
    positoinSelect.setAttribute("id", `requirePositions${index}.position`);
    positoinSelect.classList.add("form-select", "select-search");
    positoinSelect.innerHTML = positionOptions;
    console.log(positoinSelect);
    let tempRow = `
        <div class="row mt-3">
            <div class="col">
                <div class="row">
                <input type="hidden" id="requirePositions${index}.id" name="requirePositions[${index}].id" value="">
                    <div class="col-12 col-md-8 col-lg-5 col-xl-5 my-2">
                        <select class="form-select select-search" id="requirePositions${index}.position" name="requirePositions[${index}].position">
                            ${positionOptions}
                        </select>
                    </div>
                    <div class="col-6 col-md-4 col-lg-2 col-xl-1 my-2">
                        <div class="form-check form-switch">
                            <input class="form-check-input" type="checkbox" role="switch" id="requirePositions${index}.foc" name="requirePositions[${index}].foc" value="true" />
                            <input type="hidden" name="_requirePositions[${index}].foc" value="on"/>
                            <label class="form-check-label" for="requirePositions${index}.foc">FOC</label>
                        </div>
                    </div>
                    <div class="col-6 col-md-4 col-lg-1 col-xl-1 order-md-3 my-2">
                        <input type="text" class="form-control" id="requirePositions${index}.count" name="requirePositions[${index}].count" value="1">
                    </div>
                    
                    <div class="col-12 col-md-8 col-lg-4 col-xl-5 my-2 team-select-wrapper">
                        <select class="form-select select-search" id="requirePositions${index}.team" name="requirePositions[${index}].team">
                            ${teamOptions}
                        </select>
                    </div>
                </div>
            </div>
            <div class="col-auto my-2">
                <span class="btn btn-danger btnPositionRemove">
                    <svg xmlns="http://www.w3.org/2000/svg" width="16" height="16" fill="currentColor" viewBox="0 0 512 512"><!--! Font Awesome Pro 6.3.0 by @fontawesome - https://fontawesome.com License - https://fontawesome.com/license (Commercial License) Copyright 2023 Fonticons, Inc. --><path d="M256 512c141.4 0 256-114.6 256-256S397.4 0 256 0S0 114.6 0 256S114.6 512 256 512zM184 232H328c13.3 0 24 10.7 24 24s-10.7 24-24 24H184c-13.3 0-24-10.7-24-24s10.7-24 24-24z"/></svg>
                </span>
            </div>
            
        </div>
    `;
    let row = `
        <div class="row mt-3">
            <input type="hidden" id="requirePositions${index}.id" name="requirePositions[${index}].id" value="">
            <div class="offset-sm-1 col-sm-2 offset-md-1 col-md-4">
                <select class="form-select select-search" id="requirePositions${index}.position" name="requirePositions[${index}].position">
                    ${positionOptions}
                </select>
            </div>
            <div class="offset-sm-1 col-sm-1 offset-md-0 col-md-1">
                <div class="form-check form-switch">
                    <input class="form-check-input" type="checkbox" role="switch" id="requirePositions${index}.foc" name="requirePositions[${index}].foc" value="true" />
                    <input type="hidden" name="_requirePositions[${index}].foc" value="on"/>
                    <label class="form-check-label" for="requirePositions${index}.foc">FOC</label>
                </div>
            </div>
            <div class="offset-sm-1 col-sm-1 offset-md-0 col-md-1">
                <input type="text" class="form-control" id="requirePositions${index}.count" name="requirePositions[${index}].count" value="1">
            </div>
            <div class="offset-sm-1 col-sm-3 offset-md-0 col-md-3 team-select-wrapper">
                <select class="form-select select-search" id="requirePositions${index}.team" name="requirePositions[${index}].team">
                    ${teamOptions}
                </select>
            </div>
            <div class="offset-sm-1 col-sm-3 offset-md-0 col-md-2">
                <span class="btn btn-danger btnPositionRemove">Remove</span>
            </div>
        </div>
    `;

    positionWrapper.append(tempRow);
    listenRemovePositionEntry();
}


const listenRemovePositionEntry = () => {
    let btnRemoves = $("#positionWrapper .btnPositionRemove");
    btnRemoves.each(function(i, event) {
        let entry = $(this).parent().parent();
        
        $(this).click(function() {
            let idInput = document.getElementById("requirePositions" + i + ".id");
            idInput.value = -1;
            entry.addClass("d-none");
            
        });
        
    });
    
}

const ajaxQueryTeamByDepartment = () => {
    let departmentId = $('#departmentSelect').find(":selected").val();

    let data = {};
    data['departmentId'] = departmentId;
    
    $.ajax({
        type: "POST",
        contentType: "application/json",
        url: "/api/teamByDepartmentId/search",
        data: JSON.stringify(data),
        dataType: 'json',
        cache: false,
        timeout: 600000,
        success: function (data) {

			let options = "";
			for (let i = 0; i < data.length; i++) {
				let team = data[i];
				options += `<option value=${team.id}>${team.name}</option>`;
			}

			let teamSelectWrapper = $("#teamSelectorWrapper");
			teamSelectWrapper.html(`<select class='form-select'>
                <option value=''>-- select a team --</option>
                ${options}
                </select>`);

            let teamSelects = $('#positionWrapper .team-select-wrapper');
			teamSelects.each(function(i, target) {
                console.log(i);
                let optionSelect = `
                    <select class="form-select" id="requirePositions${i}.team" name="requirePositions[${i}].team">
                        <option value=''>-- select a team --</option>
                        ${options}
                    </select>
                `;
				$(this).html(optionSelect);
			});
            
        },
        error: function (e) {

            console.log('error');
        }
    });
};

const initTeamSelector = () => {
    let departmentId = $('#departmentSelect').find(":selected").val();

    let data = {};
    data['departmentId'] = departmentId;
    $.ajax({
        type: "POST",
        contentType: "application/json",
        url: "/api/teamByDepartmentId/search",
        data: JSON.stringify(data),
        dataType: 'json',
        cache: false,
        timeout: 600000,
        success: function (data) {

			let options = "";
			for (let i = 0; i < data.length; i++) {
				let team = data[i];
				options += `<option value=${team.id}>${team.name}</option>`;
			}

			let teamSelectWrapper = $("#teamSelectorWrapper");
			teamSelectWrapper.html(`<select class='form-select'>
                <option value=''>-- select a team --</option>
                ${options}</select>`);
            
        },
        error: function (e) {

            console.log('error');
        }
    });
};