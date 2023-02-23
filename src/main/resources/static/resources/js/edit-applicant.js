$(document).ready(function () {
	$("#vacancy").change(function (event){
		ajaxQuerypositionByVacancy();
	});
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

const ajaxQuerypositionByVacancy = () => {
	
	let vacancyId= $('#vacancy').find(":selected").val();
	let data={};
	
	data['vacancyId']=vacancyId;
	$.ajax({
		type:"POST",
		contentType: "application/json",
		url: "/api/positionByVacancy/search",
		data: JSON.stringify(data),
		dataType: 'json',
		cache: false,
		timeout: 600000,
		success: function (data) {
			let options = "";
			for(let i=0;i<data.length;i++){
				let position=data[i];
				options += `<option value=${position.id}>${position.position.name}/${position.team.name}/${position.team.department.name}</option>`;			
			}
					
			let positionSelectWrapper = $("#position-select-wrapper");
			positionSelectWrapper.html(`
                <option value=''>-- select a team --</option>
                ${options}`);
  		},
        error: function (e) {

            console.log('error');
        }
    }
);

};