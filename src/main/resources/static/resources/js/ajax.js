$(document).ready(function () {

    $("#department").change(function (event) {

        ajaxQueryTeamByDepartment();
    });

    ajaxQueryTeamByDepartment();

});

function ajaxQueryTeamByDepartment () {
    let departmentId = $('#department').find(":selected").val();

    let data = {};
    data['departmentId'] = departmentId;
    
    $.ajax({
        type: "POST",
        contentType: "application/json",
        url: "/api/team/search",
        data: JSON.stringify(data),
        dataType: 'json',
        cache: false,
        timeout: 600000,
        success: function (data) {

            let teamSelect = $('#team');
            

            let options = "";
            for (let i = 0; i < data.length; i++) {
                let team = data[i];
                options += `<option value=${team.id}>${team.name}</option>`;
            }

            teamSelect.html(options);
        },
        error: function (e) {

            console.log('error');
        }
    });
};