package ovh.eukon05.lokit.cardservice.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;
import ovh.eukon05.lokit.cardservice.dto.response.GetUserDTO;
import ovh.eukon05.lokit.cardservice.model.CardEntity;
import ovh.eukon05.lokit.cardservice.model.UserEntity;

import java.util.Set;
import java.util.stream.Collectors;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface UserMapper {
    GetUserDTO toGetUserDTO(UserEntity user);

    default Set<String> mapCards(Set<CardEntity> cards) {
        return cards.stream()
                .map(CardEntity::getId)
                .collect(Collectors.toSet());
    }
}
