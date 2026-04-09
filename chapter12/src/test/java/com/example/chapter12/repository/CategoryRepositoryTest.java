package com.example.chapter12.repository;

import com.example.chapter12.entity.Category;
import com.example.chapter12.entity.Product;
import com.example.chapter12.entity.ProductCategory;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Random;

@SpringBootTest
public class CategoryRepositoryTest {

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private ProductCategoryRepository productCategoryRepository;

    @Autowired
    private ProductRepository productRepository;

    @Test
    void insertDummyData() {
        // 카테고리 등록
        String[][] categories = {
                {"전자제품", "전자기기 및 가전제품"},
                {"의류", "패션 의류 및 액세서리"},
                {"식품", "신선식품 및 가공식품"},
                {"가구", "생활가구 및 인테리어"},
                {"도서", "도서 및 문구류"}
        };

        for (String[] cat : categories) {
            Category category = Category.builder()
                    .name(cat[0])
                    .description(cat[1])
                    .build();
            categoryRepository.save(category);
        }

        // 상품별 카테고리 설정 (상품 1~100에 랜덤 카테고리 배정)
        Random random = new Random();
        for (long i = 1; i <= 100; i++) {
            Product product = productRepository.findById(i).orElse(null);
            if (product == null) continue;

            // 각 상품에 1~2개 카테고리 배정
            long categoryIdx = random.nextInt(5) + 1; // 1~5
            ProductCategory pc = ProductCategory.builder()
                    .product(product)
                    .category(categoryRepository.findById(categoryIdx).orElseThrow())
                    .build();
            productCategoryRepository.save(pc);

            // 50% 확률로 두 번째 카테고리 추가
            if (random.nextBoolean()) {
                long categoryIdx2;
                do {
                    categoryIdx2 = random.nextInt(5) + 1;
                } while (categoryIdx2 == categoryIdx); // 중복 방지

                ProductCategory pc2 = ProductCategory.builder()
                        .product(product)
                        .category(categoryRepository.findById(categoryIdx2).orElseThrow())
                        .build();
                productCategoryRepository.save(pc2);
            }
        }
    }

    //12번 상품의 카테고리 목록
    @Test
    void testByProduct() {
        List<ProductCategory> categories = productCategoryRepository.findByProductIdx(12L);
        categories.forEach(c-> System.out.println(c));
    }

    //2번 카테고리의 상품 목록
    @Test
    void testByCategory() {
        Pageable pageable = PageRequest.of(0, 10,
                Sort.Direction.DESC, "crdt");
        Page<ProductCategory> products = productCategoryRepository.findByCategoryIdx(2L, pageable);
        products.forEach(p-> System.out.println(p));
    }

    // 양방향이므로 상품에서도 카테고리 조회 가능
    @Transactional
    @Test
    void testByProduct2() {
        Product product = productRepository.findById(12L).orElse(null);
        product.getProductCategories().forEach(c-> System.out.println(c));
    }
}
