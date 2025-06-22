package me.taldir.alten;

import com.github.javafaker.Faker;

import me.taldir.alten.dto.ProductDTO;
import me.taldir.alten.model.InventoryStatus;
import me.taldir.alten.model.Product;

public class TestDataFactory {
    private static final Faker faker = new Faker();

    public static ProductDTO createRandomProductDTO() {
        return new ProductDTO(
            faker.code().ean8(),
            faker.commerce().productName(),
            faker.lorem().sentence(),
            "image_" + faker.number().digits(3) + ".png",
            faker.commerce().department(),
            faker.number().randomDouble(3, 0, 100),
            faker.number().numberBetween(1, 100),
            faker.code().isbn10(),
            faker.number().numberBetween(1, 10),
            InventoryStatus.values()[faker.number().numberBetween(0, InventoryStatus.values().length)],
            faker.number().numberBetween(0, 10)
        );
    }

    public static Product createRandomProduct() {
        ProductDTO dto = createRandomProductDTO();
        return dto.toProduct();
    }
}