package pl.adamstrzelecki.database.exercise.csvdatabase.gui;

import com.vaadin.flow.component.Tag;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;

@Route(value = "user-main-gui")
@PageTitle("User database")
@Tag("user-main-gui")
public class UserMainGui extends VerticalLayout {
    public UserMainGui() {
        Label labelUserDatabase = new Label("User database");
        Button buttonUploadData = new Button("Upload data");
        Button buttonGoToListUsers = new Button("List all users");

        buttonUploadData.addClickListener(ce -> buttonUploadData.getUI().ifPresent(ui -> ui.navigate("add-data-gui")));

        buttonGoToListUsers.addClickListener(buttonClickEvent ->
                buttonGoToListUsers.getUI().ifPresent(ui -> ui.navigate("list-user-gui"))
        );

        setAlignItems(Alignment.CENTER);
        setSpacing(true);
        add(labelUserDatabase, buttonUploadData, buttonGoToListUsers);
    }
}
