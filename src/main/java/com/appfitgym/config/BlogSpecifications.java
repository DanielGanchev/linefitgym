package com.appfitgym.config;

import com.appfitgym.model.entities.Blog;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import org.springframework.data.jpa.domain.Specification;

public class BlogSpecifications implements Specification<Blog> {

    private String field;
    private String query;

    public BlogSpecifications(String field, String query) {
        this.field = field;
        this.query = query;
    }

    @Override
    public Predicate toPredicate(Root<Blog> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {

        if (field.equals("title")) {
            return criteriaBuilder.like(root.get("title"), "%" + this.query + "%");
        } else if (field.equals("author")) {
            return criteriaBuilder.like(root.get("author"), "%" + this.query + "%");
        } else if (field.equals("description")) {
            return criteriaBuilder.like(root.get("description"), "%" + this.query + "%");
        } else {
            return null;
        }
    }
}
