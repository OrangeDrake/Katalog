package sumaru.persistence.service;

import java.util.List;

import sumaru.web.domain.UserDetails;




public interface UserService {
	
	public void authorize();
	public void deleteUser(long id);
	public List<UserDetails> getAllUsers();
	public UserDetails loadUserById(long id);
	public UserDetails loadUserByUsername(String name);
	public void saveNewUser(UserDetails userDetails);
	public void saveUser(UserDetails userDetails);
	public boolean isUnique(String name);	

}
