$(document).ready(function () {
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

        var form = document.getElementById("departmentForm");
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
    }
);


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

    let form = document.getElementById("departmentForm");
    event.preventDefault();
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
            url: "/department/validate",
            data: $("#departmentForm").serialize(),
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
    let values = validate.collectFormValues(document.getElementById("departmentForm"));
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

const loadDataOfForm = (data) => {
    $.ajax({
        type: "GET",
        url: "/department/search/content",
        data: data,
        dataType: 'html',
        success: function (content) {

            let dataContent = $("#dataContent")
            dataContent.html(content);
            
        },
        error: function (e) {

            console.log('error');
        }
    });
}

const loadDataOfUrl = (url) => {
    $.ajax({
        type: "GET",
        url: url,
        dataType: 'html',
        success: function (content) {

            let dataContent = $("#dataContent")
            dataContent.html(content);
            
        },
        error: function (e) {

            console.log('error');
        }
    });
}


function showEditDepartmentModal(id) {

    removeIfExists("nameError");

    let url = "/department/data";
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
                let department = data.department;
                let title = data.title;

                $("#editDepartmentTitle").text(title);
                
                let form = $("#departmentForm");
                form.find("input[name='id']").val(department.id);
                form.find("input[name='name']").val(department.name);
                form.find("input[name='deleted']").val(department.deleted);

                let modelElement=document.getElementById("editDepartmentModal");
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