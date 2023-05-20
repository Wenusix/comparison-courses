package pl.martyna.bakula.coursesscraper;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

class UdemyPromotionalPrice {
    private final UdemyPromotionalPriceDetails udemyPromotionalPriceDetails;

    @JsonCreator
    public UdemyPromotionalPrice(
            @JsonProperty ("price") UdemyPromotionalPriceDetails udemyPromotionalPriceDetails) {
        this.udemyPromotionalPriceDetails = udemyPromotionalPriceDetails;
    }

    public UdemyPromotionalPriceDetails getUdemyPromotionalPriceDetails() {
        return udemyPromotionalPriceDetails;
    }
}
