package team.ojt7.recruitment.controller;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
@Controller
public class UserController {
	
    public void searchUsers() {
        // TODO implement here
    }

    @RequestMapping(value="/admin/user/add",method=RequestMethod.GET)
    public String addNewUser() {
    	return "adduser";
    }

    @RequestMapping(value="/admin/user/edit",method=RequestMethod.GET)
    public String editUser() {
		return "edituser";
    	
    }

    public void saveUser() {
    }

    public void showUserDetail() {
    }
    @RequestMapping(value="/admin/user/delete",method=RequestMethod.GET)
    public String deleteUser() {
		return "users";
    }
    @RequestMapping(value="/admin/user/all",method=RequestMethod.GET)
    public String AllUser() {
		return "users";
    	
    }

}