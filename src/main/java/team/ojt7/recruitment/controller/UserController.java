package team.ojt7.recruitment.controller;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
@Controller
public class UserController {

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

    @RequestMapping(value="admin/user/detail",method=RequestMethod.GET)
    public String showUserDetail() {
		return "userdetail";
    	
    }
    @RequestMapping(value="/admin/user/delete",method=RequestMethod.GET)
    public String deleteUser() {
		return "users";
    }
    @RequestMapping(value="/admin/user/search",method=RequestMethod.GET)
    public String searchUsers() {
		return "users";
    	
    }

}