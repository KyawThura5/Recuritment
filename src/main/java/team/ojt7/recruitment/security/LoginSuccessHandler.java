package team.ojt7.recruitment.security;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import team.ojt7.recruitment.model.entity.User;
import team.ojt7.recruitment.model.entity.User.Role;
import team.ojt7.recruitment.model.repo.UserRepo;

@Component
public class LoginSuccessHandler implements AuthenticationSuccessHandler {
	
	@Autowired
	private UserRepo userRepo;

	@Override
	public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
			Authentication authentication) throws IOException, ServletException {
		HttpSession session = (HttpSession) request.getSession(true);
		User user = userRepo.findOneByCode(request.getParameter("employeeCode")).get();
		session.setAttribute("loginUser", user);
		SecurityContext securityContext = SecurityContextHolder.getContext();
		List<GrantedAuthority> authorities = new ArrayList<>();
		authorities.add(new SimpleGrantedAuthority(user.getRole().name()));
		if (user.getRole() != Role.ADMIN) {
			authorities.add(new SimpleGrantedAuthority("MANAGER"));
		}
		
		IdUsernamePasswordAuthentication auth = new IdUsernamePasswordAuthentication(
				user.getId(), user.getCode(),
				user.getPassword(),
				authorities);
		securityContext.setAuthentication(auth);
		response.sendRedirect(request.getServletContext().getContextPath() + "/");
	}

}
