package org.sakaiproject.nakamura.solr;

import org.apache.solr.client.solrj.SolrServer;
import org.apache.solr.common.SolrInputDocument;
import org.osgi.service.event.Event;
import org.sakaiproject.nakamura.api.lite.ClientPoolException;
import org.sakaiproject.nakamura.api.lite.StorageClientException;
import org.sakaiproject.nakamura.api.lite.accesscontrol.AccessDeniedException;
import org.sakaiproject.nakamura.api.solr.IndexingHandler;
import org.sakaiproject.nakamura.api.solr.RepositorySession;
import org.sakaiproject.nakamura.api.solr.SolrServerService;
import org.sakaiproject.nakamura.api.solr.TopicIndexer;
import org.sakaiproject.nakamura.lite.BaseMemoryRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.xml.sax.SAXException;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Dictionary;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Map;
import java.util.Random;

import javax.xml.parsers.ParserConfigurationException;

public class ContentEventListenerSoak {

  private static final Logger LOGGER = LoggerFactory
      .getLogger(ContentEventListenerSoak.class);
  private BaseMemoryRepository baseMemoryRepository;
  
  public static void main(String[] argv) throws IOException, ParserConfigurationException, SAXException, InterruptedException, ClientPoolException, StorageClientException, AccessDeniedException, ClassNotFoundException {
    ContentEventListenerSoak s = new ContentEventListenerSoak();
    s.testContentEventListener();
  }

  public ContentEventListenerSoak() throws IOException, ParserConfigurationException,
      SAXException, ClientPoolException, StorageClientException, AccessDeniedException, ClassNotFoundException {
    baseMemoryRepository = new BaseMemoryRepository();
  }

  public void testContentEventListener() throws IOException, 
      InterruptedException, ClientPoolException, StorageClientException, AccessDeniedException {
    final ContentEventListener contentEventListener = new ContentEventListener();
    
    contentEventListener.sparseRepository = baseMemoryRepository.getRepository();

    contentEventListener.solrServerService = new SolrServerService() {
      
      public String getSolrHome() {
        return "target/solrtest";
      }
      
      public SolrServer getServer() {
        return null;
      }

      public SolrServer getUpdateServer() {
        return null;
      }
    };
    Map<String, Object> properties = new HashMap<String, Object>();
    properties.put("dont-run", true);
    contentEventListener.activate(properties);

    IndexingHandler h =  new IndexingHandler() {
      
      public Collection<SolrInputDocument> getDocuments(RepositorySession repositorySession, Event event) {
        return new ArrayList<SolrInputDocument>();
      }
      
      public Collection<String> getDeleteQueries(RepositorySession repositorySession, Event event) {
        return new ArrayList<String>();
      }

    };
    contentEventListener.addHandler("test/topic",h);
    Random r = new Random();
    for (int j = 0; j < 100; j++) {
      int e = r.nextInt(10000);
      LOGGER.debug("Adding Events {} ",e);
      for (int i = 0; i < e; i++) {
        int ttl = 45+r.nextInt(10000);
        Dictionary<String, Object> props = new Hashtable<String, Object>();
        props.put("evnumber", i);
        props.put("a nasty,key", "with a, nasty \n value");
        props.put("path", "path/"+j+"/"+i);
        props.put(TopicIndexer.TTL, ttl);
        contentEventListener.handleEvent(new Event("test/topic", props));
      }
      int t = r.nextInt(10000);
      LOGGER.info("Sleeping {} ",t);
      Thread.sleep(t);
    }
    contentEventListener.closeAll();
    LOGGER.info("Done adding Events ");

    contentEventListener.removeHandler("/test/topic", h);

    contentEventListener.deactivate(properties);

    LOGGER.info("Waiting for worker thread ");
    contentEventListener.joinAll();
    LOGGER.info("Joined worker thread");
  }

}
