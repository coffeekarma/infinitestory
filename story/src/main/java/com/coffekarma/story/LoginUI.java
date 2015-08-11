package com.coffekarma.story;

import javax.servlet.annotation.WebServlet;

import com.vaadin.annotations.Theme;
import com.vaadin.annotations.VaadinServletConfiguration;
import com.vaadin.annotations.Widgetset;
import com.vaadin.client.communication.Heartbeat;
import com.vaadin.navigator.Navigator;
import com.vaadin.navigator.ViewChangeListener;
import com.vaadin.server.VaadinRequest;
import com.vaadin.server.VaadinServlet;
import com.vaadin.server.communication.HeartbeatHandler;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Label;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Window;

/**
 *
 */
@Theme("mytheme")
@Widgetset("com.coffekarma.story.MyAppWidgetset")
public class LoginUI extends UI {

	 @Override
	    protected void init(VaadinRequest request) {

	        //
	        // Create a new instance of the navigator. The navigator will attach
	        // itself automatically to this view.
	        //
	        new Navigator(this, this);

	        //
	        // The initial log view where the user can login to the application
	        //
	        getNavigator().addView(SimpleLoginView.NAME, SimpleLoginView.class);//

	        //
	        // Add the main view of the application
	        //
	        getNavigator().addView(MainView.NAME,
	                MainView.class);

	        //
	        // We use a view change handler to ensure the user is always redirected
	        // to the login view if the user is not logged in.
	        //
	        getNavigator().addViewChangeListener(new ViewChangeListener() {

	            @Override
	            public boolean beforeViewChange(ViewChangeEvent event) {

	                // Check if a user has logged in
	                boolean isLoggedIn = getSession().getAttribute("user") != null;
	                boolean isLoginView = event.getNewView() instanceof SimpleLoginView;

	                if (!isLoggedIn && !isLoginView) {
	                    // Redirect to login view always if a user has not yet
	                    // logged in
	                    getNavigator().navigateTo(SimpleLoginView.NAME);
	                    return false;

	                } else if (isLoggedIn && isLoginView) {
	                    // If someone tries to access to login view while logged in,
	                    // then cancel
	                    return false;
	                }

	                return true;
	            }

	            @Override
	            public void afterViewChange(ViewChangeEvent event) {

	            }
	        });
	    }
	 
	    @WebServlet(value = "/*", asyncSupported = true)
	    @VaadinServletConfiguration(productionMode = false, ui = LoginUI.class, widgetset = "com.vaadin.cdi.tutorial.AppWidgetSet")
	    public static class Servlet extends VaadinServlet {
	    }

}
