package entity;

import main.GamePanel;
import main.KeyHandler;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;

public class Player extends Entity {

    GamePanel gp;
    KeyHandler keyH;

    public final int screenX;
    public final int screenY;

    public Player(GamePanel gp, KeyHandler keyH) {
        this.gp = gp;
        this.keyH = keyH;

        screenX = gp.screenWidth / 2 - (gp.tileSize / 2);
        screenY = gp.screenHeight / 2 - (gp.tileSize / 2);

        solidArea = new Rectangle(8, 16, 32, 32);


        setDefaultValues();
        getPlayerImage();
    }

    public void setDefaultValues() {
        worldX = gp.tileSize * 23;
        worldY = gp.tileSize * 21;
        speed = 4;
        direction = "down";
    }

    public void getPlayerImage() {
        try {
            up1 = ImageIO.read(getClass().getResourceAsStream("/player/boy_up_1.png"));
            up2 = ImageIO.read(getClass().getResourceAsStream("/player/boy_up_2.png"));
            down1 = ImageIO.read(getClass().getResourceAsStream("/player/boy_down_1.png"));
            down2 = ImageIO.read(getClass().getResourceAsStream("/player/boy_down_2.png"));
            left1 = ImageIO.read(getClass().getResourceAsStream("/player/boy_left_1.png"));
            left2 = ImageIO.read(getClass().getResourceAsStream("/player/boy_left_2.png"));
            right1 = ImageIO.read(getClass().getResourceAsStream("/player/boy_right_1.png"));
            right2 = ImageIO.read(getClass().getResourceAsStream("/player/boy_right_2.png"));
        } catch (IOException e) {
            e.printStackTrace();
        } catch (NullPointerException e) {
            System.out.println("⚠️ Один из файлов не найден! Проверь папку res/player/");
            e.printStackTrace();
        }
    }


    public void update() {
        if (keyH.upPressed || keyH.downPressed || keyH.leftPressed || keyH.rightPressed) {

            // направления для движения
            double dx = 0;
            double dy = 0;

            if (keyH.upPressed) {
                dy -= 1;
                direction = "up";
            }
            if (keyH.downPressed) {
                dy += 1;
                direction = "down";
            }
            if (keyH.leftPressed) {
                dx -= 1;
                direction = "left";
            }
            if (keyH.rightPressed) {
                dx += 1;
                direction = "right";
            }

            // проверка столкновений
            collisionOn = false;
            gp.cChecker.checkTile(this);

            if (!collisionOn && (dx != 0 || dy != 0)) {
                // нормализация
                double length = Math.sqrt(dx * dx + dy * dy);
                dx = (dx / length) * speed;
                dy = (dy / length) * speed;

                worldX += dx;
                worldY += dy;
            }

            // анимация
            spriteCounter++;
            if (spriteCounter > 12) {
                spriteNum = (spriteNum == 1) ? 2 : 1;
                spriteCounter = 0;
            }
        }
    }


    public void draw(Graphics g2) {
        BufferedImage image = null;

        switch (direction) {
            case "up":
                if (spriteNum == 1) {
                    image = up1;
                } else image = up2;
                break;
            case "down":
                if (spriteNum == 1) {
                    image = down1;
                } else image = down2;
                break;
            case "left":
                if (spriteNum == 1) {
                    image = left1;
                } else image = left2;
                break;
            case "right":
                if (spriteNum == 1) {
                    image = right1;
                } else image = right2;
                break;

        }
        g2.drawImage(image, screenX, screenY, gp.tileSize, gp.tileSize, null);
    }
}
