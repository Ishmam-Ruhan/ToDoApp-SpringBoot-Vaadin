package com.ishmamruhan.application.views.task;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.checkbox.Checkbox;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextArea;
import com.vaadin.flow.component.textfield.TextField;

import java.util.UUID;


public class TaskEditor extends VerticalLayout {

    private TextField taskName;
    private UUID id;

    private TextArea taskDescription;

    private Checkbox taskIsComplete;
    private Button cancelButton = new Button("Cancel");
    private Button saveButton = new Button("Save");
    private Button updateButton = new Button("Update");
    private Button deleteButton = new Button("Delete");

    public TaskEditor() {
        FormLayout formLayout = new FormLayout();

        id = UUID.randomUUID();
        taskName = new TextField("Task Name");
        taskDescription = new TextArea("Details");
        taskIsComplete = new Checkbox("Task Complete");

        formLayout.add(taskName, taskDescription,taskIsComplete);

        HorizontalLayout buttonLayout = new HorizontalLayout();

        cancelButton.addThemeVariants(ButtonVariant.LUMO_TERTIARY);
        saveButton.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        updateButton.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        deleteButton.addThemeVariants(ButtonVariant.LUMO_ERROR);

        updateButton.setVisible(false);

        buttonLayout.setWidthFull();
        buttonLayout.setJustifyContentMode(JustifyContentMode.BETWEEN);
        buttonLayout.setSpacing(false);
        buttonLayout.add(new HorizontalLayout(saveButton,updateButton,cancelButton,deleteButton));

        add(formLayout, buttonLayout);
        setWidth("600px");
        setMinWidth("300px");
        setFlexGrow(0);
    }

    public Button getCancelButton() {
        return cancelButton;
    }
    public Button getSaveButton() {
        return saveButton;
    }
    public Button getUpdateButton() {
        return saveButton;
    }
    public Button getDeleteButton() {
        return deleteButton;
    }

    public void setSaveButtonVisible(){
        saveButton.setVisible(true);
    }

    public void setUpdateButtonVisible(){
        updateButton.setVisible(true);
    }

    public void setSaveButtonNOTVisible(){
        saveButton.setVisible(false);
    }

    public void setUpdateButtonNOTVisible(){
        updateButton.setVisible(false);
    }

    public void setEmpty(){
        taskName.setValue("");
        taskDescription.setValue("");
        taskIsComplete.setValue(false);
    }
}
