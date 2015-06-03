package sumaru.persistence.service;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import sumaru.persistence.domain.Ad;
import sumaru.persistence.domain.Category;
import sumaru.persistence.domain.User;
import sumaru.persistence.repository.UserDao;
import sumaru.web.domain.UserDetails;

@Service
public class UserServiceHandler implements UserService {
	
	@Autowired
	BCryptPasswordEncoder encoder;

	@Autowired
	private UserDao userDao;

	@Autowired
	UserDetailsService userDetailsService;

	@Autowired
	protected AuthenticationManager authenticationManager;

	@Transactional
	public void authorize() {

		Authentication auth = SecurityContextHolder.getContext()
				.getAuthentication();

		User user = userDao.findByUserName(auth.getName());

		user.setRole("ROLE_USER");
		userDao.insert(user);

		authenticateUser(UserDetails.toUserDetails(user));
	}
	
	@Transactional
	public void deleteUser(long id) {

		User user = userDao.findByUserId(id);
		Set<Ad> ads = user.getAds();

		for (Ad ad : ads) {

			Category category = ad.getCategory();
			category.setCount((category.getCount()) - 1);
		}

		userDao.deleteUser(id);
	}

	@Transactional
	public List<UserDetails> getAllUsers() {

		List<User> users = userDao.allUsers();
		List<UserDetails> listUsersDetails = new LinkedList<UserDetails>();

		for (User user : users) {
			UserDetails userDetails = UserDetails.toUserDetails(user);
			listUsersDetails.add(userDetails);
		}

		return listUsersDetails;
	}

	@Transactional(readOnly = true)
	public UserDetails loadUserById(long id) {

		User user = userDao.findById(id);

		UserDetails userDetails = UserDetails.toUserDetails(user);

		return userDetails;
	}

	@Transactional(readOnly = true)
	public UserDetails loadUserByUsername(String username) {

		User user = userDao.findByUserName(username);

		UserDetails userDetails = UserDetails.toUserDetails(user);

		return userDetails;
	}

	@Transactional
	public void saveNewUser(UserDetails userDetails) {

		String password = userDetails.getPassword();
		userDetails.setRole("ROLE_PRE");
		userDetails.setPassword(encoder.encode(password));
		userDao.insert(User.fromUserDetails(userDetails));
		authenticateUser(userDetails);
	}

	@Transactional
	public void saveUser(UserDetails userDetails) {

		userDao.update(userDetails);
	}

	@Transactional
	public boolean isUnique(String name) {
		return userDao.isUserUnique(name);
	}

	private void authenticateUser(UserDetails user) {

		
		List<GrantedAuthority> aut = new ArrayList<GrantedAuthority>();
		aut.add(new SimpleGrantedAuthority(user.getRole()));
		
		Authentication authentication = new UsernamePasswordAuthenticationToken(user.getName(), user.getPassword(),aut);
		SecurityContextHolder.getContext().setAuthentication(authentication);
	}

}
