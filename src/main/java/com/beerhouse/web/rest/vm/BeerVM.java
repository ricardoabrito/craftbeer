package com.beerhouse.web.rest.vm;

import java.math.BigDecimal;
import java.util.Objects;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

public class BeerVM {

    private  Long id;
    @NotBlank
    private  String name;
    @NotBlank
    private  String ingredients;
    @NotBlank
    private  String alcoholContent;
    @NotNull
    private  BigDecimal price;
    @NotBlank
    private  String category;

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
        BeerVM beerVM = (BeerVM) o;
        return Objects.equals(id, beerVM.id) &&
                Objects.equals(name, beerVM.name) &&
                Objects.equals(ingredients, beerVM.ingredients) &&
                Objects.equals(alcoholContent, beerVM.alcoholContent) &&
                Objects.equals(price, beerVM.price) &&
                Objects.equals(category, beerVM.category);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, ingredients, alcoholContent, price, category);
    }

    @Override
    public String toString() {
        return "BeerVM{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", ingredients='" + ingredients + '\'' +
                ", alcoholContent='" + alcoholContent + '\'' +
                ", price=" + price +
                ", category='" + category + '\'' +
                '}';
    }

}
