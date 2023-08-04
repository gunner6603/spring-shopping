package shopping.cart.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import shopping.TestFixture;
import shopping.cart.domain.entity.Product;
import shopping.cart.dto.response.ProductResponse;
import shopping.cart.repository.ProductRepository;

@ExtendWith(MockitoExtension.class)
@DisplayName("ProductService 단위 테스트")
class ProductServiceTest {

    @Mock
    private ProductRepository productRepository;
    @InjectMocks
    private ProductService productService;

    @Test
    @DisplayName("성공 : 전체 상품 목록 불러오기")
    void findAll() {
        /* given */
        final Product chicken = TestFixture.createProductEntity("치킨", 20000);
        final Product pizza = TestFixture.createProductEntity("피자", 25000);
        final Product sake = TestFixture.createProductEntity("사케", 30000);

        List<Product> products = List.of(chicken, pizza, sake);
        when(productRepository.findAll()).thenReturn(products);

        /* when */
        List<ProductResponse> productResponses = productService.findAllProducts();

        /* then */
        assertThat(productResponses).hasSize(products.size());
    }
}
