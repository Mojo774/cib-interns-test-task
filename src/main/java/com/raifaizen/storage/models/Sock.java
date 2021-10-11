package com.raifaizen.storage.models;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;

import javax.persistence.*;

@Entity
public class Sock {

    @Id
    @GeneratedValue(strategy=GenerationType.AUTO, generator = "native")
    @GenericGenerator(name = "native", strategy = "native")
    private Long id;

    @NotBlank (message = "Please fill the message")
    @Length(max = 40, message = "Title too long")
    private String color;

    @NotBlank (message = "Please fill the message")
    @Min(value = 0,message = "0-100")
    @Max(value = 100)
    private int cottonPart;

    public Sock() {
    }

    public Sock(String color, int cottonPart) {
        this.color = color;
        this.cottonPart = cottonPart;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public int getCottonPart() {
        return cottonPart;
    }

    public void setCottonPart(int cottonPart) {
        this.cottonPart = cottonPart;
    }
}
