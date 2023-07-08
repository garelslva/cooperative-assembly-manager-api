package br.com.assembly.converter.structure;

import br.com.assembly.api.exception.InternalServerErrorException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Slf4j
public class ParseTo {

    private static final String CONVERT_TO_ENTITY_FROM_DOMAIN = "entityFromDomain";
    private static final String CONVERT_TO_DTO_FROM_DOMAIN = "dtoFromDomain";
    private static final String CONVERT_TO_DOMAIN_FROM_ENTITY_OR_DTO = "domainFromEntityOrDto";
    private Class<?> dtoClass;
    private Class<?> domainClass;
    private Class<?> entityClass;
    private String cursor;

    protected ParseTo(Class<?> dtoClass, Class<?> domainClass, Class<?> entityClass){
        this.domainClass = domainClass;
        this.dtoClass = dtoClass;
        this.entityClass = entityClass;
    }

    protected ParseTo ofConvertToDomainFromEntityOrDto() {
        this.cursor = CONVERT_TO_DOMAIN_FROM_ENTITY_OR_DTO;
        return this;
    }

    protected ParseTo ofConvertToEntityFromDomain() {
        this.cursor = CONVERT_TO_ENTITY_FROM_DOMAIN;
        return this;
    }

    protected ParseTo ofConvertToDtoFromDomain() {
        this.cursor = CONVERT_TO_DTO_FROM_DOMAIN;
        return this;
    }

    protected <T> Object translator(final T input, final AbstractConverter abstractConverter) {
        if (input == null || input.getClass() == null){
            throw new RuntimeException("Error: input class is missing to translator process");
        }
        if (input.getClass().equals(dtoClass)) {
            return (T) abstractConverter.converterToDomainFromDto(input);
        }
        if (input.getClass().equals(entityClass)) {
            return (T) abstractConverter.converterToDomainFromEntity(input);
        }
        if (input.getClass().equals(domainClass)) {
                if (Objects.equals(this.cursor, CONVERT_TO_DTO_FROM_DOMAIN)){
                    return abstractConverter.converterToDtoFromDomain(input);

                }else if (Objects.equals(this.cursor, CONVERT_TO_ENTITY_FROM_DOMAIN)){
                    return abstractConverter.converterToEntityFromDomain(input);
                }
        }
        return null;
    }

    protected <T, E> T parse(final E input, final ParseTo parseTo, final AbstractConverter abstractConverter) {
        if (input == null){
            return null;
        }
        try {
            return (T) parseTo.translator(input, abstractConverter);

        }catch (Exception e){
            log.error(e.getMessage());
            e.printStackTrace();
            throw new InternalServerErrorException();
        }
    }

    protected <T, E> List<T> parse(final List<E> lst, final ParseTo parseTo, final AbstractConverter abstractConverter) {
        if (lst == null){
            return null;
        }
        try {
            List<T> listOut = new ArrayList<>();
            lst.forEach(in -> {
                listOut.add(parse(in, parseTo, abstractConverter));
            });
            return listOut;

        }catch (Exception e){
            log.error(e.getMessage());
            e.printStackTrace();
            throw new InternalServerErrorException();
        }
    }

    protected <T, E> Page<T> parse(final Page<E> pageIn, final ParseTo parseTo, final AbstractConverter abstractConverter) {
        if (pageIn == null){
            return null;
        }
        try {
            return pageIn.map( item -> parse(item, parseTo, abstractConverter));

        }catch (Exception e){
            log.error(e.getMessage());
            e.printStackTrace();
            throw new InternalServerErrorException();
        }
    }

    protected <T, E> Optional<T> parseOptional(final E input, final ParseTo parseTo, final AbstractConverter abstractConverter) {
        if (input == null){
            return null;
        }
        return Optional.ofNullable(parse(input, parseTo, abstractConverter));
    }

    protected <T, E> Optional<List<T>> parseOptional(final List<E> input, final ParseTo parseTo, final AbstractConverter abstractConverter) {
        if (input == null){
            return null;
        }
        return Optional.ofNullable(parse(input, parseTo, abstractConverter));
    }

    protected <T, E> Optional<Page<T>> parseOptional(final Page<E> pageIn, final ParseTo parseTo, final AbstractConverter abstractConverter) {
        if (pageIn == null){
            return null;
        }
        return Optional.ofNullable(parse(pageIn, parseTo, abstractConverter));
    }

    protected <T, E> E parseTo(final T value, final Class clss){
        if (value == null){
            return null;
        }
        try{
            if (clss.equals(Integer.class)) {
                return (E) ((Integer) Integer.parseInt((String) value));
            }
            if (clss.equals(String.class)) {
                return (E) String.valueOf(value);
            }
            if (clss.equals(Long.class)) {
                return (E) ((Long) Long.parseLong((String) value));
            }
            return null;
        }catch (Exception e){
            log.error(e.getMessage());
            e.printStackTrace();
            throw new InternalServerErrorException();
        }
    }

    protected <T> boolean noEmpty(final T type) {
        try {
            if (type instanceof String) {
                return type != null && !((String) type).isEmpty();
            }else{
                return type != null;
            }
        }catch (NullPointerException e){
            return false;
        }
    }

    protected <T> T given(final T entity) {
        if (entity == null){
            try {
                return (T) entity.getClass().getConstructors()[0].newInstance(entity.getClass());

            } catch (InstantiationException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            } catch (InvocationTargetException e) {
                e.printStackTrace();
            }
        }
        return entity;
    }
}
