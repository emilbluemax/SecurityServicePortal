package com.example.eventManagement.controller;

import java.util.Iterator;
import java.net.*;
import java.io.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import com.example.eventManagement.model.Event;
import com.example.eventManagement.model.Feedback;
import com.example.eventManagement.model.Guard;
import com.example.eventManagement.model.User;
import com.example.eventManagement.model.Message;
import com.example.eventManagement.model.Payment;

import com.example.eventManagement.factory.*;

import com.example.eventManagement.repository.EventRepository;
import com.example.eventManagement.repository.GuardRepository;
import com.example.eventManagement.repository.UserRepository;

import com.example.eventManagement.service.EventService;
import com.example.eventManagement.service.GuardService;
import com.example.eventManagement.service.PaymentService;

import jakarta.servlet.http.HttpServletRequest;


@Controller
public class EventController {

	@Autowired
	private EventService eventservice;

	@Autowired
	private UserRepository repo;

	@Autowired
	private EventRepository event_repo;
	
	@Autowired
	private GuardRepository guard_repo;

	@Autowired
	private GuardService GuardService;
	
	@Autowired
	private PaymentService paymentService;

	private static String mail;
	private static long eventid; 

	
	//*************************************** Index page  *********************************************
	@GetMapping("/")
	public String index() {
		return "index";
	}

	//*************************************** User Register *********************************************
	@GetMapping("/register")
	public String showRegistrationForm(Model model) {
		model.addAttribute("user", new User());

		return "signup_form";
	}

	@PostMapping("/process_register")
	public String processRegister(User user) {
		//BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
		//String encodedPassword = passwordEncoder.encode(user.getPassword());
		//user.setPassword(encodedPassword);

		repo.save(user);

		return "index";
	}

	//*************************************** User Login *********************************************
	@GetMapping("/login")
	public String showLoginForm(Model model) {
		model.addAttribute("user", new User());

		return "login_form";
	}

	@PostMapping("/process_login")
	public String processLogin(HttpServletRequest request ,User user, Model model) {
		//BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
		//String encodedPassword = passwordEncoder.encode(user.getPassword());
		//user.setPassword(encodedPassword);

		String email = user.getEmail();
		String pass = user.getPassword();

		User temp = repo.validate(email,pass);
		if(temp == null) {
			return "redirect:/";
		}

		//String name = request.getParameter("email");
		//System.out.println("******* Session mail *****"+name);
		//model.addAttribute("session.user", user);
		mail = email;

		return "redirect:/userHome";
	}
	
	//*************************************** Guard Login *********************************************
	@GetMapping("/loginGuard")
	public String showGuardLoginForm(Model model) {
		model.addAttribute("guard", new Guard());

		return "guard_login_form";
	}
	
	@PostMapping("/process_login_guard")
	public String processGuardLogin(Guard guard, Model model) {
		//BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
		//String encodedPassword = passwordEncoder.encode(user.getPassword());
		//user.setPassword(encodedPassword);

		String email = guard.getEmail();
		String pass = guard.getPassword();

		Guard temp = guard_repo.validate(email,pass);
		if(temp == null) {
			return "redirect:/";
		}

		//String name = request.getParameter("email");
		//System.out.println("******* Session mail *****"+name);
		//model.addAttribute("session.user", user);
		mail = email;

		return "redirect:/guardHome";
	}
	//********************************** Home Pages *********************************************
	@GetMapping("/userHome")
	public String UserHome() {
		return "user_home";
	}

	@GetMapping("/admin")
	public String AdminHome() {
		mail = "admin@gmail.com";
		return "admin_home";
	}
	
	@GetMapping("/guardHome")
	public String GuardHome() {
		return "guard_home";
	}

	//************************************* Feedback **********************************************
	@GetMapping("/listFeedback")
	public String listFeedback(Model model) {
		model.addAttribute("listFeedback",eventservice.getAllFeedback());
		return "listFeedback";
	}

	@GetMapping("/showNewFeedbackForm")
	public String showNewFeedbackForm(Model model) {
		// create model attribute to bind form data
		ModelFactory modelfac = ModelFactory.myModel();
		//Feedback feedback = new Feedback();
		Feedback feedback = (Feedback)modelfac.createModel("Feedback");
		
		model.addAttribute("feedback", feedback);
		return "new_feedback";
	}

	@PostMapping("/saveFeedback")
	public String saveFeedback(@ModelAttribute("feedback") Feedback feedback) {
		// save event to database
		eventservice.saveFeedback(feedback);
		return "redirect:/userHome";
	}
	//************************************* Event CRUD ************************************************

	// display list of events
	@GetMapping("/listEvents")
	public String listEvents(Model model) {
		//String mail = model.getAttribute("email").toString();
		
		
		
		//System.out.println("******* Session mail *****"+mail);
		if(mail.equals("admin@gmail.com")) {
			model.addAttribute("listEvent",eventservice.getAllEvents());
		}
		else {
			long userid = repo.getId(mail);
			model.addAttribute("listEvent",event_repo.getEventById(userid));
		}

		return "listEvents";
	}
		
	@GetMapping("/showNewEventForm")
	public String showNewEventForm(Model model) {
		// create model attribute to bind form data
		ModelFactory modelfac = ModelFactory.myModel();
		//Event event = new Event();
		Event event = (Event)modelfac.createModel("Event");
		model.addAttribute("event", event);
		return "new_event";
	}

	@PostMapping("/saveEvent")
	public String saveEvent(@ModelAttribute("event") Event event) {
		// save event to database
		long userid = repo.getId(mail);
		//System.out.println("mail : "+mail);
		
		event.setUserId(userid);
		//System.out.println("User ID : "+event.getUserId());
		eventservice.saveEvent(event);
		eventid = event.getId();
		System.out.println("User ID : "+eventid);
		return "redirect:/addPayment";
	}

	@GetMapping("/deleteEvent/{id}")
    public String deleteEvent(@PathVariable(value="id") long id) {
        // call delete event method
        this.eventservice.deleteEventById(id);
        return "redirect:/listEvents";

    }

	@GetMapping("/showFormForUpdate/{id}")
    public String showFormForUpdate(@PathVariable(value = "id") long id, Model model) {
        // get event from the service
        Event event = eventservice.getEventById(id);

        //set event as a model attribute to pre-populate the form
        model.addAttribute("event", event);
        return "update_event";
    }

	//********************************* Guard CRUD **************************************
	@GetMapping("/showNewGuardForm")
	public String showNewGuardForm(Model model) {
		// create model attribute to bind form data
		ModelFactory modelfac = ModelFactory.myModel();
		//Guard Guard = new Guard();
		Guard Guard = (Guard)modelfac.createModel("Guard");
		model.addAttribute("Guard", Guard);
		return "new_Guard";
	}

	@PostMapping("/saveGuard")
	public String saveGuard(@ModelAttribute("Guard") Guard Guard) {
		// save Guard to database
		GuardService.saveGuard(Guard);
		if(mail.equals("admin@gmail.com")) {
			return "redirect:/admin";
		}
		return "redirect:/guardHome";
	}

	@GetMapping("/listGuards")
	public String listGuard(Model model) {
		if(mail.equals("admin@gmail.com")) {
			model.addAttribute("listGuards",GuardService.getAllGuards());
			return "listGuards";
		}
		else {
			long guardid = guard_repo.getId(mail);
			model.addAttribute("listGuards",guard_repo.getGuardById(guardid));
			return "listGuardLocation";
		}
		
		
	}

	@GetMapping("/showGuardFormForUpdate/{id}")
	public String showGuardFormForUpdate(@PathVariable ( value = "id") long id, Model model) {

		// get Guard from the service
		Guard Guard = GuardService.getGuardById(id);

		// set Guard as a model attribute to pre-populate the form
		model.addAttribute("Guard", Guard);
		return "update_Guard";
	}
	
	@GetMapping("/reject/{id}")
	public String rejectAssignment(@PathVariable ( value = "id") long id, Model model) {

		// get Guard from the service
		Guard Guard = GuardService.getGuardById(id);
		Guard.setLocation("NA");
		// set Guard as a model attribute to pre-populate the form
		model.addAttribute("Guard", Guard);
		return "rejectAssignment";
	}

	@GetMapping("/deleteGuard/{id}")
	public String deleteGuard(@PathVariable (value = "id") long id) {

		// call delete Guard method
		this.GuardService.deleteGuardById(id);
		return "redirect:/admin";
	}
	
	//********************************* Messages **************************************
	
	@GetMapping("/addMessage")
	public String addMessage(Model model) {
		// create model attribute to bind form data
		ModelFactory modelfac = ModelFactory.myModel();
		//Message message=new Message();
		Message message=(Message)modelfac.createModel("Message");
		
		model.addAttribute("message", message);
		return "new_message";
	}
	@GetMapping("/listMessage")
	public String showMessage(Model model) {
		// create model attribute to bind form data
		model.addAttribute("listmessage",GuardService.getAllmessages());
		return "view_message";
		
	}
	@PostMapping("/saveMessage")
	public String savemessage(@ModelAttribute("message") Message message) {
		// save Guard to database
		GuardService.savemessage(message);
		return "admin_home";
	}
	
	//********************************* Payment **************************************
	
	@GetMapping("/addPayment")
	public String addPayment(Model model) {
		// create model attribute to bind form data
		ModelFactory modelfac = ModelFactory.myModel();
		//Payment payment=new Payment();
		Payment payment = (Payment)modelfac.createModel("Payment");
		//System.out.println("Event ID "+ eventid);
		
		
		model.addAttribute("payment", payment);
		return "new_payment";
	}
	
	@GetMapping("/listPayment")
	public String showPayment(Model model) {
		// create model attribute to bind form data
		model.addAttribute("listpayment",paymentService.getAllpayments());
		return "view_payment";
		
	}
	
	@GetMapping("/recordDownload")
	public String TransactionalRecordDownload() {
		// create model attribute to bind form data
		
		Iterator<Payment> iterator = paymentService.getAllpayments().iterator();
		String TransactionRecords = "";
		
		while (iterator.hasNext()) {
			Payment pay = iterator.next();
	        // do something with event
	        //System.out.println("Event ID : "+event.getId());
			TransactionRecords +=  pay.getDate() + "," + pay.getTransaction_id() + "," + pay.getCard_number() +  "," + Long.toString(pay.getEventId()) + "," + String.valueOf(pay.getAmount()) + "\n";
	    }
		try {
	            FileWriter writer = new FileWriter("TransactionRecords.txt");
	            writer.write(TransactionRecords);
	            writer.close();
	            System.out.println("Successfully wrote to the file.");
	        } catch (IOException e) {
	            System.out.println("An error occurred.");
	            e.printStackTrace();
	        }
		return "downloadRecord";
		
	}
	
	@PostMapping("/savePayment")
	public String savepayment(@ModelAttribute("payment") Payment payment) {
		// save Guard to database
		
		payment.setEventId(eventid);
		long userid = repo.getId(mail);
		payment.setUserId(userid);
		
		paymentService.savepayment(payment);
		return "redirect:/userHome";
		
		
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	

}
