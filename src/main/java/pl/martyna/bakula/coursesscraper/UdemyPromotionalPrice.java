package pl.martyna.bakula.coursesscraper;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

class UdemyPromotionalPriceDetails {
    private final double price;

    @JsonCreator
    public UdemyPromotionalPriceDetails(@JsonProperty ("amount") double price) {
        this.price = price;
    }

    public double getPrice() {
        return price;
    }
}
