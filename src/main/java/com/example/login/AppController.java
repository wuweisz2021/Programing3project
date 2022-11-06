package com.example.login;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class AppController {
	//u11/5/22k
	@Autowired
	private UserService service;

	@Autowired
	private UserRepository userRepo;

	@GetMapping("")
	public String viewHomePage() {
		
		return "home";
	}

	@GetMapping("/register")
	public String showRegistrationForm(Model model) {
		model.addAttribute("user", new User());
		return "signup_form";
	}

	@PostMapping(path = "/process_register", consumes = "application/x-www-form-urlencoded")
	public String processRegister(@Valid @ModelAttribute("user") User user, BindingResult bindingResult, Model model) {
		BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
		String encodedPassword = passwordEncoder.encode(user.getPassword());
		user.setPassword(encodedPassword);

		if (bindingResult.hasErrors()) {
			return "signup_form";
		}

		userRepo.save(user);
		return "register_success";
	}

	@GetMapping("/users")
	public String listUsers(Model model) {
		List<User> listUsers = userRepo.findAll();
		model.addAttribute("listUsers", listUsers);
		return "users";
	}

	@RequestMapping(value = "/save", method = RequestMethod.POST)
	public String saveUser(@ModelAttribute("user") User user) {
		service.save(user);
		return "redirect:/users";
	}

	@RequestMapping("/edit/{id}")
	public ModelAndView showEditUserPage(@PathVariable(name = "id") String id) {
		ModelAndView mav = new ModelAndView("edit_user");
		User user = service.get(id);
		mav.addObject("user", user);
		return mav;
	}

	@RequestMapping("/delete/{id}")
	public String deleteUser(@PathVariable(name = "id") String id) {
		service.delete(id);
		return "redirect:/users";
	}

	/// order
	@Autowired
	private OrderService orderService;
	@Autowired
	private OrderRepository orderRepo;

	@RequestMapping("/newOrder")
	public String showNewOrderPage(Model model) {
		Order order = new Order();
		model.addAttribute("order", order);

		List<String> paymentOptions = new ArrayList<String>();
		paymentOptions.add("Visa/Mastercard");
		paymentOptions.add("E-transfer");
		paymentOptions.add("PayPal");
		paymentOptions.add("Gift Card");
		model.addAttribute("paymentOptions", paymentOptions);

		List<Park> listParks = parkRepo.findAll();
		model.addAttribute("listParks", listParks);
		return "new_order";
	}

	@GetMapping("/orders")
	public String listOrders(Model model) {
		List<Order> listOrders = orderService.listAllOrder();

		model.addAttribute("listOrders", listOrders);
		return "orders";
	}

	@RequestMapping(value = "/saveOrder", method = RequestMethod.POST)
	public String saveOrder(@Valid @ModelAttribute("order") Order order, BindingResult bindingResult, Model model) {

		Order new_order = order;
		Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();

		String sellerEmail = null;
		if (principal instanceof UserDetails) {
			sellerEmail = ((UserDetails) principal).getUsername();
		} else {
			sellerEmail = principal.toString();
		}
		User user = userRepo.findByEmail(sellerEmail);
		new_order.setUser(user);
		orderRepo.save(new_order);
		System.out.println(new_order.toString());

		return "redirect:/orders";
	}

	@RequestMapping("/editOrder/{orderId}")
	public ModelAndView showEditOrderPage(@PathVariable(name = "orderId") Long orderId) {
		ModelAndView mav = new ModelAndView("edit_order");
		Order order = orderService.get(orderId);
		mav.addObject("order", order);
		return mav;
	}

	@RequestMapping("/deleteOrder/{orderId}")
	public String deleteOrder(@PathVariable(name = "orderId") Long orderId) {
		orderService.deleteOrder(orderId);
		return "redirect:/orders";
	}

	// park

	@Autowired
	private ParkService parkService;

	@Autowired
	private ParkRepository parkRepo;

	@RequestMapping("/newPark")
	public String showNewProductPage(Model model) {
		Park park = new Park();
		model.addAttribute("park", park);
		return "new_park";
	}

	@GetMapping("/parks")
	public String listParks(Model model) {
		// for testing
		/*
		 * Park x = new Park(); x.setAddress("123 street"); x.setIntroduction("abc");
		 * x.setParkName("Centennial Park"); x.setPrice((float) 20);
		 * x.setParkId("10000"); parkService.savePark(x);
		 * 
		 * Park y = new Park(); y.setAddress("1234 street"); y.setIntroduction("abcd");
		 * y.setParkName("Autumn Park"); y.setPrice((float) 33); y.setParkId("20000");
		 * parkService.savePark(y);
		 */
		 
		List<Park> listParks = parkService.listAllPark();
		model.addAttribute("listParks", listParks);
		return "parks";
	}

	@RequestMapping(value = "/savePark", method = RequestMethod.POST)
	public String savePark(@Valid @ModelAttribute("park") Park park, BindingResult bindingResult, Model model,
			@RequestParam("image") MultipartFile multipartFile) throws IOException {
		if (multipartFile != null) {
			try {
				String fileName = StringUtils.cleanPath(multipartFile.getOriginalFilename());
				park.setImage(fileName);

				Park savedPark = parkRepo.save(park);

				String uploadDir = "park-photos/" + savedPark.getParkId();

				FileUploadUtil.saveFile(uploadDir, fileName, multipartFile);
				System.out.println("Successfully added park: " + savedPark.toString());
			} catch (IOException e) {
				System.out.println("Problem with photo, skipping...");

			}

		} else {
			System.out.println("Photo is null, skipping verifications...");
		}
		return "redirect:/parks";

	}

	@RequestMapping("/editPark/{parkId}")
	public ModelAndView showEditParkPage(@PathVariable(name = "parkId") String parkId) {
		ModelAndView mav = new ModelAndView("edit_park");
		Park park = parkService.getPark(parkId);
		mav.addObject("park", park);
		return mav;
	}

	@RequestMapping("/deletePark/{parkId}")
	public String deletePark(@PathVariable(name = "parkId") String parkId) {
		parkService.deletePark(parkId);
		return "redirect:/parks";
	}

}
