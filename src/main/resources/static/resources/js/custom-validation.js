const checkValidation = (errorLabel, errorLabelId, inputId, validation) => {
    if (validation) {
        let input = document.getElementById(inputId);
        if (!document.getElementById(errorLabelId)) {
            input.parentNode.insertBefore(errorLabel, input.nextSibling);
        }
        document.getElementById(errorLabelId).innerText = validation[0];
    } else {
        removeIfExists(errorLabelId);
    }
}

const checkServerValidation = (errorLabel, errorLabelId, inputId, validation) => {
    if (validation) {
        let input = document.getElementById(inputId);
        if (!document.getElementById(errorLabelId)) {
            input.parentNode.insertBefore(errorLabel, input.nextSibling);
        }
        document.getElementById(errorLabelId).innerText = validation;
    } else {
        removeIfExists(errorLabelId);
    }
}

const removeIfExists = (id) => {
    let element = document.getElementById(id);
    if (element) {
        element.parentNode.removeChild(element);
    }
}