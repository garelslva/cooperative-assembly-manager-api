package br.com.assembly.converter;

import org.springframework.data.domain.Page;

import java.util.List;
import java.util.Optional;

public interface Converter {

    <T, E> T toDto(E input);

    <T, E> T toDomain(E input);

    <T, E> T toEntity(E input);

    <T, E> List<T> toDto(List<E> input);

    <T, E> List<T> toDomain(List<E> input);

    <T, E> List<T> toEntity(List<E> input);

    <T, E> Optional<T> toDtoOptional(E input);

    <T, E> Optional<T> toDomainOptional(E input);

    <T, E> Optional<T> toEntityOptional(E input);

    <T, E> Optional<List<T>> toDtoOptional(List<E> input);

    <T, E> Optional<List<T>> toDomainOptional(List<E> input);

    <T, E> Optional<List<T>> toEntityOptional(List<E> input);

    <T, E> Page<T> toDto(Page<E> input);

    <T, E> Page<T> toDomain(Page<E> input);

    <T, E> Optional<Page<T>> toDomainOptionalPage(Page<E> input);

    Integer parseToInteger(String identifier);

    Long parseToLong(String value);

    <N> String parseToString(N value);
}
