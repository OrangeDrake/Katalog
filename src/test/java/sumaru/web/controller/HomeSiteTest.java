package sumaru.web.controller;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.web.FilterChainProxy;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import sumaru.config.HibernateConfig;
import sumaru.config.SecurityConfig;
import sumaru.config.WebConfig;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebAppConfiguration
@RunWith(SpringJUnit4ClassRunner.class)
@TransactionConfiguration(transactionManager = "transactionManager")
@ContextConfiguration(classes = { WebConfig.class, HibernateConfig.class,
		SecurityConfig.class })
public class HomeSiteTest {

	private MockMvc mockMvc;

	@Autowired
	private FilterChainProxy springSecurityFilterChain;

	@Autowired
	private WebApplicationContext ctx;

	@Before
	public void setup() {
		this.mockMvc = MockMvcBuilders.webAppContextSetup(this.ctx)
				.addFilters(this.springSecurityFilterChain).build();
	}

	@Test
	public void TestHome() throws Exception {		

		mockMvc.perform(get("/home")).andExpect(status().isOk())
				.andExpect(view().name("/home"))
		        .andExpect(model().size(3)); 	
	}

}