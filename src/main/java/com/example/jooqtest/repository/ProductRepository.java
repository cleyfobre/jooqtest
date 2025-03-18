package com.example.jooqtest.repository;

import java.util.List;
import java.util.Optional;

import org.jooq.DSLContext;
import org.springframework.stereotype.Repository;

import com.example.jooqtest.model.ProductDto;

import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class ProductRepository {

    private final DSLContext dsl;

    public List<ProductDto> findAll() {
        return dsl.selectFrom(PRODUCT)
                .fetch()
                .map(this::mapToDto);
    }

    public Optional<ProductDto> findById(Long id) {
        return dsl.selectFrom(PRODUCT)
                .where(PRODUCT.ID.eq(id))
                .fetchOptional()
                .map(this::mapToDto);
    }

    public ProductDto save(ProductDto productDto) {
        ProductRecord record = dsl.insertInto(PRODUCT)
                .set(PRODUCT.NAME, productDto.getName())
                .set(PRODUCT.STOCK, productDto.getStock())
                .set(PRODUCT.VERSION, productDto.getVersion())
                .returning()
                .fetchOne();
        
        return mapToDto(record);
    }

    public ProductDto update(ProductDto productDto) {
        ProductRecord record = dsl.update(PRODUCT)
                .set(PRODUCT.NAME, productDto.getName())
                .set(PRODUCT.STOCK, productDto.getStock())
                .set(PRODUCT.VERSION, productDto.getVersion())
                .where(PRODUCT.ID.eq(productDto.getId()))
                .returning()
                .fetchOne();
        
        return mapToDto(record);
    }

    public void delete(Long id) {
        dsl.deleteFrom(PRODUCT)
                .where(PRODUCT.ID.eq(id))
                .execute();
    }

    private ProductDto mapToDto(ProductRecord record) {
        return ProductDto.builder()
                .id(record.getId())
                .name(record.getName())
                .stock(record.getStock())
                .version(record.getVersion())
                .build();
    }
}