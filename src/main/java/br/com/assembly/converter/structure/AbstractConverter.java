package br.com.assembly.converter.structure;

import br.com.assembly.converter.Converter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;

import java.util.*;

@Slf4j
public abstract class AbstractConverter<Dto, Dominio, Entity> extends ParseTo implements Converter {

    private Class<?> dtoClass;
    private Class<?> domainClass;
    private Class<?> entityClass;

    public AbstractConverter(Class<?> dtoClass, Class<?> domainClass, Class<?> entityClass){
        super(dtoClass, domainClass, entityClass);
    }

    protected abstract Dominio converterToDomainFromEntity(final Entity entity);

    protected abstract Entity converterToEntityFromDomain(final Dominio domain);

    protected abstract Dominio converterToDomainFromDto(final Dto dto);

    protected abstract Dto converterToDtoFromDomain(final Dominio domain);

    @Override
    public <T, E> T toDto(final E input) {
        return super.parse(input, super.ofConvertToDtoFromDomain(), this);
    }

    @Override
    public <T, E> T toDomain(final E input) {
        return super.parse(input, super.ofConvertToDomainFromEntityOrDto(), this);
    }

    @Override
    public <T, E> T toEntity(final E input) {
        return parse(input, super.ofConvertToEntityFromDomain(), this);
    }

    @Override
    public <T, E> List<T> toDto(final List<E> lstIn) {
        return super.parse(lstIn, super.ofConvertToDtoFromDomain(), this);
    }

    @Override
    public <T, E> List<T> toDomain(final List<E> lstIn) {
        return super.parse(lstIn, super.ofConvertToDomainFromEntityOrDto(), this);
    }

    @Override
    public <T, E> List<T> toEntity(final List<E> lst) {
        return super.parse(lst, super.ofConvertToEntityFromDomain(), this);
    }

    @Override
    public <T, E> Page<T> toDto(final Page<E> pageIn) {
        return super.parse(pageIn, super.ofConvertToDtoFromDomain(), this);
    }

    @Override
    public <T, E> Page<T> toDomain(final Page<E> pageIn) {
        return super.parse(pageIn, super.ofConvertToDomainFromEntityOrDto(), this);
    }

    @Override
    public <T, E> Optional<T> toDtoOptional(final E input) {
        return super.parseOptional(input, super.ofConvertToDtoFromDomain(), this);
    }

    @Override
    public <T, E> Optional<T>toDomainOptional(final E input) {
        return super.parseOptional(input, super.ofConvertToDomainFromEntityOrDto(), this);
    }

    @Override
    public <T, E> Optional<T> toEntityOptional(final E input) {
        return super.parseOptional(input, super.ofConvertToEntityFromDomain(), this);
    }

    @Override
    public <T, E> Optional<List<T>> toDtoOptional(final List<E> input) {
        return super.parseOptional(input, super.ofConvertToDtoFromDomain(), this);
    }

    @Override
    public <T, E> Optional<List<T>> toDomainOptional(final List<E> input) {
        return super.parseOptional(input, super.ofConvertToDomainFromEntityOrDto(), this);
    }

    @Override
    public <T, E> Optional<List<T>> toEntityOptional(final List<E> input) {
        return super.parseOptional(input, super.ofConvertToEntityFromDomain(), this);
    }

    @Override
    public <T, E> Optional<Page<T>> toDomainOptionalPage(final Page<E> pageIn) {
        return super.parseOptional(pageIn, super.ofConvertToDomainFromEntityOrDto(), this);
    }

    @Override
    public Integer parseToInteger(final String value){
        return super.parseTo(value, Integer.class);
    }

    @Override
    public Long parseToLong(final String value){
        return super.parseTo(value, Long.class);
    }

    @Override
    public <N> String parseToString(final N value){
        return super.parseTo(value, String.class);
    }

}
