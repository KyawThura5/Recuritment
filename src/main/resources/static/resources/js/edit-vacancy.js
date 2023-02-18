$(document).ready(function () {

	$("#department").change(function (event) {

        ajaxQueryTeamByDepartment();
    });


	initTeamSelector();

    $("#btnAddNewPosition").click(addNewPositionEntry);
    listenRemovePositionEntry();
});

const addNewPositionEntry = () => {
    let positionWrapper = $("#positionWrapper");
    let index = positionWrapper.children().length;
    let positionOptions = $("#positionSelectorWrapper select").html();
	let teamOptions = $("#teamSelectorWrapper select").html();
    let row = `
        <div class="row mt-3">
            <input type="hidden" id="requirePositions${index}.id" name="requirePositions[${index}].id" value="">
            <div class="offset-sm-1 col-sm-2 offset-md-1 col-md-4">
                <select class="form-select" id="requirePositions${index}.position.id" name="requirePositions[${index}].position.id">
                    ${positionOptions}
                </select>
            </div>
            <div class="offset-sm-1 col-sm-1 offset-md-0 col-md-1">
                <div class="form-check form-switch">
                    <input class="form-check-input" type="checkbox" role="switch" id="flexSwitchCheckDefault${index}" name="requirePositions[${index}].foc" value="true" />
                    <input type="hidden" name="_requirePositions[${index}].foc" value="on"/>
                    <label class="form-check-label" for="flexSwitchCheckDefault${index}">FOC</label>
                </div>
            </div>
            <div class="offset-sm-1 col-sm-1 offset-md-0 col-md-1">
                <input type="text" class="form-control" id="requirePositions${index}.count" name="requirePositions[${index}].count" value="1">
            </div>
            <div class="offset-sm-1 col-sm-3 offset-md-0 col-md-3 team-select-wrapper">
                <select class="form-select" id="requirePositions${index}.team.id" name="requirePositions[${index}].team.id">
                    ${teamOptions}
                </select>
            </div>
            <div class="offset-sm-1 col-sm-3 offset-md-0 col-md-2">
                <span class="btn btn-danger btnPositionRemove">Remove</span>
            </div>
        </div>
    `;

    positionWrapper.append(row);
    listenRemovePositionEntry();
}

const listenRemovePositionEntry = () => {
    let btnRemoves = $("#positionWrapper .btnPositionRemove");
    btnRemoves.each(function() {
        let entry = $(this).parent().parent();
        $(this).click(function() {
            entry.remove();
        });
        
    });
    
}

const ajaxQueryTeamByDepartment = () => {
    let departmentId = $('#department').find(":selected").val();

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
			teamSelectWrapper.html(`<select class='form-select'>${options}</select>`)

            let teamSelects = $('#positionWrapper .team-select-wrapper');
			teamSelects.each(function(i, target) {
                console.log(i);
                let optionSelect = `
                    <select class="form-select" id="requirePositions${i}.team.id" name="requirePositions[${i}].team.id">
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
    let departmentId = $('#department').find(":selected").val();

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
			teamSelectWrapper.html(`<select class='form-select'>${options}</select>`)
            
        },
        error: function (e) {

            console.log('error');
        }
    });
};