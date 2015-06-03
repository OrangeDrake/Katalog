package sumaru.persistence.service;

import org.junit.Test;
import org.junit.runner.RunWith;



import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;

import static org.junit.Assert.*;
import sumaru.config.HibernateConfig;
import sumaru.config.SecurityConfig;
import sumaru.persistence.domain.Ad;

@RunWith(SpringJUnit4ClassRunner.class)
@TransactionConfiguration(transactionManager = "transactionManager")
@ContextConfiguration(classes = { HibernateConfig.class, SecurityConfig.class })
public class ModServiceTest {
	
	@Autowired
	FakeService fakeService;	
	
	@Test
	public void SericeSaveandLoadCategory() throws Exception {

		fakeService.saveAd();		
		Ad ad = fakeService.returnFirestAd();
		assertSame("ahoj", ad.getText());
		fakeService.modify();
		ad = fakeService.returnFirestAd();

		assertSame("Mod ahoj", ad.getText());			
	}
	
}
