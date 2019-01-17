package com.suitong.devplatform.service.mapper;

import com.suitong.devplatform.domain.Authority;
import com.suitong.devplatform.domain.User;
import com.suitong.devplatform.service.dto.UserDTO;

import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

/**
 * Mapper for the entity User and its DTO called UserDTO.
 *
 * Normal mappers are generated using MapStruct, this one is hand-coded as MapStruct
 * support is still in beta, and requires a manual step with an IDE.
 */
@Service
public class UserMapper {

    public UserDTO userToUserDTO(User user) {
        return new UserDTO(user);
    }

    public List<UserDTO> usersToUserDTOs(List<User> users) {
        return users.stream()
            .filter(Objects::nonNull)
            .map(this::userToUserDTO)
            .collect(Collectors.toList());
    }

    public User userDTOToUser(UserDTO userDTO) {
        if (userDTO == null) {
            return null;
        } else {
            User user = new User();
            user.setId(userDTO.getId());
            user.setLogin(userDTO.getLogin());
            user.setFirstName(userDTO.getFirstName());
            user.setLastName(userDTO.getLastName());
            user.setEmail(userDTO.getEmail());
            user.setImageUrl(userDTO.getImageUrl());
            user.setActivated(userDTO.isActivated());
            user.setLangKey(userDTO.getLangKey());
            Set<Authority> authorities = this.authoritiesFromStrings(userDTO.getAuthorities());
            if(authorities != null) {
                user.setAuthorities(authorities);
            }
            user.setCreatedBy(userDTO.getCreatedBy());
            user.setCreatedDate(userDTO.getCreatedDate());
            user.setLastModifiedBy(userDTO.getLastModifiedBy());
            user.setLastModifiedDate(userDTO.getLastModifiedDate());
            user.setActivationKey(userDTO.getActivationKey());
            user.setResetKey(userDTO.getResetKey());
            user.setResetDate(userDTO.getResetDate());
            user.setCharNo(userDTO.getCharNo());
            user.setSex(userDTO.getSex());
            user.setIdNo(userDTO.getIdNo());
            user.setDeptId(userDTO.getDeptId());
            user.setTel(userDTO.getTel());
            user.setMobile(userDTO.getMobile());
            user.setOnboardDate(userDTO.getOnboardDate());
            user.setExpireDate(userDTO.getExpireDate());
            user.setStatus(userDTO.getStatus());
            user.setDelFlag(userDTO.getDelFlag());
            user.setPasswordExpireDate(userDTO.getPasswordExpireDate());
            user.setDepartment(userDTO.getDepartment());
            user.setErrorCount(userDTO.getErrorCount());
            return user;
        }
    }

    public List<User> userDTOsToUsers(List<UserDTO> userDTOs) {
        return userDTOs.stream()
            .filter(Objects::nonNull)
            .map(this::userDTOToUser)
            .collect(Collectors.toList());
    }

    public User userFromId(Long id) {
        if (id == null) {
            return null;
        }
        User user = new User();
        user.setId(id);
        return user;
    }

    public Set<Authority> authoritiesFromStrings(Set<String> strings) {
        return strings.stream().map(string -> {
            Authority auth = new Authority();
            auth.setName(string);
            return auth;
        }).collect(Collectors.toSet());
    }
}
