package mongo.service;

import mongo.dto.FilterDTO;

import java.util.List;

public interface SmartFilteringService<T> {

    List<T> filterByCriteria(List<FilterDTO> filters);
}
