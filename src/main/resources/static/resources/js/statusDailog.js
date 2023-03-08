// When the user clicks the button, open the modal 
function showStatusDialog(id) {
	
   var modelElement=document.getElementById("modal1"+id);
   modelElement.style.display = "block";
   window.onclick = function(event) {
    if (event.target == modelElement) {
       modelElement.style.display = "none";
    }
    
}
 
	}
function closeStatusDialog(id) {
   var modelElement=document.getElementById("modal1"+id);
   modelElement.style.display = "none";
}