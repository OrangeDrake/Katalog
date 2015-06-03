package sumaru.persistence.service;

import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import sumaru.persistence.domain.Ad;
import sumaru.persistence.domain.Category;
import sumaru.persistence.domain.User;
import sumaru.persistence.repository.AdDao;
import sumaru.persistence.repository.UserDao;
import sumaru.web.domain.AdDetails;
import sumaru.web.domain.CategoryDetails;

@Service
public class AdServiceHandler implements AdService {

	@Autowired
	private AdDao adDao;

	@Autowired
	BCryptPasswordEncoder encoder;

	@Autowired
	private UserDao userDao;

	@Transactional
	public void addCategory(String element) {
		Category category = new Category();
		category.setElement(element);
		category.setCount(0);
		adDao.adCategory(category);
	}

	@Transactional
	public List<CategoryDetails> getAllCategories() {

		List<Category> category = adDao.allCategories();
		List<CategoryDetails> listCategoryDetails = new LinkedList<CategoryDetails>();

		for (Category cate : category) {

			listCategoryDetails.add(CategoryDetails.toCategoryDetails(cate));
		}

		return listCategoryDetails;
	}

	private List<AdDetails> fromAdList(List<Ad> ads) {

		List<AdDetails> listAdDetails = new LinkedList<AdDetails>();
		AdDetails adDetails;

		for (Ad ad : ads) {
			adDetails = AdDetails.toAdDetails(ad);
			listAdDetails.add(adDetails);
		}

		return listAdDetails;
	}

	@Transactional
	public List<AdDetails> getAdsbyLoggedUser() {

		Authentication auth = SecurityContextHolder.getContext()
				.getAuthentication();

		String username = auth.getName();
		User user = userDao.findByUserNameAds(username);

		return fromAdList(new LinkedList<Ad>(user.getAds()));
	}

	
	@Transactional
	public List<AdDetails> getAdsbyUserId(long user_id) {

		List<Ad> ads = adDao.selectAll(user_id);

		return fromAdList(ads);
	}

	@Transactional
	public List<AdDetails> getAdsInCategory(int cate_id) {

		List<Ad> ads = adDao.getAdsInCategory(cate_id);
				
		return fromAdList(ads);
	}

	@Transactional
	public CategoryDetails getCategory(int id) {
		
		Category cate = adDao.getCategory(id);

		return CategoryDetails.toCategoryDetails(cate);
	}

	@Transactional
	public List<AdDetails> getRecentAds() {

		List<Ad> ads = adDao.recentAds();				

		return fromAdList(ads);
	}
			
	@Transactional
	public void removeAd(long id) {

		Category category = adDao.getAdbyId(id).getCategory();
		category.setCount((category.getCount()) - 1);
		String owner = adDao.removeAd(id);
		adDao.removeAd2(id,owner);		
	}

	@Transactional
	public void removeCategory(int id) {
		adDao.removeCategory(id);
	}

	@Transactional
	public void saveAd(AdDetails adDetails, int category_id) {
		Ad ad = Ad.fromAdDetails(adDetails);

		Authentication auth = SecurityContextHolder.getContext()
				.getAuthentication();

		String username = auth.getName();
		User user = userDao.findByUserName(username);

		Category category = adDao.getCategoryById(category_id);
		category.setCount((category.getCount()) + 1);

		ad.setCategory(category);
		ad.setUser(user);
		ad.setDate(new Date());
		adDao.insert(ad);
	}

	@Transactional
	public void saveCategory(int id, String element) {
		Category category = adDao.getCategoryById(id);
		category.setElement(element);
		adDao.saveCategory(category);
	}

}
