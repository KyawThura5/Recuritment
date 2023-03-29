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

    $("#applicantStatusCheck").on("change", function(event) {
        if (this.checked) {
            $("#applicantStatusWrapper").removeClass("d-none");
        } else {
            $("#applicantStatusWrapper").addClass("d-none");
        }
    });

    var form = document.getElementById("changeStatusForm");
    form.addEventListener("submit", function(event) {
        validated(event);
        $(this).attr("id")
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
    comment: {
        length: {
            maximum: 500,
            message: "^Comment must be maximum 500 characters"
        }
    },
    applicantStatusComment: {
        length: {
            maximum: 255,
            message: "^Applicant remark must be maximum 255 characters"
        }
    }
};

const validated = (event) => {
    let form = document.getElementById("changeStatusForm");
    event.preventDefault();
    let values = validate.collectFormValues(form);
    let validation = validate(values, constraints);
    
    let commentError = document.createElement("label");
    commentError.id = "commentError";
    commentError.classList.add("text-danger", "validated-label");

    let applicantRemarkError = document.createElement("label");
    applicantRemarkError.id = "applicantRemarkError";
    applicantRemarkError.classList.add("text-danger", "validated-label");

    if (validation) {
        checkValidation(commentError, "commentError", "remark", validation.comment);
        checkValidation(applicantRemarkError, "applicantRemarkError", "applicantStatusComment", validation.applicantStatusComment);
    } else {
        form.submit();
    }
}

const validatedEach = (event, inputId) => {
    let form = document.getElementById("changeStatusForm");
    event.preventDefault();
    let values = validate.collectFormValues(form);
    let validation = validate(values, constraints);
    
    let commentError = document.createElement("label");
    commentError.id = "commentError";
    commentError.classList.add("text-danger", "validated-label");

    let applicantRemarkError = document.createElement("label");
    applicantRemarkError.id = "applicantRemarkError";
    applicantRemarkError.classList.add("text-danger", "validated-label");

    if (validation) {
        if (inputId == "remark") {
            checkValidation(commentError, "commentError", "remark", validation.comment);
        } else if (inputId == "applicantStatusComment") {
            checkValidation(applicantRemarkError, "applicantRemarkError", "applicantStatusComment", validation.applicantStatusComment);
        }
    }
}

function closeAlert() {
    let modelElement=document.getElementById("alert");
    modelElement.style.display = "none";
}
