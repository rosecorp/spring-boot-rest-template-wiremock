package com.rosecorp.example;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

public class FunctionalInterfaceTest {

    private Variant v1;
    private List<Variant> variants = new ArrayList<>();
    private Product p1;

    private VariantProcessor variantProcessor = (Variant v) -> p1.withVariants(variants);

    @Before
    public void before() {
        v1 = new Variant();
        v1.setId(1);
        v1.setAmount(1);
        variants.add(v1);
    }

    @Test
    public void test() {

        p1 = Product.createProduct(1, "product");
        Assert.assertEquals("Product{id=1, name='product', variants=[]}", p1.toString());

        Product newP = variantProcessor.enrich(v1);
        Assert.assertEquals("Product{id=1, name='product', variants=[Variant{id=1, amount=1}]}", newP.toString());
    }

    @FunctionalInterface
    interface VariantProcessor {
        Product enrich(Variant v);
    }

    private static class Variant {
        private Integer id;
        private Integer amount;

        public Integer getId() {
            return id;
        }

        public void setId(Integer id) {
            this.id = id;
        }

        public Integer getAmount() {
            return amount;
        }

        public void setAmount(Integer amount) {
            this.amount = amount;
        }

        @Override
        public String toString() {
            return "Variant{" +
                    "id=" + id +
                    ", amount=" + amount +
                    '}';
        }
    }

    private static class Product {
        private final Integer id;
        private final String name;
        private final List<Variant> variants;

        public Product withVariants(List<Variant> variants) {
            return new Product(id, name, variants);
        }

        public static Product createProduct(Integer id, String name) {
            return new Product(id, name);
        }

        private Product(Integer id, String name) {
            this.id = id;
            this.name = name;
            this.variants = new ArrayList<>();
        }

        private Product(Integer id, String name, List<Variant> variants) {
            this.id = id;
            this.name = name;
            this.variants = variants;
        }

        public Integer getId() {
            return id;
        }

        public String getName() {
            return name;
        }

        public List<Variant> getVariants() {
            return variants;
        }

        @Override
        public String toString() {
            return "Product{" +
                    "id=" + id +
                    ", name='" + name + '\'' +
                    ", variants=" + variants +
                    '}';
        }
    }
}
