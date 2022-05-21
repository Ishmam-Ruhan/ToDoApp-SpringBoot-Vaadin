package com.ishmamruhan.application.views.login;

import com.ishmamruhan.application.backend.DAO.UserDao;
import com.ishmamruhan.application.backend.DTO.User;
import com.ishmamruhan.application.backend.ServiceImplementations.UserImpl.AdminServiceImplementation;
import com.ishmamruhan.application.backend.Services.AdminService;
import com.ishmamruhan.application.backend.Services.AuthenticationService;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.Composite;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.html.H3;
import com.vaadin.flow.component.login.LoginOverlay;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.tabs.Tabs;
import com.vaadin.flow.component.textfield.EmailField;
import com.vaadin.flow.component.textfield.PasswordField;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.server.auth.AnonymousAllowed;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.ArrayList;
import java.util.List;

@PageTitle("Registration")
@Route(value = "/registration")
@AnonymousAllowed
public class RegistrationView extends Composite<VerticalLayout> {

    private AdminService adminService;

    private AuthenticationService authService;

    @Autowired
    private UserDao userDao;

    private TextField username = new TextField();
    private EmailField email = new EmailField();
    private PasswordField password = new PasswordField();
    private PasswordField confirmPassword = new PasswordField();
    private ComboBox<String> supervisor = new ComboBox<>();


    public RegistrationView(
            AuthenticationManager authenticationManager,
            AdminService adminService,
            AuthenticationService authService) {

        this.adminService = adminService;
        this.authService = authService;

        VerticalLayout layout = getContent();
        createFormLayout();


        layout.add(
                createTitle(),
                username,
                email,
                password,
                confirmPassword,
                supervisor,
                new Button("Register", buttonClickEvent -> {

                    User user = userDao.findByUsername(username.getValue());
                    User user1 = userDao.findByEmail(email.getValue());


                    if(user != null || user1 != null){
                        Notification.show("User Already Exist!",40, Notification.Position.TOP_END);
                        return;
                    }
                    nextProcess(authenticationManager);
                })
        );

        layout.setSizeFull();
        layout.setMargin(true);
        layout.setAlignItems(FlexComponent.Alignment.CENTER);
        layout.setJustifyContentMode(FlexComponent.JustifyContentMode.CENTER);

    }

    private void nextProcess(AuthenticationManager authenticationManager) {
        if(!password.getValue().equals(confirmPassword.getValue())){
            System.out.println("P: "+password.getValue()+" CP: "+confirmPassword.getValue());
            Notification.show("Password/Confirm Password Incorrect!",40, Notification.Position.TOP_END);
            return;
        }

        int usernameValue = username.getValue().length();
        int passwordValue = username.getValue().length();
        int emailValue = username.getValue().length();
        User associatedValue = userDao.findByUsername(supervisor.getValue());

        if(usernameValue< 3 || usernameValue> 40 || passwordValue<6 || emailValue < 5 || associatedValue == null){
            Notification.show("Fill all the informations correctly! ",40, Notification.Position.TOP_END);
            return;
        }

        User user = new User();
        user.setUserName(username.getValue());
        user.setPassword(password.getValue());
        user.setEmail(email.getValue());
        user.setAssciatedWith(associatedValue);

        authService.registration(user);

        final Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(username.getValue(),password.getValue())
        );
        if(authentication != null){
            SecurityContextHolder.getContext().setAuthentication(authentication);
            getUI().get().navigate("/user");
        }
    }

    private Component createTitle() {
        return new H3("Registration");
    }

    private void createFormLayout() {

        username.setRequired(true);
        username.setRequiredIndicatorVisible(true);
        username.setPlaceholder("Username (Minimum length 3)");
        username.setMinLength(3);
        username.setMaxLength(40);
        username.setWidth("35%");


        email.setRequiredIndicatorVisible(true);
        email.setPlaceholder("Email (Please Provide Correct email address)");
        email.setWidth("35%");
        email.setMinLength(5);

        password.setRequired(true);
        password.setRequiredIndicatorVisible(true);
        password.setPlaceholder("Password (Minimum password length 6)");
        password.setWidth("35%");
        password.setMinLength(6);

        confirmPassword.setRequired(true);
        confirmPassword.setRequiredIndicatorVisible(true);
        confirmPassword.setPlaceholder("Confirm Password");
        confirmPassword.setWidth("35%");

        supervisor.setRequired(true);
        supervisor.setRequiredIndicatorVisible(true);
        supervisor.setPlaceholder("- Select Supervisor -");
        supervisor.setWidth("35%");

        List<User> users = adminService.getAllActiveSupervisors();

        List<String> names = new ArrayList<>();

        for(User user: users){
            names.add(user.getUserName());
        }

        supervisor.setItems(names);

    }

}
