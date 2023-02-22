$(document).ready(function () {

	
});

const onCvFileChange = () => {
    let fileInput = $("#cvFileInput");
    let fileNameInput = $("#cvFileNameInput");
    let attachedFileNameInput = $("#attachedFileNameInput");
    let file = fileInput[0].files[0];
    if (file) {
        fileNameInput.val(file.name)
    } else {
        fileNameInput.val(attachedFileNameInput.val());
    }
}