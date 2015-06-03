package sumaru.persistence.repository;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.transaction.annotation.Transactional;

import sumaru.config.HibernateConfig;
import sumaru.config.SecurityConfig;
import sumaru.persistence.domain.Ad;
import sumaru.persistence.domain.User;
import sumaru.persistence.repository.AdDao;
import sumaru.persistence.repository.UserDao;
import sumaru.persistence.service.AdService;
import sumaru.persistence.service.FakeService;
import static org.junit.Assert.*;

@RunWith(SpringJUnit4ClassRunner.class)
@TransactionConfiguration(transactionManager = "transactionManager")
@ContextConfiguration(classes = { HibernateConfig.class, SecurityConfig.class })
public class ModifyTest {

	@Autowired
	AdDao adDao;

	@Autowired
	UserDao userDao;
	
	@Autowired
	FakeService fakeService;
	
	
	@Autowired
	AdService adService;

	@Transactional
	@Before
	public void insert() {
		User user = new User();
		user.setId(1);
		user.setName("Petr");

		userDao.insert(user);

		Ad ad = new Ad();
		ad.setId(1);
		ad.setText("ahoj");
		ad.setUser(user);

		adDao.insert(ad);

		Ad ad2 = new Ad();
		ad2.setId(2);
		ad2.setText("ahoj");
		ad2.setUser(user);

		adDao.insert(ad2);		
	}

	@Transactional
	@Test
	public void modAd() throws Exception {

		Ad ad = adDao.recentAds().get(0);

		assertSame("ahoj", ad.getText());

		modify();

		ad = adDao.recentAds().get(0);

		assertSame("Mod ahoj", ad.getText());
		
		System.out.println("mod " + adDao.recentAds().size());
	}
	
	private void modify() {

		Ad ad = adDao.recentAds().get(0);
		ad.setText("Mod ahoj");
	}

}
