package team.ojt7.recruitment.model.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;

import team.ojt7.recruitment.model.dto.UserDto;
import team.ojt7.recruitment.model.entity.User;
import team.ojt7.recruitment.model.entity.User.Role;
import team.ojt7.recruitment.model.repo.UserRepo;
import team.ojt7.recruitment.model.service.UserService;

public class UserServiceImpl implements UserService {

	
	@Autowired
	UserRepo userRepo;
	@Override
	public List<UserDto> search(String keyword, Role role) {
		List<User> users=userRepo.search(keyword, role);
		List<UserDto> userDtos=new ArrayList<>();
		
		for(User user:users) {
			
			UserDto userDto=new UserDto();
			userDto.setId(user.getId());
			userDto.setCode(user.getCode());
			userDto.setName(user.getName() );
			userDto.setEmail(user.getEmail());
			userDto.setPhone(user.getPhone());
			userDto.setPassword(user.getPassword());
			userDto.setRole(user.getRole());
			
			userDtos.add(userDto);
		}
		return userDtos;
	}

	@Override
	public Optional<UserDto> findById(Long id) {
		// TODO Auto-generated method stub
		User user=userRepo.findById().orElse(null);
		
		UserDto userDto=new UserDto();
		userDto.setId(user.getId());
		userDto.setCode(user.getCode());
		userDto.setName(user.getName() );
		userDto.setEmail(user.getEmail());
		userDto.setPhone(user.getPhone());
		userDto.setPassword(user.getPassword());
		userDto.setRole(user.getRole());
		
		return Optional.ofNullable(userDto);
	}

	@Override
	public UserDto save(User user) {
		// TODO Auto-generated method stub
		
		if(user.getId()==null) {
		User saveuser=userRepo.create(user);
		
		UserDto userDto=new UserDto();
		userDto.setId(saveuser.getId());
		userDto.setCode(saveuser.getCode());
		userDto.setName(saveuser.getName() );
		userDto.setEmail(saveuser.getEmail());
		userDto.setPhone(saveuser.getPhone());
		userDto.setPassword(saveuser.getPassword());
		userDto.setRole(saveuser.getRole());
		
		return userDto;
		}
		else {
			User updateUser=userRepo.update(user);
			UserDto userDto=new UserDto();
			userDto.setId(updateUser.getId());
			userDto.setCode(updateUser.getCode());
			userDto.setName(updateUser.getName() );
			userDto.setEmail(updateUser.getEmail());
			userDto.setPhone(updateUser.getPhone());
			userDto.setPassword(updateUser.getPassword());
			userDto.setRole(updateUser.getRole());
			
			return userDto;
		}
	}

	@Override
	public boolean deleteById(Long id) {
		userRepo.deleteById(id);
		return true;
	}

}
