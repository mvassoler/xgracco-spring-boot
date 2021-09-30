package br.com.finchsolucoes.xgracco.core.interfaces;

import org.modelmapper.Conditions;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.data.domain.Page;

import java.util.List;
import java.util.stream.Collectors;

public interface TransformerModelMapper<DTO, ENTITY>{

    default DTO toDtoMapper(ENTITY entity,Class<DTO> dtoReference) {

        return getModelMapper().map(entity, dtoReference);
    }

    default ENTITY toEntityMapper(DTO dto,Class<ENTITY> entityReference) {

        return getModelMapper().map(dto, entityReference);
    }

    default Page<DTO> toDtoPagedMapper(Page<ENTITY> entityPaged,Class<DTO> dtoReference) {
        return entityPaged.map(entity -> {
            return toDtoMapper(entity,dtoReference);
        });
    }

    default Page<ENTITY> toEntityPagedMapper(Page<DTO> dtoPaged,Class<ENTITY> entityReference) {
        return dtoPaged.map(dto -> {
            return toEntityMapper(dto,entityReference);
        });
    }

    default List<DTO> toDtoListMapper(List<ENTITY> entityList, Class<DTO> dtoReference) {
        return entityList.stream().map(entity -> {
            return this.toDtoMapper(entity,dtoReference);
        }).collect(Collectors.toList());
    }

    default List<ENTITY> toEntityListMapper(List<DTO> dtoList, Class<ENTITY> entityReference) {
        return dtoList.stream().map(dto -> {
            return this.toEntityMapper(dto,entityReference);
        }).collect(Collectors.toList());
    }

    default ModelMapper getModelMapper() {
        ModelMapper model = new ModelMapper();
        model.getConfiguration().setPropertyCondition(Conditions.isNotNull());
        model.getConfiguration().setMatchingStrategy(MatchingStrategies.LOOSE);
        return model;
    }
}
