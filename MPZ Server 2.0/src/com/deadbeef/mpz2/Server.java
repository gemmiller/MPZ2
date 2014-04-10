package com.deadbeef.mpz2;

import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;
import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.HtmlForm;
import com.gargoylesoftware.htmlunit.html.HtmlInput;
import com.gargoylesoftware.htmlunit.html.HtmlPage;
import com.gargoylesoftware.htmlunit.html.HtmlSubmitInput;
import com.gargoylesoftware.htmlunit.html.HtmlTextInput;

public class Server {
	static HtmlInput input;
	static HtmlInput submit;
	public static void main(String[] args)
	{
		
		Firebase ref = new Firebase("https://brilliant-fire-4503.firebaseio.com/");

		// Write data to Firebase
		
		 final WebClient webClient = new WebClient();
		 try{
		    HtmlPage page = webClient.getPage("http://www.web-adventures.org/cgi-bin/webfrotz?s=ZorkDungeon&n=5567");
		    System.out.println(page.asText().replace("Enter", ""));
		 // Get the form that we are dealing with and within that form, 
		    // find the submit button and the field that we want to change.
		    final HtmlForm form = page.getFormByName("f");

		    final HtmlSubmitInput button = form.getInputByValue("Enter");
		    final HtmlTextInput textField = form.getInputByName("a");
		    textField.setValueAttribute("go west");

		    // Now submit the form by clicking the button and get back the second page.
		    page = button.click();
		    //send data to firebase
		    ref.child("zorktext").setValue(page.getBody().asText().replace("Enter", ""));
		    System.out.println(page.getBody().asText().replace("Enter", ""));
		 }
		    
		 catch(Exception e)
		 {
			 System.out.println(e.getMessage());
		 }
		    webClient.closeAllWindows();
		    System.exit(0);
	}
}
