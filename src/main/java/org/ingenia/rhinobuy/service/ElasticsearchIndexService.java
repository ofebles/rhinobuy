package org.ingenia.rhinobuy.service;

import com.codahale.metrics.annotation.Timed;
import org.ingenia.rhinobuy.domain.*;
import org.ingenia.rhinobuy.repository.*;
import org.ingenia.rhinobuy.repository.search.*;
import org.elasticsearch.indices.IndexAlreadyExistsException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.elasticsearch.core.ElasticsearchTemplate;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.inject.Inject;
import java.lang.reflect.Method;
import java.util.List;

@Service
public class ElasticsearchIndexService {

    private final Logger log = LoggerFactory.getLogger(ElasticsearchIndexService.class);

    @Inject
    private CategoryRepository categoryRepository;

    @Inject
    private CategorySearchRepository categorySearchRepository;

    @Inject
    private CustomerRepository customerRepository;

    @Inject
    private CustomerSearchRepository customerSearchRepository;

    @Inject
    private CustomerPaymentMethodRepository customerPaymentMethodRepository;

    @Inject
    private CustomerPaymentMethodSearchRepository customerPaymentMethodSearchRepository;

    @Inject
    private InvoiceRepository invoiceRepository;

    @Inject
    private InvoiceSearchRepository invoiceSearchRepository;

    @Inject
    private OrderItemRepository orderItemRepository;

    @Inject
    private OrderItemSearchRepository orderItemSearchRepository;

    @Inject
    private OrderItemStatusCodeRepository orderItemStatusCodeRepository;

    @Inject
    private OrderItemStatusCodeSearchRepository orderItemStatusCodeSearchRepository;

    @Inject
    private OrdersRepository ordersRepository;

    @Inject
    private OrdersSearchRepository ordersSearchRepository;

    @Inject
    private PaymentRepository paymentRepository;

    @Inject
    private PaymentSearchRepository paymentSearchRepository;

    @Inject
    private PictureRepository pictureRepository;

    @Inject
    private PictureSearchRepository pictureSearchRepository;

    @Inject
    private ProductRepository productRepository;

    @Inject
    private ProductSearchRepository productSearchRepository;

    @Inject
    private ProductDescriptionRepository productDescriptionRepository;

    @Inject
    private ProductDescriptionSearchRepository productDescriptionSearchRepository;

    @Inject
    private PromotionRepository promotionRepository;

    @Inject
    private PromotionSearchRepository promotionSearchRepository;

    @Inject
    private ReferenceInvoiceStatusRepository referenceInvoiceStatusRepository;

    @Inject
    private ReferenceInvoiceStatusSearchRepository referenceInvoiceStatusSearchRepository;

    @Inject
    private ReferenceLanguageRepository referenceLanguageRepository;

    @Inject
    private ReferenceLanguageSearchRepository referenceLanguageSearchRepository;

    @Inject
    private ReferencePaymentMethodRepository referencePaymentMethodRepository;

    @Inject
    private ReferencePaymentMethodSearchRepository referencePaymentMethodSearchRepository;

    @Inject
    private ShipmentRepository shipmentRepository;

    @Inject
    private ShipmentSearchRepository shipmentSearchRepository;

    @Inject
    private ShopingCartRepository shopingCartRepository;

    @Inject
    private ShopingCartSearchRepository shopingCartSearchRepository;

    @Inject
    private SupplierRepository supplierRepository;

    @Inject
    private SupplierSearchRepository supplierSearchRepository;

    @Inject
    private WishListRepository wishListRepository;

    @Inject
    private WishListSearchRepository wishListSearchRepository;

    @Inject
    private UserRepository userRepository;

    @Inject
    private UserSearchRepository userSearchRepository;

    @Inject
    private ElasticsearchTemplate elasticsearchTemplate;

    @Async
    @Timed
    public void reindexAll() {
        reindexForClass(Category.class, categoryRepository, categorySearchRepository);
        reindexForClass(Customer.class, customerRepository, customerSearchRepository);
        reindexForClass(CustomerPaymentMethod.class, customerPaymentMethodRepository, customerPaymentMethodSearchRepository);
        reindexForClass(Invoice.class, invoiceRepository, invoiceSearchRepository);
        reindexForClass(OrderItem.class, orderItemRepository, orderItemSearchRepository);
        reindexForClass(OrderItemStatusCode.class, orderItemStatusCodeRepository, orderItemStatusCodeSearchRepository);
        reindexForClass(Orders.class, ordersRepository, ordersSearchRepository);
        reindexForClass(Payment.class, paymentRepository, paymentSearchRepository);
        reindexForClass(Picture.class, pictureRepository, pictureSearchRepository);
        reindexForClass(Product.class, productRepository, productSearchRepository);
        reindexForClass(ProductDescription.class, productDescriptionRepository, productDescriptionSearchRepository);
        reindexForClass(Promotion.class, promotionRepository, promotionSearchRepository);
        reindexForClass(ReferenceInvoiceStatus.class, referenceInvoiceStatusRepository, referenceInvoiceStatusSearchRepository);
        reindexForClass(ReferenceLanguage.class, referenceLanguageRepository, referenceLanguageSearchRepository);
        reindexForClass(ReferencePaymentMethod.class, referencePaymentMethodRepository, referencePaymentMethodSearchRepository);
        reindexForClass(Shipment.class, shipmentRepository, shipmentSearchRepository);
        reindexForClass(ShopingCart.class, shopingCartRepository, shopingCartSearchRepository);
        reindexForClass(Supplier.class, supplierRepository, supplierSearchRepository);
        reindexForClass(WishList.class, wishListRepository, wishListSearchRepository);
        reindexForClass(User.class, userRepository, userSearchRepository);

        log.info("Elasticsearch: Successfully performed reindexing");
    }

    @Transactional(readOnly = true)
    @SuppressWarnings("unchecked")
    private <T> void reindexForClass(Class<T> entityClass, JpaRepository<T, Long> jpaRepository,
                                                          ElasticsearchRepository<T, Long> elasticsearchRepository) {
        elasticsearchTemplate.deleteIndex(entityClass);
        try {
            elasticsearchTemplate.createIndex(entityClass);
        } catch (IndexAlreadyExistsException e) {
            // Do nothing. Index was already concurrently recreated by some other service.
        }
        elasticsearchTemplate.putMapping(entityClass);
        if (jpaRepository.count() > 0) {
            try {
                Method m = jpaRepository.getClass().getMethod("findAllWithEagerRelationships");
                elasticsearchRepository.save((List<T>) m.invoke(jpaRepository));
            } catch (Exception e) {
                elasticsearchRepository.save(jpaRepository.findAll());
            }
        }
        log.info("Elasticsearch: Indexed all rows for " + entityClass.getSimpleName());
    }
}
