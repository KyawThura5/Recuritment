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
                maximum: 30,
                message: "^Name must be maximum 30 characters"
            }
        }
        
    };

    var form = document.getElementById("recruitmentResourceForm");
    form.addEventListener("submit", function(event) {
        event.preventDefault();
        let values = validate.collectFormValues(form);
        let validation = validate(values, constraints);
        let codeError = document.createElement("label");
        codeError.id = "codeError";
        codeError.classList.add("text-danger");
        
        let nameError = document.createElement("label");
        nameError.id = "nameError";
        nameError.classList.add("text-danger");

       

        if (validation) {
	        checkValidation(codeError, "codeError", "code", validation.code);
            checkValidation(nameError, "nameError", "name", validation.name);
             } else {
            $.ajax({
                type:"POST",
                url: "/recruitmentresource/direct/validate",
                data: $("#recruitmentResourceForm").serialize(),
                cache: false,
                timeout: 600000,
                success: function (data) {
                    console.log(data);
                    console.log(Object.keys(data).length)
                    if (Object.keys(data).length === 0) {
                        form.submit();
                    } else {
                        checkServerValidation(codeError, "codeError", "code", data.code);
                        checkServerValidation(nameError, "nameError", "name", data.name);
                        }
                },
                error: function (e) {

                    console.log('error');
                }
            }
            );
        }
        
    });
});


function showEditDirectRecruitmentResourceModal(id) {

    let url = "/recruitmentresource/direct/data";
    if (id) {
        url = url + "?id=" + id;
    }

    $.ajax({
        type:"GET",
        url: url,
        cache: false,
        timeout: 600000,
        success: function (data) {
            let directRecruitmentResource = data.directRecruitmentResource;
            
            let title = data.title;

            $("#editRecruitmentResourceModalTitle").text(title);
            
            let form = $("#recruitmentResourceForm");
            form.find("input[name='id']").val(directRecruitmentResource.id);
            form.find("input[name='code']").val(directRecruitmentResource.code);
            form.find("input[name='name']").val(directRecruitmentResource.name);
            let modelElement=document.getElementById("editDirectRecruitmentModal");
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

  