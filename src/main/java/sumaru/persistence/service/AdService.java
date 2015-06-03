package sumaru.persistence.service;


import java.util.List;

import sumaru.web.domain.AdDetails;
import sumaru.web.domain.CategoryDetails;

public interface AdService {	
	
	public void addCategory(String element);
	public List<CategoryDetails>getAllCategories();	
	public List<AdDetails> getAdsbyLoggedUser();
	public List<AdDetails> getAdsbyUserId(long id);
	public List<AdDetails> getAdsInCategory(int id);
	public CategoryDetails getCategory(int id);
	public List<AdDetails> getRecentAds();
	public void removeAd(long id);
	public void removeCategory(int id);
	public void saveAd (AdDetails adDetails, int category_id);
	public void saveCategory(int id, String element);	

}
