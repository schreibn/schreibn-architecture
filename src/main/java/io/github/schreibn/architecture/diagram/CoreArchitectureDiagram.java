package io.github.schreibn.architecture.diagram;

import com.structurizr.Workspace;
import com.structurizr.model.*;
import com.structurizr.view.Shape;
import com.structurizr.view.Styles;
import com.structurizr.view.SystemContextView;
import com.structurizr.view.ViewSet;
import io.github.schreibn.architecture.aop.Diagram;

@Diagram("coreDiagram")
public class CoreArchitectureDiagram extends AbstractDiagram {

    @Override
    public Workspace produceView() {
        Workspace workspace = new Workspace("Getting Started", "This is a core diagram test.");
        Model model = workspace.getModel();
        Person user = model.addPerson("User", "A user of my software system.");
        model.addPerson("Another user", "Just hanging around");
        SoftwareSystem softwareSystem = model.addSoftwareSystem("Software System", "My software system.");
        user.uses(softwareSystem, "Uses");

        ViewSet views = workspace.getViews();
        SystemContextView contextView = views.createSystemContextView(softwareSystem, "SystemContext", "This is a core diagram test.");
        contextView.addAllElements();
        contextView.addAllSoftwareSystems();
        contextView.addAllPeople();

        Styles styles = views.getConfiguration().getStyles();
        styles.addElementStyle(Tags.SOFTWARE_SYSTEM).background("#1168bd").color("#ffffff");
        styles.addElementStyle(Tags.PERSON).background("#08427b").color("#ffffff").shape(Shape.Person);

        return workspace;
    }
}
