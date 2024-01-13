package org.egx.news.secuiry;

import org.springframework.core.convert.converter.Converter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.jwt.Jwt;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class KeyCloakRoleConverter implements Converter<Jwt, Collection<GrantedAuthority>> {
    @Override
    public Collection<GrantedAuthority> convert(Jwt source) {
        Map<String,Map<String,Object>> resource_access =(Map<String,Map<String,Object>>)source.getClaims().get("resource_access");
        Map<String,Object> result = (Map<String,Object>)resource_access.get("EGX-ADMIN-CLI");
        if(result.size()==0 || result==null) return new ArrayList<>();
        Collection<GrantedAuthority> roles =((List<String>) result.get("roles"))
                .stream().map(role -> "ROLE_"+role).map(SimpleGrantedAuthority::new).collect(Collectors.toList());
        return roles;
    }
}
