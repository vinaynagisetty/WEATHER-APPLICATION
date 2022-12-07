package com.weather.weatherbackend;

import com.weather.weatherbackend.domain.User;
import com.weather.weatherbackend.domain.WishList;
import com.weather.weatherbackend.domain.cityDataSchema.City;
import com.weather.weatherbackend.repo.UserRepo;
import com.weather.weatherbackend.repo.WishListRepo;
import com.weather.weatherbackend.service.ApiDataService;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;

@SpringBootTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class WeatherBackendApplicationTests {

	@Autowired
	UserRepo userRepository;

	@Autowired
	WishListRepo favouriteRepository;

	@Autowired
	ApiDataService apiDataService;


	private String userName = "chaitanya";
	private String userPass = "chaitu@0428";
	private int favId = 1275339;
	@Test
	@Order(1)
	public void testCreateUser(){
		User user = new User();
		user.setPhone("9032458334");
		user.setName("chaitanya");
		user.setUserName(userName);
		user.setPassword(userPass);
		User usrData = userRepository.save(user);
		assertNotNull(userRepository.findById(usrData.getId()).get());
	}
	@Test
	@Order(2)
	public void testLoginUser(){
		assertNotNull(userRepository.findByUserNameAndPassword(userName, userPass).get());
	}


	@Test
	@Order(3)
	public void takeCityWeather(){
		City output = apiDataService.consumeCityApi("https://api.openweathermap.org/data/2.5/weather?q=mumbai&appid=f896b0fcb80ccea35259af44e58dd80a");
		assertNotNull(output);
	}

	@Test
	@Order(4)
	public void favoriteAdd(){
		int id = favId;
		String ids = ""+favId+"";
		WishList wish = new WishList(favId, "mumbai", 12.23,34.4, "22");
		WishList saveFav = favouriteRepository.save(wish);
		assertNotNull(favouriteRepository.findById(id).get());
	}

	@Test
	@Order(5)
	public void favoriteGet(){

		String favIds = "";
		List<WishList> data = this.favouriteRepository.findAll();
		assertNotNull(data);

	}

	@Test
	@Order(6)
	public void deleteFavorite(){
		favouriteRepository.deleteById(favId);
	assertTrue(true);
	}

	@Test
	@Order(7)
	public void testLogout(){
		User user = new User();
		user.setUserName(userName);
		user.setPassword(userPass);
		userRepository.deleteById(userRepository.findByUserNameAndPassword(userName, userPass).get().getId());
		assertTrue(true);
	}

}
