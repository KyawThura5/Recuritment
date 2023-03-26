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
    
    var form = document.getElementById("positionForm");
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
        
    };

    event.preventDefault();
        let form = document.getElementById("positionForm")
        let values = validate.collectFormValues(form);
        let validation = validate(values, constraints);
        
        let nameError = document.createElement("label");
        nameError.id = "nameError";
        nameError.classList.add("text-danger", "validated-label");

        if (validation) {
            checkValidation(nameError, "nameError", "name", validation.name);
        } else {
            $.ajax({
                type:"POST",
                url: "/position/validate",
                data: $("#positionForm").serialize(),
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
        
    };

    event.preventDefault();
        let values = validate.collectFormValues(document.getElementById("positionForm"));
        let validation = validate(values, constraints);
        
        let nameError = document.createElement("label");
        nameError.id = "nameError";
        nameError.classList.add("text-danger", "validated-label");

        if (validation) {
            if (inputId == "name") {
                checkValidation(nameError, "nameError", "name", validation.name);
            }
        }
}

function showEditPositionModal(id) {

    removeIfExists("nameError");

    let url = "/position/data";
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
                let position = data.position;
                let title = data.title;

                $("#editPositionTitle").text(title);
                
                let form = $("#positionForm");
                form.find("input[name='id']").val(position.id);
                form.find("input[name='name']").val(position.name);
                form.find("input[name='deleted']").val(position.deleted);
                  

                let modelElement=document.getElementById("editPositionModal");
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