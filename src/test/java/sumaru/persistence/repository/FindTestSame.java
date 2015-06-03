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

import java.util.List;

import static junit.framework.TestCase.assertNotNull;
import static org.junit.Assert.*;


@RunWith(SpringJUnit4ClassRunner.class)
@TransactionConfiguration(transactionManager="transactionManager")
@ContextConfiguration(classes = {HibernateConfig.class, SecurityConfig.class})
public class FindTestSame {

	@Autowired
	AdDao adDao;
	
	@Autowired
	UserDao userDao;
	
	@Transactional
	@Before
	public void insert(){
		User user = new User();
		user.setId(66);
		user.setName("Petr");
		
		userDao.insert(user);
		
		Ad ad = new Ad();
		ad.setId(666);
		ad.setText("ahoj");
		ad.setUser(user);

		adDao.insert(ad);
		
		Ad ad2 = new Ad();
		ad2.setId(555);
		ad2.setText("ahoj222");
		ad2.setUser(user);

		adDao.insert(ad2);		
	}	
	
	@Transactional
	@Test
	public void sameAd() throws Exception {		

		Ad adT = adDao.recentAds().get(0);
		Ad adT2 = adDao.recentAds().get(0);

		assertNotNull(adT);
	    assertSame(adT,adT2);
	    
	    System.out.println(adDao.recentAds().size());

	}
	
	@Transactional
	@Test
	public void notSameList() throws Exception {		

		List<Ad> list = adDao.recentAds();
		List<Ad>list2 = adDao.recentAds();
		
	    assertNotSame(list,list2);
	    assertSame(list.get(0),list2.get(0));
	    
	    System.out.println(list.size());
	}
	
	@Transactional
	@Test
	public void sameUser() throws Exception {
		

		List<Ad> list = adDao.recentAds();			
	    
	    assertSame(list.get(0).getUser(),list.get(1).getUser());
	    
	    System.out.println(list.size());	    
	}	
	
}

