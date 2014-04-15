package com.deadbeef.mpz2;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;

import com.firebase.client.ChildEventListener;
import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.GenericTypeIndicator;
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
	static ArrayList<String> commands = new ArrayList<String>();
	public static void main(String[] args)
	{
		HtmlPage page = null;
		final WebClient webClient = new WebClient();
		
		//connect to Zork
		 try{
			 page = webClient.getPage("http://www.web-adventures.org/cgi-bin/webfrotz?s=ZorkDungeon&n=29262");
		 }
		    
		 catch(Exception e)
		 {
			 System.out.println(e.getMessage());
		 }
		Firebase ref = new Firebase("https://brilliant-fire-4503.firebaseio.com/");
		Firebase comRef = ref.child("command");
		Firebase zorkRef = ref.child("zorktext");
		 zorkRef.setValue(page.getBody().asText().replace("Enter", ""));
		
		
		
		//add command listener events
		// Read data and react to changes
		comRef.addValueEventListener(new ValueEventListener() {

		    @Override
		    public void onDataChange(DataSnapshot snap) {
		    	String com = (String)snap.getValue();
		        if(com!=null&&!com.trim().isEmpty())
		        {
		        	commands.add(com);
		        }
		    }

			@Override
			public void onCancelled(FirebaseError arg0) {
				// TODO Auto-generated method stub
				
			}
		});	
			//update page loop
			while(true)
			{
				
				try{
				// Get the form that we are dealing with and within that form, 
			    // find the submit button and the field that we want to change.
					if(!commands.isEmpty()){
					    final HtmlForm form = page.getFormByName("f");
		
					    final HtmlSubmitInput button = form.getInputByValue("Enter");
					    final HtmlTextInput textField = form.getInputByName("a");
					    textField.setValueAttribute(commands.remove(0));
		
					    // Now submit the form by clicking the button and get back the second page.
					    page = button.click();
					    zorkRef.setValue(page.getBody().asText().replace("Enter", ""));
					}
				}
				catch(Exception e)
				{
					break;
				}
			}
		
		 
		    webClient.closeAllWindows();
		    System.exit(0);
	}
}
