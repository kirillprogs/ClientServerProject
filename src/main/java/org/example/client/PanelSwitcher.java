package org.example.client;

import java.awt.*;

public class PanelSwitcher extends CardLayout {

    private final ProductPanel productPanel;
    private final GroupPanel groupPanel;

    public PanelSwitcher(ProductPanel productPanel, GroupPanel groupPanel) {
        this.productPanel = productPanel;
        this.groupPanel = groupPanel;
    }

    @Override
    public void show(Container parent, String name) {
        if (name.equals("group"))
            groupPanel.retrieveAll();
        else if (name.equals("product"))
            productPanel.retrieveAll();
        super.show(parent, name);
    }
}