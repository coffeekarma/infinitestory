package com.coffeekarma.story;

import java.util.function.Consumer;
import java.util.logging.Logger;

import com.coffeekarma.story.communication.Broadcaster;
import com.google.gwt.safehtml.shared.SafeHtmlUtils;
import com.vaadin.annotations.Push;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.shared.ui.label.ContentMode;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.CustomComponent;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.Panel;
import com.vaadin.ui.TextArea;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;

@Push
public class StoryView extends CustomComponent implements View {

	private static final String ID_SEND = "Send";

	private static final String ID_LOGOUT = "Logout";

	public static final String NAME = "";

	private Label mWelcomeLabel = new Label();

	Button mLogout = new Button(ID_LOGOUT, getClickListener());

	private ClickListener mClickListener = null;

	private Label mStoryTextContent = null;

	private TextArea mInputArea = null;

	private Consumer<String> mListener = null;

	public StoryView() {
		createComponents();
		mListener = createBroadCastListener();
		Broadcaster.register(mListener);
	}

	private void createComponents() {
		// Top
		mWelcomeLabel.setStyleName("welcomelabel");
		HorizontalLayout topLayout = new HorizontalLayout(mWelcomeLabel,
				mLogout);
		topLayout.setComponentAlignment(mWelcomeLabel, Alignment.MIDDLE_LEFT);
		topLayout.setSpacing(true);

		Panel storyPanel = new Panel();
		storyPanel.setWidth("100%");
		storyPanel.addStyleName("bordered");
		// storyTextArea.setHeight("10px");
		mStoryTextContent = new Label();
		mStoryTextContent.setContentMode(ContentMode.HTML);
		mStoryTextContent
				.setValue("<p style=\"color:red\">1. Testing <br/> 2. Testing</p>");
		storyPanel.setContent(mStoryTextContent);
		storyPanel.setHeight("60ex");
		mInputArea = new TextArea();
		mInputArea.setWidth("100%");
		Button sendButton = new Button(ID_SEND, getClickListener());
		VerticalLayout bottomLayout = new VerticalLayout(mInputArea, sendButton);
		bottomLayout.setComponentAlignment(sendButton, Alignment.BOTTOM_LEFT);
		bottomLayout.setWidth("100%");
		bottomLayout.setSpacing(true);

		VerticalLayout rootLayout = new VerticalLayout();
		rootLayout.setSpacing(true);
		rootLayout.setMargin(true);
		rootLayout.addComponents(topLayout, storyPanel, bottomLayout);
		rootLayout.setComponentAlignment(topLayout, Alignment.MIDDLE_RIGHT);
		rootLayout.setSizeFull();
		setCompositionRoot(rootLayout);
	}

	@Override
	public void enter(ViewChangeEvent event) {
		// Get the user name from the session
		String username = String.valueOf(getSession().getAttribute("user"));

		// And show the username
		mWelcomeLabel.setValue("Hello " + username);

	}

	@Override
	public void detach() {
		Broadcaster.unregister(mListener);
		super.detach();
	};

	private Consumer<String> createBroadCastListener() {
		return text -> {
			UI.getCurrent().access(() -> {
				if (mStoryTextContent != null && getUI() != null
						&& !text.equals(mStoryTextContent.getValue())) {
					mStoryTextContent.setValue(text);
					getUI().push();
				}	
			});
		};
	}

	private ClickListener getClickListener() {
		if (mClickListener == null) {
			mClickListener = new Button.ClickListener() {

				@Override
				public void buttonClick(ClickEvent event) {
					if (ID_LOGOUT.equals(event.getButton().getCaption())) {

						// "Logout" the user
						getSession().setAttribute("user", null);

						// Refresh this view, should redirect to login view
						getUI().getNavigator().navigateTo(NAME);
					} else if (ID_SEND.equals(event.getButton().getCaption())) {
						String inputValue = mInputArea.getValue();
						inputValue = SafeHtmlUtils.htmlEscape(inputValue);
						Broadcaster.broadcast(mStoryTextContent.getValue()
								+ "<br/>" + inputValue);
					}
				}
			};
		}
		return mClickListener;
	}
}