package com.rosecorp.wiremock;

import org.junit.Test;

import java.util.List;

public class WiremockApplicationTests {

    @Test
    public void contextLoads() {
        String name = "Java Lambda";
        Processor stringProcessor = (String str, Integer value) -> str.length() + value;
        int length = stringProcessor.getStringLength(name, 1000);

        System.out.println(length);
    }

    @FunctionalInterface
    interface Processor {
        int getStringLength(String str, Integer value);
    }

    @Test
    public void testJooq() {
        Variant v1 = new Variant();
        v1.setId(1);
        v1.setAmount(1);

        Variant v2 = new Variant();
        v2.setId(2);
        v2.setAmount(2);
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
        private List<WiremockApplicationTests.Variant> variants;

        public Product(Integer id, String name, List<WiremockApplicationTests.Variant> variants) {
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

        public List<WiremockApplicationTests.Variant> getVariants() {
            return variants;
        }

        public void setVariants(List<WiremockApplicationTests.Variant> variants) {
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
