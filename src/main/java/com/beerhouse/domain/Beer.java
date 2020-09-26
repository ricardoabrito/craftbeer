package com.beerhouse.domain;


import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.util.Objects;

@Entity
@DynamicUpdate
public class Beer {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotBlank
    private String name;
    @NotBlank
    private String ingredients;
    @NotBlank
    private String alcoholContent;
    @NotNull
    private BigDecimal price;
    @NotBlank
    private String category;

    public Beer() {
    }

    public Beer(Long id, @NotBlank String name, @NotBlank String ingredients, @NotBlank String alcoholContent, @NotNull BigDecimal price, @NotBlank String category) {
        this.id = id;
        this.name = name;
        this.ingredients = ingredients;
        this.alcoholContent = alcoholContent;
        this.price = price;
        this.category = category;
    }

    public Beer update(Beer beer) {
        return new Beer(id, beer.name, beer.ingredients, beer.alcoholContent,
                beer.price, beer.category);
    }

    public Beer patch(Beer beer) {
        return new Beer(id,
                patchValue(beer.name, name), patchValue(beer.ingredients, ingredients),
                patchValue(beer.alcoholContent, alcoholContent), patchValue(beer.price, price),
                patchValue(beer.category, category));
    }

    private <T> T patchValue(T newValue, T pastValue) {
        return newValue == null ? pastValue : newValue;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getIngredients() {
        return ingredients;
    }

    public void setIngredients(String ingredients) {
        this.ingredients = ingredients;
    }

    public String getAlcoholContent() {
        return alcoholContent;
    }

    public void setAlcoholContent(String alcoholContent) {
        this.alcoholContent = alcoholContent;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Beer beer = (Beer) o;
        return Objects.equals(id, beer.id) &&
                Objects.equals(name, beer.name) &&
                Objects.equals(ingredients, beer.ingredients) &&
                Objects.equals(alcoholContent, beer.alcoholContent) &&
                Objects.equals(price, beer.price) &&
                Objects.equals(category, beer.category);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, ingredients, alcoholContent, price, category);
    }

    @Override
    public String toString() {
        return "Beer{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", ingredients='" + ingredients + '\'' +
                ", alcoholContent='" + alcoholContent + '\'' +
                ", price=" + price +
                ", category='" + category + '\'' +
                '}';
    }
}
