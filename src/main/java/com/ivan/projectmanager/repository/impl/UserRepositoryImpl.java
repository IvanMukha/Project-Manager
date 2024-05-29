package com.ivan.projectmanager.repository.impl;

import com.ivan.projectmanager.model.Role;
import com.ivan.projectmanager.model.User;
import com.ivan.projectmanager.model.User_;
import com.ivan.projectmanager.repository.AbstractRepository;
import com.ivan.projectmanager.repository.UserRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Repository
@Transactional
public class UserRepositoryImpl extends AbstractRepository<User, Long> implements UserRepository {

    public UserRepositoryImpl(EntityManager entityManager) {
        super(entityManager, User.class);
    }

    public Page<User> getAll(Pageable pageable) {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<User> criteriaQuery = criteriaBuilder.createQuery(User.class);
        Root<User> root = criteriaQuery.from(User.class);
        criteriaQuery.select(root);

        TypedQuery<User> typedQuery = entityManager.createQuery(criteriaQuery);
        typedQuery.setFirstResult((int) pageable.getOffset());
        typedQuery.setMaxResults(pageable.getPageSize());

        List<User> resultList = typedQuery.getResultList();

        CriteriaQuery<Long> countQuery = criteriaBuilder.createQuery(Long.class);
        countQuery.select(criteriaBuilder.count(countQuery.from(User.class)));
        Long totalRows = entityManager.createQuery(countQuery).getSingleResult();

        return new PageImpl<>(resultList, pageable, totalRows);
    }

    @Override
    public Optional<User> getById(Long id) {
        return super.getById(id);
    }

    @Override
    public Optional<User> update(Long id, User updatedEntity) {
        return super.getById(id).map(user -> {
            user.setUsername(updatedEntity.getUsername());
            user.setPassword(updatedEntity.getPassword());
            user.setEmail(updatedEntity.getEmail());
            user.setRoles(updatedEntity.getRoles());
            entityManager.merge(user);
            return user;
        });
    }

    @Override
    public void delete(Long id) {
        super.delete(id);
    }

    public List<User> getByUsername(String username) {
        CriteriaBuilder builder = entityManager.getCriteriaBuilder();
        CriteriaQuery<User> query = builder.createQuery(User.class);
        Root<User> root = query.from(User.class);
        Predicate predicate = builder.equal(root.get(User_.USERNAME), username);
        query.select(root).where(predicate);
        return entityManager.createQuery(query).getResultList();
    }

    @Transactional
    public Set<Role> findRolesByUserUsername(String username) {
        return entityManager.createQuery(
                        "SELECT r FROM Role r JOIN r.users u WHERE u.username = :username", Role.class)
                .setParameter("username", username).getResultStream().collect(Collectors.toSet());
    }
}

