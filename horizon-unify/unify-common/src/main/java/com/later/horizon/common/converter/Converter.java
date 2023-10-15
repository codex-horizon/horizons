package com.later.horizon.common.converter;

import ma.glasnost.orika.MapperFacade;
import ma.glasnost.orika.MapperFactory;
import ma.glasnost.orika.impl.DefaultMapperFactory;

import java.util.List;

public class Converter implements IConverter {

    private static MapperFacade MapperFacade;

    public Converter() {
        MapperFacade = getMapperFactory().getMapperFacade();
    }

    @Override
    public MapperFactory getMapperFactory() {
        return new DefaultMapperFactory.Builder().build();
    }

    @Override
    public <A, B> B convert(A clazzA, Class<B> clazzB) {
        return MapperFacade.map(clazzA, clazzB);
    }

    @Override
    public <A, B> List<B> convert(Iterable<A> clazzAs, Class<B> clazzB) {
        return MapperFacade.mapAsList(clazzAs, clazzB);
    }

}
