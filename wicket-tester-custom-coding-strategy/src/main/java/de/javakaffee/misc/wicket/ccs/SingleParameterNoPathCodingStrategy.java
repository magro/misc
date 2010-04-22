/*
 * Copyright 2009 Martin Grotzke
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an &quot;AS IS&quot; BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 */
package de.javakaffee.misc.wicket.ccs;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.wicket.request.target.coding.IndexedParamUrlCodingStrategy;
import org.apache.wicket.request.target.coding.QueryStringUrlCodingStrategy;
import org.apache.wicket.util.string.AppendingStringBuffer;
import org.apache.wicket.util.value.ValueMap;

/**
 */
public class SingleParameterNoPathCodingStrategy extends IndexedParamUrlCodingStrategy {

    public static final String FIRST_PARAMETER_KEY = "0";

    private final DelegatingQueryStringUrlCodingStrategy _queryStringUrlCodingStrategy;

    public SingleParameterNoPathCodingStrategy(  final String mountPath,  final Class<?> bookmarkablePageClass ) {
        super( mountPath, bookmarkablePageClass );

        _queryStringUrlCodingStrategy = new DelegatingQueryStringUrlCodingStrategy( mountPath, bookmarkablePageClass );
    }

    @Override
    @SuppressWarnings( { "unchecked" } )
    protected void appendParameters(  final AppendingStringBuffer url,  final Map parameters ) {

        if ( url.length() == 0 ) {
            // if the url does not end with "/", super.appendParameters would append a slash, which
            // would create an absolute path
            url.append( "./" );
        }

        // append first param to url, not as query-string param
        final Object firstParam = parameters.get( FIRST_PARAMETER_KEY );
        if ( firstParam != null ) {
            super.appendParameters( url, Collections.singletonMap( FIRST_PARAMETER_KEY, firstParam ) );
        } else {
            throw new IllegalArgumentException(
                    "The SingleParameterNoPathCodingStrategy must not be used without the first parameter." );
        }

        // add all other params to the query-string
        _queryStringUrlCodingStrategy.appendParameters( url, parametersWithoutFirst( parameters ) );
    }

    
    private Map<String, Object> parametersWithoutFirst(  final Map<String, Object> parameters ) {
        final Map<String, Object> result = new HashMap<String, Object>( parameters.size() );
        for ( final Entry<String, Object> entry : parameters.entrySet() ) {
            if ( !entry.getKey().equals( FIRST_PARAMETER_KEY ) ) {
                result.put( entry.getKey(), entry.getValue() );
            }
        }
        return result;
    }

    @Override
    @SuppressWarnings( "unchecked" )
    protected ValueMap decodeParameters(  final String urlFragment,  final Map urlParameters ) {
        final ValueMap firstParam = super.decodeParameters( urlFragment, urlParameters );

        final ValueMap params = _queryStringUrlCodingStrategy.decodeParameters( urlFragment, urlParameters );
        final Object firstParamValue = firstParam.get( FIRST_PARAMETER_KEY );
        if ( firstParamValue != null ) {
            params.put( FIRST_PARAMETER_KEY, firstParamValue );
        }

        return params;
    }
    
    private static class DelegatingQueryStringUrlCodingStrategy extends QueryStringUrlCodingStrategy {
        
        public DelegatingQueryStringUrlCodingStrategy(  final String mountPath,
                 final Class<?> bookmarkablePageClass ) {
            super( mountPath, bookmarkablePageClass );
        }

        @Override
        @SuppressWarnings( "unchecked" )
        public void appendParameters(  final AppendingStringBuffer url,  final Map parameters ) {
            super.appendParameters( url, parameters );
        }

        @Override
        @SuppressWarnings( "unchecked" )
        public ValueMap decodeParameters(  final String fragment,  final Map passedParameters ) {
            return super.decodeParameters( fragment, passedParameters );
        }

    }

}