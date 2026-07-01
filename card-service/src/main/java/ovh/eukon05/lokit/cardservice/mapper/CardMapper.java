package ovh.eukon05.lokit.cardservice.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;
import ovh.eukon05.lokit.cardservice.dto.request.CreateCardDTO;
import ovh.eukon05.lokit.cardservice.dto.response.GetCardDTO;
import ovh.eukon05.lokit.cardservice.model.CardEntity;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface CardMapper {
    @Mapping(source = "user.id", target = "userId")
    GetCardDTO toGetCardDTO(CardEntity cardEntity);

    CardEntity fromCreateCardDTO(CreateCardDTO createCardDTO);
}
