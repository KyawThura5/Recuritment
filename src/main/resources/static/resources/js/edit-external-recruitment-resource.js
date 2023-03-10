$(document).ready(function() {
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
        },
        pic : {
            presence : {message : "^Enter PIC name"},
            length : {
                maximum : 50,
                message  : "^PIC name must be maximum 50 characters"
            }
        },
        contactPerson : {
            presence : {message : "^Enter contact person name"},
            length : {
                maximum : 50,
                message  : "^Contact person name must be maximum 50 characters"
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
        type : {
            presence : {message : "^Select type"}
        },
        websiteLink : {
            format : {
                pattern : /(https?:\/\/(?:www\.|(?!www))[a-zA-Z0-9][a-zA-Z0-9-]+[a-zA-Z0-9]\.[^\s]{2,}|www\.[a-zA-Z0-9][a-zA-Z0-9-]+[a-zA-Z0-9]\.[^\s]{2,}|https?:\/\/(?:www\.|(?!www))[a-zA-Z0-9]+\.[^\s]{2,}|www\.[a-zA-Z0-9]+\.[^\s]{2,})|[-a-zA-Z0-9@:%._\+~#=]{1,256}\.[a-zA-Z0-9()]{1,6}\b([-a-zA-Z0-9()@:%_\+.~#?&//=]*)/,
                message : "^Enter valid url"
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

        let picError = document.createElement("label");
        picError.id = "picError";
        picError.classList.add("text-danger");

        let contactPersonError = document.createElement("label");
        contactPersonError.id = "contactPersonError";
        contactPersonError.classList.add("text-danger");

        let phoneError = document.createElement("label");
        phoneError.id = "phoneError";
        phoneError.classList.add("text-danger");

        let emailError = document.createElement("label");
        emailError.id = "emailError";
        emailError.classList.add("text-danger");

        let typeError = document.createElement("label");
        typeError.id = "typeError";
        typeError.classList.add("text-danger");

        let websiteLinkError = document.createElement("label");
        websiteLinkError.id = "websiteLinkError";
        websiteLinkError.classList.add("text-danger");

        if (validation) {
            checkValidation(codeError, "codeError", "code", validation.code);
            checkValidation(nameError, "nameError", "name", validation.name);
            checkValidation(picError, "picError", "pic", validation.pic);
            checkValidation(contactPersonError, "contactPersonError", "contactPerson", validation.contactPerson);
            checkValidation(phoneError, "phoneError", "phone", validation.phone);
            checkValidation(emailError, "emailError", "email", validation.email);
            checkValidation(typeError, "typeError", "type", validation.type);
            checkValidation(websiteLinkError, "websiteLinkError", "websiteLink", validation.websiteLink);
        } else {
            form.submit();
        }
        
    });
});