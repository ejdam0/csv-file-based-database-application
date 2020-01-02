package pl.adamstrzelecki.database.exercise.csvdatabase.gui;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.notification.NotificationVariant;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextArea;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import org.springframework.beans.factory.annotation.Autowired;
import pl.adamstrzelecki.database.exercise.csvdatabase.entity.User;
import pl.adamstrzelecki.database.exercise.csvdatabase.gui.service.UserGuiService;

@Route("add-update-user-gui")
@PageTitle("Add user to the database")
public class AddUpdateUserGui extends VerticalLayout {
    private UserGuiService userGuiService;

    @Autowired
    public AddUpdateUserGui(UserGuiService userGuiService) {
        this.userGuiService = userGuiService;

        Label labelTitle = new Label("Add a new user");

        TextArea textAreaFirstName = new TextArea("First name", "Write here...");
        TextArea textAreaLastName = new TextArea("Last name", "Write here...");
        TextArea textAreaPhoneNo = new TextArea("Phone number", "Write here...");
        TextArea textAreaBirthDate = new TextArea("Birth date", "Write here...");
        TextArea textAreaId = new TextArea("Id", "User id");


        ComboBox<User> comboBoxUsers = new ComboBox<>("Users");
        comboBoxUsers.setItems(userGuiService.findAll());
        comboBoxUsers.setMinWidth("300px");

        Button buttonSelect = new Button("Select");
        Button buttonConfirm = new Button("Confirm");
        Button buttonReturn = new Button("Return", new Icon(VaadinIcon.ARROW_LEFT));

        textAreaFirstName.setClearButtonVisible(true);
        textAreaLastName.setClearButtonVisible(true);
        textAreaPhoneNo.setClearButtonVisible(true);
        textAreaBirthDate.setClearButtonVisible(true);
        textAreaId.setEnabled(false);

        textAreaPhoneNo.setMinLength(9);
        textAreaPhoneNo.setMaxLength(9);

        buttonReturn.addClickListener(buttonClickEvent ->
                buttonReturn.getUI().ifPresent(ui -> ui.navigate("list-user-gui"))
        );

        buttonSelect.addClickListener(clickEvent -> {
            User userToUpdate = userGuiService.findById(comboBoxUsers.getValue().getId());
            textAreaId.setValue(String.valueOf(userToUpdate.getId()));
            textAreaFirstName.setValue(userToUpdate.getFirstName());
            textAreaLastName.setValue(userToUpdate.getLastName());
            textAreaPhoneNo.setValue(userToUpdate.getPhoneNo());
            textAreaBirthDate.setValue(userToUpdate.getBirthDate());

        });

        buttonConfirm.addClickListener(clickEvent -> {
            if (textAreaFirstName.getValue().isEmpty()
                    || textAreaLastName.getValue().isEmpty()
                    || textAreaPhoneNo.getValue().isEmpty()
                    || textAreaBirthDate.getValue().isEmpty()) {
                Notification error = new Notification("You have to fill in all the data.", 3000,
                        Notification.Position.MIDDLE);
                error.addThemeVariants(NotificationVariant.LUMO_ERROR);
                error.open();
            } else {
                User userToAdd = new User(
                        textAreaFirstName.getValue(),
                        textAreaLastName.getValue(),
                        textAreaBirthDate.getValue(),
                        textAreaPhoneNo.getValue());

                if (textAreaId.isEmpty()) {
                    userToAdd.setId(0);
                } else {
                    userToAdd.setId(Integer.parseInt(textAreaId.getValue()));
                }

                if (!userGuiService.save(userToAdd)) {
                    Notification error = new Notification("Provided data is incorrect or already in the database! Try again.",
                            4000, Notification.Position.MIDDLE);
                    error.addThemeVariants(NotificationVariant.LUMO_ERROR);
                    error.open();
                } else {
                    Notification notification =
                            new Notification("Adding user to the database",
                                    3000, Notification.Position.MIDDLE);
                    notification.addThemeVariants(NotificationVariant.LUMO_SUCCESS);
                    notification.open();
                    textAreaId.clear();
                    textAreaFirstName.clear();
                    textAreaLastName.clear();
                    textAreaPhoneNo.clear();
                    textAreaBirthDate.clear();
                }
            }
        });

        HorizontalLayout horizontalLayout = new HorizontalLayout();
        HorizontalLayout primaryHorizontalLayout = new HorizontalLayout();
        horizontalLayout.add(buttonReturn, buttonConfirm);
        primaryHorizontalLayout.add(comboBoxUsers, buttonSelect);
        primaryHorizontalLayout.setAlignItems(Alignment.CENTER);

        this.setAlignItems(Alignment.CENTER);
        this.add(labelTitle, primaryHorizontalLayout, textAreaId, textAreaFirstName, textAreaLastName, textAreaPhoneNo, textAreaBirthDate, horizontalLayout);
    }
}
