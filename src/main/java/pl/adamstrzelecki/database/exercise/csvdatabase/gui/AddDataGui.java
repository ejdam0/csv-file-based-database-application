package pl.adamstrzelecki.database.exercise.csvdatabase.gui;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.notification.NotificationVariant;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextArea;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import org.springframework.beans.factory.annotation.Autowired;
import pl.adamstrzelecki.database.exercise.csvdatabase.gui.service.UserGuiService;
import pl.adamstrzelecki.database.exercise.csvdatabase.service.SaveToDatabaseService;

@Route("add-data-gui")
@PageTitle("Add data to the database")
public class AddDataGui extends VerticalLayout {

    private UserGuiService userService;
    private SaveToDatabaseService saveToDatabaseService;

    @Autowired
    public AddDataGui(UserGuiService userService, SaveToDatabaseService saveToDatabaseService) {
        this.userService = userService;
        this.saveToDatabaseService = saveToDatabaseService;

        Label labelUserDatabase = new Label("Add data to the database");

        TextArea textAreaUploadData = new TextArea("Add data", "The csv data...");
        textAreaUploadData.setAutofocus(true);
        textAreaUploadData.getStyle()
                .set("minHeight", "200px")
                .set("maxHeight", "500px");

        Button buttonConfirm = new Button("Confirm");
        Button buttonReturn = new Button("Return", new Icon(VaadinIcon.ARROW_LEFT));

        buttonConfirm.addClickListener(clickEvent -> {
            if (textAreaUploadData.getValue().isEmpty()) {
                Notification error = new Notification("Provided data is incorrect! Try again.", 3000, Notification.Position.MIDDLE);
                error.addThemeVariants(NotificationVariant.LUMO_ERROR);
                error.open();
            }
            long savedUsers = saveToDatabaseService.saveListToDataBase(textAreaUploadData.getValue());
            Notification notification =
                    new Notification("Adding users to the database", 3000, Notification.Position.MIDDLE);
            notification.addThemeVariants(NotificationVariant.LUMO_SUCCESS);
            notification.open();
            textAreaUploadData.clear();
        });

        buttonReturn.addClickListener(buttonClickEvent ->
                buttonReturn.getUI().ifPresent(ui -> ui.navigate("user-main-gui"))
        );
        setAlignItems(Alignment.CENTER);
        add(textAreaUploadData, buttonConfirm, buttonReturn);
    }
}
