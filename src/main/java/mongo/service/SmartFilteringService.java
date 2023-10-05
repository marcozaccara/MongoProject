package mongo.service;

import mongo.dto.FilterDTO;

import java.util.List;

/**
 * Smart filtering interface. Given a list of {@link FilterDTO} return a list of T generics
 *
 * @param <T>
 */
public interface SmartFilteringService<T> {

    List<T> filterByCriteria(List<FilterDTO> filters);
}
