package team.ojt7.recruitment.security;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import team.ojt7.recruitment.model.entity.User;
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
		IdUsernamePasswordAuthentication auth = new IdUsernamePasswordAuthentication(
				user.getId(), user.getCode(),
				user.getPassword(),
				List.of(new SimpleGrantedAuthority(user.getRole().name())));
		securityContext.setAuthentication(auth);
		response.sendRedirect(request.getServletContext().getContextPath() + "/");
	}

}
