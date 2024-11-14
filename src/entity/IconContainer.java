package entity;

import engine.DrawManager;

import java.awt.*;

public class IconContainer extends Entity{

    public IconContainer() {
        super(0,0, 15 * 2, 15 * 2, Color.GREEN);
        this.spriteType = DrawManager.SpriteType.Container;
    }
}
