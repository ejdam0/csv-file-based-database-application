package pl.adamstrzelecki.database.exercise.csvdatabase.gui;

import com.vaadin.flow.component.button.Button;
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
import pl.adamstrzelecki.database.exercise.csvdatabase.gui.service.SaveToDatabaseGuiService;
import pl.adamstrzelecki.database.exercise.csvdatabase.gui.service.UserGuiService;

@Route("add-data-gui")
@PageTitle("Add data to the database")
public class AddDataGui extends VerticalLayout {

    private UserGuiService userService;
    private SaveToDatabaseGuiService saveToDatabaseService;

    @Autowired
    public AddDataGui(UserGuiService userService, SaveToDatabaseGuiService saveToDatabaseService) {
        this.userService = userService;
        this.saveToDatabaseService = saveToDatabaseService;

        Label labelUserDatabase = new Label("Add data to the database");

        TextArea textAreaUploadData = new TextArea("Add data", "The csv data...");
        textAreaUploadData.setAutofocus(true);
        textAreaUploadData.getStyle()
                .set("minHeight", "500px")
                .set("maxHeight", "500px")
                .set("minWidth", "500px");

        Button buttonConfirm = new Button("Confirm");
        Button buttonReturn = new Button("Return", new Icon(VaadinIcon.ARROW_LEFT));

        HorizontalLayout horizontalLayout = new HorizontalLayout();
        horizontalLayout.add(buttonConfirm, buttonReturn);

        buttonConfirm.addClickListener(clickEvent -> {
            if (textAreaUploadData.getValue().isEmpty()) {
                Notification error = new Notification("Provided data is incorrect! Try again.", 3000, Notification.Position.MIDDLE);
                error.addThemeVariants(NotificationVariant.LUMO_ERROR);
                error.open();
            }
            if (!saveToDatabaseService.saveListToDataBase(textAreaUploadData.getValue())) {
                Notification error = new Notification("Provided data is incorrect! Try again.", 3000, Notification.Position.MIDDLE);
                error.addThemeVariants(NotificationVariant.LUMO_ERROR);
                error.open();
            } else {
                Notification notification =
                        new Notification("Adding users to the database", 3000, Notification.Position.MIDDLE);
                notification.addThemeVariants(NotificationVariant.LUMO_SUCCESS);
                notification.open();
                textAreaUploadData.clear();
            }
        });

        buttonReturn.addClickListener(buttonClickEvent ->
                buttonReturn.getUI().ifPresent(ui -> ui.navigate("user-main-gui"))
        );
        setAlignItems(Alignment.CENTER);
        add(textAreaUploadData, horizontalLayout);
    }
}
