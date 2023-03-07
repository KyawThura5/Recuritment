

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




