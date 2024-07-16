package br.com.alura.forum.domain.user;

import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

@Converter
public class RoleConverter implements AttributeConverter<UserRoles, String> {

    @Override
    public String convertToDatabaseColumn(UserRoles role) {
        return role.name();
    }

    @Override
    public UserRoles convertToEntityAttribute(String roleString) {
        return UserRoles.valueOf(roleString.toUpperCase());
    }
}
