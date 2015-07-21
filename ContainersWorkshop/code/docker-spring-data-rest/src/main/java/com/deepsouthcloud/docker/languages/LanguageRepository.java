package com.deepsouthcloud.docker.languages;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource
public interface LanguageRepository extends PagingAndSortingRepository<Language, Long> {
}
