package com.coffekarma.story;

import com.vaadin.data.validator.AbstractValidator;
import com.vaadin.data.validator.EmailValidator;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.shared.ui.MarginInfo;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.CustomComponent;
import com.vaadin.ui.PasswordField;
import com.vaadin.ui.TextField;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.themes.Reindeer;

public class SimpleLoginView extends CustomComponent implements View,
        Button.ClickListener {

    public static final String NAME = "login";

    private final TextField mUser;

    private final PasswordField mPassword;

    private final Button mLoginButton;

    public SimpleLoginView() {
        setSizeFull();

        // Create the user input field
        mUser = new TextField("User:");
        mUser.setWidth("300px");
        mUser.setRequired(true);
        mUser.setInputPrompt("Your username (eg. max@mustermann.com)");
        mUser.addValidator(new EmailValidator(
                "Username must be an email address"));
        mUser.setInvalidAllowed(false);

        // Create the password input field
        mPassword = new PasswordField("Password:");
        mPassword.setWidth("300px");
        mPassword.addValidator(new PasswordValidator());
        mPassword.setRequired(true);
        mPassword.setValue("");
        mPassword.setNullRepresentation("");

        // Create login button
        mLoginButton = new Button("Login", this);

        // Add both to a panel
        VerticalLayout fields = new VerticalLayout(mUser, mPassword, mLoginButton);
        fields.setCaption("Please login to access the application.");
        fields.setSpacing(true);
        fields.setMargin(new MarginInfo(true, true, true, false));
        fields.setSizeUndefined();

        // The view root layout
        VerticalLayout viewLayout = new VerticalLayout(fields);
        viewLayout.setSizeFull();
        viewLayout.setComponentAlignment(fields, Alignment.MIDDLE_CENTER);
        viewLayout.setStyleName(Reindeer.LAYOUT_BLUE);
        setCompositionRoot(viewLayout);
    }

    @Override
    public void enter(ViewChangeEvent event) {
        // focus the username field when user arrives to the login view
        mUser.focus();
    }

    // Validator for validating the passwords
    private static final class PasswordValidator extends
            AbstractValidator<String> {

        public PasswordValidator() {
            super("The password provided is not valid");
        }

        @Override
        protected boolean isValidValue(String value) {
            //
            // Password must be at least 7 characters long
            //
            if (value != null
                    && (value.length() < 7)) {
                return false;
            }
            return true;
        }

        @Override
        public Class<String> getType() {
            return String.class;
        }
    }

    @Override
    public void buttonClick(ClickEvent event) {


//        if (!mUser.isValid() || !mPassword.isValid()) {
//            return;
//        }

        String username = mUser.getValue();
        String password = this.mPassword.getValue();

        //
        // Validate username and password with database here. For examples sake
        // I use a dummy username and password.
        //
        boolean isValid = true;
//        		username.equals("admin@test.com")
//                && password.equals("mustermann");

        if (isValid) {

            // Store the current user in the service session
            getSession().setAttribute("user", username);

            // Navigate to main view
            getUI().getNavigator().navigateTo(StoryView.NAME);//

        } else {

            // Wrong password clear the password field and refocuses it
            this.mPassword.setValue(null);
            this.mPassword.focus();

        }
    }
}