package naga.providers.toolkit.cn1.nodes.controls;

import naga.toolkit.spi.nodes.controls.ToggleSwitch;


/**
 * @author Bruno Salmon
 */
public class Cn1CheckBox extends Cn1ButtonBase<com.codename1.ui.CheckBox> implements naga.toolkit.spi.nodes.controls.CheckBox, ToggleSwitch {

    public Cn1CheckBox() {
        this(new com.codename1.ui.CheckBox());
    }

    public Cn1CheckBox(com.codename1.ui.CheckBox button) {
        super(button);
    }
}

