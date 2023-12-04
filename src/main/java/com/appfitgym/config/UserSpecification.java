package com.appfitgym.config;

import com.appfitgym.model.entities.UserEntity;
import com.appfitgym.model.entities.mail.VerificationToken;
import com.appfitgym.model.enums.UserRoleEnum;
import jakarta.persistence.criteria.*;
import java.time.LocalDate;
import org.springframework.data.jpa.domain.Specification;

public class UserSpecification implements Specification<UserEntity> {

  private String field;
  private String query;

  public UserSpecification(String field, String query) {
    this.field = field;
    this.query = query;
  }

  @Override
  public Predicate toPredicate(
      Root<UserEntity> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {
    if (field.equals("username")
        || field.equals("email")
        || field.equals("firstName")
        || field.equals("lastName")) {
      return criteriaBuilder.like(root.get(field), "%" + query + "%");
    } else if (field.equals("country")) {
      return criteriaBuilder.like(root.join("country").get("name"), "%" + query + "%");
    } else if (field.equals("city")) {
      return criteriaBuilder.like(root.join("city").get("name"), "%" + query + "%");
    } else if (field.equals("role")) {
      try {
        UserRoleEnum roleEnum = UserRoleEnum.valueOf(query.toUpperCase());
        return criteriaBuilder.equal(root.join("roles").get("role"), roleEnum);
      } catch (IllegalArgumentException e) {
        // Role doesn't exist
        return null;
      }
    } else if (field.equals("createdOn")) {
      return criteriaBuilder.like(root.get(field), "%" + query + "%");
    } else if (field.equals("age")) {
      int age = Integer.parseInt(query);
      LocalDate now = LocalDate.now();
      LocalDate from = now.minusYears(age + 1).plusDays(1);
      LocalDate to = now.minusYears(age);

      return criteriaBuilder.between(root.get("birthDate"), from, to);
    } else if (field.equals("status")) {
      Subquery<String> subquery = criteriaQuery.subquery(String.class);
      Root<VerificationToken> verificationTokenRoot = subquery.from(VerificationToken.class);
      subquery
          .select(criteriaBuilder.literal("decline"))
          .where(
              criteriaBuilder.equal(verificationTokenRoot.get("user"), root),
              criteriaBuilder.isFalse(root.get("isActive")));

      Expression<String> statusExpression =
          criteriaBuilder
              .<String>selectCase()
              .when(criteriaBuilder.isTrue(root.get("isActive")), "approved")
              .when(criteriaBuilder.exists(subquery), "pending")
              .otherwise("decline");

      return criteriaBuilder.equal(statusExpression, query);
    } else if (field.equals("coaches")) {
      if (query.isEmpty()) {
        return criteriaBuilder.equal(root.join("roles").get("role"), UserRoleEnum.COACH);
      }

    } else if (field.equals("trainees")) {
      if (query.isEmpty()) {
        return criteriaBuilder.equal(root.join("roles").get("role"), UserRoleEnum.TRAINEE);
      }
    }

    return null;
  }
}
