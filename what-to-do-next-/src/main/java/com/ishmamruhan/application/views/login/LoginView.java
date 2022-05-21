package com.ishmamruhan.application.views.login;

import com.ishmamruhan.application.backend.Security.Configurations.AuthenticatedUser;
import com.ishmamruhan.application.data.CurrentUserData;
import com.ishmamruhan.application.views.MainLayout;
import com.ishmamruhan.application.views.admin.AdminView;
import com.ishmamruhan.application.views.supervisor.SupervisorView;
import com.ishmamruhan.application.views.user.UserView;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.Composite;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.html.Paragraph;
import com.vaadin.flow.component.login.LoginI18n;
import com.vaadin.flow.component.login.LoginOverlay;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.tabs.Tab;
import com.vaadin.flow.component.tabs.Tabs;
import com.vaadin.flow.component.textfield.PasswordField;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import org.apache.coyote.http11.Http11AprProtocol;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@PageTitle("Login")
@Route(value = "login")
public class LoginView extends Composite<VerticalLayout> {

    private AuthenticatedUser authenticatedUser;

    public LoginView(AuthenticationManager authenticationManager, AuthenticatedUser authenticatedUser) {
        this.authenticatedUser = authenticatedUser;

        VerticalLayout layout = getContent();

        TextField username = new TextField("Username");
        PasswordField password = new PasswordField("Password");

        layout.add(
            new H1("Sign In"),
                username,
                password,
                new Button("Take me in...", buttonClickEvent -> {
                    try{
                        final Authentication authentication = authenticationManager.authenticate(
                                new UsernamePasswordAuthenticationToken(username.getValue(),password.getValue())
                        );
                        if(authentication != null){
                            SecurityContextHolder.getContext().setAuthentication(authentication);
                            navigateToPage();
                        }
                    }catch (AuthenticationException ex){
                        Notification.show("Invalid Login!",4, Notification.Position.TOP_END);
                    }
                }),
                new Button("Don't have an account? Sign Up", buttonClickEvent -> {
                    getUI().get().navigate("/registration");
                })
        );

        layout.setSizeFull();
        layout.setAlignItems(FlexComponent.Alignment.CENTER);
        layout.setJustifyContentMode(FlexComponent.JustifyContentMode.CENTER);

    }

    private void navigateToPage() {
        String position = new CurrentUserData(authenticatedUser).getUserPosition();


        if(position.equals("USER")){
            getUI().get().navigate("/user");
        }
        else if(position.equals("SUPERVISOR")){
            getUI().get().navigate("/supervisor");
        }
        else if(position.equals("ADMIN")){
            getUI().get().navigate("/admin");
        }
    }

}
