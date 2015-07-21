package com.deepsouthcloud.docker.languages;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

// tag::language_repository[]
@RepositoryRestResource
public interface LanguageRepository extends PagingAndSortingRepository<Language, Long> {
}
// end::language_repository[]
