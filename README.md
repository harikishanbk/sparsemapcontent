# Sparse Map post OAE Fork.


# What is SparseMap ?

Sparse Map is a content repository supporting shallow wide hierachies of content typical of 
Social Media content systems. It has support for storing that content on sharded databases and 
ColumnDBs with file bodies store on a number of persistence mechanisms.

This project contains the core content server, with driver and a application framework that uses Apache Felix
and some supporting components from Apache Sling. The content store is exposed via WebDav using the Milton
WebDAV implementation, and through restfull services using JAX-RS provided by RESTEasy. There is also support
for Proxying to other applications. Authentication is via OpenID, OAuth or BasicAuth with exposed service APIs 
to allow extension. User sessions are stateless and are managed by a rotaiting cryptographic token to eliminate
the need to store anything on a per user basis.

The application server uses Jetty to manage the HTTP protocol and is intended to have a minimal footprint ( ie < 64MB), 
be 100% concurrent with no points of synchronisation between threads servicing requests and capable of serving both synchronous
and asynchronous requests within the same container.

The core content system contains support for Content, Users, Groups, AccessControl and Locking. Support for other 
aspects related to social media will be added over time.

# Future Features

* Some form of templating library
* Support for Async services (WebSocket etc)
* Asymetric Social graph
* ElasticSearch suport for indexing content
* Single Instance content body store.
* Support for resmon based monitoring.


# Building

## Building artifacts

mvn clean install

This will build and run tests for the drivers.

## Building the application server

mvn -Pbuild-app 

## Running the application server

mvn -Prun

## Running integration tests

A server must be running, the integration tests will not start a server.

mvn -Prun

Then run the integration tests.

mvn -Pintegration test 



