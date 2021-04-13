package com.oop.mappers;

import com.oop.entities.dao.CreditCard;
import com.oop.entities.response.CreditCardResponse;
import org.mapstruct.Mapper;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface CreditCardMapper {
    CreditCardMapper INSTANCE = Mappers.getMapper(CreditCardMapper.class);

    List<CreditCardResponse> toResponses(List<CreditCard> creditCards);
    CreditCardResponse toResponse(CreditCard creditCard);
}
