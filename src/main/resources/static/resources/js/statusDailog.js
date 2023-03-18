function showChangeStatusDialog(id) {

    $("#applicantStatusCheck").prop("checked", false);
    $("#applicantStatusWrapper").addClass("d-none");

    $.ajax({
        type: "GET",
        url: "/interview/status/change/api?id=" + id,
        dataType: "json",
        success: function (data) {       
            console.log(data);
            let form = $("#changeStatusForm");
            let applicantIdInput = form.find("input[name='id']");
            applicantIdInput.val(data.id);
            

            let statusSelect = form.find("select[name='status']");
            statusSelect.val(data.status);

            let remarkTextarea = form.find("textarea[name='comment']");
            remarkTextarea.val(data.comment);

            let applicantStatusSelect = form.find("select[name='applicantStatus']");
            applicantStatusSelect.val(data.applicantStatus);

            let applicantRemarkTextarea = form.find("textarea[name='applicantStatusComment']");
            applicantRemarkTextarea.val("");

            let modelElement=document.getElementById("changeStatusModal");
            modelElement.style.display = "block";
            window.onclick = function(event) {
                if (event.target == modelElement) {
                    modelElement.style.display = "none";
                }
            }

            let selectedStatus = data.status;
            let selectedApplicantStatus = data.applicantStatus;
            let btnSubmit = $('#btnStatusSubmit');
            btnSubmit.addClass("disabled");
           $('#status1').on('change', function(event) {
                let currentApplicantStatus = $("#applicantStatus").val();
                let changeValue = $("#status1").val();
                if ((changeValue == selectedStatus && remarkTextarea.val() == data.comment) || ($("#applicantStatusCheck").is(":checked") && (selectedApplicantStatus == currentApplicantStatus || currentApplicantStatus == null))) {
                    btnSubmit.addClass("disabled");
                } else {
                    btnSubmit.removeClass("disabled");
                }
            });

            $("#applicantStatusCheck").on('change', function(event) {
                let currentApplicantStatus = $("#applicantStatus").val();
                let changeValue = $("#status1").val();
                if ((changeValue == selectedStatus && remarkTextarea.val() == data.comment) || ($("#applicantStatusCheck").is(":checked") && (selectedApplicantStatus == currentApplicantStatus || currentApplicantStatus == null))) {
                    btnSubmit.addClass("disabled");
                } else {
                    btnSubmit.removeClass("disabled");
                }

                if (!$("#applicantStatusCheck").is(":checked")) {
                    applicantStatusSelect.val(data.applicantStatus);
                    applicantRemarkTextarea.val("");
                }
            });

            $("#applicantStatus").on('change', function(event) {
                let currentApplicantStatus = $("#applicantStatus").val();
                let changeValue = $("#status1").val();
                if ((changeValue == selectedStatus && remarkTextarea.val() == data.comment) || ($("#applicantStatusCheck").is(":checked") && (selectedApplicantStatus == currentApplicantStatus || currentApplicantStatus == null))) {
                    btnSubmit.addClass("disabled");
                } else {
                    btnSubmit.removeClass("disabled");
                }
            });

            remarkTextarea.on('input', function(event) {
                let currentApplicantStatus = $("#applicantStatus").val();
                let changeValue = $("#status1").val();
                if ((changeValue == selectedStatus && remarkTextarea.val() == data.comment) || ($("#applicantStatusCheck").is(":checked") && (selectedApplicantStatus == currentApplicantStatus || currentApplicantStatus == null))) {
                    btnSubmit.addClass("disabled");
                } else {
                    btnSubmit.removeClass("disabled");
                }
            });
        },
        error: function (e) {
            console.log('error');
        }
    });
}
function closeModal(id) {
    let modelElement=document.getElementById(id);
    modelElement.style.display = "none";
}
