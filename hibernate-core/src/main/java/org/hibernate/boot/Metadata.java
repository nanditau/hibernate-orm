/*
 * Hibernate, Relational Persistence for Idiomatic Java
 *
 * Copyright (c) 2014, Red Hat Inc. or third-party contributors as
 * indicated by the @author tags or express copyright attribution
 * statements applied by the authors.  All third-party contributions are
 * distributed under license by Red Hat Inc.
 *
 * This copyrighted material is made available to anyone wishing to use, modify,
 * copy, or redistribute it subject to the terms and conditions of the GNU
 * Lesser General Public License, as published by the Free Software Foundation.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY
 * or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU Lesser General Public License
 * for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with this distribution; if not, write to:
 * Free Software Foundation, Inc.
 * 51 Franklin Street, Fifth Floor
 * Boston, MA  02110-1301  USA
 */
package org.hibernate.boot;

import java.util.Map;
import java.util.UUID;

import org.hibernate.SessionFactory;
import org.hibernate.boot.model.IdentifierGeneratorDefinition;
import org.hibernate.boot.model.TypeDefinition;
import org.hibernate.boot.model.relational.Database;
import org.hibernate.cfg.annotations.NamedEntityGraphDefinition;
import org.hibernate.cfg.annotations.NamedProcedureCallDefinition;
import org.hibernate.dialect.function.SQLFunction;
import org.hibernate.engine.ResultSetMappingDefinition;
import org.hibernate.engine.spi.FilterDefinition;
import org.hibernate.engine.spi.Mapping;
import org.hibernate.engine.spi.NamedQueryDefinition;
import org.hibernate.engine.spi.NamedSQLQueryDefinition;
import org.hibernate.mapping.Collection;
import org.hibernate.mapping.FetchProfile;
import org.hibernate.mapping.PersistentClass;
import org.hibernate.mapping.Table;

/**
 * Represents the ORM model as determined from all provided mapping sources.
 *
 * NOTE : for the time being this is essentially a copy of the legacy Mappings contract, split between
 * reading the mapping information exposed here and collecting it via InFlightMetadataCollector
 *
 * @author Steve Ebersole
 */
public interface Metadata extends Mapping {
	/**
	 * Get the builder for {@link org.hibernate.SessionFactory} instances based on this metamodel,
	 *
	 * @return The builder for {@link org.hibernate.SessionFactory} instances.
	 */
	SessionFactoryBuilder getSessionFactoryBuilder();

	/**
	 * Short-hand form of building a {@link org.hibernate.SessionFactory} through the builder without any additional
	 * option overrides.
	 *
	 * @return THe built SessionFactory.
	 */
	SessionFactory buildSessionFactory();

	/**
	 * Gets the {@link java.util.UUID} for this metamodel.
	 *
	 * @return the UUID.
	 */
	UUID getUUID();

	/**
	 * Retrieve the database model.
	 *
	 * @return The database model.
	 */
	Database getDatabase();

	/**
	 * Retrieves the PersistentClass entity metadata representation for known all entities.
	 *
	 * Returned collection is immutable
	 *
	 * @return All PersistentClass representations.
	 */
	java.util.Collection<PersistentClass> getEntityBindings();

	/**
	 * Retrieves the PersistentClass entity mapping metadata representation for
	 * the given entity name.
	 *
	 * @param entityName The entity name for which to retrieve the metadata.
	 *
	 * @return The entity mapping metadata, or {@code null} if no matching entity found.
	 */
	PersistentClass getEntityBinding(String entityName);

	/**
	 * Retrieves the Collection metadata representation for known all collections.
	 *
	 * Returned collection is immutable
	 *
	 * @return All Collection representations.
	 */
	java.util.Collection<Collection> getCollectionBindings();

	/**
	 * Retrieves the collection mapping metadata for the given collection role.
	 *
	 * @param role The collection role for which to retrieve the metadata.
	 *
	 * @return The collection mapping metadata, or {@code null} if no matching collection found.
	 */
	Collection getCollectionBinding(String role);

	/**
	 * Retrieves all defined imports (class renames).
	 *
	 * @return All imports
	 */
	Map<String,String> getImports();

	/**
	 * Retrieve named query metadata by name.
	 *
	 * @param name The query name
	 *
	 * @return The named query metadata, or {@code null}.
	 */
	NamedQueryDefinition getNamedQueryDefinition(String name);

	java.util.Collection<NamedQueryDefinition> getNamedQueryDefinitions();

	/**
	 * Retrieve named SQL query metadata.
	 *
	 * @param name The SQL query name.
	 *
	 * @return The named query metadata, or {@code null}
	 */
	NamedSQLQueryDefinition getNamedNativeQueryDefinition(String name);

	java.util.Collection<NamedSQLQueryDefinition> getNamedNativeQueryDefinitions();

	java.util.Collection<NamedProcedureCallDefinition> getNamedProcedureCallDefinitions();

	/**
	 * Retrieve the metadata for a named SQL result set mapping.
	 *
	 * @param name The mapping name.
	 *
	 * @return The named result set mapping metadata, or {@code null} if none found.
	 */
	ResultSetMappingDefinition getResultSetMapping(String name);

	Map<String, ResultSetMappingDefinition> getResultSetMappingDefinitions();

	/**
	 * Retrieve a type definition by name.
	 *
	 * @param typeName The name of the type definition to retrieve.
	 *
	 * @return The named type definition, or {@code null}
	 */
	TypeDefinition getTypeDefinition(String typeName);

	/**
	 * Retrieves the complete map of filter definitions.
	 *
	 * Returned map is immutable
	 *
	 * @return The filter definition map.
	 */
	Map<String,FilterDefinition> getFilterDefinitions();

	/**
	 * Retrieves a filter definition by name.
	 *
	 * @param name The name of the filter definition to retrieve
	 * .
	 * @return The filter definition, or {@code null}.
	 */
	FilterDefinition getFilterDefinition(String name);

	FetchProfile getFetchProfile(String name);

	java.util.Collection<FetchProfile> getFetchProfiles();

	NamedEntityGraphDefinition getNamedEntityGraph(String name);

	Map<String, NamedEntityGraphDefinition> getNamedEntityGraphs();

	IdentifierGeneratorDefinition getIdentifierGenerator(String name);

	java.util.Collection<Table> collectTableMappings();

	Map<String,SQLFunction> getSqlFunctionMap();
}
