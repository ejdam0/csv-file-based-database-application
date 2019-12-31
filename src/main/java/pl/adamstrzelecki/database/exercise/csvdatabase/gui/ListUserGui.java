package pl.adamstrzelecki.database.exercise.csvdatabase.gui;

import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.notification.NotificationVariant;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import org.springframework.beans.factory.annotation.Autowired;
import pl.adamstrzelecki.database.exercise.csvdatabase.entity.User;
import pl.adamstrzelecki.database.exercise.csvdatabase.gui.service.UserGuiService;

import java.util.List;

@Route("list-user-gui")
@PageTitle("User list")
public class ListUserGui extends VerticalLayout {

    private UserGuiService userService;

    @Autowired
    public ListUserGui(UserGuiService userService) {
        this.userService = userService;

        // define buttons
        Label labelListUser = new Label("List of all users");
        Button buttonAddUser = new Button("Add new user");
        Button buttonUpdateUser = new Button("Update existing user");
        Button buttonDeleteUser = new Button("Delete user using ID");
        Button buttonDeleteAll = new Button("Delete all");
        Button buttonFindEldestUser = new Button("Find eldest user in the database");
        Button buttonCountUsers = new Button("Count users in the database");

        // define layouts
        VerticalLayout verticalLayout = new VerticalLayout();
        verticalLayout.setAlignItems(Alignment.CENTER);

        HorizontalLayout horizontalLayoutOne = new HorizontalLayout();
        horizontalLayoutOne.setAlignItems(Alignment.CENTER);
        HorizontalLayout horizontalLayoutTwo = new HorizontalLayout();
        horizontalLayoutTwo.setAlignItems(Alignment.CENTER);

        // add buttons to the layouts
        horizontalLayoutOne.add(buttonAddUser, buttonUpdateUser, buttonDeleteUser, buttonDeleteAll);
        horizontalLayoutTwo.add(buttonFindEldestUser, buttonCountUsers);

        // dialog to add new user

        // dialog to update existing user

        // dialog to delete user using ID
        Dialog dialogDeleteById = new Dialog();
        Button buttonConfirmDelete = new Button("Confirm");
        TextField textFieldId = new TextField("Select user id");
        dialogDeleteById.add(textFieldId, buttonConfirmDelete);

        buttonDeleteUser.addClickListener(ce -> dialogDeleteById.open());
        buttonConfirmDelete.addClickListener(ce -> {
            int userId = Integer.parseInt(textFieldId.getValue());
            userService.deleteById(userId);
            UI.getCurrent().getPage().reload();
            Notification notification = new Notification("Deleted user with id : " + userId, 5000, Notification.Position.MIDDLE);
            notification.addThemeVariants(NotificationVariant.LUMO_ERROR);
            notification.open();
        });

        // delete all
        buttonDeleteAll.addClickListener(ce -> {
            userService.deleteAll();
            UI.getCurrent().getPage().reload();
            Notification notification = new Notification("Deleted all users", 5000, Notification.Position.MIDDLE);
            notification.addThemeVariants(NotificationVariant.LUMO_ERROR);
            notification.open();
        });

        // dialog to find eldest user in the db
        Dialog dialogEldestUser = new Dialog();
        buttonFindEldestUser.addClickListener(ce -> {
            User eldest = userService.findEldest();
            dialogEldestUser.add(new Label("The eldest user is: " + eldest.getFirstName() + " " + eldest.getLastName()));
            dialogEldestUser.open();
        });

        // dialog to count users in the db
        Dialog dialogCountUsers = new Dialog();
        buttonCountUsers.addClickListener(ce -> {
            long count = userService.count();
            dialogCountUsers.add(new Label("There are " + count + " users in the database."));
            dialogCountUsers.open();
        });

        // add list of users
        List<User> users = userService.findAll();
        Grid<User> grid = new Grid<>(User.class);
        grid.setItems(users);
        grid.setColumnReorderingAllowed(true);

        verticalLayout.add(horizontalLayoutOne, horizontalLayoutTwo, grid);
        add(labelListUser, verticalLayout);
    }
}
