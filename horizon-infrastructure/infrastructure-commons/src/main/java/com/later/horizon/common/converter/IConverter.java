package com.later.horizon.common.converter;

import ma.glasnost.orika.MapperFactory;

import java.util.List;

public interface IConverter {

    MapperFactory initMapperFactory();

    <A, B> B convert(A clazzA, Class<B> clazzB);

    <A, B> List<B> convert(Iterable<A> clazzAs, Class<B> clazzB);

}
