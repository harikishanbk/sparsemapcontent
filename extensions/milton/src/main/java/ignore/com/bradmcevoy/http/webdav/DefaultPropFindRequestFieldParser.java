/*
* Licensed to the Apache Software Foundation (ASF) under one
* or more contributor license agreements.  See the NOTICE file
* distributed with this work for additional information
* regarding copyright ownership.  The ASF licenses this file
* to you under the Apache License, Version 2.0 (the
* "License"); you may not use this file except in compliance
* with the License.  You may obtain a copy of the License at
*
* http://www.apache.org/licenses/LICENSE-2.0
*
* Unless required by applicable law or agreed to in writing,
* software distributed under the License is distributed on an
* "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
* KIND, either express or implied.  See the License for the
* specific language governing permissions and limitations
* under the License.
*/

package ignore.com.bradmcevoy.http.webdav;

import com.bradmcevoy.http.webdav.PropFindRequestFieldParser;
import com.bradmcevoy.http.webdav.PropFindSaxHandler;
import com.bradmcevoy.http.webdav.PropertiesRequest;
import com.bradmcevoy.io.StreamUtils;
import java.io.ByteArrayInputStream;
import org.apache.commons.io.output.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.LinkedHashSet;
import java.util.Set;
import javax.xml.namespace.QName;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.XMLReader;
import org.xml.sax.helpers.XMLReaderFactory;

/**
 * Simple implmentation which just parses the request body. If no xml is present
 * it will return an empty set.
 *
 * Note this generally shouldnt be used directly, but should be wrapped by
 * MSPropFindRequestFieldParser to support windows clients.
 *
 * @author brad
 */
public class DefaultPropFindRequestFieldParser implements PropFindRequestFieldParser {

    private static final Logger log = LoggerFactory.getLogger( DefaultPropFindRequestFieldParser.class );

    public DefaultPropFindRequestFieldParser() {
    }

	@Override
    public PropertiesRequest getRequestedFields( InputStream in ) {
        try {
            final Set<QName> set = new LinkedHashSet<QName>();
            ByteArrayOutputStream bout = new ByteArrayOutputStream();
            StreamUtils.readTo( in, bout, false, true );
            byte[] arr = bout.toByteArray();
            if( arr.length > 1 ) {
                ByteArrayInputStream bin = new ByteArrayInputStream( arr );
                XMLReader reader = XMLReaderFactory.createXMLReader();
                PropFindSaxHandler handler = new PropFindSaxHandler();
                reader.setContentHandler( handler );
                try {
                    reader.parse( new InputSource( bin ) );
                    if( handler.isAllProp() ) {
                        return new PropertiesRequest();
                    } else {
                        set.addAll( handler.getAttributes().keySet() );
                    }
                } catch( IOException e ) {
                    log.warn( "exception parsing request body", e );
                    // ignore
                } catch( SAXException e ) {
                    log.warn( "exception parsing request body", e );
                    // ignore
// ieb modification
                    throw new RuntimeBadRequestException(e.getMessage(), e);
                }
            }
            return PropertiesRequest.toProperties(set);
        } catch( Exception ex ) {
            throw new RuntimeException( ex );
        }
    }
}
