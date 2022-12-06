package com.example.demo;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Bounds;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.util.Duration;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class Controler implements Initializable {

    @FXML
    private AnchorPane scene;

    @FXML
    private Circle circle;

    Double deltaX = 0.5;
    Double deltaY = 0.5;

    private Rectangle slider;
    private Button left ,right;

    ArrayList<Rectangle> all_bricks = new ArrayList<>();
    Timeline timeline = new Timeline(new KeyFrame(Duration.millis(10), new EventHandler<ActionEvent>() {
        @Override
        public void handle(ActionEvent actionEvent) {
            // to move the boll , collision feature
            circle.setLayoutX(circle.getLayoutX() + deltaX);
            circle.setLayoutY(circle.getLayoutY() + deltaY);

            // check collision inside the scene
            check_bricks_collision();
            check_scene_collision();
            check_slider_collision();
            // check if there is any brick if not then game is over

        }
    }));

    public void check_slider_collision(){
        if (circle.getBoundsInParent().intersects(slider.getBoundsInParent())){
            deltaY *= -1;
        }
    }

    public void check_bricks_collision(){
        if(!all_bricks.isEmpty()){
            all_bricks.removeIf(component -> check_collision(component));
        }
        else{
            System.exit(1);
        }
    }
    public boolean check_collision(Rectangle current_bricks){
        if(circle.getBoundsInParent().intersects(current_bricks.getBoundsInParent())){

            boolean rightside = circle.getLayoutX() >= ((current_bricks.getLayoutX() + current_bricks.getWidth()) + circle.getRadius());
            boolean lefttside = circle.getLayoutX() <= ((current_bricks.getLayoutX())  - circle.getRadius());
            boolean upside = circle.getLayoutY() <= (current_bricks.getLayoutY() + circle.getRadius());
            boolean bottomside = circle.getLayoutY() >= ((current_bricks.getLayoutY() + current_bricks.getHeight()) - circle.getRadius());

            System.out.println();
            if(lefttside || rightside){
                deltaX *= -1;
            }
            if(upside || bottomside){
                deltaY *= -1;
            }
            scene.getChildren().remove(current_bricks);
            return true;
        }
        else return false;
    }
    public void check_scene_collision(){
        Bounds bounds = scene.getBoundsInLocal();
        boolean rightside = circle.getLayoutX() >= (bounds.getMaxX() - circle.getRadius());
        boolean lefttside = circle.getLayoutX() <= (bounds.getMinX() + circle.getRadius());
        boolean upside = circle.getLayoutY() <= (bounds.getMinY() + circle.getRadius());
        boolean bottomside = circle.getLayoutY() >= (bounds.getMaxY() - circle.getRadius());

        if(lefttside || rightside){
            deltaX *= -1;
        }
        if(upside || bottomside){
            deltaY *= -1;
        }
        if(bottomside){
            System.exit(1);

        }
    }
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        // we will initialize all the bricks
        createbircks();
        timeline.setCycleCount(Animation.INDEFINITE);
        add_slider();
        add_button();
        timeline.play();
    }

    public  void  createbircks(){
        int flag = 1;
        for(int i = 250; i > 0; i -= 35){
            for(int j = 465; j > 0; j -= 25){
                if(flag % 2 == 0){
                    Rectangle rectangle = new Rectangle(j,i,28,28);
                    if(flag % 3 == 0){
                        rectangle.setFill(Color.RED);
                    } else if (flag % 3 == 1) {
                        rectangle.setFill(Color.GREEN);
                    } else {
                        rectangle.setFill(Color.BLUE);
                    }
                    scene.getChildren().add(rectangle);
                    all_bricks.add(rectangle);
                }
                flag++;
            }
        }
    }
    public void add_slider(){
        slider = new Rectangle(300,380,78,15);
        slider.setFill(Color.BLACK);
        scene.getChildren().add(slider);
    }
    public void add_button(){
        right = new Button("Right");
        right.setLayoutX(430);
        right.setLayoutY(370);

        left = new Button("Left");
        left.setLayoutX(20);
        left.setLayoutY(370);

        right.setOnAction(moveright);
        left.setOnAction(moveleft);
        scene.getChildren().add(right);
        scene.getChildren().add(left);
    }
    EventHandler<ActionEvent> moveright = new EventHandler<ActionEvent>() {
        @Override
        public void handle(ActionEvent actionEvent) {
            slider.setLayoutX(slider.getLayoutX() + 10);
        }
    };
    EventHandler<ActionEvent> moveleft = new EventHandler<ActionEvent>() {
        @Override
        public void handle(ActionEvent actionEvent) {
            slider.setLayoutX(slider.getLayoutX() - 10);
        }
    };
}