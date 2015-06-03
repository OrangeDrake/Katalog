package sumaru.persistence.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import sumaru.persistence.domain.Ad;
import sumaru.persistence.domain.User;
import sumaru.persistence.repository.AdDao;
import sumaru.persistence.repository.UserDao;

@Service
public class FakeService {	
	
	@Autowired
	AdDao adDao;
	
	@Autowired
	UserDao userDao;
	
	@Transactional
	public void modify() {

		Ad ad = adDao.recentAds().get(0);
		ad.setText("Mod ahoj");
	}
	
	@Transactional
	public Ad returnFirestAd() {
		Ad ad = adDao.recentAds().get(0);
		return ad;
	}
	
	@Transactional
	public void saveAd() {
		User user = new User();
		user.setId(1);
		user.setName("Petr");

		userDao.insert(user);

		Ad ad = new Ad();
		ad.setId(1);
		ad.setText("ahoj");
		ad.setUser(user);

		adDao.insert(ad);
	}

}
