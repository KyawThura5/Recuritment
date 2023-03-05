$(document).ready(function () {

	$("#department").change(function (event) {

        ajaxQueryTeamByDepartment();
    });


	initTeamSelector();

    $("#btnAddNewPosition").click(addNewPositionEntry);
    listenRemovePositionEntry();

    addSelectSearch();
    
});

const addSelectSearch = () => {
    $('.select-search').each(function(i, t) {
        dselect(t, {
            search: true
        });
    });
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

    positionWrapper.append(row);
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
			teamSelectWrapper.html(`<select class='form-select'>
                <option value=''>-- select a team --</option>
                ${options}</select>`);
            
        },
        error: function (e) {

            console.log('error');
        }
    });
};