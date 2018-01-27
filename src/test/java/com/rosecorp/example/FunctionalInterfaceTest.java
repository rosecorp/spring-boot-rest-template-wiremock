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

    @Before
    public void before() {
        v1 = new Variant();
        v1.setId(1);
        v1.setAmount(1);
        variants.add(v1);
    }

    @Test
    public void test() {

        p1 = new Product(1, "product", null);
        ProductWrapper pw = ProductWrapper.product(p1);
        Assert.assertEquals("ProductWrapper{product=Product{id=1, name='product', variants=null}}", pw.toString());

        ProductWrapper pwNew = pw.withVariant(v1);
        Assert.assertEquals("ProductWrapper{product=Product{id=1, name='product', variants=[Variant{id=1, amount=1}]}}", pwNew.toString());

        Product product = pwNew.getProduct();
        Assert.assertEquals("Product{id=1, name='product', variants=[Variant{id=1, amount=1}]}", product.toString());

    }


    private static class ProductWrapper {
        private final Product product;

        public ProductWrapper withVariant(Variant variant) {
            List<Variant> variants = new ArrayList<>();
            variants.add(variant);

            return new ProductWrapper(new Product(product.getId(), product.getName(), variants));
        }

        public static ProductWrapper product(Product product) {
            return new ProductWrapper(product);
        }

        private ProductWrapper(Product product) {
            this.product = product;
        }

        public Product getProduct() {
            return product;
        }

        @Override
        public String toString() {
            return "ProductWrapper{" +
                    "product=" + product +
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
