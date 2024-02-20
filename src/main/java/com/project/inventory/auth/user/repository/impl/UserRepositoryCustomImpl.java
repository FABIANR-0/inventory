package com.project.inventory.auth.user.repository.impl;

import com.project.inventory.auth.permission.dto.PermissionResponse;
import com.project.inventory.auth.permission.entity.Permission;
import com.project.inventory.auth.role.entity.Role;
import com.project.inventory.auth.user.entity.User;
import com.project.inventory.auth.user.repository.UserRepositoryCustom;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import jakarta.persistence.criteria.*;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.List;

@Slf4j
public class UserRepositoryCustomImpl implements UserRepositoryCustom {

    @PersistenceContext
    private EntityManager manager;

    @Override
    public List<PermissionResponse> getPermissionByUserName(String userName) {
        CriteriaBuilder cb = manager.getCriteriaBuilder();
        List<PermissionResponse> result = new ArrayList<>();

        try {
            CriteriaQuery<PermissionResponse> cq = cb.createQuery(PermissionResponse.class);

            Root<User> root = cq.from(User.class);
            Join<User, Role> userRoleJoin = root.join("roles", JoinType.INNER);
            Join<Role, Permission> permissionJoin = userRoleJoin.join("permissions",JoinType.INNER);

            cq.select(cb.construct(
                    PermissionResponse.class,
                    permissionJoin.get("permissionName")
            ));

            cq.where(cb.equal(root.get("userName"),userName));

            cq.distinct(true);

            TypedQuery<PermissionResponse> query = manager.createQuery(cq);
            result = query.getResultList();

        }catch (Exception ex){
            log.error("error en la consulta criteria getPermissionByUserName {}",ex.getMessage());
        }
        manager.close();
        return result;
    }
}
