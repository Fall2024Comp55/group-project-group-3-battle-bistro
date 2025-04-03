package UI;

import Utils.Action;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;

public class ActionButton extends Button implements ActionListener {
    private final Action action;

    public ActionButton(String text, Action action) {
        super(text);
        this.action = action;
    }

    @Override
    public void onPress(MouseEvent e) {
        super.onPress(e);
        action.performAction();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        action.performAction();
    }
}
