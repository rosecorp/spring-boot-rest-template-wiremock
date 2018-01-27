package com.rosecorp.example;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.Arrays;
import java.util.List;

public class FunctionalInterfaceTest {

    private Variant v1;
    private Product p1;

    @Before
    public void before() {
        v1 = new Variant();
        v1.setId(1);
        v1.setAmount(1);
    }

    @Test
    public void test() {

        p1 = new Product(1, "p1", null);
        ProductProcessor pw = ProductProcessor.of(p1);
        Assert.assertEquals("ProductProcessor{of=Product{id=1, name='p1', variants=null}}", pw.toString());

        ProductProcessor pwNew = pw.enrich(v1);
        Assert.assertEquals("ProductProcessor{of=Product{id=1, name='p1', variants=[Variant{id=1, amount=1}]}}", pwNew.toString());

        Product product = pwNew.getProduct();
        Assert.assertEquals("Product{id=1, name='p1', variants=[Variant{id=1, amount=1}]}", product.toString());

        // check if original product object didn't change.
        Assert.assertEquals("Product{id=1, name='p1', variants=null}", p1.toString());

    }

    private static class ProductProcessor {
        private final Product product;

        public static ProductProcessor of(Product product) {
            return new ProductProcessor(product);
        }

        public ProductProcessor enrich(Variant variant) {
            return productProcessor(newProductInstanceWith(Arrays.asList(variant)));
        }

        private Product newProductInstanceWith(List<Variant> variants) {
            return new Product(product.getId(), product.getName(), variants);
        }

        private ProductProcessor productProcessor(Product product) {
            return new ProductProcessor(product);
        }

        private ProductProcessor(Product product) {
            this.product = product;
        }

        public Product getProduct() {
            return product;
        }

        @Override
        public String toString() {
            return "ProductProcessor{" +
                    "of=" + product +
                    '}';
        }
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
        private Integer id;
        private String name;
        private List<Variant> variants;

        public Product(Integer id, String name, List<Variant> variants) {
            this.id = id;
            this.name = name;
            this.variants = variants;
        }

        public Integer getId() {
            return id;
        }

        public void setId(Integer id) {
            this.id = id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public List<Variant> getVariants() {
            return variants;
        }

        public void setVariants(List<Variant> variants) {
            this.variants = variants;
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
