$(document).ready(function() {
    let alert = document.getElementById("alert");
    if(alert) {
        let modelElement=document.getElementById("alert");
        modelElement.style.display = "block";
        window.onclick = function(event) {
            if (event.target == modelElement) {
                modelElement.style.display = "none";
            }
        }
        window.setTimeout(closeAlert, 5000);
    }

    var form = document.getElementById("teamForm");
    form.addEventListener("submit", function(event) {
        validated(event);
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

const validated = (event) => {

    var constraints = {
        name: {
            presence: {message : "^Enter name"},
            length: {
                maximum: 50,
                message: "^Name must be maximum 50 characters"
            }
        },
        department : {
            presence: {message : "^Select department"}
        }
        
    };

    let form = document.getElementById("teamForm");
    event.preventDefault();
        let values = validate.collectFormValues(form);
        let validation = validate(values, constraints);
        
        let nameError = document.createElement("label");
        nameError.id = "nameError";
        nameError.classList.add("text-danger", "validated-label");

        let departmentError = document.createElement("label");
        departmentError.id = "departmentError";
        departmentError.classList.add("text-danger", "validated-label");

        if (validation) {
            checkValidation(nameError, "nameError", "name", validation.name);
            checkValidation(departmentError, "departmentError", "departmentHelp", validation.department);
        } else {
            $.ajax({
                type:"POST",
                url: "/team/validate",
                data: $("#teamForm").serialize(),
                dataType: "json",
                cache: false,
                timeout: 600000,
                success: function (data) {
                    console.log(data);
                    console.log(Object.keys(data).length)
                    if (Object.keys(data).length === 0) {
                        form.submit();
                    } else {
                        checkServerValidation(nameError, "nameError", "name", data.name);
                        checkServerValidation(departmentError, "departmentError", "department", data.department);
                    }
                },
                error: function (e) {

                    console.log('error');
                }
            }
            );
        }
}

const validatedEach = (event, inputId) => {

    var constraints = {
        name: {
            presence: {message : "^Enter name"},
            length: {
                maximum: 50,
                message: "^Name must be maximum 50 characters"
            }
        },
        department : {
            presence: {message : "^Select department"}
        }
        
    };

    event.preventDefault();
        let values = validate.collectFormValues(document.getElementById("teamForm"));
        let validation = validate(values, constraints);
        
        let nameError = document.createElement("label");
        nameError.id = "nameError";
        nameError.classList.add("text-danger", "validated-label");

        let departmentError = document.createElement("label");
        departmentError.id = "departmentError";
        departmentError.classList.add("text-danger", "validated-label");

        if (validation) {
            if (inputId == "name") {
                checkValidation(nameError, "nameError", "name", validation.name);
            } else if (inputId == "department") {
                checkValidation(departmentError, "departmentError", "departmentHelp", validation.department);
            }
        }
}


function showEditTeamModal(id) {

    removeIfExists("nameError");
    removeIfExists("departmentError");

    let url = "/team/data";
    if (id) {
        url = url + "?id=" + id;
    }

    $.ajax({
        type:"GET",
        url: url,
        dataType: "json",
        cache: false,
        timeout: 600000,
        success: function (data) {
            let team = data.team;
            let departmentList = data.departmentList;
            let title = data.title;

            $("#editTeamModalTitle").text(title);
            
            let form = $("#teamForm");
            form.find("input[name='id']").val(team.id);
            form.find("input[name='deleted']").val(team.deleted);
            form.find("input[name='name']").val(team.name);

            let departmentSelect = form.find("select[name='department']");

            let options = "<option value=''>-- select department --</option>";

            $.each(departmentList, function( index, value ) {
                    options += `<option value="${value.id}">${value.name}</option>`
                }
            );

            departmentSelect.html(options);

            if (team.department) {
                departmentSelect.val(team.department.id);
            }

            let modelElement=document.getElementById("editTeamModal");
            modelElement.style.display = "block";
            window.onclick = function(event) {
                if (event.target == modelElement) {
                    modelElement.style.display = "none";
                }
            }
        },
        error: function (e) {

            console.log('error');
        }
    }
    );
}