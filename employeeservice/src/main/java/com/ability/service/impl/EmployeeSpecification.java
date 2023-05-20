package com.ability.service.impl;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.criteria.Predicate;

import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;

import com.ability.entity.EmployeeEntity;
import com.ability.model.SearchRequest;

@Component
public class EmployeeSpecification {

    public Specification<EmployeeEntity> getEmployees(SearchRequest request) {
        return (root, query, criteriaBuilder) -> {
            List<Predicate> predicates = new ArrayList<>();
            if (request.getEmailAddress() != null && !request.getEmailAddress().isEmpty()) {
                predicates.add(criteriaBuilder.equal(root.get("emailAddress"), request.getEmailAddress()));
            }
            if (request.getFirstName() != null && !request.getFirstName().isEmpty()) {
                predicates.add(criteriaBuilder.like(criteriaBuilder.lower(root.get("firstName")),
                        "%" + request.getFirstName().toLowerCase() + "%"));
            }
            if (request.getLastName() != null && !request.getLastName().isEmpty()) {
                predicates.add(criteriaBuilder.like(criteriaBuilder.lower(root.get("lastName")),
                        "%" + request.getLastName().toLowerCase() + "%"));
            }
            if (request.getIsVisited() != null && !request.getIsVisited().isEmpty()) {
                predicates.add(criteriaBuilder.equal(root.get("isVisited"), request.getIsVisited()));
            }
            if (request.getIsConsentSigned() != null && !request.getIsConsentSigned().isEmpty()) {
                predicates.add(criteriaBuilder.equal(root.get("isConsentSigned"), request.getIsConsentSigned()));
            }
            query.orderBy(criteriaBuilder.desc(root.get("timeCreated")));
            return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
        };
    }
}
