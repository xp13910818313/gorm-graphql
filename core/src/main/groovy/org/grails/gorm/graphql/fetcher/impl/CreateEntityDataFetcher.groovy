package org.grails.gorm.graphql.fetcher.impl

import grails.gorm.transactions.Transactional
import graphql.schema.DataFetchingEnvironment
import groovy.transform.CompileStatic
import groovy.transform.InheritConstructors
import org.grails.datastore.gorm.GormEntity
import org.grails.datastore.mapping.model.PersistentEntity
import org.grails.gorm.graphql.binding.GraphQLDataBinder
import org.grails.gorm.graphql.fetcher.BindingGormDataFetcher
import org.grails.gorm.graphql.fetcher.DefaultGormDataFetcher
import org.grails.gorm.graphql.fetcher.GraphQLDataFetcherType

/**
 * A class for creating entities with GraphQL
 *
 * @param <T> The domain type to create
 * @author James Kleeh
 */
@CompileStatic
@InheritConstructors
class CreateEntityDataFetcher<T> extends DefaultGormDataFetcher<T> implements BindingGormDataFetcher {

    GraphQLDataBinder dataBinder

    @Override
    @Transactional
    T get(DataFetchingEnvironment environment) {
        GormEntity instance = (GormEntity)entity.javaClass.newInstance()
        dataBinder.bind(instance, (Map)environment.getArgument(entity.decapitalizedName))
        instance.save()
        (T)instance
    }

    @Override
    GraphQLDataFetcherType getType() {
        GraphQLDataFetcherType.CREATE
    }

}