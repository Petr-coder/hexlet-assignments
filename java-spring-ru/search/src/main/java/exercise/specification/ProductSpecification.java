package exercise.specification;

import exercise.dto.ProductParamsDTO;
import exercise.model.Product;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;

import java.util.function.Function;
@Component
public class ProductSpecification {

    public static Specification<Product> hasCategory(Long categoryId) {
        return nullableSpecification(categoryId, id -> (root, query, criteriaBuilder) ->
                criteriaBuilder.equal(root.get("category").get("id"), id));
    }

    public static Specification<Product> priceGreaterThan(Integer price) {
        return nullableSpecification(price, p -> (root, query, criteriaBuilder) ->
                criteriaBuilder.greaterThan(root.get("price"), p));
    }

    public static Specification<Product> priceLessThan(Integer price) {
        return nullableSpecification(price, p -> (root, query, criteriaBuilder) ->
                criteriaBuilder.lessThan(root.get("price"), p));
    }

    public static Specification<Product> ratingGreaterThan(Double rating) {
        return nullableSpecification(rating, r -> (root, query, criteriaBuilder) ->
                criteriaBuilder.greaterThan(root.get("rating"), r));
    }

    public static Specification<Product> titleContains(String title) {
        return nullableSpecification(title, t -> (root, query, criteriaBuilder) ->
                criteriaBuilder.like(criteriaBuilder.lower(root.get("title")), "%" + t.toLowerCase() + "%"));
    }

    private static <T> Specification<Product> nullableSpecification(T value, Function<T, Specification<Product>> function) {
        return (root, query, criteriaBuilder) ->
                value == null ? criteriaBuilder.conjunction() : function.apply(value).toPredicate(root, query, criteriaBuilder);
    }

    public static Specification<Product> build(ProductParamsDTO params) {
        return Specification.where(hasCategory(params.getCategoryId()))
                .and(priceGreaterThan(params.getPriceGt()))
                .and(priceLessThan(params.getPriceLt()))
                .and(ratingGreaterThan(params.getRatingGt()))
                .and(titleContains(params.getTitleCont()));
    }
}