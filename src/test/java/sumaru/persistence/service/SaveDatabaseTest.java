
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
import sumaru.persistence.service.AdService;
import sumaru.web.domain.CategoryDetails;

import java.util.List;

@RunWith(SpringJUnit4ClassRunner.class)
@TransactionConfiguration(transactionManager = "transactionManager")
@ContextConfiguration(classes = { HibernateConfig.class, SecurityConfig.class })
public class SaveDatabaseTest {	
	
	@Autowired
	AdService adService;	
	
	@Test
	public void SericeSaveandLoadCategory() throws Exception {

		adService.addCategory("Prodej");
		List<CategoryDetails> categories = adService.getAllCategories();
		List<CategoryDetails> categories2 = adService.getAllCategories();		
		
		assertEquals(1, categories.size());
		assertEquals("Prodej", categories.get(0).getElement());
		assertNotSame(categories.get(0), categories2.get(0));
	}
	
}
