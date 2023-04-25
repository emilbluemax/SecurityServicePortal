package com.example.eventManagement.factory;

import com.example.eventManagement.model.Event;
import com.example.eventManagement.model.Feedback;
import com.example.eventManagement.model.Guard;
import com.example.eventManagement.model.User;
import com.example.eventManagement.model.Message;
import com.example.eventManagement.model.Payment;

public class ModelFactory {
	
	private static ModelFactory myModelInstance = null;
	
	private ModelFactory() {
		
	}
	
	public static ModelFactory myModel() {
		if(myModelInstance == null) {
			myModelInstance = new ModelFactory();
		}
		return myModelInstance;
	}
	public Object createModel(String mod) {
		
		
        if (mod.equals("Event")) {
            return new Event();
        } else if (mod.equals("Feedback")) {
            return new Feedback();
        } else if (mod.equals("Guard")) {
            return new Guard();
        } else if (mod.equals("User")) {
            return new User();
        } else if (mod.equals("Message")) {
            return new Message();
        } else if (mod.equals("Payment")) {
            return new Payment();
        }else {
            throw new IllegalArgumentException("Invalid type: " + mod);
        }
    }
}
