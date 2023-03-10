

// When the user clicks the button, open the modal 
function showDialog(id) {
	
   var modelElement=document.getElementById("modal"+id);
   modelElement.style.display = "block";
   window.onclick = function(event) {
    if (event.target == modelElement) {
       modelElement.style.display = "none";
    }
    
}
 
	}
function closeDialog(id) {
   var modelElement=document.getElementById("modal"+id);
   modelElement.style.display = "none";
}

function addInterviewName()
{
	document.getElementById("interviewTitle").innerHTML="Add Interview Title";
	var interviewName=document.getElementById("interviewNameId");
	interviewName.style.display="block";
}
function closeInterviewName()
{
	var interviewName=document.getElementById("interviewNameId");
	interviewName.style.display="none";
}
function editInterviewName(id,name) {
	document.getElementById("interviewTitle").innerHTML="Edit Interview Title";
   var modelElement=document.getElementById("interviewId");
   modelElement.value = id;
    var name1=document.getElementById("interviewName");
   name1.value = name;
   var interviewName=document.getElementById("interviewNameId");
	interviewName.style.display="block";
}




