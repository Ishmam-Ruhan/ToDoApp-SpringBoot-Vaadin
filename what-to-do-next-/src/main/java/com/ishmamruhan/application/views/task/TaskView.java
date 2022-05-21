package com.ishmamruhan.application.views.task;

import com.ishmamruhan.application.backend.DTO.Task;
import com.ishmamruhan.application.backend.DTO.User;
import com.ishmamruhan.application.backend.Security.Configurations.AuthenticatedUser;
import com.ishmamruhan.application.backend.Services.CommonService;
import com.ishmamruhan.application.views.MainLayout;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.splitlayout.SplitLayout;
import com.vaadin.flow.data.binder.BeanValidationBinder;
import com.vaadin.flow.data.binder.ValidationException;
import com.vaadin.flow.data.provider.DataProvider;
import com.vaadin.flow.data.provider.ListDataProvider;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import org.springframework.beans.factory.annotation.Autowired;

import javax.annotation.security.RolesAllowed;


@PageTitle("Task")
@Route(value = "task", layout = MainLayout.class)
@RolesAllowed({"ADMIN","USER","SUPERVISOR"})
public class TaskView extends SplitLayout {
    private Grid<Task> grid;
    private final TaskEditor editor;

    ListDataProvider<Task> dataProvider;

    private BeanValidationBinder<Task> binder;

    private CommonService commonService;

    private AuthenticatedUser authenticatedUser;

    private Task task;

    public TaskView(@Autowired CommonService commonService, AuthenticatedUser authenticatedUser) {
        editor = new TaskEditor();
        this.commonService = commonService;
        this.authenticatedUser = authenticatedUser;

        configureGrid();
        configureButtons();
        setSizeFull();
        addToPrimary(editor);
        addToSecondary(grid);
    }

    private void configureButtons() {
        binder = new BeanValidationBinder<>(Task.class);
        //Bind member fields found in the EditorLayout object.
        binder.bindInstanceFields(editor);

        editor.getDeleteButton().addClickListener(e -> {
            if (this.task != null) {

                commonService.deleteTask(task);

                dataProvider.getItems().remove(task);
                clearForm();
                refreshGrid();
                Notification.show("Task deleted.");
            }
        });

        editor.getCancelButton().addClickListener(e -> {
            clearForm();
            refreshGrid();
        });

        editor.getSaveButton().addClickListener(e -> {
            try {
                if (this.task == null) {
                    this.task = new Task();
                }
                binder.writeBean(this.task);

                User user = authenticatedUser.get().orElse(new User());

                commonService.addTask(user.getId(),this.task);

                dataProvider.getItems().add(task);

                editor.setEmpty();
                clearForm();
                refreshGrid();
                Notification.show("Task details stored.");
            } catch (ValidationException validationException) {
                Notification.show("Please enter a valid Task details.");
            }
        });

        editor.getUpdateButton().addClickListener(e -> {
            try {
                if (this.task == null) {
                    this.task = new Task();
                }

                Task temptask = new Task();
                binder.writeBean(temptask);

                this.task.setTaskName(temptask.getTaskName());
                this.task.setTaskDescription(temptask.getTaskDescription());
                this.task.setTaskIsComplete(temptask.isTaskIsComplete());

                commonService.updateTask(this.task);

                editor.setEmpty();
                clearForm();
                refreshGrid();
                Notification.show("Task details stored.");
            } catch (ValidationException validationException) {
                Notification.show("Please enter a valid Task details.");
            }

            this.task=null;
            editor.setSaveButtonVisible();
            editor.setUpdateButtonNOTVisible();
        });
    }

    private void configureGrid() {
        grid = new Grid<>(Task.class,true);

//        grid.addColumn(Task::getId).setHeader("Task ID").setAutoWidth(true);
//        grid.addColumn(Task::getTaskName).setHeader("Task Name").setAutoWidth(true);
//        grid.addColumn(Task::getTaskDescription).setHeader("Description").setAutoWidth(true);
//        grid.addColumn(Task::isTaskIsComplete).setHeader("Status").setAutoWidth(true);
//        grid.addColumn(Task::getCreateAt).setHeader("Created At").setAutoWidth(true);

        grid.setSizeFull();

        User user = authenticatedUser.get().orElse(new User());

        dataProvider = DataProvider.ofCollection(commonService.getAllTasks(user.getId()));
        grid.setDataProvider(dataProvider);

        grid.asSingleSelect().addValueChangeListener(event -> {

            editor.setSaveButtonNOTVisible();
            editor.setUpdateButtonVisible();

            Task task = event.getValue();

            if (task != null) {
                this.task = task;
                populateForm(task);
            } else {
                clearForm();
            }

        });
    }

    void clearForm() {
        populateForm(null);
    }
    void populateForm(Task task) {
        this.task = task;
        binder.readBean(this.task);
    }
    public void refreshGrid() {
        grid.select(null);
        dataProvider.refreshAll();
    }

}
